# java12-io-socket-client-server-workshop

_Reference_: https://www.amazon.com/Reactive-Programming-RxJava-Asynchronous-Applications/dp/1491931655  
_Reference_: https://www.amazon.com/Netty-Action-Norman-Maurer/dp/1617291471  
_Reference_: https://docs.oracle.com/javase/tutorial/networking/sockets/index.html

* https://stackoverflow.com/questions/28480575/whats-the-difference-between-websocket-and-plain-socket-communication
* https://papweb.wordpress.com/2010/10/30/understanding-tomcat-executor-thread-pooling/

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