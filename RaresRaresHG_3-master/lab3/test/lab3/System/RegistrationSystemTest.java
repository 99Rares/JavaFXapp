package lab3.System;

import junit.framework.TestCase;
import lab3.controller.CourseController;
import lab3.controller.StudentController;
import lab3.controller.TeacherController;
import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;
import lab3.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to test the functions of the Registration System
 *
 * @author rares astilean
 */
class RegistrationSystemTest extends TestCase {

    StudentFileRepository studentFileRepository = new StudentFileRepository();
    Student student1 = new Student("rares", "astilean", 1);
    Student student2 = new Student("rares", "dan", 2);
    StudentController studentController = new StudentController(studentFileRepository);

    TeacherFileRepository teacherFileRepository = new TeacherFileRepository();
    Teacher teacher1 = new Teacher("teacher", "1", 1);
    Teacher teacher2 = new Teacher("teacher", "2", 2);
    TeacherController teacherController = new TeacherController(teacherFileRepository);

    CourseFileRepository courseFileRepository = new CourseFileRepository();
    Course course1 = new Course("course1", teacher1, 10, 6, 1);
    Course course2 = new Course("course2", teacher2, 5, 5, 2);
    CourseController courseController = new CourseController(courseFileRepository);

    RegistrationSystem registrationSystem;

