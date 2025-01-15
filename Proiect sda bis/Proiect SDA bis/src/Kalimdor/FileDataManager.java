package Kalimdor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class FileDataManager {

    public String filaStudenti = "Studenti.txt";
    String filaCursuri = "Cursuri.txt";
    String filaProfesori = "Profesori.txt";
    String filaNote = "Note.txt";

    String line="";
    String splitter=",";

    public List<Student> studenti = new ArrayList<>();
    public List<Profesor> profesori = new ArrayList<>();
    public List<Curs> cursuri = new ArrayList<>();
    public List<Note> note = new ArrayList<>();

    public List<Student> createStudentsData() {
        try (BufferedReader br = new BufferedReader(new FileReader(filaStudenti))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] date = line.split(splitter);

                int ID = Integer.parseInt(date[0].trim());
                String NUME = date[1].trim();
                String PRENUME = date[2].trim();
                int AN = Integer.parseInt(date[3].trim());
                String GRUPA = date[4].trim();
                String USERNAME = date[5].trim();
                String PASSWORD = date[6].trim();

                Student student = new Student(ID, NUME, PRENUME, AN, GRUPA, USERNAME, PASSWORD);
                studenti.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studenti;
    }


    public void createProfesorData() {
        try (BufferedReader br = new BufferedReader(new FileReader(filaProfesori))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {

                if (!line.trim().isEmpty()) {
                    String[] date = line.split(splitter);
                    if (date.length > 0 && !date[0].trim().isEmpty()) {
                        try {
                            int ID = Integer.parseInt(date[0].trim());
                            String NUME = date[1].trim();
                            String PRENUME = date[2].trim();
                            String USERNAME = date[3].trim();
                            String PASSWORD = date[4].trim();

                            Profesor profesor = new Profesor(ID, NUME, PRENUME, USERNAME, PASSWORD);
                            profesori.add(profesor);

                        } catch (NumberFormatException e) {
                            System.err.println("Eroare la conversia ID-ului pentru linia: " + line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Curs> createCoursesData() {
        try (BufferedReader br = new BufferedReader(new FileReader(filaCursuri))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] date = line.split(splitter);

                int ID = Integer.parseInt(date[0].trim());
                String NUME = date[1].trim();
                String DESCRIERE = date[2].trim();
                int IDPROF=Integer.parseInt(date[3].trim());
                int AN = Integer.parseInt(date[4].trim());

                Curs curs = new Curs(ID,NUME,DESCRIERE,IDPROF,AN);
                cursuri.add(curs);
            }
        }catch(IOException e)
            {
                e.printStackTrace();
            }
        return cursuri;

    }


    public List<Note> createNotesData() {
        try (BufferedReader br = new BufferedReader(new FileReader(filaNote))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] date = line.split(splitter);

                int IDCURS = Integer.parseInt(date[0].trim());
                int IDSTUD = Integer.parseInt(date[1].trim());
                int NOTA = Integer.parseInt(date[2].trim());


                Note nota = new Note(IDCURS, IDSTUD, NOTA);
                note.add(nota);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return note;
    }

    public List<Student> getStudenti() {
        return studenti;
    }

    public List<Profesor> getProfesori() {
        if (profesori.isEmpty()) {
            createProfesorData();
        }
        return profesori;
    }

    public List<Curs> getCursuri() {
        return cursuri;
    }

    public List<Student> loadStudenti() {
        List<Student> studentis = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filaStudenti))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                    int id = Integer.parseInt(data[0]);
                    String nume = data[1];
                    String prenume = data[2];
                    int an = Integer.parseInt(data[3]);
                    String grupa = data[4];
                    String username = data[5];
                    String password = data[6];

                    studentis.add(new Student(id, nume, prenume, an,grupa, username, password));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentis;
    }

    public List<Curs> loadCursuri(int idProfesor) {
        List<Curs> cursuri = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filaCursuri))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] date = line.split(splitter);
                if (date.length > 0) {
                    int idProf = Integer.parseInt(date[3].trim());
                    if (idProf == idProfesor) {
                        int ID = Integer.parseInt(date[0].trim());
                        String NUME = date[1].trim();
                        String DESCRIERE = date[2].trim();
                        int AN = Integer.parseInt(date[4].trim());
                        Curs curs = new Curs(ID, NUME, DESCRIERE, idProf, AN);
                        cursuri.add(curs);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cursuri;
    }



}



