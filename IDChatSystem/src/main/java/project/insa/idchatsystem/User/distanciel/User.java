
package project.insa.idchatsystem.User.distanciel;

import project.insa.idchatsystem.Exceptions.Uninitialized;

import java.net.*;
import java.sql.Timestamp;

public class User {

    private String username;

    private int id = -1;

    private final String ipAddress;

    private Timestamp lastSeen;
    private int conversationHandlerListenerPort;
    private static String current_username = "";
    private static int current_id;
    private static String current_ipAddress;
    private static int current_conversationHandlerListenerPort;

    /************************Current user methods***************************************/
    public static void init_current_user(int id) {
        User.current_id = id;
        User.current_ipAddress = User.calculate_current_ip();
    }
    public static String calculate_current_ip()  {
        return "127.0.0.1";
    }
    public static void set_current_username(String username) throws Uninitialized {
        if (User.current_id == -1) {
            throw new Uninitialized("CurrentUser not initialized");
        }
        User.current_username = username;
    }

    public static String get_current_username() throws Uninitialized {
        if (User.current_id == -1) {
            throw new Uninitialized("CurrentUser not initialized");
        }
        return User.current_username;
    }

    public static int get_current_id() throws Uninitialized {
        if (User.current_id == -1) {
            throw new Uninitialized("CurrentUser not initialized");
        }
        return User.current_id;
    }

    public static String get_current_ipAddress() throws Uninitialized {
        if (User.current_id == -1) {
            throw new Uninitialized("CurrentUser not initialized");
        }
        return User.current_ipAddress;
    }
    public static String current_user_transfer_string() {
        return String.format("%s,%d,%s,%d",User.current_username,User.current_id,User.current_ipAddress,User.current_conversationHandlerListenerPort);
    }

    public static User getCurrentUser() throws Uninitialized {
        return new User(User.get_current_username(), User.get_current_id(), User.get_current_ipAddress());
    }
    public static void setCurrentConversationHandlerListenerPort(int conversationHandlerListenerPort) {
        User.current_conversationHandlerListenerPort = conversationHandlerListenerPort;
    }
    /***********************Other users methods***************************/
    public User(String username, int id, String ipAddress) {
        this.username = username;
        this.id = id;
        this.ipAddress = ipAddress;
        this.lastSeen = new Timestamp(System.currentTimeMillis());
        this.conversationHandlerListenerPort = -1;
    }

    public void setConversationHandlerListenerPort(int conversationHandlerListenerPort) {
        this.conversationHandlerListenerPort = conversationHandlerListenerPort;
    }

    public String get_username() {
        return this.username;
    }

    public int get_id() {
        return this.id;
    }

    public String get_ipAddress() {
        return this.ipAddress;
    }

    public Timestamp get_lastSeen() {
        return this.lastSeen;
    }
    public String transfer_string() {
        return String.format("%s,%d,%s",this.username,this.id,this.ipAddress);
    }
    public void setUsername(String username) { this.username = username;}
    public void setLastSeen(Timestamp lastSeen) {this.lastSeen = lastSeen;}

    /*********************Utilities methods*****************************/
    @Override
    public String toString() {
        return String.format("User %s ; id %d ; ipAddress %s ; lastSeen %s ; convListPort %d", this.username, this.id, this.ipAddress, this.lastSeen.toString(),this.conversationHandlerListenerPort);
    }

    public int getConversationHandlerListenerPort() {
        return conversationHandlerListenerPort;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User))
            return false;
        User other = (User) o;
        if (this.id == other.get_id())
            return true;
        else
            return false;
    }

}
