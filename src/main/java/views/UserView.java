package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;

    UserView(String email) {
        this.email = email;
    }

    public void home() {
        try {
            Scanner sc = new Scanner(System.in);
            do {
                System.out.println("Welcome " + this.email);
                System.out.println("Press 1 to Show Hidden Files");
                System.out.println("Press 2 to Hide a new File");
                System.out.println("Press 3 to UnHide a File");
                System.out.println("Press 0 to Exit");

                int ch = Integer.parseInt(sc.nextLine());

                switch (ch) {
                    case 1:
                        try {
                            List<Data> files = DataDAO.getAllFiles(this.email);
                            System.out.println("ID - File Name");
                            for (Data file : files) {
                                System.out.println(file.getId() + " - " + file.getFileName());
                            }
                        } catch (SQLException  e) {
                            e.printStackTrace();
                        }
                        break;

                    case 2:
                        System.out.println("Enter the File path");
                        String path = sc.nextLine();
                        File f = new File(path);
                        Data file = new Data(0, f.getName(), path, this.email);
                        DataDAO.hideFile(file);
                        break;

                    case 3:
                        try {
                            List<Data> files = DataDAO.getAllFiles(this.email);
                            System.out.println("ID - File Name");
                            for (Data fil : files) {
                                System.out.println(fil.getId() + " - " + fil.getFileName());
                            }
                            System.out.println("Enter the ID of File to unHide");
                            int id = Integer.parseInt(sc.nextLine());
                            boolean isValidID = false;
                            for (Data data : files) {
                                if (data.getId() == id) {
                                    isValidID = true;
                                    break;
                                }
                            }

                            if (isValidID) {
                                DataDAO.unHide(id);
                            } else {
                                System.out.println("Wrong ID");
                            }
                        } catch (SQLException | IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 0:
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
