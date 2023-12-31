package server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SendingInformation {

    private String type;
    private int thick;
    private int  red;
    private int green;
    private int blue;

    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private String text = null;
    private String cmd = null;
    private String user = null;


    public String getText() {
        return text;
    }


    public String getType() {
        return type;
    }

    public int getThick() {
        return thick;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }
    public String getCmd(){
        return cmd;
    }

    public String getUser() {
        return user;
    }

    public SendingInformation(String user, String cmd, String type, int thick, int red, int green, int blue, int startX, int startY, int endX, int endY, String text){
        this.cmd = cmd;
        this.type= type;
        this.thick = thick;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.text  = text;
        this.user = user;

    }

    public static SendingInformation JsonToObject(String str) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject command = (JSONObject) parser.parse(str);
        String user = command.get("user").toString();
        String cmd = command.get("cmd").toString();
        String type = command.get("type").toString();
        int thick = Integer.parseInt(command.get("thick").toString());
        int red = Integer.parseInt(command.get("red").toString());
        int green = Integer.parseInt(command.get("green").toString());
        int blue = Integer.parseInt(command.get("blue").toString());
        int startX = Integer.parseInt(command.get("startX").toString());
        int startY = Integer.parseInt(command.get("startY").toString());
        int endX = Integer.parseInt(command.get("endX").toString());
        int endY = Integer.parseInt(command.get("endY").toString());
        String text = command.get("text").toString();

        SendingInformation sendingInformation = new SendingInformation(user,cmd,type,thick,red,green,blue,startX,startY,endX,endY,text);
        return sendingInformation;
    }
    public static JSONObject ObjectToJson(SendingInformation information){
        String user = information.getUser();
        String cmd = information.getCmd();
        String type = information.getType();
        int thick = information.getThick();
        int red = information.getRed();
        int green = information.getGreen();
        int blue = information.getBlue();
        int startX = information.getStartX();
        int startY = information.getStartY();
        int endX = information.getEndX();
        int endY = information.getEndY();

        String text = information.getText();
        JSONObject command = new JSONObject();
        command.put("user",user);
        command.put("cmd",cmd);
        command.put("type", type);
        command.put("thick", thick);
        command.put("red",red);
        command.put("green",green);
        command.put("blue",blue);
        command.put("startX",startX);
        command.put("startY",startY);
        command.put("endX",endX);
        command.put("endY",endY);
        command.put("text",text);

        return command;


    }




}
