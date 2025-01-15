package Kalimdor;

import java.util.List;

public class Profesor {
    private int id;
    private String Prenume;
    private String Nume;
    private String Username;
    private String Password;

    public Profesor(int id, String prenume, String nume, String username, String password) {
        this.id = id;
        Prenume = prenume;
        Nume = nume;
        Username = username;
        Password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenume() {
        return Prenume;
    }

    public void setPrenume(String prenume) {
        Prenume = prenume;
    }

    public String getNume() {
        return Nume;
    }

    public void setNume(String nume) {
        Nume = nume;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "id=" + id +
                ", Prenume='" + Prenume + '\'' +
                ", Nume='" + Nume + '\'' +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }



}
