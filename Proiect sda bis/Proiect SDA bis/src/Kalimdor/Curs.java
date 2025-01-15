package Kalimdor;

public class Curs{
    private int id;
    private String nume;
    private String descriere;
    private int idProf;
    private int An;

    public Curs(int id, String nume, String descriere, int idProf, int an) {
        this.id = id;
        this.nume = nume;
        this.descriere = descriere;
        this.idProf = idProf;
        An = an;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getIdProf() {
        return idProf;
    }

    public void setIdProf(int idProf) {
        this.idProf = idProf;
    }

    public int getAn() {
        return An;
    }

    public void setAn(int an) {

        An = an;
    }

    public int getIdProfesor() {
        return idProf;
    }



    @Override
    public String toString() {
        return "Curs{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", descriere='" + descriere + '\'' +
                ", idProf=" + idProf +
                ", An=" + An +
                '}';
    }
}
