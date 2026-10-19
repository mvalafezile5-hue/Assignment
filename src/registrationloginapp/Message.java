package registrationloginapp;

public class Message {

    private String messageID;
    private String messageHash;
    private String recipient;
    private String messageText;

    public Message(String messageID, String messageHash, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageHash = messageHash;
        this.recipient = recipient;
        this.messageText = messageText;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }
}
