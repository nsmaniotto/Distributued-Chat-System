
package project.insa.idchatsystem;

import project.insa.idchatsystem.Exceptions.Uninitialized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class LocalUserModel implements ObservableUserModel,UserModel {
    private final HashMap<Integer,User> users;
    private final LocalUserModelEmitter emitter;
    private final ArrayList<UsersStatusObserver> connected_user_observers;

    public LocalUserModel(int id)  {
        this.users = new HashMap<Integer,User>();
        User.init_current_user(id);
        new Thread(new LocalUserModelReceiver(this)).start();
        this.emitter = new LocalUserModelEmitter();
        this.connected_user_observers = new ArrayList<UsersStatusObserver>();
    }
    @Override
    public void setUsername(String newUserName) {
        try {
            User.set_current_username(newUserName);
        } catch (Uninitialized uninitialized) {
            uninitialized.printStackTrace();
        }
    }
    @Override
    public void addOnlineUser(User user) {
        boolean new_user = this.users.containsKey(user.get_id());
        this.users.put(user.get_id(),user);//Replace automatically the previous version if already in the HashMap
        if(new_user) {
            //notify observers
            this.notifyNewUserObservers(user);
        }
    }
    @Override
    public void removeOnlineUser(User user) {
        if(this.users.remove(user.get_id()) == null) {
            System.out.printf("%s is not connected%n",user.toString());
        }
        else {
            this.notifyDisconnectedObservers(user);
        }
    }
    @Override
    public HashMap<Integer,User> getOnlineUsers() {
        return this.users;
    }
    @Override
    public void diffuseNewUsername(){
        String response = User.current_user_transfer_string();
        this.emitter.diffuseNewUsername(response);
    }
    @Override
    public boolean checkAvailable(String username) {
        for (Map.Entry<Integer, User> integerUserEntry : this.users.entrySet()) {
            User user = (User) ((Map.Entry) integerUserEntry).getValue();
            if (user.get_username().equals(username)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addObserver(UsersStatusObserver obs) {
        this.connected_user_observers.add(obs);
    }

    @Override
    public void deleteObserver(UsersStatusObserver obs) {
        this.connected_user_observers.remove(obs);
    }

    @Override
    public void notifyNewUserObservers(User user) {
        for (UsersStatusObserver obs :
                this.connected_user_observers) {
            obs.onlineUser(this,user);
        }
    }
    @Override
    public void notifyDisconnectedObservers(User user) {
        for (UsersStatusObserver obs :
                this.connected_user_observers) {
            obs.offlineUser(this,user);
        }
    }

}
