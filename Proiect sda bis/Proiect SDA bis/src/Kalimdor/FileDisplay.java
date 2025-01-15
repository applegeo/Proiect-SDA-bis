package Kalimdor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class FileDisplay {

    public String filaStudenti = "Studenti.txt";
    String filaCursuri = "Cursuri.txt";
    String filaProfesori = "Profesori.txt";

    String line="";
    String splitter=",";

    public void displayStudents(Student[] students) {
        try {
            List<String> existingStudents = StudDeja();
            int nextStudentId = getNextStudentId();

            try (FileWriter writer = new FileWriter(filaStudenti, true)) {
                for (Student student : students) {
                    String fullName = student.getNume() + " " + student.getPrenume();

                    if (!existingStudents.contains(fullName)) {
                        student.setId(nextStudentId);
                        writer.write(student.getId() + "," +
                                student.getNume() + "," +
                                student.getPrenume() + "," +
                                student.getAn() + "," +
                                student.getGrupa() + "," +
                                student.getUsername() + "," +
                                Cript.Cript(student.getPassword()) + "\n");
                        System.out.println("Studentul " + fullName + " a fost adaugat.");
                        nextStudentId++;
                    } else {
                        System.out.println("Studentul " + fullName + " exista deja și nu va fi adaugat.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> StudDeja() {
        List<String> existingStudents = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filaStudenti))) {
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(splitter);
                if (parts.length > 2) {
                    String fullName = parts[1].trim() + " " + parts[2].trim();
                    existingStudents.add(fullName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return existingStudents;
    }

    public int getNextStudentId() {
        int maxId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filaStudenti))) {
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(splitter);
                if (parts.length > 0) {
                    int currentId = Integer.parseInt(parts[0].trim());
                    if (currentId > maxId) {
                        maxId = currentId;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maxId + 1;
    }

    public int getNextProfId() {
        int maxId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filaProfesori))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(splitter);
                    if (parts.length > 0 && !parts[0].trim().isEmpty()) {
                        try {
                            int currentId = Integer.parseInt(parts[0].trim());
                            if (currentId > maxId) {
                                maxId = currentId;
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Eroare la conversia ID-ului din linia: " + line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return maxId + 1;
    }

    public void displayProfessors(Profesor[] professors) {
        try {

            List<String> existingProfessors = ProfDeja();
            int nextProfId = getNextProfId();

            try (FileWriter writer = new FileWriter(filaProfesori, true)) {
                for (Profesor professor : professors) {
                    String fullName = professor.getNume() + " " + professor.getPrenume();


                    if (!existingProfessors.contains(fullName)) {
                        professor.setId(nextProfId);

                        writer.write(professor.getId() + "," +
                                professor.getNume() + "," +
                                professor.getPrenume() + "," +
                                professor.getUsername() + "," +
                                Cript.Cript(professor.getPassword()) + "\n");
                        System.out.println("Profesorul " + fullName + " a fost adaugat.");
                    } else {
                        System.out.println("Profesorul " + fullName + " exista deja și nu va fi adaugat.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private List<String> ProfDeja() {
        List<String> existingProfessors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filaProfesori))) {
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(splitter);
                if (parts.length > 2) {
                    String fullName = parts[1].trim() + " " + parts[2].trim();
                    existingProfessors.add(fullName);
                }
            }
        } catch (IOException e) {
            System.out.println("Fisierul nu a putut fi citit: " + e.getMessage());
        }

        return existingProfessors;
    }


    public void displayCourses(Curs[] courses) {
        try {

            List<String> existingCourses = CursDeja();

            try (FileWriter writer = new FileWriter(filaCursuri, true)) {
                for (Curs course : courses) {
                    String courseName = course.getNume();


                    if (!existingCourses.contains(courseName)) {
                        writer.write(course.getId() + "," +
                                course.getNume() + "," +
                                course.getDescriere() + "," +
                                course.getIdProf() + "," +
                                course.getAn() + "\n");
                        System.out.println("Cursul " + courseName + " a fost adaugat.");
                    } else {
                        System.out.println("Cursul " + courseName + " exista deja și nu va fi adaugat.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<String> CursDeja() {
        List<String> existingCourses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filaCursuri))) {
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(splitter);
                if (parts.length > 1) {
                    String courseName = parts[1].trim();
                    existingCourses.add(courseName);
                }
            }
        } catch (IOException e) {
            System.out.println("Fisierul nu a putut fi citit: " + e.getMessage());
        }

        return existingCourses;
    }



}
