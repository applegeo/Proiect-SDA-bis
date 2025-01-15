package loginapp;

public class CurrentUser {
    private static int idStudent;
    private static int idProfesor;

    public static void setIdStudent(int id) {
        idStudent = id;
    }

    public static int getIdStudent() {
        return idStudent;
    }

    public static void setIdProfesor(int id) {
        idProfesor = id;
    }

}
