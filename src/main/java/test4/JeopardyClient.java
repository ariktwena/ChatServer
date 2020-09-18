package test4;

import test3.Client2;
import test3.Game;
import test3.Server2;
import test4.classes.Question_board;
import test4.tui.TUI;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class JeopardyClient extends Thread implements Closeable {
    private final JeopardyServer server;
    private final Socket socket;
    private String name;
    private final ClientHandler handler;
    private final BlockingQueue<String> messageQueue;
    private int score;
    private boolean playerTurn;
    private final TUI tui;
//    private final ArrayList<ArrayList<Question_board>> theQuestionsArraysForTheGame;

    public JeopardyClient(JeopardyServer server, Socket socket, String name) throws IOException {
        this.server = server;
        this.socket = socket;
        this.handler = new ClientHandler(
                socket.getInputStream(),
                new PrintWriter(socket.getOutputStream(), true));
        this.messageQueue = new LinkedBlockingQueue<>();
        this.name = name;
        this.score = 0;
        this.playerTurn = false;
        this.tui = new TUI(new Scanner(socket.getInputStream()), new PrintWriter(socket.getOutputStream()));
//        this.theQuestionsArraysForTheGame = theQuestionsArraysForTheGame;
    }

    public String getClientName() {
        return name;
    }


    @Override
    public void run() {
//        Thread t = new Thread(() -> {
//            try {
//                System.out.println("We are here");
//                tui.welcomeMessage(getClientName());
//                System.out.println("After");
//
//                JeopardyClient superThis = this;
//                JeopardyGame game = server.getActiveGame();
//                game.play(new JeopardyGame.GameParticipant() {
//                    @Override
//                    public void notifyGameStart(String secretWord) {
//                        handler.printMessage("Game started, write: " + secretWord);
//                    }
//
//                    @Override
//                    public void notifyWinner(JeopardyClient winner) {
//                        if (winner.equals(superThis)) {
//                            handler.printMessage("Yes you won");
//                        } else {
//                            handler.printMessage("Dammm, you lost to " + winner.getClientName());
//                        }
//                    }
//
//                    @Override
//                    public String getAnswer() {
//                        return handler.waitForLine();
//                    }
//
//                    @Override
//                    public JeopardyClient getClient() {
//                        return superThis;
//                    }
//
//                    @Override
//                    public void weHaveAllThePlayers() {
//                        tui.weHaveAllThePlayers();
//                        tui.loader();
//                    }
//
//                    @Override
//                    public void WeAreWaitingForMorePlayers() {
//                        tui.waitingForOnMorePlayer(getClientName());
//                    }
//                });

//                while (true) {
//                    handler.showPrompt();
//                    String line = handler.waitForLine();
//                    if (line.startsWith("!rename")) {
//                        String previousName = name;
//                        name = handler.fetchName();
//                        server.announceName(this, previousName);
//
//                    } else if (line.startsWith("!play")) {
//
//                    } else if (line.startsWith("!exit")) {
//                        socket.close();
//                        break;
//                    } else{
//                        server.broadcast(this, line);
//                    }
//                }
//            } catch (InterruptedException | IOException e) { }
//        });
        try {
            if(server.getNumberOfPlayer() <= 2){
                name = tui.getPlayerName();


                JeopardyClient superThis = this;
                JeopardyGame game = server.getActiveGame();
                game.play(superThis, new JeopardyGame.GameParticipant() {

                    @Override
                    public void notifyGameStart(String secretWord) {
                        handler.printMessage("Game started, write: " + secretWord);
                    }

                    @Override
                    public void notifyWinner(JeopardyClient winner) {
                        if (winner.equals(superThis)) {
                            handler.printMessage("Yes you won");
                        } else {
                            handler.printMessage("Dammm, you lost to " + winner.getClientName());
                        }
                    }

                    @Override
                    public String getAnswer() {
                        return handler.waitForLine();
                    }

                    @Override
                    public JeopardyClient getClient() {
                        return superThis;
                    }

                    @Override
                    public void weHaveAllThePlayers() {
                        tui.weHaveAllThePlayers();
                        tui.loader();
                        tui.welcomeMessage(getClientName());
                        tui.loaderLong();
                    }

                    @Override
                    public void WeAreWaitingForMorePlayers() {
                        tui.waitingForOnMorePlayer(getClientName());
                    }

                    @Override
                    public void youStartTheGameAndChooseCategory() {
                        tui.youWonThetoes();
                    }

                    @Override
                    public void theOtherPlayerISChoosingACategory() {
                        tui.youDidntWinThetoes();
                    }

                    @Override
                    public void drawBoard(ArrayList<Question_board> list) {
                        String score1, score2, score3, score4, score5, score6;

                        //We print the header of the board (The list has 30 spots. Every 5 spot is a now line of questions)
                        tui.getBoardHeader();
                        tui.getBoardCategoryLeftAlignFormat("A: " + list.get(0).getCategory().getCategoryName(),
                                "B: " +list.get(5).getCategory().getCategoryName(),
                                "C: " +list.get(10).getCategory().getCategoryName(),
                                "D: " +list.get(15).getCategory().getCategoryName(),
                                "E: " +list.get(20).getCategory().getCategoryName(),
                                "F: " +list.get(25).getCategory().getCategoryName());

                        //We print 5 rows of point on the board
                        for( int i = 0 ; i < 5 ; i++ ){
                            tui.getBoardSeparator();
                            score1 = list.get(0 + i).getAnswered() == null ? String.valueOf(list.get(0 + i).getScore()) : list.get(0 + i).getAnswered();
                            score2 = list.get(5 + i).getAnswered() == null ? String.valueOf(list.get(5 + i).getScore()) : list.get(5 + i).getAnswered();
                            score3 = list.get(10 + i).getAnswered() == null ? String.valueOf(list.get(10 + i).getScore()) : list.get(10 + i).getAnswered();
                            score4 = list.get(15 + i).getAnswered() == null ? String.valueOf(list.get(15 + i).getScore()) : list.get(15 + i).getAnswered();
                            score5 = list.get(20 + i).getAnswered() == null ? String.valueOf(list.get(20 + i).getScore()) : list.get(20 + i).getAnswered();
                            score6 = list.get(25 + i).getAnswered() == null ? String.valueOf(list.get(25 + i).getScore()) : list.get(25 + i).getAnswered();


                            tui.getBoardScoreLeftAlignFormatRow(score1, score2, score3, score4, score5, score6);
                        }
                        tui.getBoardFooter();
                    }

                    @Override
                    public String playerCategoryChoice() {
                        return tui.playerCategoryInput();
                    }

                    @Override
                    public void getCategoryTitle(String categoryName, String clientName) {
                        tui.getCategoryTitle(clientName, categoryName);
                    }


                });


                //tui.loader();
//                t.start();
//                tui.loader();


//                if(server.getNumberOfPlayer() != 2){
//                    tui.waitingForOnMorePlayer(getClientName());
//                    while(server.getNumberOfPlayer() != 2){
//                        this.wait();
//                    }
//                }
//                tui.welcomeMessage(getClientName());
//                tui.loaderLong();
                //socket.close();
            } else {
                tui.toManyPlayers();
                socket.close();
                server.removeClient(this);
            }



//            String previousName = name;
//            name = handler.fetchName();
//            server.announceName(this, previousName);
            //t.start();

//            while (true) {
//                String inbound = messageQueue.take();
//                handler.printMessage(inbound);
//            }
        } catch (IOException | InterruptedException | ParseException e) {
            System.out.println(name + " exited with: " + e.getMessage());
        } finally {
            try { close(); } catch (IOException e) {
                e.printStackTrace();
            }
//            try {
//                t.interrupt();
//                t.join(1000);
//            } catch (InterruptedException e) {
//                t.stop();
//            }
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    @Override
    public String toString() {
        return "Player: " + name + " has score: " + score;
    }

    @Override
    public void close() throws IOException {
        server.removeClient(this);
        socket.close();
    }

    public void sendMessage(String s) {
        messageQueue.add(s);
    }

    public class ClientHandler {
        private final InputStream in;
        private final PrintWriter out;

        public ClientHandler(InputStream in, PrintWriter out) {
            this.in = in;
            this.out = out;
        };

        private void showPrompt() {
            out.print("> ");
            out.flush();
        }

        private String prompt() {
            showPrompt();
            return waitForLine();
        }

        private String fetchName() {
            out.println("What's your name, man?");
            return prompt();
        }

        private String waitForLine() {
            return new Scanner(in).nextLine();
        }

        public boolean hasInput() throws IOException {
            in.readAllBytes();
            return in.available() > 0;
        }

        public void printMessage(String message) {
            out.println(message);
            out.flush();
        }
    }
}