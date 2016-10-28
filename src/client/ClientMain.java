package client;

import javax.swing.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientMain {

    public static void main(String[] args) {
//		try {
//			Client client = new Client("name", Inet4Address.getLocalHost(), 8080);
//			client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			client.setVisible(true);
//		} catch (UnknownHostException e) {
//			System.out.println(e.getMessage());
//		}


        Dialog dialog = new Dialog();
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
        dialog.setStartListener(new ClientDialogInterface() {
            @Override
            public void message(String nick, String addr, int port) {
                Client client = new Client(nick, getAddr(addr), port);
                client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                client.setVisible(true);
            }
        });

    }

    public static InetAddress getAddr(String addr){
        byte[] bytes = new byte[4];
        int tmp;

        for(int i = 0; i < 4; i++){
            if(i != 3){
                tmp = Integer.parseInt(addr.substring(0, addr.indexOf('.')));
            }else{
                tmp = Integer.parseInt(addr);
            }
            bytes[i] = (byte) tmp;
            addr = addr.substring(addr.indexOf('.') + 1);
        }

        try {
            return Inet4Address.getByAddress(bytes);
        } catch (UnknownHostException e) {
            return null;
        }
    }

}

