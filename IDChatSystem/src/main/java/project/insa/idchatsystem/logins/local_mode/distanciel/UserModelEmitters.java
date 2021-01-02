package project.insa.idchatsystem.logins.local_mode.distanciel;

import project.insa.idchatsystem.Observers.UserModelEmittersObserver;
import project.insa.idchatsystem.servlet.ServerController;

import java.util.ArrayList;

public class UserModelEmitters implements Runnable {
    private LocalUserModelEmitter localEmitter;
    protected String last_user_updated_string;
    protected  boolean emission = true;
    protected String state = "connected";
    private UserModelEmittersObserver obs;
    public UserModelEmitters(UserModelEmittersObserver obs,int emitter_port, ArrayList<Integer> others) {
        this.obs = obs;
        localEmitter = new LocalUserModelEmitter(emitter_port,others);
        this.last_user_updated_string = "";
    }
    public void sendMessage(String message) {
        this.localEmitter.sendBroadcast(message);
        //this.askSendMessage(message); //TODO : implémenter l'iterface

    }
    public void askUpdate() {
        //ask to the other users to send their infos
        this.sendMessage("update");
    }
    public String getState(){
        return this.state;
    }
    public void stopperEmission(){
        this.emission = false;
    }
    public void disconnect(int id) {
        this.stopperEmission();
        String disconnected_str = String.format("%d,disconnected",id);
        this.sendMessage(disconnected_str);
        this.state = "disconnected";
    }
    private void diffuse(){

        this.sendMessage(last_user_updated_string);
        this.obs.newMsgToSend(last_user_updated_string);
    }

    public void diffuseNewUsername(String updatedUserString) {
        this.last_user_updated_string = updatedUserString;
        this.diffuse();
    }
    @Override
    public void run() {
        while(this.emission) {
            //System.out.println("I am in a loooooooooop");
            this.diffuse();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}