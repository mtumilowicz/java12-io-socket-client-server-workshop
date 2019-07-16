[![Build Status](https://travis-ci.com/mtumilowicz/java12-io-socket-client-server-workshop.svg?branch=master)](https://travis-ci.com/mtumilowicz/java12-io-socket-client-server-workshop)

# java12-io-socket-client-server-workshop

_Reference_: https://www.amazon.com/Reactive-Programming-RxJava-Asynchronous-Applications/dp/1491931655  
_Reference_: https://www.amazon.com/Netty-Action-Norman-Maurer/dp/1617291471  
_Reference_: https://docs.oracle.com/javase/tutorial/networking/sockets/index.html  
_Reference_: https://stackoverflow.com/questions/28480575/whats-the-difference-between-websocket-and-plain-socket-communication  
_Reference_: https://www.javaworld.com/article/2077995/java-concurrency-asynchronous-processing-support-in-servlet-3-0.html  
_Reference_: https://docs.oracle.com/javaee/7/tutorial/servlets012.htm  
_Reference_: https://www.hackerearth.com/practice/notes/asynchronous-servlets-in-java/  
_Reference_: https://developerlife.com/2011/04/13/creating-asynchronous-servlets-with-tomcat-7-servlet-3-0-api/

# project description
* the main goal of this project is to show how to implement blocking server using `java.io` and `java.net`:
    * single threaded server
    * thread per connection server
    * thread pool server
* on the workshop we will try to fix failing tests from `test/workshop` package by following steps and hints in
`java/workshop` package
* answers: `java/answers` package

# theory in a nutshell
* `java.io`, `java.net` can be used for implementing low-level network communication, for example: client-server 
application
* it uses TCP (communication that occurs between the client and the server must be reliable - no data can be 
dropped and it must arrive on the client side in the same order in which the server sent it)
* client and server bind a socket to its end of the connection
* to communicate, the client and the server each reads from and writes to the socket bound to the connection
* server runs on a specific computer and has a socket that is bound to a specific port number
* server just waits, listening to the socket for a client to make a connection request
* an endpoint is a combination of an IP address and a port number
    * every TCP connection can be uniquely identified by its two endpoints
    * that way you can have multiple connections between your host and the server
* client-side
    * client knows the hostname of the machine on which the server is running and the port number on which the 
    server is listening
    * the client also needs to identify itself to the server so it binds to a local port number that it will use 
    during this connection (this is usually assigned by the system)
    ```
    Accepted connection from Socket[addr=/127.0.0.1,port=52453,localport=1]
    Accepted connection from Socket[addr=/127.0.0.1,port=52454,localport=1]
    ```
    * if the connection is accepted, a socket is successfully created and the client can use the socket to 
    communicate with the server
* server-side
    * if everything goes well, the server accepts the connection
    * the server gets a new socket bound to the same local port and also has its remote endpoint set to the address 
    and port of the client
    * it needs a new socket so that it can continue to listen to the original socket for connection requests while 
    tending to the needs of the connected client
* webSockets and regular sockets are not the same thing
    * a webSocket runs over a regular socket
    * the biggest difference is that ALL webSocket connections start with an HTTP request from client to server
    * the fact that a webSocket connection starts with an HTTP connection is critically important because if you can 
    reach the web server for normal web communication, then you can reach it for a webSocket request without any 
    networking infrastructure anywhere between client and server having to open new holes in the firewall or open new 
    ports or anything like that
    
## concerning threads
* on 64 bit JVM 1.8 thread consumes 1,024 KB of RAM by default (-Xss flag)
    * 1000 concurrent connections mean 1,000 threads and about 1 GB of stack space
    * stack space is independent from heap space - application will consume far more than this a gigabyte
* a thread pool has many advantages over simply creating threads on demand:
    * thread is already initialized and started, therefore you do not have to wait or
    warm up, reducing client latency
    * we put a limit on the total number of threads running in our system so
    that we can safely reject connections under peak load rather than crashing
    * a thread pool has a configurable queue that can temporarily hold short peaks of
    load
    * if both the pool and queue are saturated, there is also a configurable rejection
    policy (error, running in client thread instead, etc.)
* servlet 3.0 specification made it possible to write asynchronous servlets
    * the idea is to decouple the processing request from the container
    thread
    * whenever the application wants to send the response, it can do it from any
    thread at any point in time
    * the original container thread that picked up the request might be already gone or it might be 
    handling some other request
    * we just shifted the problem of thread explosion into a different place
    * application could begin responding slowly due to frequent garbage collection cycles and
    context switching
    * there are two common scenarios in which a thread associated with a request can be sitting idle
    * the thread needs to wait for a resource to become available or process data before building the 
    response - for example - an application may need to query a database or access data from a remote web 
    service before generating the response
    * the thread needs to wait for an event before generating the response - for example - an application 
    may have to wait for a JMS message, new information from another client, or new data available in a 
    queue before generating the response.
    * asynchronous processing refers to assigning these blocking operations to a new thread and retuning 
    the thread associated with the request immediately to the container
    * although most of the processing has nothing to do with the servlet request or response
    * this can leads to Thread Starvation – since our servlet thread is blocked until all the processing is done, 
    if server gets a lot of requests to process, it will hit the maximum servlet thread limit and further requests 
    will get Connection Refused errors
* example:
    * tomcat: 100 thread for processing requests + 100 container threads (business logic)
    * netty: 4 threads in event loop + 100 container threads (business logic)
    
## http 1.1 and persistent connections
* in HTTP 1.0, a connection between a Web client and server is closed after a single request/response cycle
* in HTTP 1.1, a connection is kept alive and reused for multiple requests
* persistent connections reduce communication lag perceptibly, because the client doesn't need to
 renegotiate the TCP connection after each request
* thread per HTTP connection, which is based on HTTP 1.1's persistent connections, is a common solution vendors 
have adopted
* under this strategy, each HTTP connection between client and server is associated with one thread on the server side
* threads are allocated from a server-managed thread pool. Once a connection is closed, the dedicated thread is 
    recycled back to the pool and is ready to serve other tasks
* depending on the hardware configuration, this approach can scale to a high number of concurrent connections
* threads are relatively expensive in terms of memory use
* servers configured with a fixed number of threads can suffer the thread starvation problem, whereby requests 
    from new clients are rejected once all the threads in the pool are taken
* for many Web sites, users request pages from the server only sporadically - this is known as a page-by-page model; 
the connection threads are idling most of the time, which is a waste of resources
* thanks to the non-blocking I/O capability introduced in Java 4's New I/O APIs for the Java Platform (NIO) package, 
a persistent HTTP connection doesn't require that a thread be constantly attached to it
* threads can be allocated to connections only when requests are being processed
* when a connection is idle between requests, the thread can be recycled, and the connection is placed in a 
centralized NIO select set to detect new requests without consuming a separate thread
* this model, called **thread per request**, potentially allows Web servers to handle a growing number of user 
connections with a fixed number of threads
* users of Ajax applications interact with the Web server much more frequently than in the page-by-page model
* unlike ordinary user requests, Ajax requests can be sent frequently by one client to the server
    * in addition, both the client and scripts running on the client side can poll the Web server regularly for updates
* more simultaneous requests cause more threads to be consumed, which cancels out the benefit of the thread-per-request 
    approach to a high degree
* some slow-running back-end routines worsen the situation - for example - a request could be blocked by a 
depleted JDBC connection pool, or a low-throughput Web service endpoint
    * until the resource becomes available, the thread could be stuck with the pending request for a long time, 
    it would be better to place the request in a centralized queue waiting for available resources and recycle 
    that thread
* this effectively throttles the number of request threads to match the capacity of the slow-running back-end routines
* it also suggests that at a certain point during request processing (when the request is stored in the queue), 
no threads are consumed for the request at all

## asynchronous support in Servlet 3.0
* asynchronous support in Servlet 3.0 is designed to achieve this scenario through a universal and portable approach
* in short, an asynchronous servlet enables an application to process incoming requests in an asynchronous fashion: 
    a given HTTP worker thread handles an incoming request and then passes the request to another background 
    thread which in turn will be responsible for processing the request and send the response back to the client 
    * The initial HTTP worker thread will return to the HTTP thread pool as soon as it passes the request to the 
    background thread, so it becomes available to process another request
* this approach by itself may solve the problem of HTTP thread pool exhaustion, but will not solve the problem of 
system resources consumption
* after all, another background thread was created for processing the request, so the number of simultaneous active 
threads will not decrease and the system resource consumption will not be improved
* example: container's HTTP thread pool size to 2
    * thread per connection: as one may expect, as soon as we fire a considerable number of requests against the servlet endpoint we will 
    leave the container completely unresponsive
    * asynchronous: the result is that this time our server will not become unresponsive when we fire a considerable 
    number of requests against the servlet endpoint
        * the 2 existing HTTP worker threads will handle the requests and spawn a background thread that will 
        continue to process the requests
        * the HTTP threads will immediately return to the HTTP thread pool and handle the next request in the 
        same asynchronous fashion
        * we have solved the problem of the HTTP thread pool exhaustion, but the number of required threads to handle 
        the requests has not improved: we are just spawning background threads to handle the requests
        * in terms of simultaneous running thread count this should be equivalent to simply increase the HTTP thread 
        pool size: under heavy load the system will not scale

# conclusions in a nutshell
* `Socket` - client side of the connection
* `ServerSocket` - server side of the connection
* closing `Socket` will close its streams
    ```
    * <p> Closing this socket will also close the socket's
    * {@link java.io.InputStream InputStream} and
    * {@link java.io.OutputStream OutputStream}.
    public synchronized void close() throws IOException
    ```
* closing `ServerSocket` will not close its streams
* `ServerSocket` is a `java.net` class that provides a system-independent implementation of the server side of a 
client/server socket connection
    * useful constructors
        ```
        public ServerSocket(int port) throws IOException {
            this(port, 50, null);
        }
        ```
        ```
        public ServerSocket(int port, int backlog) throws IOException {
            this(port, backlog, null);
        }
        ```
        * backlog (the default is 50) requested maximum length of the queue of incoming connections
            * note that HTTP/1.1 uses persistent connections by default and until the client
          disconnects we keep the TCP/IP connection open - blocking any new clients
    * `serverSocket.accept()`
      * waits until a client starts up and requests a connection on the host and port of this server
      * returns a new `Socket` which is bound to the same local port and has its remote address 
      and remote port set to that of the client when a connection is successfully established 
* `Step9_ThreadPoolServerAnswer` - uses a thread per connection, but threads are recycled when a client 
disconnects (no thread warm up for every client)
    * tomcat: The standard executor internally uses a `java.util.concurrent.ThreadPoolExecutor`
        * http://tomcat.apache.org/tomcat-9.0-doc/config/executor.html
* this is pretty much how servlet containers like Tomcat work, managing 200 threads in a pool
  by default
  * note that Tomcat has the so-called NIO connector that handles some of the operations
    on sockets asynchronously, but the real work in servlets and frameworks built
    on top of them is still blocking
  * this means that traditional applications are inherently
    limited to a couple thousand connections, even built on top of modern servlet
    containers