package server;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Communication extends Thread{

    public Socket socket;
    public String name;

    public static String editingName = null;
    public DataOutputStream dos;
    public Communication(Socket socket){
        this.socket = socket;
    }


    public void run(){
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream((socket.getOutputStream()));
            while(true){

                SendingInformation sendingInformation = SendingInformation.JsonToObject(dis.readUTF());
                if(sendingInformation.getCmd().equals("begin")){
                    ArrayList<SendingInformation> records = CreateWhiteBoard.whiteBoard.getRecord();

                    for(int i = 0;i<records.size();i++){
                        for(int j = 0;j<Bind.communications.size();j++){
                            Communication st = Bind.communications.get(j);


                            JSONObject command = SendingInformation.ObjectToJson(records.get(i));
                            st.dos.writeUTF(command.toJSONString());
                            st.dos.flush();
                            System.out.println("get information begin");
                        }
                    }
                    String userList = Bind.usernames.get(0)+" ";
                    String[] userList2 = new String[10];
                    for(int i = 1;i<Bind.usernames.size();i++){
                        userList =userList +Bind.usernames.get(i);
                        userList2[i] = Bind.usernames.get(i);
                    }
                    for(int i = 0;i<Bind.usernames.size();i++){
                        userList2[i] = Bind.usernames.get(i);
                    }


                    CreateWhiteBoard.whiteBoard.users.setListData(userList2);
                    for(int i = 0;i<records.size();i++){
                        for(int j = 0;j<Bind.communications.size();j++){
                            Communication st = Bind.communications.get(j);

                            SendingInformation sendUser = new SendingInformation("null","list","null", 0,0, 0,0,0,0,0,0,userList);
                            JSONObject command = SendingInformation.ObjectToJson(sendUser);
                            st.dos.writeUTF(command.toJSONString());
                            st.dos.flush();
                            System.out.println("get user list ");
                        }
                    }


                }else if(sendingInformation.getCmd().equals("request")){
                    String curName = sendingInformation.getUser();
                    name = curName;
                    if(Bind.usernames.contains(name)) {

                        SendingInformation send =new SendingInformation("null","feedback","null",0,0,0,0,0,0,0,0,"already created one");
                        JSONObject command = SendingInformation.ObjectToJson(send);
                        dos.writeUTF(command.toJSONString());
                        dos.flush();
                        Bind.communications.remove(this);
                        socket.close();
                    }else{

                        int ans = JOptionPane.showConfirmDialog(null,curName+ " want to create","Confirm", JOptionPane.INFORMATION_MESSAGE);
                        if(ans == JOptionPane.YES_OPTION){


                            SendingInformation send =new SendingInformation("null","feedback","null",0,0,0,0,0,0,0,0,"yes");
                            JSONObject command = SendingInformation.ObjectToJson(send);
                            dos.writeUTF(command.toJSONString());

                            dos.flush();
                            Bind.usernames.add(curName);

                        }else if (ans==JOptionPane.CANCEL_OPTION||ans==JOptionPane.CLOSED_OPTION||ans==JOptionPane.NO_OPTION){
                            SendingInformation send =new SendingInformation("null","feedback","null",0,0,0,0,0,0,0,0,"no");
                            JSONObject command = SendingInformation.ObjectToJson(send);
                            dos.writeUTF(command.toJSONString());
                            dos.flush();
                            Bind.communications.remove(this);

                        }

                    }
                }else if(sendingInformation.getCmd().equals("draw")){
                    editingName = sendingInformation.getUser();
                    for(int i = 0; i< Bind.communications.size();i++){
                        Communication st = Bind.communications.get(i);

                        JSONObject command = SendingInformation.ObjectToJson(sendingInformation);
                        st.dos.writeUTF(command.toJSONString());
                        st.dos.flush();
                        System.out.println("sending information to all users");
                    }
                    CreateWhiteBoard.whiteBoard.update(sendingInformation);
                    CreateWhiteBoard.whiteBoard.canvas.repaint();
                    System.out.println("get information");

                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }
}
