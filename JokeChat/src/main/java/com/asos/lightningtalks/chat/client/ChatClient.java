package com.asos.lightningtalks.chat.client;

import com.asos.lightningtalks.cosmos.model.ChatParticipant;
import com.asos.lightningtalks.cosmos.model.Joke;
import com.asos.lightningtalks.cosmos.service.CosmosRepository;
import com.azure.cosmos.CosmosException;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * This is the chat client program.
 * Type 'bye' to terminte the program.
 *
 * @author www.codejava.net
 */
public class ChatClient {
    public static final String SECONDPERSON = "secondperson";
    public static final String FIRSTPERSON = "firstperson";
    private String hostname;
    private int port;
    private ChatParticipant chatParticipant;
    private String userName;

    private Object[] jokeScript;

    private CosmosRepository cosmosRepository;

    public ChatClient(String hostname, int port, ChatParticipant chatParticipant) {
        this.hostname = hostname;
        this.port = port;
        this.chatParticipant = chatParticipant;
        this.cosmosRepository = new CosmosRepository();
    }

    public void execute() {
        try {
            ArrayList<Joke> allJokes = cosmosRepository.queryItems();

            this.jokeScript = allJokes.stream().map(j -> {
                var tmpJokes = (this.chatParticipant == ChatParticipant.PERSON_ONE) ? j.getFirstPersonLines() : j.getSecondPersonLines();
                return tmpJokes;
            }).flatMap(List::stream).collect(Collectors.toList()).toArray();

//Arrays.stream(this.jokeScript).flatMap(List::stream).collect(Collectors.toList());
            Socket socket = new Socket(hostname, port);

            System.out.println("Connected to the chat server");

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        } catch (CosmosException cex) {
            System.out.println("Cosmos Exception: " + cex.getMessage());
        }

    }

    void setUserName(String userName) {
        this.userName = userName;
    }

    String getUserName() {
        return this.userName;
    }

    Object[] getJokeScript() { return this.jokeScript;}

    ChatParticipant getChatParticipant() {
        return this.chatParticipant;
    }


    public static void main(String[] args) {
        ChatParticipant chatParticipant = ChatParticipant.PERSON_ONE;
        if (args.length < 3) {
            System.out.println("Usage: ChatClient <host ip> <port> <firstperson/secondperson>");
            System.out.println(String.format( "Chat client only received %d of 3 start up arguments", args.length));
            return;
        };

        if(!Arrays.stream(args).anyMatch(s -> s.matches("(?i)firstperson|secondperson"))) {
            System.out.println("Please specify firstperson or secondperson");
            return;
        }

        if (args[2].equalsIgnoreCase(SECONDPERSON)) {
                chatParticipant = ChatParticipant.PERSON_TWO;
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        ChatClient client = new ChatClient(hostname, port, chatParticipant);
        client.execute();
    }
}
