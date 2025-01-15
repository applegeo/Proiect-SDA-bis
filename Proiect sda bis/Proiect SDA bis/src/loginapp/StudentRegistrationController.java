package loginapp;

import Kalimdor.Cript;
import Kalimdor.FileDisplay;
import Kalimdor.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Kalimdor.Student;

public class StudentRegistrationController {

    @FXML
    private TextField numeField;
    @FXML
    private TextField prenumeField;
    @FXML
    private TextField grupaField;
    @FXML
    private TextField anField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private FileDisplay fileDisplay = new FileDisplay();


    @FXML
    private void handleSubmitButton() {
        String nume = numeField.getText();
        String prenume = prenumeField.getText();
        int an = Integer.parseInt(anField.getText());
        String grupa = grupaField.getText();

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (nume.isEmpty() || prenume.isEmpty() || grupa.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Toate câmpurile trebuie completate!");
            return;
        }


        int nextId = fileDisplay.getNextStudentId();


        Student newStudent = new Student(nextId, nume, prenume, an,grupa , username, Cript.Cript(password));

        newStudent.setId(nextId);

        try (FileWriter fw = new FileWriter(fileDisplay.filaStudenti, true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println(newStudent.getId() + "," + newStudent.getNume() + "," + newStudent.getPrenume() + "," + newStudent.getAn() + ","+newStudent.getGrupa() +"," + newStudent.getUsername() + "," + newStudent.getPassword());

            showAlert(Alert.AlertType.INFORMATION, "Success", "Student adaugat cu succes!");

            numeField.clear();
            prenumeField.clear();
            grupaField.clear();
            usernameField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "File Error", "Nu s-a putut scrie în fisierul Studenti.txt.");
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
