
import java.net.*;
import java.io.*;


public class WLFBServerThread extends Thread {

	
  private Socket actionSocket = null;
  private SharedActionState mySharedActionStateObject;
  private String myWLFBServerThreadName;
  private double mySharedVariable;
  public String clientRole;
 
   
  //Setup the thread
  	public WLFBServerThread(Socket actionSocket, String WLFBServerThreadName, SharedActionState SharedObject) {
	
//	  super(ActionServerThreadName);
	  this.actionSocket = actionSocket;
	  mySharedActionStateObject = SharedObject;
	  myWLFBServerThreadName = WLFBServerThreadName;
	}

  public void run() {
    try {
    	
      BufferedReader in = new BufferedReader(new InputStreamReader(actionSocket.getInputStream()));
      PrintWriter out = new PrintWriter(actionSocket.getOutputStream(), true);
      
      String clientRole = in.readLine();  // e.g., "Supplier"
      System.out.println(myWLFBServerThreadName + " connected with client: " + clientRole);
      
      String inputLine, outputLine;
      while ((inputLine = in.readLine()) != null) {
    	  // Get a lock first
    	  try { 
    		  mySharedActionStateObject.acquireLock();  
    		  outputLine = mySharedActionStateObject.processInput(clientRole, inputLine);
    		  out.println(outputLine);
    		  mySharedActionStateObject.releaseLock();  
    	  } 
    	  catch(InterruptedException e) {
    		  System.err.println("Failed to get lock when reading:"+e);
    	  }
      }

       out.close();
       in.close();
       actionSocket.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}