    @BeforeEach
    public void setUp() {
        studentController.save(student1);
        studentController.save(student2);

        teacherController.save(teacher1);
        teacherController.save(teacher2);

        courseController.save(course1);
        courseController.save(course2);

        registrationSystem = new RegistrationSystem(studentController, teacherController, courseController);

        // Testing the contents of the studentRepository
        assertEquals(student1, registrationSystem.getStudentController().findOne((long) 1));
        assertEquals(student2, registrationSystem.getStudentController().findOne((long) 2));

        // Testing the contents of the teacherRepository
        assertEquals(teacher1, registrationSystem.getTeacherController().findOne((long) 1));
        assertEquals(teacher2, registrationSystem.getTeacherController().findOne((long) 2));

        // Testing the contents of the courseRepository
        assertEquals(course1, registrationSystem.getCourseController().findOne((long) 1));
        assertEquals(course2, registrationSystem.getCourseController().findOne((long) 2));
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void register() {
        // Enrolling the student with ID 1 to the course with ID 1 and course with ID 2
        assertTrue(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 1), registrationSystem.getCourseController().findOne((long) 1)));
        assertEquals(6, registrationSystem.getStudentController().findOne((long) 1).getTotalCredits());  // Student should have now a total of 6 credits
        assertTrue(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 1), registrationSystem.getCourseController().findOne((long) 2)));
        assertEquals(11, registrationSystem.getStudentController().findOne((long) 1).getTotalCredits());  // Student should have now a total of 6 credits

        // Enrolling the student with ID 2 to the course with ID 1 and course with ID 2
        assertTrue(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 2), registrationSystem.getCourseController().findOne((long) 1)));
        assertEquals(6, registrationSystem.getStudentController().findOne((long) 2).getTotalCredits());  // Student should have now a total of 6 credits
        assertTrue(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 2), registrationSystem.getCourseController().findOne((long) 2)));
        assertEquals(11, registrationSystem.getStudentController().findOne((long) 2).getTotalCredits());  // Student should have now a total of 6 credits

        // Trying to enroll the students for the same courses
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 1), registrationSystem.getCourseController().findOne((long) 1)));
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 1), registrationSystem.getCourseController().findOne((long) 2)));
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 2), registrationSystem.getCourseController().findOne((long) 1)));
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 2), registrationSystem.getCourseController().findOne((long) 2)));

        // Trying to enroll the students, that already have 30 credits
        student1.setTotalCredits(30);
        student2.setTotalCredits(30);
        registrationSystem.getStudentController().update(student1);
        registrationSystem.getStudentController().update(student2);
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 1), registrationSystem.getCourseController().findOne((long) 1)));
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 1), registrationSystem.getCourseController().findOne((long) 2)));
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 2), registrationSystem.getCourseController().findOne((long) 1)));
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 2), registrationSystem.getCourseController().findOne((long) 2)));

        // Trying to enroll the students for a course, that has no more free places left
        course1.setMaxEnrollment(2);
        course2.setMaxEnrollment(2);
        registrationSystem.getCourseController().update(course1);
        registrationSystem.getCourseController().update(course2);
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 1), registrationSystem.getCourseController().findOne((long) 1)));
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 1), registrationSystem.getCourseController().findOne((long) 2)));
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 2), registrationSystem.getCourseController().findOne((long) 1)));
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 2), registrationSystem.getCourseController().findOne((long) 2)));

        // Trying to enroll the students to a non existing course
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 1), new Course("newCourse", teacher1, 50, 6, 20)));
        assertFalse(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 2), new Course("newCourse", teacher1, 50, 6, 21)));

        // Trying to enroll non existing students
        assertFalse(registrationSystem.register(new Student("new", "student", 10), registrationSystem.getCourseController().findOne((long) 1)));
        assertFalse(registrationSystem.register(new Student("new", "student", 11), registrationSystem.getCourseController().findOne((long) 2)));
    }

    @Test
    void retrieveCoursesWithFreePlaces() {
        List<Course> coursesWithFreePlaces;
        coursesWithFreePlaces = registrationSystem.retrieveCoursesWithFreePlaces();
        assertEquals(List.of(course1, course2), coursesWithFreePlaces);  // List should contain course1 and course2

        // course1 and course2 have no places
        course1.setMaxEnrollment(0);
        course2.setMaxEnrollment(0);
        registrationSystem.getCourseController().update(course1);
        registrationSystem.getCourseController().update(course2);
        coursesWithFreePlaces = registrationSystem.retrieveCoursesWithFreePlaces();
        assertEquals(List.of(), coursesWithFreePlaces);  // List should be empty
    }

    @Test
    void retrieveStudentsEnrolledForACourse() {
        List<Student> studentsEnrolledForACourse = new ArrayList<>();

        // There are no students enrolled for any of this courses yet
        assertEquals(studentsEnrolledForACourse, registrationSystem.retrieveStudentsEnrolledForACourse(course1));
        assertEquals(studentsEnrolledForACourse, registrationSystem.retrieveStudentsEnrolledForACourse(course2));

        // Enrolling the student with ID 1 to the course with ID 1 and course with ID 2
        assertTrue(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 1), registrationSystem.getCourseController().findOne((long) 1)));
        assertEquals(6, registrationSystem.getStudentController().findOne((long) 1).getTotalCredits());  // Student should have now a total of 6 credits
        assertTrue(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 1), registrationSystem.getCourseController().findOne((long) 2)));
        assertEquals(11, registrationSystem.getStudentController().findOne((long) 1).getTotalCredits());  // Student should have now a total of 6 credits

        // Enrolling the student with ID 2 to the course with ID 1 and course with ID 2
        assertTrue(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 2), registrationSystem.getCourseController().findOne((long) 1)));
        assertEquals(6, registrationSystem.getStudentController().findOne((long) 2).getTotalCredits());  // Student should have now a total of 6 credits
        assertTrue(registrationSystem.register(registrationSystem.getStudentController().findOne((long) 2), registrationSystem.getCourseController().findOne((long) 2)));
        assertEquals(11, registrationSystem.getStudentController().findOne((long) 2).getTotalCredits());  // Student should have now a total of 6 credits

        assertEquals(List.of((long)1,(long)2), registrationSystem.retrieveStudentsEnrolledForACourse(course1));
        assertEquals(List.of((long)1,(long)2), registrationSystem.retrieveStudentsEnrolledForACourse(course2));
    }

    @Test
    void getAllCourses() {
        List<Course> allCourses;
        allCourses = registrationSystem.getAllCourses();

        assertEquals(List.of(course1, course2), allCourses);

        // Deleting the courses from the repository
        registrationSystem.getCourseController().delete((long) 1);
        registrationSystem.getCourseController().delete((long) 2);
        allCourses = registrationSystem.getAllCourses();
        assertEquals(List.of(), allCourses);
    }

    @Test
    void delete() {
        List<Long> list1 = List.of(course1.getCourseID());
        List<Long> list2 = List.of(course2.getCourseID());
        teacher1.setCourses(list1);
        teacher2.setCourses(list2);
        assertTrue(registrationSystem.deleteCourse(teacher1, course1));
        assertTrue(registrationSystem.deleteCourse(teacher2, course2));
        assertFalse(registrationSystem.deleteCourse(teacher1, course2));
        assertFalse(registrationSystem.deleteCourse(teacher2, course1));
    }
}

