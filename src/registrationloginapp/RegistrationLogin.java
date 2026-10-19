package registrationloginapp;

import java.util.Scanner;

public class RegistrationLogin {

    private String username;
    private String password;
    private String phoneNumber;
    private boolean registrationValid;
    private Scanner input;

    public RegistrationLogin() {
        input = new Scanner(System.in);
    }

    public RegistrationLogin(Scanner input) {
        this.input = input;
    }

    public String registration() {
        String result = "";

        System.out.print("Enter username: ");
        username = input.nextLine();

        System.out.print("Enter password: ");
        password = input.nextLine();

        System.out.print("Enter cell phone number (+27XXXXXXXXX): ");
        phoneNumber = input.nextLine();

        boolean usernameValid = checkUserName(username);
        boolean passwordValid = checkPasswordComplexity(password);
        boolean phoneValid = checkCellPhoneNumber(phoneNumber);

        registrationValid = usernameValid && passwordValid && phoneValid;

        if (usernameValid) {
            result += "Username successfully captured.\n";
        } else {
            result += "Username must contain an underscore and be no more than 5 characters.\n";
        }

        if (passwordValid) {
            result += "Password successfully captured.\n";
        } else {
            result += "Password must have at least 8 characters, a capital letter, a number and a special character.\n";
        }

        if (phoneValid) {
            result += "Cell phone number successfully captured.\n";
        } else {
            result += "Cell phone number must start with +27 and have 9 digits after it.\n";
        }

        return result;
    }

    public boolean isRegistrationValid() {
        return registrationValid;
    }

    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char letter = password.charAt(i);

            if (Character.isUpperCase(letter)) {
                hasCapital = true;
            } else if (Character.isDigit(letter)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(letter)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    public boolean checkCellPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\+27[6-9][0-9]{8}");
    }

    public boolean login(String enteredUsername, String enteredPassword) {
        return username != null && password != null
                && username.equals(enteredUsername)
                && password.equals(enteredPassword);
    }

    public boolean loginPrompt() {
        System.out.print("Enter username: ");
        String enteredUsername = input.nextLine();

        System.out.print("Enter password: ");
        String enteredPassword = input.nextLine();

        return login(enteredUsername, enteredPassword);
    }

    public String returnLoginStatus(boolean loginStatus) {
        if (loginStatus) {
            return "Successful login";
        }
        return "Failed login";
    }
}
