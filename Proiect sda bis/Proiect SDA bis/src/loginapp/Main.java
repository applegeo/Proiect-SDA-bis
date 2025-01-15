package loginapp;

import Kalimdor.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {

    private boolean isRunning = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();

        Thread consoleMenuThread = new Thread(new Runnable() {
            @Override
            public void run() {
                showConsoleMenu();
            }
        });

        consoleMenuThread.setDaemon(true);
        consoleMenuThread.start();
    }

    private void showConsoleMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!isRunning) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                continue;
            }

            System.out.println("Selectati opțiunea dorita:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Close");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    handleLogin(scanner);
                    break;
                case "2":
                    handleRegister();
                    break;
                case "3":
                    handleClose();
                    return;
                default:
                    System.out.println("Optiune invalida.");
            }
        }
    }


    private void handleLogin(Scanner scanner) {
        System.out.println("Introduceti username-ul:");
        String username = scanner.nextLine();

        System.out.println("Introduceti parola:");
        String parola = scanner.nextLine();


        if (validateLogin(username, parola)) {
            System.out.println("Login reusit!");
        } else {
            System.out.println("Username sau parola incorecta.");
        }
    }

    private boolean validateLogin(String username, String parola) {
        boolean isStudent = false;
        boolean isProfesor = false;

        FileDataManager fileDataManager = new FileDataManager();

        for (Student student : fileDataManager.createStudentsData()) {
            if (student.getUsername().equals(username) && student.getPassword().equals(Cript.Cript(parola))) {
                CurrentUser.setIdStudent(student.getId());
                isStudent = true;
                break;
            }
        }

        for (Profesor profesor : fileDataManager.getProfesori()) {
            if (profesor.getUsername().equals(username) && profesor.getPassword().equals(Cript.Cript(parola))) {
                CurrentUser.setIdProfesor(profesor.getId());
                isProfesor = true;
                break;
            }
        }
        if (isStudent) {
            showStudentMenu();
            return true;
        }
        if (isProfesor) {
            showProfesorMenu();
            return true;
        }

        return false;
    }


    private void showStudentMenu() {
        DashboardStudController dashboardController = new DashboardStudController();

        System.out.println("Meniu Student:");
        System.out.println("1. Vezi cursuri");
        System.out.println("2. Vezi note");
        System.out.println("3. Vezi restante");
        System.out.println("4. Logout");

        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                System.out.println("Vizualizeaza cursurile studentului...");
                dashboardController.handleViewCourses();
                break;
            case "2":
                System.out.println("Vizualizeaza notele studentului...");
                dashboardController.handleViewGrades();
                break;
            case "3":
                System.out.println("Vizualizeaza restanțele studentului...");
                dashboardController.handleViewResits();
                break;
            case "4":
                System.out.println("Logout din contul de student.");
                break;
            default:
                System.out.println("Optiune invalida!");
        }
    }



    private void showProfesorMenu() {
        System.out.println("Meniu Profesor:");
        System.out.println("1. Vezi cursuri predare");
        System.out.println("2. Notează studenti");
        System.out.println("3. Logout");

        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                System.out.println("Vizualizeaza cursurile profesorului...");
                break;
            case "3":
                System.out.println("Notează studenti...");
                break;
            case "2":
                System.out.println("Vezi Studenti");
                break;
            default:
                System.out.println("Optiune invalida!");
        }
    }

    private void handleRegister() {
        isRunning = false;
        Platform.runLater(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Tipul de inregistrare:");
            System.out.println("1. Inregistrare Student");
            System.out.println("2. Inregistrare Profesor");

            String option = scanner.nextLine();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
                Parent root = loader.load();
                RegisterController registerController = loader.getController();

                switch (option) {
                    case "1":
                        System.out.println("Se deschide interfata pentru inregistrare student.");
                        registerController.handleStudentRegistration();
                        break;
                    case "2":
                        System.out.println("Se deschide interfata pentru inregistrare profesor.");
                        registerController.handleTeacherRegistration();
                        break;
                    default:
                        System.out.println("Opțiune invalida.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                isRunning = true;
            }
        });
    }



    private void handleClose() {
        isRunning = false;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("Inchiderea aplicatiei.");
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
