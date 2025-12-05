/*
Client program for the supplier. Connects to the server, sends commands to add stock or check inventory, and receives responses. Demonstrates a simplee client design where networking is separate from shared-state management.
*/


import java.io.*;
import java.net.*;

public class Supplier {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket ActionClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int ActionSocketNumber = 5000;
        String ActionServerName = "localhost";
        String ActionClientID = "Supplier";
     

        try {
            ActionClientSocket = new Socket(ActionServerName, ActionSocketNumber);
            out = new PrintWriter(ActionClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(ActionClientSocket.getInputStream()));
            
            out.println(ActionClientID);
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ ActionSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + ActionClientID + " client and IO connections");
        
        // This is modified as it's the client that speaks first

        while(true) {
        	System.out.println("\n Enter command: "); 
        	fromUser = stdIn.readLine();
        	int quantity = 1;//define default quantity unless user specifies
        	
        	if (fromUser.equalsIgnoreCase("Add_oranges")||
        		fromUser.equalsIgnoreCase("Add_apples")) {
        		
        		System.out.println("Enter quantity: ");
        		String qtyStr = stdIn.readLine();
        		try {
        			quantity = Integer.parseInt(qtyStr);
        			
        		}catch(NumberFormatException e) {
        			System.out.println("Invalid number, defaulting to 1 ");
        			quantity = 1;
        		}
        		// append quantity 
        		fromUser = fromUser + "(" + quantity + ")";
        	}
        	// send command to server
        	out.println(fromUser);
        	System.out.println(ActionClientID + " sending: " + fromUser);
        	
        	fromServer = in.readLine();
        	System.out.println(ActionClientID + " received: " + fromServer);
        	
        	}
        
   
        //  while (true) {
        
        /*    fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println(ActionClientID + " sending " + fromUser + " to ActionServer");
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println(ActionClientID + " received " + fromServer + " from ActionServer");
        //}

         */   
        
       // Tidy up - not really needed due to true condition in while loop
      //  out.close();
       // in.close();
       // stdIn.close();
       // ActionClientSocket.close();
    }
}

