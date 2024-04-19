package seanie.mark.cs4076p2client;


import seanie.mark.cs4076p2client.views.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class App {
    private static InetAddress host;
    private static final int PORT = 6558;
    public static BufferedReader in;
    public static PrintWriter out;

    public static void startApp(){

        Socket link;
        try {
            //Server Setup Stuff
            link = new Socket(host, PORT);
            in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            out = new PrintWriter(link.getOutputStream(), true);

            homeScreen.startMainScreen();
        }
        catch (IOException e){
            System.out.println("Unable to connect to server");
            System.exit(1);
        }
    }

    public static void main(String [] args ) {
        try{
            host = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e){
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        startApp();
    }
}

