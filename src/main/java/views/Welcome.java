package views;

import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;
import service.SendOTPService;

public class Welcome {
    public void welcomeScreen() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the Application:");
        System.out.println("Press 1 to Login");
        System.out.println("Press 2 to SignUp");
        System.out.println("Press 0 to Exit");
        int choice = 0;

        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                signUp();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter email:");
        String email = sc.nextLine();

        try {
            if (UserDAO.isExist(email)) {
                String genOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email, genOTP);
                System.out.println("Enter the OTP:");
                String otp = sc.nextLine();
                if (otp.equals(genOTP)) {
                    new UserView(email).home();
                }
                else {
                    System.out.println("Wrong OTP");
                }
            } else {
                System.out.println("User Not Found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void signUp() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name:");
        String name = sc.nextLine();
        System.out.println("Enter email:");
        String email = sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.println("Enter the OTP:");
        String otp = sc.nextLine();
        if (otp.equals(genOTP)) {
            User user = new User(name, email);
            int response = UserService.saveUser(user);
            try {
                switch (response) {
                    case 0:
                        System.out.println("User Registered");
                        break;
                    case 1:
                        System.out.println("User already Exists");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Wrong OTP");
        }
    }
}
