package seanie.mark.cs4076p2server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


public class CS4076P2Server {

    private static ServerSocket servSock;
    private static final int PORT = 6558;
    
    public static void main(String[] args) {

        final List<Module> currentModules = new ArrayList<>();

        try{
            servSock = new ServerSocket(PORT);
        } catch(IOException e){
            System.out.println("Unable to attach port!");
            System.exit(1);
        }

        //noinspection InfiniteLoopStatement
        do{
            try {
                Socket link = servSock.accept();
                Thread clientThread = new Thread(new ClientHandler(link, currentModules));
                clientThread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }while(true);
    }

}

