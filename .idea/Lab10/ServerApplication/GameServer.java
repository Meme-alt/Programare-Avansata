package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private static final int PORT = 8080;
    private boolean running = true;
    private ServerSocket serverSocket;

    public void stopServer(){
        this.running = false;
        try{
            if(serverSocket != null){
                serverSocket.close();
            }
        }catch(IOException e){
            System.err.println("Error closing server: " + e.getMessage());
        }
    }

    public void start(){
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started!");
            while(running){
                Socket socket = serverSocket.accept();
                new ClientThread(socket, this).start();
            }
        }catch(IOException e){
            if(running){
                System.err.println("Server exception: " + e.getMessage());
            }
            else{
                System.out.println("Server stopped!");
            }
        }finally {
            stopServer();
        }
    }

    public static void main(String[] args){
        GameServer server = new GameServer();
        server.start();
    }
}
