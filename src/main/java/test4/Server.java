package test4;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{

    private final ServerSocket socket;
    private final List<Client> clients;


    //1. constructor where we give all the information
    public Server(ServerSocket socket, List<Client> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    //2. constructor where we give the minimum information
    public Server(ServerSocket socket) {
        this(socket, new ArrayList<Client>());
    }


    //This method runs automatically when we run Server.start()
    @Override
    public void run(){

        //Message from the server
        System.out.print("Listing for client at: " + socket);

        //We start listening for incoming connections
        try {
            while(true) {

                //We create/initialize a new client with "this" Server, the accepted socket, and a start name
                Client client = new Client(this, socket.accept(), "anonymous");

                //We add the client to the clients list
                addClient(client);

                //We start the client and "Go TO The Client Java File"
                client.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Methode to add a client that only 1 client can access at a time
    public synchronized void addClient(Client client){
        System.out.print("Accepted Client: " + client + toString());
        clients.add(client);
    }

    //Methode to remove a client that only 1 client can access at a time
    public synchronized void removeClient(Client client){
        System.out.print("Accepted Client: " + client + toString());
        clients.remove(client);
    }

    //Methode to broadcast to other clients, that only 1 client can access at a time
    public synchronized void broadcast(Client fromClient, String message) {
        //We loop the client list
        for (Client client : clients){
            //If the client is also the broadcaster, then we scip/continue
            if(client.equals(fromClient)){
                continue;
            } else {
                //We broadcast the message to all the other clients
                client.sendMessage(fromClient.getClientName() + ": " + message);
            }
        }

    }

    public static void main(String[] args) throws IOException {

        //The port we are connecting to
        final int port = 2424;

        //We create a serverSocket from the port (We are using 1 of our 2 constructors, where we only have to give the minimum info)
        ServerSocket serverSocket = new ServerSocket(port);

        //We create/initialize a Server from the ServerSocket
        Server server = new Server(serverSocket);

        //We start the Server
        server.start();

        //Message to the server
        System.out.print("Server started");

    }

}
