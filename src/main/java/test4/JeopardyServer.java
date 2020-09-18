package test4;

import test3.Client2;
import test3.Game;
import test3.Server2;
import test4.classes.Question_board;
import test4.readQuestions.ProcessQuestionsForGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class JeopardyServer extends Thread {
    private final ServerSocket socket;
    private final List<JeopardyClient> clients;

    public JeopardyServer(ServerSocket socket, List<JeopardyClient> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    public JeopardyServer(ServerSocket socket) {
        this(socket, new ArrayList<>());
    }

    @Override
    public void run() {
        System.out.println("Listing for clients at: " + socket);
        try {
            while (true) {
                JeopardyClient client = new JeopardyClient(
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


    public static void main(String[] args) throws IOException, ParseException {
        JeopardyServer server = new JeopardyServer(new ServerSocket(2424));
        server.start();


        /**
         * Problem **************************************
         */
        ArrayList<ArrayList<Question_board>> theQuestionsArraysForTheGame = new ArrayList<>();
        theQuestionsArraysForTheGame = new ProcessQuestionsForGame().theProcessor();

        for(int i = 0 ; i < 20 ; i++){

            theQuestionsArraysForTheGame.get(0).get(i).toString();

        }
        //*************************************************



        System.out.println("Server Started!");
    }









    public synchronized void addClient(JeopardyClient client) {
        System.out.println("Accepted client: " + client);
        clients.add(client);
    }

    public synchronized void removeClient(JeopardyClient client) {
        System.out.println("Closed client: " + client);
        clients.remove(client);
    }

    public synchronized void broadcast(JeopardyClient from, String msg) {
        for (JeopardyClient c : clients) {
            if (c.equals(from)) continue;
            c.sendMessage(from.getClientName() + ": " +  msg);
        }
    }

    public void announceName(JeopardyClient from, String previous) {
        System.out.println(from.getClientName() + " joined the chat!");
        for (JeopardyClient c : clients) {
            if (c.equals(from)) continue;
            c.sendMessage(from.getClientName() + " joined the chat!");
        }
    }

    private volatile JeopardyGame jeopardyGame;
    public synchronized JeopardyGame getActiveGame () {
        if (jeopardyGame == null || jeopardyGame.done()) {
            jeopardyGame = new JeopardyGame(1);
        }
        return jeopardyGame;
    }

}