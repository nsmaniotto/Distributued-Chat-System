
package project.insa.idchatsystem.Conversation;

import project.insa.idchatsystem.Observers.ConversationHandlerObserver;
import project.insa.idchatsystem.Observers.ConversationObservable;
import project.insa.idchatsystem.Data;
import project.insa.idchatsystem.Message;
import project.insa.idchatsystem.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

class Conversation implements ConversationObservable, Runnable {
    private Socket socket;
    private boolean isOpen;
    private User correspondent;
    private ArrayList<Message> history;
    private ConversationHandlerObserver conversation_observer_handler;

    /**
     * Initialize a passive conversation with a given correspondent
     *
     * @param correspondent : User - reference of the correspondent
     * @param socket : Integer - Socket on which the conversation is
     */
    public Conversation(User correspondent, Socket socket) {
        this.socket = socket;
        this.isOpen = false;
        
        this.correspondent = correspondent;
        
        // Empty for now, will be loaded later
        this.history = new ArrayList<>();
    }
    
    @Override
    public void run() {
        this.loadConversation();
        
        // Listen on the current socket
        this.listen();
    }
    
    /**
     * Method making the conversation to open itself
     *
     */
    public void open() {
        this.isOpen = true;
        
        // Load pasts messages
        this.loadConversation();
        
        //TODO notify the client view to show, passing 'this' as an argument
    }    
    
    /**
     * Method making the conversation to close itself
     *
     */
    public void close() {
        this.isOpen = false;
        
        // Close socket unilaterally
        try {
            this.socket.close();
        }
        catch(IOException e) {
            System.out.println("EXCEPTION WHILE CLOSING THE SOCKET (" + e + ")");
            System.exit(0);
        }
        
        //TODO notify the client view to close this conversation
    }
    
    private void storeMessage(Message message) {

    }

    private Message generateMessage(Data data) {
        return null;
    }

    private void loadConversation() {

    }

    
    private void onReceive(String input) {
        // Generate a Message instance from the given input
        Message newMessage = new Message(input);
        
        // Store the new message
        this.storeMessage(newMessage);
        
        // Check whether this conversation is opened or not
        if(this.isOpen) {
            //TODO notify the client view in order to display the new message
        } else {
            //TODO notify the client view to show a notification from this.correspondent
        }
    }
    
    /**
     * Listening on the current socket for incoming messages
     * 
     */
    private void listen() {
        BufferedReader inputStream = null;
        String inputBuffer = null;
        
        //Getting the input stream
        try {
            inputStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }
        catch(IOException e) {
            System.out.println("EXCEPTION WHILE RETRIEVING THE INPUT STREAM (" + e + ")");
            System.exit(0);
        }
        
        // Continuously listen the input stream
        try {
            while((inputBuffer = inputStream.readLine()) != null) {
                // We received a new message
                this.onReceive(inputBuffer);
            }
        }
        catch(IOException e) {
            // Connection lost with the correspondent
        }
    }

    /**
     * Send a given message to the communication socket
     *
     * @param message : Message - message we want to send
     */
    private void send(Message message) {
        PrintWriter outputStreamLink = null;

	try {
            outputStreamLink = new PrintWriter(this.socket.getOutputStream(),true);
        }
        catch(IOException e) {
            System.out.println("EXCEPTION WHILE RETRIEVING THE SOCKET OUTPUT STREAM (" + e + ")");
            System.exit(0);
        }        
        
        // Send message through the dedicated socket
        outputStreamLink.println(message.toStream());
        
        // Store the message in the local database
        this.storeMessage(message);
        
        //TODO display the newly sent message using client view notification
    }

    @Override
    public void addConversationObserver(ConversationHandlerObserver obs) {
        this.conversation_observer_handler = obs;
    }

    @Override
    public void deleteConversationObserver(ConversationHandlerObserver obs) {
        this.conversation_observer_handler = null;
    }

    @Override
    public void notifyObserversSent(Message message) {
        this.conversation_observer_handler.newMessageSent(message);
    }

    @Override
    public void notifyObserversRcv(Message message) {
        this.conversation_observer_handler.newMessageRcv(message);
    }
    
    /* GETTERS/SETTERS */
    public User getCorrespondent() {
        return this.correspondent;
    }
    
    public void setSocket(Socket newSocket) {
        this.socket = newSocket;
    }
}