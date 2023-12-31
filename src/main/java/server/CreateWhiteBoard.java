package server;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateWhiteBoard {
    private static String address;
    private static int port;
    private static String username;
    static Manager whiteBoard;
    public static void main(String[] args){
        if(args.length==3){
            address = args[0];
            String portString = args[1];
            port = Integer.parseInt(portString);
            username = args[2];
        }
        else{
            address ="localhost";
            port = 8080;
            username = "manager";
            System.out.println("Default manager");
        }
        EventQueue.invokeLater(()->{
            whiteBoard = new Manager(username);
        });

        Communication instance;
        ServerSocket server;
        Bind.usernames.add(username);
        int count = 0;
        try{
            server = new ServerSocket(port);
            Socket client;
            while(true){
                client = server.accept();
                count++;
                System.out.println("count: "+count);
                instance = new Communication(client);
                Bind.communications.add(instance);
                instance.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}