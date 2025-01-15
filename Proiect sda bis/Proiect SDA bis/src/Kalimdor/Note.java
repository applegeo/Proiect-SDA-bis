package Kalimdor;

public class Note {
    private int idCurs;
    private int idStudent;
    private int nota;

    public Note(int idCurs, int idStudent, int nota) {
        this.idCurs = idCurs;
        this.idStudent = idStudent;
        this.nota = nota;
    }

    public int getIdCurs() {
        return idCurs;
    }

    public void setIdCurs(int idCurs) {
        this.idCurs = idCurs;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Note{" +
                "idCurs=" + idCurs +
                ", idStudent=" + idStudent +
                ", nota=" + nota +
                '}';
    }
}
