package client;


import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class Communication {

    public Socket socket;
    public static String name;
    public DataInputStream dis;
    public DataOutputStream dos;

    String status  ;

    Communication(Socket socket){

        try{
            status = " ";
            this.socket = socket;
            dos = new DataOutputStream((this.socket.getOutputStream()));
            dis = new DataInputStream((this.socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void launch(){
        try{
            while(true){

                SendingInformation sendingInformation = SendingInformation.JsonToObject(dis.readUTF());

                if(sendingInformation.getCmd().equals("draw")){
                    name = sendingInformation.getUser();
                    JoinWhiteBoard.user.update(sendingInformation);
                    JoinWhiteBoard.user.canvas.repaint();
                    System.out.println("get information");

                }
                else if(sendingInformation.getCmd().equals("feedback")){
                    if(sendingInformation.getText().equals("yes")){
                        System.out.println("get command yes");
                        status = "yes";
                    }else if(sendingInformation.getText().equals("already created one")){
                        status = "already created one";
                    }else if(sendingInformation.getText().equals("no")){
                        status = "no";
                    }

                }else if(sendingInformation.getCmd().equals("list")){
                    String users = sendingInformation.getText();
                    String[] userList = users.split(" ");
                    JoinWhiteBoard.user.users.setListData(userList);
                    System.out.println(userList.toString());
                }
                else if(sendingInformation.getCmd().equals("delete")){
                    String users = sendingInformation.getText();
                    String[] userList = users.split(" ");
                    JoinWhiteBoard.user.users.setListData(userList);
                    JOptionPane.showMessageDialog(new JFrame(),sendingInformation.getUser()+" has been kicked by manager");

                }
                else if(sendingInformation.getCmd().equals("kick")){
                    JOptionPane.showMessageDialog(new JFrame(), "manager kick you out!!!");
                    sleep(2000);
                    System.exit(1);

                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    String getStatus(){
        return status;
    }




}
