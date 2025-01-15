package Kalimdor;

public class Main {
    public static void main(String[] args) {


        Student[] students = {
                new Student(1, "Calistru", "Bogdan", 2, "Grupa1", "bogdan.calistru@student.unitbv.ro", "(parola criptata)"),
                new Student(1, "Primar", "Bodan", 2, "Grupa1", "bogdan.calistru@student.unitbv.ro", "SFGHSFGH"),
        };
        FileDisplay fila1 = new FileDisplay();
        fila1.displayStudents(students);


        Profesor[] newProfessors = {
                new Profesor(3, "Gunoiu", "George", "george.buinoiu@student.unitbv.ro", "parola3"),
        };

        FileDisplay fila2 = new FileDisplay();
        fila2.displayProfessors(newProfessors);


        Curs[] newCourses = {
                new Curs(1, "Matematici cam speciale", "Nu stiu nu m am dus la curs", 1, 1),
        };


        FileDisplay fila3 = new FileDisplay();
        fila3.displayCourses(newCourses);


        FileDataManager fileDataManager = new FileDataManager();
        fileDataManager.createCoursesData();
        fileDataManager.createNotesData();
        fileDataManager.createProfesorData();
        fileDataManager.createStudentsData();

        System.out.println("Lista Studenților:");
        for (Student student : fileDataManager.getStudenti()) {
            System.out.println(student);
        }


        System.out.println("\nLista Profesorilor:");
        for (Profesor profesor : fileDataManager.getProfesori()) {
            System.out.println(profesor);
        }


        System.out.println("\nLista Cursurilor:");
        for (Curs curs : fileDataManager.getCursuri()) {
            System.out.println(curs);
        }


    }
}
