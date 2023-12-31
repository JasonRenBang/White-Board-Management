package server;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JPanel{
    private ArrayList<SendingInformation> recordList = new ArrayList<SendingInformation>();

    public void paint(Graphics gr){
        super.paint(gr);
        draw((Graphics2D)gr, recordList);

    }

    public void setList(ArrayList<SendingInformation> recordList){
        this.recordList = recordList;
    }


    public void draw(Graphics2D g, ArrayList<SendingInformation> recordList){

        for(int i = 0;i<recordList.size();i++){
            SendingInformation sendingInformation = recordList.get(i);
            int startX, startY, endX, endY,t,red,green,blue;
            String content;
            Color color;
            if(sendingInformation.getType().equals("Circle")){
                t = sendingInformation.getThick();
                g.setStroke(new BasicStroke(t));
                red = sendingInformation.getRed();
                green = sendingInformation.getGreen();
                blue = sendingInformation.getBlue();
                color = new Color(red, green, blue);
                g.setColor(color);
                startX = sendingInformation.getStartX();
                startY = sendingInformation.getStartY();
                endX = sendingInformation.getEndX();
                endY = sendingInformation.getEndY();
                int diameter = Math.min(Math.abs(startX - endX),Math.abs(startY-endY));
                g.drawOval(Math.min(startX, endX),Math.min(startY, endY), diameter, diameter);
                System.out.println(startX+"from can");
                System.out.println(startY+"from can");
                System.out.println(endX+"from can");
                System.out.println(endY+"from can");

            }
            else if(sendingInformation.getType().equals("Line")){
                t = sendingInformation.getThick();
                g.setStroke(new BasicStroke(t));
                red = sendingInformation.getRed();
                green = sendingInformation.getGreen();
                blue = sendingInformation.getBlue();
                color = new Color(red, green, blue);
                g.setColor(color);
                startX = sendingInformation.getStartX();
                startY = sendingInformation.getStartY();
                endX = sendingInformation.getEndX();
                endY = sendingInformation.getEndY();
                g.drawLine(startX,startY,endX,endY);


            }
            else if(sendingInformation.getType().equals("Triangle")){
                t = sendingInformation.getThick();
                g.setStroke(new BasicStroke(t));
                red = sendingInformation.getRed();
                green = sendingInformation.getGreen();
                blue = sendingInformation.getBlue();
                color = new Color(red, green, blue);
                g.setColor(color);
                startX = sendingInformation.getStartX();
                startY = sendingInformation.getStartY();
                endX = sendingInformation.getEndX();
                endY = sendingInformation.getEndY();
                g.drawLine(startX,startY,endX,endY);
                g.drawLine(startX,endY,endX,endY);
                g.drawLine(startX,startY,startX,endY);

            }
            else if(sendingInformation.getType().equals("Rectangle")){
                t = sendingInformation.getThick();
                g.setStroke(new BasicStroke(t));
                red = sendingInformation.getRed();
                green = sendingInformation.getGreen();
                blue = sendingInformation.getBlue();
                color = new Color(red, green, blue);
                g.setColor(color);
                startX = sendingInformation.getStartX();
                startY = sendingInformation.getStartY();
                endX = sendingInformation.getEndX();
                endY = sendingInformation.getEndY();
                g.drawLine(startX,startY,endX,startY);
                g.drawLine(startX,startY,startX,endY);
                g.drawLine(endX,startY,endX,endY);
                g.drawLine(startX,endY,endX,endY);

            }
            else if(sendingInformation.getType().equals("Text")){
                t = sendingInformation.getThick();
                g.setStroke(new BasicStroke(t));
                red = sendingInformation.getRed();
                green = sendingInformation.getGreen();
                blue = sendingInformation.getBlue();
                color = new Color(red, green, blue);
                content = sendingInformation.getText();
                g.setColor(color);
                startX = sendingInformation.getStartX();
                startY = sendingInformation.getStartY();
                g.drawString(content, startX,startY);
            }

        }
    }


}
