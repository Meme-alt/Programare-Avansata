package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 8080;
    private void comunicateWithServer(Socket socket) throws IOException{
        try(
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)
                ){
            while(true){
                System.out.println("Enter command: ");
                String command = scanner.nextLine();
                if(command.equalsIgnoreCase("exit")){
                    break;
                }
                out.println(command);
                String response = in.readLine();
                System.out.println(response);
                if(command.equalsIgnoreCase("stop")){
                    break;
                }
            }
        }
    }
    public void startClient(){
        try(Socket socket = new Socket(SERVER_ADDRESS, PORT)){
            System.out.println("Connected to server!");
            comunicateWithServer(socket);
        }catch(IOException e){
            System.err.println("Connection error: " + e.getMessage());
        }
    }
    public static void main(String[] args){
        GameClient client = new GameClient();
        client.startClient();
    }
}
