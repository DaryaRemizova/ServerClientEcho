package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends JFrame{
    private final int DEFAULT_WIDTH = 400;
    private final int DEFAULT_HEIGHT = 300;
    private String nickName;

    private JTextField field;
    private JTextArea area;
    private JButton button;
    private JPanel panel;

    private BufferedReader in = null;
    private PrintWriter out = null;

    private Socket socket;

    public Client(String nickName, InetAddress inetAddress, int port){
        if(inetAddress == null) {
            JOptionPane.showMessageDialog(this, "IP is null", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        try {
            socket = new Socket(inetAddress, port);
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }

        this.nickName = nickName;

        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((scrSize.width - DEFAULT_WIDTH) / 2,
                (scrSize.height - DEFAULT_HEIGHT) / 2,
                DEFAULT_WIDTH, DEFAULT_HEIGHT);

        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        setMinimumSize(this.getSize());
        this.setTitle("Client. Nick: " + nickName);

        createComp();
        setComp();

        new Thread(new Runnable() {
            private String inputLine;
            @Override
            public void run() {
                try {
                    while((inputLine = in.readLine()) != null){
                        area.append("Server: " + inputLine + "\n");
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(Client.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(-1);
                }

            }
        }).start();

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (field.getText().equals("")) return;
                out.println(field.getText());
                area.append("Me: " + field.getText() + "\n");
            }
        });
    }

    private void createComp(){
        button = new JButton("Send");
        area = new JTextArea();
        area.setEditable(false);
        field = new JTextField(25);
        panel = new JPanel();
    }

    private void setComp(){
        panel.add(field, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);

        this.add(area, BorderLayout.CENTER);
        this.add(panel, BorderLayout.SOUTH);
    }


}

