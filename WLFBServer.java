/*
Main server class. Listens for TCP client connections and assigns a designated WLFBServerThread for each client. Passes a shared SharedActionState object to all threads to manage the warehouse state safely
*/

import java.net.*;
import java.io.*;




public class WLFBServer {
  public static void main(String[] args) throws IOException {

	ServerSocket ActionServerSocket = null;
    boolean listening = true;
    String ActionServerName = "WLFBServer";
    int ActionServerNumber = 5000;
    
     int startApples = 1000;
     int startOranges = 1000;

    //Create the shared object in the global scope...
    
    SharedActionState ourSharedActionStateObject = new SharedActionState( startApples, startOranges);
        
    // Make the server socket

    try {
      ActionServerSocket = new ServerSocket(ActionServerNumber);
    } catch (IOException e) {
      System.err.println("Could not start " + ActionServerName + " specified port.");
      System.exit(-1);
    }
    System.out.println(ActionServerName + " started");

    //Got to do this in the correct order with only four clients!  Can automate this...
    
    while (listening){
      new WLFBServerThread(ActionServerSocket.accept(),"WLFBThread1",ourSharedActionStateObject).start();
      new WLFBServerThread(ActionServerSocket.accept(),"WLFBThread2", ourSharedActionStateObject).start();
      new WLFBServerThread(ActionServerSocket.accept(),"WLFBThread3", ourSharedActionStateObject).start();
      System.out.println("New " + ActionServerName + " thread started.");
    }
    ActionServerSocket.close();
  }

}
