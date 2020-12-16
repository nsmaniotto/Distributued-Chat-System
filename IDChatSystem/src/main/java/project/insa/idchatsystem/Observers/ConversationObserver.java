package project.insa.idchatsystem.Observers;

import project.insa.idchatsystem.Message;

public interface ConversationObserver {
    public void newMessageReceived(Message receivedMessage, boolean isCurrentConversation);
    public void newMessageSent(Message sentMessage);
}