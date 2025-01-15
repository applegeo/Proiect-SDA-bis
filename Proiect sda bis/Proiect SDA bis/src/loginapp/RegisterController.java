package loginapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField usernameField;

    @FXML
    public void handleStudentRegistration() {
        try {
            if (usernameField != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("student_registration.fxml"));
                Parent root = fxmlLoader.load();
                Stage registrationStage = new Stage();
                registrationStage.setTitle("Student Registration");
                registrationStage.setScene(new Scene(root));
                registrationStage.show();

                Stage currentStage = (Stage) usernameField.getScene().getWindow();
                currentStage.close();
            } else {
                System.out.println("usernameField este null!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void handleTeacherRegistration() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TeacherRegistration.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Teacher Registration");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
