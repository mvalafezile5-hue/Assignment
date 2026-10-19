package registrationloginapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Messages {

    private Scanner input;
    private Random random;
    private List<Message> sentMessages;
    private List<Message> storedMessages;
    private int totalMessagesSent;

    public Messages() {
        this(new Scanner(System.in));
    }

    public Messages(Scanner input) {
        this.input = input;
        random = new Random();
        sentMessages = new ArrayList<>();
        storedMessages = new ArrayList<>();
        totalMessagesSent = 0;
    }

    public void start() {
        int option = 0;

        while (option != 3) {
            System.out.println("\nWelcome to QuickChat");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            option = readInteger();

            if (option == 1) {
                sendMessages();
            } else if (option == 2) {
                printMessages();
            } else if (option == 3) {
                System.out.println("Good Bye");
                System.out.println("Total messages sent: " + returnTotalMessages());
            } else {
                System.out.println("Please choose 1, 2 or 3.");
            }
        }
    }

    private int readInteger() {
        while (!input.hasNextInt()) {
            System.out.print("Please enter a number: ");
            input.nextLine();
        }

        int number = input.nextInt();
        input.nextLine();
        return number;
    }

    public void sendMessages() {
        System.out.print("How many messages would you like to send? ");
        int numberOfMessages = readInteger();

        if (numberOfMessages < 1) {
            System.out.println("Please enter at least 1 message.");
            return;
        }

        for (int i = 0; i < numberOfMessages; i++) {
            System.out.println("\nMessage " + (i + 1));
            String recipient = readRecipient();
            String messageText = readMessage();

            Message message = createMessage(recipient, messageText, i + 1);

            System.out.println("Message ID: " + message.getMessageID());
            System.out.println("Message Hash: " + message.getMessageHash());
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Choose an option: ");

            int choice = readInteger();

            if (choice == 1) {
                sentMessages.add(message);
                totalMessagesSent++;
                System.out.println("Message successfully sent.");
                displayMessage(message);
            } else if (choice == 2) {
                System.out.println("Message disregarded.");
            } else if (choice == 3) {
                storedMessages.add(message);
                storeMessages();
                System.out.println("Message successfully stored.");
            } else {
                System.out.println("Invalid choice. Message was not sent.");
            }
        }

        System.out.println("Total messages sent: " + returnTotalMessages());
    }

    private String readRecipient() {
        while (true) {
            System.out.print("Enter recipient cell number (+27XXXXXXXXX): ");
            String recipient = input.nextLine();

            if (checkRecipientCell(recipient)) {
                return recipient;
            }

            System.out.println("Invalid number. Use +27 followed by 9 digits.");
        }
    }

    private String readMessage() {
        while (true) {
            System.out.print("Write your message (maximum 250 characters): ");
            String messageText = input.nextLine();

            if (messageText.length() <= 250) {
                return messageText;
            }

            System.out.println("Your message is too long. Please shorten it.");
        }
    }

    public boolean checkMessageID(String messageID) {
        return messageID != null && messageID.matches("[0-9]{10}");
    }

    public boolean checkRecipientCell(String recipient) {
        return recipient != null && recipient.matches("\\+27[6-9][0-9]{8}");
    }

    public String createMessageHash(String messageID, int messageNumber, String messageText) {
        String trimmedMessage = messageText.trim();
        String firstWord = "";
        String lastWord = "";

        if (!trimmedMessage.isEmpty()) {
            String[] words = trimmedMessage.split("\\s+");
            firstWord = words[0].toUpperCase();
            lastWord = words[words.length - 1].toUpperCase();
        }

        return messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord + " " + lastWord;
    }

    private Message createMessage(String recipient, String messageText, int messageNumber) {
        String messageID = String.format("%010d", Math.floorMod(random.nextLong(), 10000000000L));
        String messageHash = createMessageHash(messageID, messageNumber, messageText);

        return new Message(messageID, messageHash, recipient, messageText);
    }

    public void displayMessage(Message message) {
        System.out.println("Message ID: " + message.getMessageID());
        System.out.println("Message Hash: " + message.getMessageHash());
        System.out.println("Recipient: " + message.getRecipient());
        System.out.println("Message: " + message.getMessageText());
    }

    public void printMessages() {
        if (sentMessages.isEmpty()) {
            System.out.println("Coming Soon");
            return;
        }

        System.out.println("Recently sent messages:");
        for (Message message : sentMessages) {
            displayMessage(message);
        }
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    private void storeMessages() {
        try (FileWriter writer = new FileWriter("messages.json")) {
            writer.write("[");
            for (int i = 0; i < storedMessages.size(); i++) {
                Message message = storedMessages.get(i);

                if (i > 0) {
                    writer.write(",");
                }

                writer.write("{");
                writer.write("\"messageID\":\"" + jsonEscape(message.getMessageID()) + "\",");
                writer.write("\"messageHash\":\"" + jsonEscape(message.getMessageHash()) + "\",");
                writer.write("\"recipient\":\"" + jsonEscape(message.getRecipient()) + "\",");
                writer.write("\"message\":\"" + jsonEscape(message.getMessageText()) + "\"");
                writer.write("}");
            }
            writer.write("]");
        } catch (IOException e) {
            System.out.println("Could not store messages.");
        }
    }

    private String jsonEscape(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
