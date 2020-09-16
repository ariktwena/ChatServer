package test4;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

//We implement Closeable som we can close our socket connection: close();
public class Client extends Thread implements Closeable {
    private final Server server;
    private final Socket socket;
    private String name;
    private final ClientHandler clientHandler;
    private final ArrayList<String> pendingMessages;


    public Client(Server server, Socket socket, String name) throws IOException {
        this.server = server;
        this.socket = socket;
        this.name = name;
        this.clientHandler = new ClientHandler(new Scanner(socket.getInputStream()), new PrintWriter(socket.getOutputStream()), socket.getInputStream());
        this.pendingMessages = new ArrayList<String>();
    }

    @Override
    public void run(){


        try {

            name = clientHandler.fetchName();

            while(true){

                String msg = clientHandler.prompt();
//                if(clientHandler.serverInStream.){
//                    clientHandler.setWriting(true);
//                } else {
//                    clientHandler.setWriting(false);
//                }
                server.broadcast(this, msg);

            }


//        } catch (IOException e) {
//            e.printStackTrace();
        } finally {
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public String toString() {
        return "Server{" +
                "socket=" + socket +
                '}';
    }

    @Override
    public void close() throws IOException {
        server.removeClient(this);
        socket.close();
    }

    public void sendMessage(String s) {
        if(!clientHandler.isWriting()){
            clientHandler.out.println(s);
            clientHandler.out.print("> ");
            clientHandler.out.flush();
        } else {
            pendingMessages.add(s);
        }
//        clientHandler.out.println(s);
//        clientHandler.out.print("> ");
//        clientHandler.out.flush();
    }

    public String getClientName() {
        return name;
    }

    /**
     *
     * ClientHandler Class
     *
     */


    public class ClientHandler{

        private final Scanner in;
        private final PrintWriter out;
        private final InputStream serverInStream;
        private boolean isWriting;

        public ClientHandler(Scanner in, PrintWriter out, InputStream serverInStream) {
            this.in = in;
            this.out = out;
            this.serverInStream = serverInStream;
            this.isWriting = false;
        }

        private String prompt(){
            out.print("> ");
            out.flush();
//            if(in.hasNext()){
//                isWriting = true;
//            }
            return in.nextLine();
        }

        private String fetchName(){
            out.print("What is your name ");
            return prompt();
        }

        public boolean isWriting() {
            return isWriting;
        }

        public void setWriting(boolean writing) {
            isWriting = writing;
        }
    }
}



