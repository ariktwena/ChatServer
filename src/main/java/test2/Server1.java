package test2;

import test1.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server1 extends Thread {
    private final ServerSocket socket;
    private final List<Client1> clients;

    public Server1(ServerSocket socket, List<Client1> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    public Server1(ServerSocket socket) {
        this(socket, new ArrayList<>());
    }

    @Override
    public void run() {
        System.out.println("Listing for clients at: " + socket);
        try {
            while (true) {
                Client1 client = new Client1(
                        this,
                        socket.accept(),
                        "anonymous");
                addClient(client);
                client.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        Server1 server = new Server1(new ServerSocket(2424));
        server.start();

        System.out.println("Server Started!");
    }

    public synchronized void addClient(Client1 client) {
        System.out.println("Accepted client: " + client);
        clients.add(client);
    }

    public synchronized void removeClient(Client1 client) {
        System.out.println("Closed client: " + client);
        clients.remove(client);
    }

    public synchronized void broadcast(Client1 from, String msg) {
        for (Client1 c : clients) {
            if (c.equals(from)) continue;
            c.sendMessage(from.getClientName() + ": " +  msg);
        }
    }

    public void announceName(Client1 from, String previous) {
        System.out.println(from.getClientName() + " joined the chat!");
        for (Client1 c : clients) {
            if (c.equals(from)) continue;
            c.sendMessage(from.getClientName() + " joined the chat!");
        }
    }
}
