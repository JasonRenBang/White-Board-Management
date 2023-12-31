package client;


import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;



import static java.lang.Thread.sleep;

public class JoinWhiteBoard {
    private static String address;
    private static int port;
    private static String username;

    public static Communication communication;
    public static User user;
    private static Socket socket;

    public static void main(String[] args) throws IOException {
        if(args.length==3){
            address = args[0];
            String portString = args[1];
            port = Integer.parseInt(portString);
            username = args[2];
        }
        else{
            address ="localhost";
            port = 8080;
            username = "user";
            System.out.println("Default user");
        }


        socket  = new Socket(address, port);
        communication  = new Communication(socket);

        EventQueue.invokeLater(()->{
            try{
                SendingInformation sendingInformation = new SendingInformation(username,"request"," ",0,0,0,0,0,0,0,0," ");

                JSONObject command = SendingInformation.ObjectToJson(sendingInformation);
                communication.dos.writeUTF(command.toJSONString());
                communication.dos.flush();
                sleep(5000);
                String allow = communication.getStatus();
                if(allow.equals("yes")){
                    if(user==null){
                        user = new User(communication, username);
                    }
                    System.out.println("successed");

                }
                else if(allow.equals("no")){
                    JOptionPane.showMessageDialog(new JFrame(), "manager refused");
                    System.exit(1);
                }
                else if(allow.equals("already created one")){
                    JOptionPane.showMessageDialog(new JFrame(), "you already have one, you can not create a new board");
                    System.exit(1);
                }

            }catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        communication.launch();

    }
}
