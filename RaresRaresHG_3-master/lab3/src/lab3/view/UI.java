package lab3.view;

import lab3.Exceptions.InputMenuException;
import lab3.Exceptions.NegativeNumberException;
import lab3.Exceptions.NullObjectException;
import lab3.System.RegistrationSystem;
import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.repository.CourseFileRepository;
import lab3.repository.StudentFileRepository;
import lab3.repository.TeacherFileRepository;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Trough this class will be realised the communication between a admin/user and the registration system
 *
 * @author rares dan
 */
public class UI {
    RegistrationSystem registrationSystem;

    public UI(RegistrationSystem registrationSystem) {
        this.registrationSystem = registrationSystem;
    }

    /**
     * prints out the courses with free places
     */
    private void coursesWithFreePlaces() {
        System.out.println("\n\nYou can choose a course from the following list\nHere is a list with all the available courses:");
        //System.out.println(registrationSystem.retrieveCoursesWithFreePlaces());
        for (Course i : registrationSystem.retrieveCoursesWithFreePlaces())
            System.out.println(i);
    }

    /**
     * prints out the text of the menu
     */
    private void menuText() {
        System.out.print("""
                1. Student anmelden Kurs\s
                2. Verfügbare Kurse
                3. Studenten in einem Kurs\s
                4. Print Kurse
                5. Kurs löschen
                6. Filter Kurse
                7. Sort Students/Courses
                0. Exit
                """
        );
    }

    /**
     * prints out all courses
     */
    private void printAllCourses() {
        System.out.println();
        for (Course i : registrationSystem.getAllCourses())
            System.out.println(i);
    }

    /**
     * represents the menu of the application
     * lets the user chose witch operation he wants
     */
    public void menu() {
        Scanner console = new Scanner(System.in);
        menuText();
        System.out.println("Enter option : ");
        int option = 0;
        try {
            option = console.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("InputMismatchException enter an integer");
            e.printStackTrace();
        }

        // Validating the input
        try {
            inputMenuCheck(option);
        } catch (InputMenuException e) {
            e.printStackTrace();
        }

        while (option != 0) {

            switch (option) {
                case 1 -> enrollStudents();
                case 2 -> coursesWithFreePlaces();
                case 3 -> enrolledCourse();
                case 4 -> printAllCourses();
                case 5 -> delete();
                case 6 -> filter();
                case 7 -> sort();
            }
            menuText();
            System.out.println("Enter option : ");
            try {
                option = console.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("InputMismatchException enter an integer");
                option = 0;
            }

            // Validating the input
            try {
                inputMenuCheck(option);
            } catch (InputMenuException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Goodbye :) ");


        new StudentFileRepository().writeData(this.registrationSystem.getStudentController().getStudentFileRepository().findAll());
        new TeacherFileRepository().writeData(this.registrationSystem.getTeacherController().getTeacherFileRepository().findAll());
        new CourseFileRepository().writeData(this.registrationSystem.getCourseController().getCourseFileRepository().findAll());
    }

    /**
     * Validates the user input for the menu
     *
     * @param option an int representing a choice for the console application
     * @throws InputMenuException if the option is not between 0 and 5
     */
    private void inputMenuCheck(int option) throws InputMenuException {
        if (option > 7 || option < 0)
            throw new InputMenuException("Input should be between 0 and 5");
    }


    /**
     * enrolls the student to a course. if the student can't join the course
     * the message "Try another course." is printed.
     */
    private void enrollStudents() {
        boolean answer;
        System.out.println("\nStudent anmelden Kurs ");
        Scanner console = new Scanner(System.in);
        long id, id2;
        Course course;
        Student student;
        System.out.println("Enter student ID: ");
        id = console.nextInt();
        coursesWithFreePlaces();
        System.out.println("Enter course ID: ");
        id2 = console.nextInt();
        course = registrationSystem.getCourseController().findOne(id2);
        student = registrationSystem.getStudentController().findOne(id);
        // Validating Student and Course with the given ID's
        try {
            ObjectNullCheck(student);
            ObjectNullCheck(course);

            answer = registrationSystem.register(student, course);

            if (answer)
                System.out.println("Student registered");
            else System.out.println("Try another course");
        } catch (NullObjectException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates id input for student/teacher/course
     *
     * @param o Represents an object, in our case a student/teacher/course
     * @throws NullObjectException if the object with the given id does not exist
     */
    private void ObjectNullCheck(Object o) throws NullObjectException {
        if (o == null)
            throw new NullObjectException("One of the give ID's for student/course/teacher doesn't not exist\n Try using valid ID's");
    }

    /**
     * Validates id input for the Ids
     *
     * @param x Represents a Number
     * @throws NegativeNumberException if the number is lower then zero
     */
    private void negativeNumberCheck(long x) throws NegativeNumberException {
        if (x < 0)
            throw new NegativeNumberException("Enter a positive Number");
    }

    /**
     * prints out the students that are enrolled in a given course
     */
    private void enrolledCourse() {
        System.out.println("Studenten in einem Kurs");
        Scanner console = new Scanner(System.in);
        printAllCourses();
        System.out.println("Enter course ID: ");
        long id = console.nextInt();
        try {
            negativeNumberCheck(id);
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }
        Course course = registrationSystem.getCourseController().findOne(id);

        // Validating the course with the given ID
        try {
            ObjectNullCheck(course);

            System.out.println(registrationSystem.retrieveStudentsEnrolledForACourse(course));
        } catch (NullObjectException e) {
            e.printStackTrace();
        }
    }

    /**
     * a teacher can delete a course that he/she teaches
     */

    private void delete() {
        boolean answer;
        Scanner console = new Scanner(System.in);
        System.out.println("\nLöschen Kurs ");
        long id, id2;
        System.out.println("Enter teacher ID: ");
        id = console.nextInt();
        System.out.println("Enter course ID: ");
        id2 = console.nextInt();
        Teacher teacher = registrationSystem.getTeacherController().findOne(id);
        Course course = registrationSystem.getCourseController().findOne(id2);

        // Validating the course and teacher with the given ID's
        try {
            ObjectNullCheck(course);
            ObjectNullCheck(teacher);

            answer = registrationSystem.deleteCourse(teacher, course);
            if (answer)
                System.out.println("Course deleted");
            else System.out.println("Try another course");
        } catch (NullObjectException e) {
            e.printStackTrace();
        }

    }

    /**
     * filtering courses with a credit number less than a given number
     */
    private void filter() {
        Scanner console = new Scanner(System.in);
        System.out.println("\nFilter Kurs ");
        int credit;
        System.out.println("Enter Kurs Credit: ");
        credit = console.nextInt();
        try {
            negativeNumberCheck(credit);
        } catch (NegativeNumberException e) {
            e.printStackTrace();

        }
        System.out.println(registrationSystem.getCourseController().filterByCredits(credit));
    }

    private void sort() {
        Scanner console = new Scanner(System.in);
        System.out.println("\n1. Sort Courses by Credits\n2. Sort Students in LexicoGraphic order ");
        int option;
        System.out.println("Enter 1 or 2: ");
        option = console.nextInt();
        try {
            negativeNumberCheck(option);
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }

        if (option == 1) {
            registrationSystem.getCourseController().sortByCredits();
            System.out.println("Courses after sort: ");
            registrationSystem.getCourseController().findAll().forEach(System.out::println);
        } else if (option == 2) {
            registrationSystem.getStudentController().sortInLexicographicOrder();
            System.out.println("Students after sort: ");
            registrationSystem.getStudentController().findAll().forEach(System.out::println);
        }

    }

}
