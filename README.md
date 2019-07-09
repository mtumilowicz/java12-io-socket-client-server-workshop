# java12-io-socket-client-server-workshop

_Reference_: https://www.amazon.com/Reactive-Programming-RxJava-Asynchronous-Applications/dp/1491931655  
_Reference_: https://www.amazon.com/Netty-Action-Norman-Maurer/dp/1617291471  
_Reference_: https://docs.oracle.com/javase/tutorial/networking/sockets/index.html
_Reference_: https://stackoverflow.com/questions/28480575/whats-the-difference-between-websocket-and-plain-socket-communication
* https://developerlife.com/2011/04/13/creating-asynchronous-servlets-with-tomcat-7-servlet-3-0-api/

* low-level network communication
* client-server application
* TCP (communication that occurs between the client and the server must be reliable - no data can be dropped and it 
must arrive on the client side in the same order in which the server sent it)
* TCP provides a reliable, point-to-point communication channel
* client and server bind a socket to its end of the connection
* to communicate, the client and the server each reads from and writes to the socket bound to the connection
* socket is one end-point of a two-way communication link between two programs running on the network
* server runs on a specific computer and has a socket that is bound to a specific port number
* server just waits, listening to the socket for a client to make a connection request
* An endpoint is a combination of an IP address and a port number. 
    * Every TCP connection can be uniquely identified by its two endpoints. 
    * That way you can have multiple connections between your host and the server.
* Socket - client side of the connection
* ServerSocket - server side of the connection
* client-side
    * client knows the hostname of the machine on which the server is running and the port number on which the 
    server is listening
    * the client also needs to identify itself to the server so it binds to a local port number that it will use 
    during this connection (This is usually assigned by the system)
    * if the connection is accepted, a socket is successfully created and the client can use the socket to communicate 
    with the server
* server-side
    * if everything goes well, the server accepts the connection
    * the server gets a new socket bound to the same local port and also has its remote endpoint set to the address 
    and port of the client
    * it needs a new socket so that it can continue to listen to the original socket for connection requests while 
    tending to the needs of the connected client
* public synchronized void close() throws IOException
    ```
    * <p> Closing this socket will also close the socket's
    * {@link java.io.InputStream InputStream} and
    * {@link java.io.OutputStream OutputStream}.
    ```
* ServerSocket is a java.net class that provides a system-independent implementation of the server side of a 
client/server socket connection
* clientSocket = serverSocket.accept();
  * The accept method waits until a client starts up and requests a connection on the host and port of this server
  *  When a connection is requested and successfully established, the accept method returns a new Socket object which 
  is bound to the same local port and has its remote address and remote port set to that of the client
* webSockets and regular sockets are not the same thing
* a webSocket runs over a regular socket
* The biggest difference right away is that ALL webSocket connections start with an HTTP request from client to server
* The fact that a webSocket connection starts with an HTTP connection is critically important because if you can 
reach the web server for normal web communication, then you can reach it for a webSocket request without any 
networking infrastructure anywhere between client and server having to open new holes in the firewall or open new 
ports or anything like that

* This value (the
  default is 50) caps the maximum number of pending connections that can wait in a
  queue. Above that number, they are rejected. To make matters worse, we pretend to
  implement HTTP/1.1 which uses persistent connections by default. Until the client
  disconnects we keep the TCP/IP connection open just in case, blocking new clients.
* also uses a thread per
  connection, but threads are recycled when a client disconnects so that we do not pay
  the price of thread warm up for every client
  * This is pretty much how all popular
    servlet containers like Tomcat and Jetty work, managing 100 to 200 threads in a pool
    by default. Tomcat has the so-called NIO connector that handles some of the operations
    on sockets asynchronously, but the real work in servlets and frameworks built
    on top of them is still blocking.
  * This means that traditional applications are inherently
    limited to a couple thousand connections, even built on top of modern servlet
    containers.
    
* On 64 bit JVM 1.8, each
  thread consumes 1,024 KB of RAM by default (see -Xss flag). A thousand concurrent
  connections, even idle, mean 1,000 threads and about 1 GB of stack space. Now, do
  not be confused, stack space is independent from heap space, so your application will
  consume far more than this a gigabyte
  
* A thread pool has many advantages over simply creating
  threads on demand:
  * Thread is already initialized and started, therefore you do not have to wait or
  warm up, reducing client latency.
  * We put a sharp limit on the total number of threads running in our system so
  that we can safely reject connections under peak load rather than crashing.
  * A thread pool has a configurable queue that can temporarily hold short peaks of
  load.
  * If both the pool and queue are saturated, there is also a configurable rejection
  policy (error, running in client thread instead, etc.).
  
* Servlet 3.0 specification made it possible to write scalable applications on top of asynchronous
  servlets. The idea is to decouple the processing request from the container
  thread. Whenever the application wants to send the response, it can do it from any
  thread at any point in time. The original container thread that picked up the request
  might be already gone or it might be handling some other request. This is a revolutionary
  idea; however, the rest of the application must be built this way. Otherwise,
  the application is more responsive (the container thread pool is almost never saturated),
  but if there is another user thread that must process that request, we just shifted
  the problem of thread explosion into a different place. When the number of threads
  reaches several hundred or a few thousand, the application begins to misbehave; for
  example, it begins responding slowly due to frequent garbage collection cycles and
  context switching.
  
* tomcat: The standard executor internally uses a java.util.concurrent.ThreadPoolExecutor
    * http://tomcat.apache.org/tomcat-9.0-doc/config/executor.html