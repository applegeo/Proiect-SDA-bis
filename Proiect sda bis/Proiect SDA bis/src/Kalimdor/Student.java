package Kalimdor;

public class Student {
    private int id;
    private String Nume;
    private String Prenume;
    private String Grupa;
    private int An;
    private String Username;
    private String Password;

    public Student(int id, String nume, String prenume, int an, String grupa, String username, String password) {
        this.id = id;
        Nume = nume;
        Prenume = prenume;
        Grupa = grupa;
        An = an;
        Username = username;
        Password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return Nume;
    }

    public void setNume(String nume) {
        Nume = nume;
    }

    public String getPrenume() {
        return Prenume;
    }

    public void setPrenume(String prenume) {
        Prenume = prenume;
    }

    public String getGrupa() {
        return Grupa;
    }

    public void setGrupa(String grupa) {
        Grupa = grupa;
    }

    public int getAn() {
        return An;
    }

    public void setAn(int an) {
        An = an;
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
        return "Student{" +
                "id=" + id +
                ", Nume='" + Nume + '\'' +
                ", Prenume='" + Prenume + '\'' +
                ", Grupa='" + Grupa + '\'' +
                ", An=" + An +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
