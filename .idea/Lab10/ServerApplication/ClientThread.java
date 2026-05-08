package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private final Socket socket;
    private final GameServer server;
    public ClientThread(Socket socket, GameServer server){
        this.socket = socket;
        this.server = server;
    }
    private String processCommand(String request){
        if(request.equalsIgnoreCase("stop")){
            server.stopServer();
            return "Server stopped";
        }else{
            return "Server received: " + request;
        }
    }
    @Override
    public void run(){
        try(
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ){
            String request;
            while((request = in.readLine()) != null){
                String response = processCommand(request);
                out.println(response);
                if(request.equalsIgnoreCase("stop")){
                    break;
                }
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }finally {
            try{
                socket.close();
            }catch(IOException e){
                System.err.println(e.getMessage());
            }
        }
    }
}
