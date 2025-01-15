package loginapp;

import Kalimdor.Cript;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import Kalimdor.FileDataManager;
import Kalimdor.Student;
import Kalimdor.Profesor;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private FileDataManager fileDataManager = new FileDataManager();

    @FXML
    private void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidUser(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Success", "Welcome, " + username + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
        }
    }

    private boolean isValidUser(String username, String password) {
        for (Student student : fileDataManager.createStudentsData()) {
            if (student.getUsername().equals(username) && student.getPassword().equals(Cript.Cript(password))) {
                CurrentUser.setIdStudent(student.getId());
                handleLoginSuccessStudent(student);
                return true;
            }
        }
        for (Profesor profesor : fileDataManager.getProfesori()) {
            if (profesor.getUsername().equals(username) && profesor.getPassword().equals(Cript.Cript(password))) {
                CurrentUser.setIdProfesor(profesor.getId());
                handleLoginSuccessProfesor(profesor);
                return true;
            }
        }
        return false;
    }

    private void handleLoginSuccessStudent(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardStud.fxml"));
            Parent root = loader.load();

            Stage studentStage = (Stage) usernameField.getScene().getWindow();
            studentStage.setScene(new Scene(root));
            studentStage.setTitle("Dashboard Student");
            studentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load student dashboard.");
        }
    }

    private void handleLoginSuccessProfesor(Profesor profesor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardProfesor.fxml"));
            Parent root = loader.load();

            DashboardProfesorController controller = loader.getController();

            controller.setProfesor(profesor);

            Stage profesorStage = (Stage) usernameField.getScene().getWindow();
            profesorStage.setScene(new Scene(root));
            profesorStage.setTitle("Dashboard Profesor");
            profesorStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load professor dashboard.");
        }
    }


    @FXML
    private void handleRegisterButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = fxmlLoader.load();

            Stage registerStage = new Stage();
            registerStage.setTitle("Register");
            registerStage.setScene(new Scene(root));
            registerStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
