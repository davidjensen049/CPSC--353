/**
*  UDP Server Program
*  Listens on a UDP port
*  Receives a line of input from a UDP client
*  Returns an upper case version of the line to the client
*
*  @author: Rita Sachechelashvili
*  email: sache100@mail.chapman.edu
*  date: /2018
*  @version: 3.0
*/

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class ChatServer {
  public static void main(String[] args) throws Exception {
    DatagramSocket serverSocket = null;
    int port = 0;
    int port1 = 0;
    int port2 = 0;
    String name1 = "";
    String name2 = "";
    InetAddress ipAddress = null;
    InetAddress ipAddress1 = null;
    InetAddress ipAddress2 = null;
    String message = "";
    String response = "100";
    DatagramPacket receivePacket;
    DatagramPacket sendPacket;
    int state = 0;

    try { //attempt connect
      serverSocket = new DatagramSocket(9876);
    } catch (Exception e) {
      System.out.println("Failed to open UDP socket");
      System.exit(0);
    }
    byte[] receiveData = new byte[1024];
    byte[] sendData  = new byte[1024];
    byte[] messageBytes = new byte[1024];
    System.out.println("Server is running...");
    while (state < 3) {
      receiveData = new byte[1024];
      sendData = new byte[1024];
      //while with different cases depending on whether one client is connected or two.
      //If one, server sends a 100 response, if two,
      //both clients get a 200 response and go into chat mode.
      //Once one of them types 'Goodbye', the chat ends.
      switch (state) {
        case 0: //first client connects and receives a 100 response
          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          serverSocket.receive(receivePacket);
          message = new String(receivePacket.getData());
          ipAddress1 = receivePacket.getAddress();
          port1 = receivePacket.getPort();
          sendData = response.getBytes();
          sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress1, port1);
          serverSocket.send(sendPacket);
          state = 1;
          break;
        case 1: // state 1: waits for second client to connect
          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          serverSocket.receive(receivePacket);
          message = new String(receivePacket.getData());
          ipAddress2 = receivePacket.getAddress();
          port2 = receivePacket.getPort();
          response = "200";
          sendData = response.getBytes();
          //replies to both clients
          sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress1, port1);
          serverSocket.send(sendPacket);
          sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress2, port2);
          serverSocket.send(sendPacket);
          state = 2; //transitions to state 2: chat mode
          break;
        case 2: // state 2: Chat mode
          //receives message from one client
          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          serverSocket.receive(receivePacket);
          message = new String(receivePacket.getData());
          //receives message from one client
          // checks for Goodbye message
          if (message.length() >= 7 && message.substring(0,7).equals("Goodbye")) {
            state = 3;
            break;
          }
          //if not a Goodbye message, relays message to the other client
          ipAddress = receivePacket.getAddress();
          port = receivePacket.getPort();
          if ((port == port1) && (ipAddress.equals(ipAddress1))) {
            ipAddress = ipAddress2;
            port = port2;
          } else {
            ipAddress = ipAddress1;
            port = port1;
          }
          messageBytes = message.getBytes();
          sendPacket = new DatagramPacket(messageBytes, sendData.length, ipAddress, port);
          serverSocket.send(sendPacket);
          state = 2;
          break;
        default:
          System.out.println("The connection was not established properly");
          break;
      } //ends switch
    } //ends while
    //sends Goodbye messages to both clients
    //closes the socket
    messageBytes = message.getBytes();
    sendPacket = new DatagramPacket(messageBytes, sendData.length, ipAddress1, port1);
    serverSocket.send(sendPacket);
    sendPacket = new DatagramPacket(messageBytes, sendData.length, ipAddress2, port2);
    serverSocket.send(sendPacket);
  }
}
