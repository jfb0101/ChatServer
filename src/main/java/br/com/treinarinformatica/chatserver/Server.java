/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.treinarinformatica.chatserver;

import br.com.treinarinformatica.chatserver.model.Chat;
import br.com.treinarinformatica.chatserver.service.ChatService;
import com.google.protobuf.ServiceException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Treinar
 */
public class Server {

    private List<ThreadClient> threadList;
    private ServerSocket serverSocket;
    private int count;
    private ChatService chatService;

    public Server() {
        threadList = new ArrayList<>();
        count = 0;
        chatService = new ChatService();
    }

    public void sendMessageForAllClients(String name, String message, ThreadClient source) {
        
        Chat chat = new Chat();
        chat.setName(name);
        chat.setMessage(message);
        chat.setDatetime(new Date());
        
        try {
            chatService.save(chat);
        } catch (ServiceException ex) {
            ex.printStackTrace();
        }
        
        for (ThreadClient threadClient : threadList) {
            if (threadClient != source) {
                threadClient.sendMessage(name, message);
            }
        }
    }

    public void runServer() {
        try {
            System.out.println("Starting server...");
            serverSocket = new ServerSocket(5050);

            while (true) {
                System.out.println("Waiting for client " + (count + 1));
                Socket socket = serverSocket.accept();
                count++;
                System.out.println("Client " + count + " connected!");
                ThreadClient threadClient = new ThreadClient(socket, this);
                threadClient.start();
                threadList.add(threadClient);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().runServer();
    }
}
