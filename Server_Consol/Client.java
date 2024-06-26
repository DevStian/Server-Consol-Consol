import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static final int SERVER_PORT = 5000;
    private static final int BUFFER_SIZE = 1024;
    private static DatagramSocket udpSocket;
    private static byte[] buffer = new byte[BUFFER_SIZE];
    private static DatagramPacket request;

    public static void main(String[] args) {
        try {
            udpSocket = new DatagramSocket();
            String localClientIP = udpSocket.getLocalAddress().getHostAddress();
            Scanner scanner = new Scanner(System.in);

            // Loop for message exchange
            while (true) {
                // Client writes a message
                System.out.print("Client: ");
                String clientMessage = scanner.nextLine();
                sendMessage(clientMessage);

                // Exit loop if client wants to exit
                if (clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the chat..." + localClientIP);
                    break;
                }

                // Receive and display server's response
                String serverResponse = receiveMessage();
                System.out.println("Server: " + serverResponse);

                // Exit loop if server wants to exit
                if (serverResponse.equalsIgnoreCase("exit")) {
                    System.out.println("The server has closed the connection. Exiting the chat...");
                    break;
                }
            }

            udpSocket.close(); // Close the socket when exiting the loop
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void sendMessage(String message) {
        try {
            byte[] messageBuffer = message.getBytes();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            DatagramPacket question = new DatagramPacket(messageBuffer, messageBuffer.length, serverAddress, SERVER_PORT);
            udpSocket.send(question);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String receiveMessage() {
        try {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(request);
            return new String(request.getData(), 0, request.getLength());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
