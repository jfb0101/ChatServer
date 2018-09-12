/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.treinarinformatica.chatserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Treinar
 */
public class ThreadClient extends Thread {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private Server server;

    public ThreadClient(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception ex) {
            System.out.println("Failed to create ThreadClient");
            ex.printStackTrace();
            interrupt();
        }

    }

    @Override
    public void run() {
        try {
            while (true) {
                String name = in.readUTF();
                String message = in.readUTF();
                
                server.sendMessageForAllClients(name, message,this);
            }
        } catch (Exception ex) {
            System.out.println("Error during thread execution");
            ex.printStackTrace();
        }
    }
    
    public void sendMessage(String name, String message) {
        try {
            out.writeUTF(name);
            out.writeUTF(message);
            out.flush();
        } catch (Exception ex) {
            System.out.println("Failed to send message from " + name);
            ex.printStackTrace();
        }
    }
    
    
}
