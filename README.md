# CS3004-Network-Computing-2025---The-WLFB-Warehouse-Application
A multi-threaded client-server warehouse application demonstrating networked concurrency, shared-state management, and simple protocol design in Java. The system includes a server that manages the inventory of apples and oranges and three clients, CustomerA, CustomerB, and Supplier.
## How to Run
1. Compile all Java files:
2. Start the server first:
3. Start each client in separate terminals:

## Class Overview
- **WLFBServer.java:** Main server, listens for connections and starts a thread for each client.  
- **WLFBServerThread.java:** Handles communication with a single client, interacts with shared warehouse state.  
- **SharedActionState.java:** Stores inventory, synchronizes access, enforces role permissions and business rules.  
- **Supplier.java:** Client that can add stock and check inventory.  
- **CustomerA.java / CustomerB.java:** Clients that can buy items and check inventory.
