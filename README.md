# UDP Chat Application
This project is a simple chat application implemented in Java using the User Datagram Protocol (UDP).

Description:
The chat application consists of two files: Server.java and Client.java. The server listens for client connections and displays incoming messages in its console. The client connects to the server, allowing users to send messages from their console. Communication between server and client happens in real-time through UDP messages.

Running on the Same PC:
Open a terminal and navigate to the project directory.
Compile the files using javac Server.java Client.java.
Start the server by running java Server.
Open another terminal window and navigate to the project directory.
Start the client by running java Client.
Now you can send and receive messages between the server and client on the same PC.
Running on Different Devices:
Ensure both devices are connected to the same local network.
Find the local IP address of the device running the server (e.g., using ipconfig on Windows or ifconfig on Linux/Mac).
Update the Client.java file with the server's IP address.
Compile the files on both devices using javac Server.java Client.java.
Start the server on one device by running java Server.
Start the client on the other device by running java Client.
Now you can send and receive messages between the devices.
Note:
Make sure the firewall allows UDP traffic on the specified port (default port is 12345).
To change the port, modify the SERVER_PORT variable in both Server.java and Client.java.
