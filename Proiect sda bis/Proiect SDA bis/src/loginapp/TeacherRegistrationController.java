package loginapp;

import Kalimdor.FileDisplay;
import Kalimdor.Profesor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class TeacherRegistrationController {

    @FXML
    private TextField numeField;

    @FXML
    private TextField prenumeField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private FileDisplay fileDisplay = new FileDisplay();

    @FXML
    private void handleSubmitButton() {
        String nume = numeField.getText();
        String prenume = prenumeField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (nume.isEmpty() || prenume.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Eroare", "Toate campurile trebuie completate!");
            return;
        }

        int professorId = fileDisplay.getNextProfId();

        Profesor professor = new Profesor(professorId, nume, prenume, username, password);

        fileDisplay.displayProfessors(new Profesor[]{professor});

        showAlert(Alert.AlertType.INFORMATION, "Succes", "Profesorul a fost inregistrat cu succes cu ID-ul " + professorId + "!");

        clearFields();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        numeField.clear();
        prenumeField.clear();
        usernameField.clear();
        passwordField.clear();
    }
}
