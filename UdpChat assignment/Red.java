

/**
*  UDP Server Program
*  Listens on a UDP port
*  Receives a line of input from a UDP client
*  Returns an upper case version of the line to the client
*
*  @author: David Jensen
*  email: jense178@mail.chapman.edu
*  date: /2018
*  @version: 3.0
*/

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.DatagramPacket;

import java.net.DatagramSocket;

import java.net.InetAddress; //importing librarys

class Red { //Red
  public static void main(String[] args) throws Exception {

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    DatagramSocket clientSocket = new DatagramSocket();

    InetAddress ipAddress = InetAddress.getByName("10.49.139.187"); //connect to the server IP

    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];

    int state = 0;
    String response = "";

    String message = "Hello Red"; //set message to be sent

    DatagramPacket sendPacket = null;
    DatagramPacket receivePacket = null;


    while (state < 3) { //3 possible states to be iterated through
      receiveData = new byte[1024];
      sendData = new byte[1024];


      switch (state) {
        case 0:
          System.out.println("Now sending message to the server:");

          sendData = message.getBytes(); //send the message to bytes

          sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 9876);
          clientSocket.send(sendPacket);

          System.out.println("The message has been sent - Please wait while we wait.");

          //receiving the packet from the server with 100/200 message

          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          clientSocket.receive(receivePacket);

          response = new String(receivePacket.getData()); //push the response to string

          System.out.println("The server has responded:");


          if (response.substring(0,3).equals("100")) {
            state = 1; //You are first client. wait for second client to connect
          } else if (response.substring(0,3).equals("200")) {
            state = 2; //you are second client. Wait for message from first client
          }

          break;


        case 1:
          //receive the data, through the client socket
          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          clientSocket.receive(receivePacket);
          response = new String(receivePacket.getData());

          System.out.println("New message from the Server: " + response);

          message = inFromUser.readLine(); //read message

          sendData = message.getBytes(); //push the message to bytes
          sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 9876);

          clientSocket.send(sendPacket); //send the message through socket

          state = 2;
          break;

        case 2:

          receivePacket = new DatagramPacket(receiveData, receiveData.length);
          clientSocket.receive(receivePacket);
          response = new String(receivePacket.getData()); //take the response and push to string

          System.out.println("New message from Blue User: " + response);

          if (response.length() >= 7 && response.substring(0,7).equals("Goodbye")) {
            state = 3;
            break;
          } else {
            message = inFromUser.readLine(); //if the message is goodbye break, else keep reading
            sendData = message.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 9876);
            clientSocket.send(sendPacket); //send packet to bytes and through socket
          }
          break;
        default: //default to handle exception
          System.out.println("Connection error:");
          break;
      }
    }
    clientSocket.close(); //close the socket, conversation complete
  }
}
