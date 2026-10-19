package registrationloginapp;

import java.util.Scanner;

public class RegistrationLoginApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        RegistrationLogin user = new RegistrationLogin(input);

        System.out.println("REGISTRATION");
        System.out.print(user.registration());

        while (!user.isRegistrationValid()) {
            System.out.println("Please enter your details again.");
            System.out.print(user.registration());
        }

        System.out.println("\nLOGIN");
        boolean loginStatus = user.loginPrompt();
        System.out.println(user.returnLoginStatus(loginStatus));

        if (loginStatus) {
            Messages messages = new Messages(input);
            messages.start();
        }
    }
}
