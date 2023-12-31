package client;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class User extends JFrame {

    static Canvas canvas;
    Color color = Color.BLACK;
    int startX, startY, endX, endY;
    Graphics2D g;
    String type = "Line";
    int t = 1;
    public Communication communication;
    public String content = "null";
    private JPanel panel1;
    private JButton circle;
    private JButton btColor;
    private JButton line;
    private JButton triangle;
    private JButton rectangle;
    private JButton freeHand;
    private JButton text;
    private JTextArea input;
    private JLabel inputText;
    private JLabel editing;
    public JList users;
    public ArrayList<SendingInformation> sendingInformations = new ArrayList<SendingInformation>();
    public User(Communication communication, String userName) {
        super(userName);
        this.communication = communication;
        this.setTitle("WhiteBoard: " + userName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, 1200, 800);
        JPanel toolPanel = new JPanel();
        toolPanel.setBounds(0, 0, 1200, 50);
        toolPanel.setLayout(new FlowLayout(FlowLayout.CENTER));


        line.setPreferredSize(new Dimension(150, 20));
        line.setActionCommand("Line");
        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editing.setText("Editing: " + userName);
                type = e.getActionCommand();

            }
        });
        toolPanel.add(line);

        circle.setPreferredSize(new Dimension(150, 20));
        circle.setActionCommand("Circle");
        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editing.setText("Editing: " + userName);
                type = e.getActionCommand();
            }
        });
        toolPanel.add(circle);

        triangle.setPreferredSize(new Dimension(150, 20));
        triangle.setActionCommand("Triangle");
        triangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editing.setText("Editing: " + userName);
                type = e.getActionCommand();
            }
        });
        toolPanel.add(triangle);

        rectangle.setPreferredSize(new Dimension(150, 20));
        rectangle.setActionCommand("Rectangle");
        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editing.setText("Editing: " + userName);
                type = e.getActionCommand();

            }
        });
        toolPanel.add(rectangle);

        freeHand.setPreferredSize(new Dimension(150, 20));
        freeHand.setActionCommand("Free Hand");
        freeHand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                editing.setText("Editing: " + userName);
                type = e.getActionCommand();
            }
        });
        toolPanel.add(freeHand);

        text.setPreferredSize(new Dimension(150, 20));
        text.setActionCommand("Text");
        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editing.setText("Editing: " + userName);
                type = e.getActionCommand();

            }
        });
        toolPanel.add(text);


        this.getContentPane().setLayout(null);
        this.getContentPane().add(toolPanel);

        JPanel toolPanel2 = new JPanel();
        toolPanel2.setBounds(0, 50, 200, 750);
        toolPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(toolPanel2);


        editing.setBounds(0, 0, 200, 30);
        toolPanel2.add(BorderLayout.CENTER, editing);

        canvas = new Canvas();
        canvas.setBorder(null);
        canvas.setBounds(100, 100, 1050, 780);
        canvas.setBackground(Color.white);
        canvas.setList(getRecord());
        this.getContentPane().add(canvas);

        this.setVisible(true);
        this.setResizable(false);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                editing.setText("Editing: " + userName);
                startX = e.getX();
                startY = e.getY();
                if (!g.getColor().equals(color)) {
                    g.setColor(color);
                }
                if (type.equals("Free Hand")) {
                    g.drawLine(startX, startY, startX, startY);

                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    SendingInformation sendingInformation = new SendingInformation(userName, "draw", "Line", t, red, green, blue, startX, startY, startX, startY, content);
                    sendingInformations.add(sendingInformation);

                } else if (type.equals("Text")) {
                    String str = input.getText();
                    g.drawString(str, startX, startY);


                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    SendingInformation sendingInformation = new SendingInformation(userName, "draw", type, t, red, green, blue, startX, startY, endX, endY, str);
                    sendingInformations.add(sendingInformation);

                    try {

                        JSONObject command = SendingInformation.ObjectToJson(sendingInformation);
                        JoinWhiteBoard.communication.dos.writeUTF(command.toJSONString());
                        JoinWhiteBoard.communication.dos.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                editing.setText("Editing: " + userName);
                endX = e.getX();
                endY = e.getY();
                if (type.equals("Line")) {
                    g.drawLine(startX, startY, endX, endY);

                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    SendingInformation sendingInformation = new SendingInformation(userName, "draw", type, t, red, green, blue, startX, startY, endX, endY, content);
                    sendingInformations.add(sendingInformation);

                    try {

                        JSONObject command = SendingInformation.ObjectToJson(sendingInformation);
                        JoinWhiteBoard.communication.dos.writeUTF(command.toJSONString());

                        JoinWhiteBoard.communication.dos.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (type.equals("Circle")) {
                    int diameter = Math.min(Math.abs(startX - endX), Math.abs(startY - endY));
                    g.drawOval(Math.min(startX, endX), Math.min(startY, endY), diameter, diameter);
                    System.out.println(startX);
                    System.out.println(startY);
                    System.out.println(endX);
                    System.out.println(endY);

                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    SendingInformation sendingInformation = new SendingInformation(userName, "draw", type, t, red, green, blue, startX, startY, endX, endY, content);
                    sendingInformations.add(sendingInformation);

                    try {

                        JSONObject command = SendingInformation.ObjectToJson(sendingInformation);
                        JoinWhiteBoard.communication.dos.writeUTF(command.toJSONString());

                        JoinWhiteBoard.communication.dos.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("send information");
                } else if (type.equals("Triangle")) {
                    g.drawLine(startX, startY, endX, endY);
                    g.drawLine(startX, endY, endX, endY);
                    g.drawLine(startX, startY, startX, endY);
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    SendingInformation sendingInformation = new SendingInformation(userName, "draw", type, t, red, green, blue, startX, startY, endX, endY, content);
                    sendingInformations.add(sendingInformation);

                    try {

                        JSONObject command = SendingInformation.ObjectToJson(sendingInformation);
                        JoinWhiteBoard.communication.dos.writeUTF(command.toJSONString());
                        JoinWhiteBoard.communication.dos.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (type.equals("Rectangle")) {
                    g.drawLine(startX, startY, endX, startY);
                    g.drawLine(startX, startY, startX, endY);
                    g.drawLine(endX, startY, endX, endY);
                    g.drawLine(startX, endY, endX, endY);
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    SendingInformation sendingInformation = new SendingInformation(userName, "draw", type, t, red, green, blue, startX, startY, endX, endY, content);
                    sendingInformations.add(sendingInformation);

                    try {

                        JSONObject command = SendingInformation.ObjectToJson(sendingInformation);
                        JoinWhiteBoard.communication.dos.writeUTF(command.toJSONString());
                        JoinWhiteBoard.communication.dos.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                editing.setText("Editing: " + userName);
                endX = e.getX();
                endY = e.getY();
                if (type.equals("Free Hand")) {
                    g.drawLine(startX, startY, endX, endY);
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    SendingInformation sendingInformation = new SendingInformation(userName, "draw", "Line", t, red, green, blue, startX, startY, endX, endY, content);
                    sendingInformations.add(sendingInformation);
                    startX = endX;
                    startY = endY;
                    try {

                        JSONObject command = SendingInformation.ObjectToJson(sendingInformation);
                        JoinWhiteBoard.communication.dos.writeUTF(command.toJSONString());
                        JoinWhiteBoard.communication.dos.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        setG(canvas.getGraphics());

        btColor.setPreferredSize(new Dimension(150, 20));
        toolPanel.add(btColor);

        btColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editing.setText("Editing: " + userName);
                JFrame jf = new JFrame("color penal");
                jf.setSize(300, 300);
                jf.setLocationRelativeTo(null);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                Color curColor = JColorChooser.showDialog(jf, "choose color", null);
                if (curColor != null) {
                    color = curColor;
                }
            }
        });

        inputText.setPreferredSize(new Dimension(150, 30));
        toolPanel.add(BorderLayout.CENTER, inputText);

        input.setPreferredSize(new Dimension(1000, 30));
        toolPanel.add(BorderLayout.CENTER, input);


        Thread t = new Thread(() -> setEditing());
        t.start();

        try {

            SendingInformation sendingInformation = new SendingInformation(userName, "begin", "null", 0, 0, 0, 0, 0, 0, 0, 0, "null");
            JSONObject command = SendingInformation.ObjectToJson(sendingInformation);
            communication.dos.writeUTF(command.toJSONString());
            communication.dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        users.setPreferredSize(new Dimension(200, 300));
        toolPanel2.add(BorderLayout.CENTER, users);


    }
    public void setEditing() {
        while (true) {
            String name = Communication.name;
            editing.setText("Editing: " + name);
        }
    }

    public void setG(Graphics g) {
        this.g = (Graphics2D) g;
    }

    public ArrayList<SendingInformation> getRecord() {

        return sendingInformations;
    }

    public void update(SendingInformation sendingInformation) {

        sendingInformations.add(sendingInformation);
    }



    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(5, 7, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        circle = new JButton();
        circle.setText("circle");
        panel2.add(circle, new GridConstraints(0, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btColor = new JButton();
        btColor.setText("btColor");
        panel2.add(btColor, new GridConstraints(0, 1, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        line = new JButton();
        line.setText("line");
        panel2.add(line, new GridConstraints(0, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        triangle = new JButton();
        triangle.setText("triangle");
        panel2.add(triangle, new GridConstraints(0, 3, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rectangle = new JButton();
        rectangle.setText("rectangle");
        panel2.add(rectangle, new GridConstraints(0, 4, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        freeHand = new JButton();
        freeHand.setText("freeHand");
        panel2.add(freeHand, new GridConstraints(0, 5, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        text = new JButton();
        text.setText("text");
        panel2.add(text, new GridConstraints(0, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        input = new JTextArea();
        input.setText("input");
        panel2.add(input, new GridConstraints(1, 6, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        inputText = new JLabel();
        inputText.setText("Input content");
        panel2.add(inputText, new GridConstraints(2, 2, 3, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editing = new JLabel();
        editing.setText(" Editing: ");
        panel2.add(editing, new GridConstraints(3, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        users = new JList();
        panel2.add(users, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
