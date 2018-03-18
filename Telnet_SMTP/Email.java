/**
*  TCP Client Program
*  Connects to a TCP Server
*  Receives a line of input from the keyboard and sends it to the server
*  Receives a response from the server and displays it.
*
* this code is going to connect to SMTP via port 25
*
*  @author: David Jensen
*  Email:  jense178@mail.chapman.edu
*  Date:  3/5/2018
*  @  version: 3.0
*/

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

//declairing variables, setting them to empty strings
class Email {
  public static void main(String[] argv) throws Exception {
    String sentence;
    String subject;
    int numLines = 0;
    String[] email = new String[20];
    Socket clientSocket = null;
    String fromAddress = "";
    String toAddress = "";
    String line = "";

    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));


    while (true) {
      System.out.print("from: "); //this is going to take in the variable for who its from
      fromAddress = inFromUser.readLine().toString();

      if (fromAddress.isEmpty()) { //if the user doesnt enter anything, promp a response
        System.out.println("\n error: sorry, but this cant be left blank");
      } else {

        break;

      }
    }

    while (true) {
      System.out.print("TO: "); //who you want the message to be from
      toAddress = inFromUser.readLine().toString();

      if (toAddress.isEmpty()) { //cant leave the space blank
        System.out.println("\n error: sorry, but this cant be left blank");
      } else {
        break;
      }
    }

    while (true) { //what do you want the subject to be
      System.out.println("Subject: ");
      subject = inFromUser.readLine().toString();

      if (subject.isEmpty()) { //cant be null
        System.out.println("\n error: cant be left blank, sorry");
      } else {
        break;
      }
    }


    System.out.println("Please type the body of your message.");
    System.out.println("\nto finish the message type a '.' on a line by its self.");



    while (true) { //this is going to be invoked after the message has been terminated
      line = inFromUser.readLine().toString();
      if (line.length() == 1 && line.substring(0,1).equals(".")) {
        System.out.println("**** message being transmitted ****");
        break;
      }

      email[numLines] = line;
      ++numLines;


      if (numLines == 20) { // if the max number of instances has been reached
        System.out.println(" sorry, maximum number of lines is reached");
        break;
      }
    }
    System.out.println("Now opening smtp socket");
    try {
      clientSocket = new Socket("smtp.chapman.edu", 25); //connect to smpt and port 25
    } catch (Exception e) {
      System.out.println("Failed to open socket connection");
      System.exit(0);
    }




    String modifiedSentence; // write to new client socket
    PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(),true);
    BufferedReader inFromServer =  new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));




    modifiedSentence = inFromServer.readLine(); // write the sentence to the from server
    System.out.println("FROM SERVER: " + modifiedSentence);



    outToServer.println("HELO llb16.chapman.edu"); //connect to smtp command
    modifiedSentence = inFromServer.readLine();
    System.out.println("FROM SERVER: " + modifiedSentence);

    outToServer.println("MAIL FROM: " + fromAddress); //connecting to mail from
    modifiedSentence = inFromServer.readLine();
    System.out.println("FROM SERVER: " + modifiedSentence);

    outToServer.println("RCPT TO: " + toAddress); //who to
    modifiedSentence = inFromServer.readLine();
    System.out.println("FROM SERVER " + modifiedSentence);

    outToServer.println("DATA"); //data to follow
    modifiedSentence = inFromServer.readLine();
    System.out.println("FROM SERVER: " + modifiedSentence);


    outToServer.println("From: " + fromAddress); //input from info
    outToServer.println("To: " + toAddress); //input to info
    outToServer.println("Subject: " + subject);// input subject info

    for (int i = 0; i < numLines; ++i) {
      outToServer.println(email[i]);
    }

    outToServer.println("."); //terminate emil to send

    modifiedSentence = inFromServer.readLine();
    System.out.println(modifiedSentence);

    outToServer.println("QUIT");
//quit program



    clientSocket.close();

  }
}
