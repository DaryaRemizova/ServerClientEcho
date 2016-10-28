package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int NUM_PORT = 8080;


    public Server() {
        ServerSocket server = null;
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        String inputLine;

        try {
            server = new ServerSocket(NUM_PORT);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        try {
            System.out.println("Waiting for a client connect");
            clientSocket = server.accept();
            System.out.println("Connected");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }


        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        System.out.println("Waiting for a messages from client");


        try {
            while((inputLine = in.readLine()) != null){
                if (inputLine.equalsIgnoreCase("exit")) break;
                System.out.println("Client: " + inputLine);
                out.println("Echo: " + inputLine);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        } finally {
            try {
                in.close();
                clientSocket.close();
                server.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
            out.close();
        }

    }

}
