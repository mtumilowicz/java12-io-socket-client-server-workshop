[![Build Status](https://travis-ci.com/mtumilowicz/java12-io-socket-client-server-workshop.svg?branch=master)](https://travis-ci.com/mtumilowicz/java12-io-socket-client-server-workshop)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

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
* in the workshop we will try to fix failing tests from `test/workshop` package by following steps and hints in
`java/workshop` package
* answers: `java/answers` package

# theory in a nutshell
* `java.io`, `java.net` can be used for implementing low-level network communication, for example: client-server 
application
* it uses TCP (communication that occurs between the client and the server must be reliable - no data can be 
dropped and it must arrive on the client side in the same order in which the server sent it)
* client and server bind a socket to its end of the connection
* to communicate, the client and the server each reads from and writes to the socket bound to the connection
* server just waits, listening to the socket for a client to make a connection request
* webSockets and regular sockets are not the same thing
    * a webSocket runs over a regular socket
    * webSocket connections start with an HTTP request from client to server
        * it is critically important because if you can reach the web server for normal web communication, 
        then you can reach it for a webSocket request without any networking infrastructure anywhere between 
        client and server having to open new holes in the firewall or open new ports or anything like that
    
## concerning threads
* on 64 bit JVM 1.8 thread consumes 1,024 KB of RAM by default (-Xss flag)
    * 1000 concurrent connections mean 1,000 threads and about 1 GB of stack space
    * stack space is independent from heap space - application will consume far more than this a gigabyte
    * threads are relatively expensive in terms of memory use
* a thread pool has many advantages over simply creating threads on demand
    * thread is already initialized and started, therefore you do not have to wait or
    warm up, reducing client latency
    * we put a limit on the total number of threads running in our system so
    that we can safely reject connections under peak load rather than crashing
    * a thread pool has a configurable queue that can temporarily hold short peaks of
    load
    * if both the pool and queue are saturated, there is also a configurable rejection
    policy (error, running in client thread instead, etc.)
* servlet 3.0 specification made it possible to write asynchronous servlets
    * decouples the processing request from the container thread
    * most of the processing has nothing to do with the servlet request or response
    * whenever the application wants to send the response, it can do it from any
    thread at any point in time
    * the thread that picked up the request might be already gone or it might be 
    handling some other request
    * just shift the problem of thread explosion into a different place
    * application could begin responding slowly due to frequent garbage collection cycles and
    context switching
* there are two common scenarios in which a thread associated with a request can be sitting idle
    * the thread needs to wait for a resource to become available or process data before building the 
    response, for example an application may need to query a database or access data from a remote web 
    service before generating the response
    * the thread needs to wait for an event before generating the response, for example an application 
    may have to wait for a JMS message, new information from another client, or new data available in a 
    queue before generating the response
    * asynchronous processing refers to assigning these blocking operations to a new thread and returning 
    the thread associated with the request immediately to the container
    
## http 1.1 and persistent connections
* in HTTP 1.0, a connection between a Web client and server is closed after a single request/response cycle
* in HTTP 1.1, a connection is kept alive and reused for multiple requests
* persistent connections reduce communication lag perceptibly, because the client doesn't need to
 renegotiate the TCP connection after each request
* thread per HTTP connection, which is based on HTTP 1.1's persistent connections, is a common solution vendors 
have adopted
    * each HTTP connection between client and server is associated with one thread on the server side
    * threads are allocated from a server-managed thread pool and once a connection is closed, the dedicated thread is 
    recycled back to the pool and is ready to serve other tasks

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
* `Step9_ThreadPoolServerAnswer`
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
