package loginapp;

import Kalimdor.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class DashboardProfesorController {

    private Profesor profesor;



    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public void handleViewCourses() {

        FileDataManager fileDataManager = new FileDataManager();
        List<Curs> cursuri = fileDataManager.loadCursuri(profesor.getId());
        VBox newVBox = new VBox();

        for (Curs curs : cursuri) {
            TextArea textArea = new TextArea();
            textArea.setText("Curs: " + curs.getNume() + "\nDescriere: " + curs.getDescriere());
            textArea.setWrapText(true);
            textArea.setEditable(false);

            ScrollPane scrollPane = new ScrollPane(textArea);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(100);

            newVBox.getChildren().add(scrollPane);
        }

        Stage newStage = new Stage();
        Scene scene = new Scene(newVBox, 400, 200);
        newStage.setTitle("Cursuri Profesor");
        newStage.setScene(scene);
        newStage.show();
    }



    public void handleViewStudents() {
        FileDataManager fileDataManager = new FileDataManager();
        List<Curs> cursuri = fileDataManager.loadCursuri(profesor.getId());
        VBox newVBox = new VBox();

        for (Curs curs : cursuri) {
            Button btnCurs = new Button("Vezi Studenti - " + curs.getNume());

            btnCurs.setOnAction(e -> handleViewStudentsForCurs(curs));

            newVBox.getChildren().add(btnCurs);
        }

        Stage newStage = new Stage();
        Scene scene = new Scene(newVBox, 400, 300);
        newStage.setTitle("Studenti cursuri profesor");
        newStage.setScene(scene);
        newStage.show();
    }

    public void handleViewStudentsForCurs(Curs curs) {
        FileDataManager fileDataManager = new FileDataManager();

        List<Student> studenti = fileDataManager.loadStudenti();
        System.out.println("Studenti încarcati: ");
        for (Student student : studenti) {
            System.out.println("ID: " + student.getId() + ", Nume: " + student.getNume() + ", An: " + student.getAn());
        }

        System.out.println("Curs selectat: " + curs.getNume() + ", An curs: " + curs.getAn());

        VBox studentVBox = new VBox();

        for (Student student : studenti) {
            if (student.getAn() == curs.getAn()) {
                TextArea studentText = new TextArea();
                studentText.setText("ID: " + student.getId() + " - " + student.getNume() + " " + student.getPrenume() +
                        "\nGrupa: " + student.getGrupa() +
                        "\nUsername: " + student.getUsername());
                studentText.setEditable(false);

                ScrollPane scrollPane = new ScrollPane(studentText);
                scrollPane.setFitToWidth(true);
                scrollPane.setPrefHeight(100);

                studentVBox.getChildren().add(scrollPane);
            }
        }

        if (studentVBox.getChildren().isEmpty()) {
            TextArea noStudentsText = new TextArea("Nu exista studenti pentru acest curs.");
            noStudentsText.setEditable(false);
            studentVBox.getChildren().add(noStudentsText);
        }
        Stage studentStage = new Stage();
        Scene studentScene = new Scene(studentVBox, 400, 300);
        studentStage.setTitle("Studenti pentru cursul: " + curs.getNume());
        studentStage.setScene(studentScene);
        studentStage.show();
    }









    public void handleViewGrades() {
        FileDataManager fileDataManager = new FileDataManager();
        List<Curs> cursuri = fileDataManager.loadCursuri(profesor.getId());
        VBox gradesVBox = new VBox();
        for (Curs curs : cursuri) {
            Button btnCurs = new Button("Noteaza la - " + curs.getNume());
            btnCurs.setOnAction(e -> openGradingPanel(curs));
            gradesVBox.getChildren().add(btnCurs);
        }

        Stage gradesStage = new Stage();
        Scene gradesScene = new Scene(gradesVBox, 400, 300);
        gradesStage.setTitle("Note cursuri profesor");
        gradesStage.setScene(gradesScene);
        gradesStage.show();
    }

    private void openGradingPanel(Curs curs) {
        VBox gradingVBox = new VBox(10);

        TextArea nameField = new TextArea();
        nameField.setPromptText("Introdu numele studentului");

        TextArea surnameField = new TextArea();
        surnameField.setPromptText("Introdu prenumele studentului");

        TextArea gradeField = new TextArea();
        gradeField.setPromptText("Introdu nota (ex: 10)");

        Button saveButton = new Button("Salveaza Nota");
        saveButton.setOnAction(e -> saveGrade(curs, nameField.getText(), surnameField.getText(), gradeField.getText()));

        gradingVBox.getChildren().addAll(nameField, surnameField, gradeField, saveButton);

        Stage gradingStage = new Stage();
        Scene gradingScene = new Scene(gradingVBox, 300, 200);
        gradingStage.setTitle("Notare pentru curs: " + curs.getNume());
        gradingStage.setScene(gradingScene);
        gradingStage.show();
    }

    private void saveGrade(Curs curs, String nume, String prenume, String notaText) {
        if (nume.isEmpty() || prenume.isEmpty() || notaText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Campuri obligatorii", "Toate campurile sunt obligatorii!");
            return;
        }
        try {
            int nota = Integer.parseInt(notaText);
            FileDataManager fileDataManager = new FileDataManager();
            List<Student> studenti = fileDataManager.loadStudenti();

            Student student = studenti.stream()
                    .filter(s -> s.getNume().equalsIgnoreCase(nume) && s.getPrenume().equalsIgnoreCase(prenume))
                    .findFirst()
                    .orElse(null);

            if (student == null) {
                showAlert(Alert.AlertType.ERROR, "Eroare", "Studentul nu a fost gasit!");
                return;
            }

            if (student.getAn() != curs.getAn()) {
                showAlert(Alert.AlertType.ERROR, "Eroare", "Studentul nu este în același an cu cursul!");
                return;
            }
            String linie = curs.getId() + "," + student.getId() + "," + nota;
            java.nio.file.Files.write(java.nio.file.Paths.get("Note.txt"), (linie + System.lineSeparator()).getBytes(), java.nio.file.StandardOpenOption.APPEND);

            showAlert(Alert.AlertType.INFORMATION, "Succes", "Nota a fost salvata cu succes!");
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.ERROR, "Eroare", "Nota trebuie să fie un numar valid!");
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Eroare", "A apărut o problema la salvarea notei!");
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
