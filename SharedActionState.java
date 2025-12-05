/*
Manages the warehouse inventory of apples and oranges. Provides synchronized methods to safely update and read stock, validates inputs, and prevents race conditions in a concurrent environment.
*/


import java.net.*;
import java.io.*;

public class SharedActionState{
	
	private SharedActionState mySharedObj;
	private String myThreadName;
	
// Shared warehouse variables
	private int check_stock;
    private int apples;
    private int oranges;

	
	private boolean accessing=false; // true a thread has a lock, false otherwise
	private int threadsWaiting=0; // number of waiting writers

// Constructor	
	
	  public SharedActionState(int startApples, int startOranges) {
	        apples = startApples;
	        oranges = startOranges;
	    }

//Attempt to aquire a lock
	
	  public synchronized void acquireLock() throws InterruptedException{
	        Thread me = Thread.currentThread(); // get a ref to the current thread
	        System.out.println(me.getName()+" is attempting to acquire a lock!");	
	        ++threadsWaiting;
		    while (accessing) {  // while someone else is accessing or threadsWaiting > 0
		      System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
		      //wait for the lock to be released - see releaseLock() below
		      wait();
		    }
		    // nobody has got a lock so get one
		    --threadsWaiting;
		    accessing = true;
		    System.out.println(me.getName()+" got a lock!"); 
		  }

		  // Releases a lock to when a thread is finished
		  
		  public synchronized void releaseLock() {
			  //release the lock and tell everyone
		      accessing = false;
		      notifyAll();
		      Thread me = Thread.currentThread(); // get a ref to the current thread
		      System.out.println(me.getName()+" released a lock!");
		  }
	
	
    /* The processInput method */

	public synchronized String processInput(String myThreadName, String theInput) {
    		System.out.println(myThreadName + " received "+ theInput);
    		String theOutput = null;
    		// Check what the client said
    		
    		int num = 0;
     	   if (theInput.contains("(") && theInput.contains(")")) {
     	        try {
     	            num = Integer.parseInt(
     	                theInput.substring(theInput.indexOf("(") + 1, theInput.indexOf(")"))
     	            );
     	        } catch (NumberFormatException e) {
     	            return "Invalid number format";
     	        }
     	    }
     	   
    		
    		//BUY ORANGES 
    		if (theInput.toLowerCase().startsWith("buy_oranges(")) {
    		
    			if (myThreadName.equals("CustomerA") || myThreadName.equals("CustomerB")) {
    				 if (num > oranges) {
    			            return "Not enough oranges in stock. Current stock: " + oranges;
    			        }
    				 if (num <= 0) {
    		     		    return "Invalid amount. Please enter a positive number.";
    		     		}
    		    		
    				oranges-= num;
   				
    				theOutput = "success you have bought your orange(s)";
    			}  else {
    				
    				theOutput = "supplier cannot buy oranges";
    			
    			}
    		}
    		
    		
    			//BUY APPLES
    		else if (theInput.toLowerCase().startsWith("buy_apples(")) {
    			
        			if (myThreadName.equals("CustomerA") || myThreadName.equals("CustomerB")) {
        				 if (num > apples) {
        			            return "Not enough apples in stock. Current stock: " + apples;
        			        }
        				 if (num <= 0) {
        		     		    return "Invalid amount. Please enter a positive number.";
        		     		}
        		    		
        				apples-=num;
       
        				theOutput = "success you have your apple(s)";
        			}  else if (myThreadName.equals("Supplier")){
        			
        				theOutput = "supplier cannot buy apples";
        			}
        		}
        			//ADD ORANGES
    		else if (theInput.toLowerCase().startsWith("add_oranges(")) {
    			
            			if (myThreadName.equals("Supplier")) {
            				if (num <= 0) {
            	     		    return "Invalid amount. Please enter a positive number.";
            	     		}
            	    		
            				oranges+=num;
            				
           				
            				theOutput = "success you have added orange(s)";
            			}  else if (myThreadName.equals("CustomerA") || myThreadName.equals("CustomerB")){
            			
            				theOutput = "Customers cannot add oranges";
            			}
            		}
            			//ADD APPLES
    		else if (theInput.toLowerCase().startsWith("add_apples(")) {
    			
                			if (myThreadName.equals("Supplier")) {
                				if (num <= 0) {
                	     		    return "Invalid amount. Please enter a positive number.";
                	     		}
                	    		
                				apples+=num;
                				
               	
                				theOutput = "success you have added apple(s)";
                			}  else if (myThreadName.equals("CustomerA") || myThreadName.equals("CustomerB")){
                			
                				theOutput = "Customers cannot add apples";
                			}
                		}
                			//CHECK STOCK
    		else if (theInput.equalsIgnoreCase("check_stock")) {
                    			//Correct request
                    			if (myThreadName.equals("Supplier") || myThreadName.equals("CustomerA") || myThreadName.equals("CustomerB")) {
             
                   				
                    				theOutput = "Number of apples: " + apples + " , Number of oranges: " + oranges;
                    				
                    			}  
                    			

    		}else {
            			theOutput = myThreadName + " received incorrect request";
                			}
                	//Return the output message to the ActionServer
            		System.out.println(theOutput);
            		return theOutput;
            		}
            	
            }


