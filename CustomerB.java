import java.io.*;
import java.net.*;

public class CustomerB {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket ActionClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int ActionSocketNumber = 5000;
        String ActionServerName = "localhost";
        String ActionClientID = "CustomerB";

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
        	
        	if (fromUser.equalsIgnoreCase("Buy_apples")|| 
        		fromUser.equalsIgnoreCase("Buy_oranges")) {
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
            
        
       // Tidy up - not really needed due to true condition in while loop
      //  out.close();
       // in.close();
       // stdIn.close();
       // ActionClientSocket.close();
    }
}
