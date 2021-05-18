package lab3;

import javafx.application.Application;
import javafx.stage.Stage;
import lab3.System.RegistrationSystem;
import lab3.controller.CourseController;
import lab3.controller.StudentController;
import lab3.controller.TeacherController;
import lab3.repository.CourseFileRepository;
import lab3.repository.StudentFileRepository;
import lab3.repository.TeacherFileRepository;
import lab3.view.GUI;

import java.io.IOException;

/**
 * Main class where program starts.
 */
public class StartApp extends Application {

    /**
     * Start point of the application
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    RegistrationSystem registrationSystem;

    @Override
    public void start(Stage primaryStage) throws IOException {

        StudentFileRepository studentFileRepository = new StudentFileRepository();
        studentFileRepository.populateListWithStudents();

        CourseFileRepository courseFileRepository = new CourseFileRepository();
        courseFileRepository.populateListWithCourses();

        TeacherFileRepository teacherFileRepository = new TeacherFileRepository();
        teacherFileRepository.populateListWithTeachers();

        registrationSystem = new RegistrationSystem(new StudentController(studentFileRepository),
                new TeacherController(teacherFileRepository), new CourseController(courseFileRepository));

        GUI gui = new GUI(primaryStage, registrationSystem);
        gui.displayUi();
    }

    @Override
    public void stop() {
        registrationSystem.getStudentController().getStudentFileRepository().
                writeData(registrationSystem.getStudentController().getStudentFileRepository().findAll());
        registrationSystem.getCourseController().getCourseFileRepository().
                writeData(registrationSystem.getCourseController().getCourseFileRepository().findAll());
        registrationSystem.getTeacherController().getTeacherFileRepository().
                writeData(registrationSystem.getTeacherController().getTeacherFileRepository().findAll());
    }
}
