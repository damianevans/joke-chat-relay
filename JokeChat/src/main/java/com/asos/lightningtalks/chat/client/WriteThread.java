package com.asos.lightningtalks.chat.client;

import com.asos.lightningtalks.cosmos.model.ChatParticipant;

import java.io.*;
import java.net.*;

/**
 * This thread is responsible for reading user's input and send it
 * to the server.
 * It runs in an infinite loop until the user types 'bye' to quit.
 *
 * @author www.codejava.net
 */
public class WriteThread extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;

    public WriteThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {

        Console console = System.console();

        String userName = client.getChatParticipant() == ChatParticipant.PERSON_ONE ? "Eric" : "Ernie";
       // String userName = console.readLine("\nEnter your name: ");
        client.setUserName(userName);
        writer.println(userName);

        int jokesLength = client.getJokeScript().length;
        int jokeCounter = 0;

        String text;

        do {
            text = (String)client.getJokeScript()[jokeCounter];//console.readLine("[" + userName + "]: ");

            if(client.getChatParticipant() == ChatParticipant.PERSON_TWO) {
                delayResponse();
            }

            writer.println(text);
            jokeCounter++;
            if(jokeCounter >= jokesLength) {
                jokeCounter = 0;
            }

            if(client.getChatParticipant() == ChatParticipant.PERSON_ONE) {
                delayResponse();
            }

        }  while (!text.equals("bye"));

        try {
            socket.close();
        } catch (IOException ex) {

            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }

    private void delayResponse() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}