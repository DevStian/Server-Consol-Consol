import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 1024;
    private static DatagramSocket udpSocket;
    private static byte[] buffer = new byte[BUFFER_SIZE];
    private static DatagramPacket request;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("UDP Server Started");
            udpSocket = new DatagramSocket(PORT);
            String localServerIP = udpSocket.getLocalAddress().getHostAddress();

            while (true) {
                String clientMessage = receiveMessage();
                System.out.println("Client: " + clientMessage);

                // Notify if Client left the chat
                if (clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("The client has closed the connection. Exiting the chat...");
                    sendMessage("exit"); // Notify the client that it's closing the connection
                }

                // Server writes a message
                System.out.print("Server: ");
                String serverMessage = scanner.nextLine();
                sendMessage(serverMessage);

                // Exit loop if the server wants to exit
                if (serverMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the chat..." + localServerIP);
                    break;
                }
            }

            udpSocket.close(); // Close the socket when exiting the loop
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String receiveMessage() {
        String receivedMessage = "";
        try {
            request = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(request);

            receivedMessage = new String(request.getData(), 0, request.getLength());
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return receivedMessage;
    }

    private static void sendMessage(String message) {
        try {
            byte[] messageBytes = message.getBytes();

            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();
	    
            DatagramPacket response = new DatagramPacket(messageBytes, messageBytes.length, clientAddress, clientPort);
            udpSocket.send(response);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
