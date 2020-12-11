/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conversation;

import project.insa.idchatsystem.ClientController;
import project.insa.idchatsystem.Conversation.ConversationHandler;
import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;

/**
 *
 * @author smani
 */
public class ClientTestNathan {    
    public ClientTestNathan() {
        int id = 10;
        ArrayList<Integer> arrayBroadCast = new ArrayList<Integer>();
        arrayBroadCast.add(2001);
        ClientController controller = new ClientController(id,2000,2010,arrayBroadCast,2500,2501);
        try {
            Thread.sleep(5000);//On laisse le temps d'entrer le login
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if(User.get_current_username().equals(String.format("--user%d",id))) {
                System.out.print("Error, the login has not be taken into account\n");
            }
            else {
                System.out.printf("All Green, login correct ? %s\n",User.get_current_username());
            }
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }
}