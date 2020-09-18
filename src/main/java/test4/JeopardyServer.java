package test4;

import test3.Client2;
import test3.Game;
import test3.Server2;
import test4.classes.Question_board;
import test4.readQuestions.InputStreamFromFile;
import test4.readQuestions.ProcessQuestionsForGame;
import test4.tui.TUI;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JeopardyServer extends Thread {
    private final ServerSocket socket;
    private final List<JeopardyClient> clients;
//    private ArrayList<ArrayList<Question_board>> theQuestionsArraysForTheGame = new ArrayList<>();

    public JeopardyServer(ServerSocket socket, List<JeopardyClient> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    public JeopardyServer(ServerSocket socket) {
        this.socket = socket;
        this.clients = new ArrayList<>();
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
//                if(clients.size() == 2){
//                    initiateGame();
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Start the server
    public static void main(String[] args) throws IOException {
        JeopardyServer server = new JeopardyServer(new ServerSocket(2424));
        server.start();

        System.out.println("Server Started!");
    }

    //Get number of player
    public int getNumberOfPlayer(){
        return clients.size();
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

//    public void initiateGame () throws IOException, ParseException {
////        ArrayList<ArrayList<Question_board>> theQuestionsArraysForTheGame = new ArrayList<>();
//        theQuestionsArraysForTheGame = new ProcessQuestionsForGame().theProcessor();
//
////        InputStreamFromFile inputStreamFromFile = new InputStreamFromFile();
////        ArrayList<ArrayList<Question_board>> theQuestionsArraysForTheGame;
////        theQuestionsArraysForTheGame = inputStreamFromFile.injectInputStreamToReader();
//
//        //Test if we get Stream input
////        for(int i = 0 ; i < 20 ; i++){
////
////            System.out.println(theQuestionsArraysForTheGame.get(0).get(i).toString());
////
////        }
////        for(int i = 0 ; i < 20 ; i++){
////
////            System.out.println(theQuestionsArraysForTheGame.get(1).get(i).toString());
////
////        }
//    }

    private volatile JeopardyGame jeopardyGame;

    public synchronized JeopardyGame getActiveGame () throws IOException, ParseException {

        ArrayList<ArrayList<Question_board>> theQuestionsArraysForTheGame = new ArrayList<>();
        theQuestionsArraysForTheGame = new ProcessQuestionsForGame().theProcessor();

//        InputStreamFromFile inputStreamFromFile = new InputStreamFromFile();
//        ArrayList<ArrayList<Question_board>> theQuestionsArraysForTheGame;
//        theQuestionsArraysForTheGame = inputStreamFromFile.injectInputStreamToReader();

        //Test if we get Stream input
//        for(int i = 0 ; i < 20 ; i++){
//
//            System.out.println(theQuestionsArraysForTheGame.get(0).get(i).toString());
//
//        }
//        for(int i = 0 ; i < 20 ; i++){
//
//            System.out.println(theQuestionsArraysForTheGame.get(1).get(i).toString());
//
//        }


        if (jeopardyGame == null || jeopardyGame.done()) {
            jeopardyGame = new JeopardyGame(2, clients, theQuestionsArraysForTheGame);
        }
        return jeopardyGame;
    }

}