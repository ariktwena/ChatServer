package test3;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server2 extends Thread {
    private final ServerSocket socket;
    private final List<Client2> clients;

    public Server2(ServerSocket socket, List<Client2> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    public Server2(ServerSocket socket) {
        this(socket, new ArrayList<>());
    }

    @Override
    public void run() {
        System.out.println("Listing for clients at: " + socket);
        try {
            while (true) {
                Client2 client = new Client2(
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
        Server2 server = new Server2(new ServerSocket(2424));
        server.start();

        System.out.println("Server Started!");
    }

    public synchronized void addClient(Client2 client) {
        System.out.println("Accepted client: " + client);
        clients.add(client);
    }

    public synchronized void removeClient(Client2 client) {
        System.out.println("Closed client: " + client);
        clients.remove(client);
    }

    public synchronized void broadcast(Client2 from, String msg) {
        for (Client2 c : clients) {
            if (c.equals(from)) continue;
            c.sendMessage(from.getClientName() + ": " +  msg);
        }
    }

    public void announceName(Client2 from, String previous) {
        System.out.println(from.getClientName() + " joined the chat!");
        for (Client2 c : clients) {
            if (c.equals(from)) continue;
            c.sendMessage(from.getClientName() + " joined the chat!");
        }
    }

    private volatile Game game;
    public synchronized Game getActiveGame () {
        if (game == null || game.done()) {
            game = new Game(1);
        }
        return game;
    }

}
