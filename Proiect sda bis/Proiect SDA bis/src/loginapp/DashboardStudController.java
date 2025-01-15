package loginapp;

import Kalimdor.Note;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;
import java.io.*;

public class DashboardStudController {


    @FXML
    public void handleViewCourses() {
        int idStudentCurent = CurrentUser.getIdStudent();
        int anStudent = loadStudentYear("Studenti.txt", idStudentCurent);

        if (anStudent == -1) {
            showAlert("Cursuri", "Studentul nu a fost gasit.");
            return;
        }
        List<String> matchingCourses = loadCoursesForYear("Cursuri.txt", anStudent);
        if (matchingCourses.isEmpty()) {
            showAlert("Cursuri", "Nu exista cursuri disponibile pentru anul tău.");
        } else {
            StringBuilder sb = new StringBuilder("Cursurile tale:\n");
            for (String course : matchingCourses) {
                sb.append(course).append("\n");
            }
            showAlert("Cursuri", sb.toString());
        }
    }

    public int loadStudentYear(String fileName, int idStudent) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    int id = Integer.parseInt(parts[0].trim());
                    if (id == idStudent) {
                        return Integer.parseInt(parts[3].trim());
                    }
                }
            }
        } catch (IOException e) {
            showAlert("Eroare", "Nu am putut incarca fisierul de studenti.");
            e.printStackTrace();
        }
        return -1;
    }


    private  List<String> loadCoursesForYear(String fileName, int anStudent) {
        List<String> courses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    int anCurs = Integer.parseInt(parts[4].trim());
                    if (anCurs == anStudent) {
                        courses.add(parts[1].trim());
                    }
                }
            }
        } catch (IOException e) {
            showAlert("Eroare", "Nu am putut incarca fisierul de cursuri.");
            e.printStackTrace();
        }
        return courses;
    }


    @FXML
    public  void handleViewGrades() {
        int idStudentCurent = CurrentUser.getIdStudent();

        Map<Integer, String> courses = loadCoursesFromFile("Cursuri.txt");

        List<String> grades = loadStudentGrades("Note.txt", idStudentCurent, courses);
        if (grades.isEmpty()) {
            showAlert("Note", "Nu exista note disponibile pentru studentul curent.");
        } else {
            StringBuilder sb = new StringBuilder("Notele tale:\n");
            for (String grade : grades) {
                sb.append(grade).append("\n");
            }
            showAlert("Note", sb.toString());
        }
    }

    private  List<String> loadStudentGrades(String fileName, int idStudent, Map<Integer, String> courses) {
        List<String> grades = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    int idCurs = Integer.parseInt(parts[0].trim());
                    int idStudentNota = Integer.parseInt(parts[1].trim());
                    int nota = Integer.parseInt(parts[2].trim());

                    if (idStudentNota == idStudent && nota >= 5) {
                        String numeCurs = courses.getOrDefault(idCurs, "Curs necunoscut");
                        grades.add("Curs: " + numeCurs + ", Nota: " + nota);
                    }
                }
            }
        } catch (IOException e) {
            showAlert("Eroare", "Nu am putut incarca notele.");
            e.printStackTrace();
        }
        return grades;
    }


    @FXML
    private void handleViewAverage() {
        int idStudentCurent = CurrentUser.getIdStudent();

        List<Note> notes = loadNotesFromFile("Note.txt");

        List<Integer> studentGrades = notes.stream()
                .filter(note -> note.getIdStudent() == idStudentCurent)
                .map(Note::getNota)
                .collect(Collectors.toList());

        if (studentGrades.isEmpty()) {
            showAlert("Media", "Nu ai note inregistrate.");
        } else {
            boolean hasResits = studentGrades.stream().anyMatch(grade -> grade < 5);

            List<Integer> validGrades = studentGrades.stream()
                    .filter(grade -> grade >= 5)
                    .collect(Collectors.toList());

            if (validGrades.isEmpty()) {
                showAlert("Media", "Toate notele sunt sub 5. Nu se poate calcula media.");
            } else {
                double average = validGrades.stream()
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0.0);

                if (hasResits) {
                    showAlert("Media", "Ai restante, dar media notelor peste 5 este: " + String.format("%.2f", average));
                } else {
                    showAlert("Media", "Media ta este: " + String.format("%.2f", average));
                }
            }
        }
    }





    @FXML
    public void handleViewResits() {
        int studentId = CurrentUser.getIdStudent();
        List<Note> notes = loadNotesFromFile("Note.txt");
        Map<Integer, String> courses = loadCoursesFromFile("Cursuri.txt");

        List<String> resits = notes.stream()
                .filter(note -> note.getIdStudent() == studentId && note.getNota() < 5)
                .map(note -> {
                    String courseName = courses.getOrDefault(note.getIdCurs(), "Curs necunoscut");
                    return "Curs: " + courseName + ", Nota: " + note.getNota();
                })
                .collect(Collectors.toList());

        if (resits.isEmpty()) {
            showAlert("Restante", "Nu ai restante.");
        } else {
            showAlert("Restante", String.join("\n", resits));
        }
    }


    private List<Note> loadNotesFromFile(String fileName) {
        List<Note> notes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine(); // Sari peste prima linie (antetul)
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int idCurs = Integer.parseInt(parts[0].trim());
                    int idStudent = Integer.parseInt(parts[1].trim());
                    int nota = Integer.parseInt(parts[2].trim());
                    notes.add(new Note(idCurs, idStudent, nota));
                }
            }
        } catch (IOException e) {
            showAlert("Eroare", "Nu am putut incarca datele notelor.");
            e.printStackTrace();
        }
        return notes;
    }
    private  Map<Integer, String> loadCoursesFromFile(String fileName) {
        Map<Integer, String> courses = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    int idCurs = Integer.parseInt(parts[0].trim());
                    String numeCurs = parts[1].trim();
                    courses.put(idCurs, numeCurs);
                }
            }
        } catch (IOException e) {
            showAlert("Eroare", "Nu am putut incarca datele cursurilor.");
            e.printStackTrace();
        }
        return courses;
    }



    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

}
