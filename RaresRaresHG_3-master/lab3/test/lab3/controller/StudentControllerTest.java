package lab3.controller;

import junit.framework.TestCase;
import lab3.model.Student;
import lab3.repository.StudentFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for StudentController
 * Same test as the tests from repository, but applied on the controller
 * Comments available in the StudentRepositoryTest class
 *
 * @author rares astilean
 */
class StudentControllerTest extends TestCase {

    StudentFileRepository studentFileRepository = new StudentFileRepository();

    Student student1 = new Student("rares", "astilean");
    Student student2 = new Student("rares", "dan");
    Student studentNoId1 = new Student("no", "id");  // studentNoId1 is the same with studentNoId2
    Student studentNoId2 = new Student("no", "id");  //

    StudentController studentController = new StudentController(studentFileRepository);

    @BeforeEach
    public void setUp() {
        student1.setStudentId(1);
        student2.setStudentId(2);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void sortInLexicographicOrder() {
        studentController.sortInLexicographicOrder();
        assertEquals(List.of(), studentController.findAll());

        Student student3 = new Student("dragos", "astilean", 3);
        Student student4 = new Student("bogdan", "astilean", 4);
        Student student5 = new Student("paul", "manea", 5);
        Student student6 = new Student("gabriel", "rotaru", 6);
        studentController.save(student1);
        studentController.save(student2);
        studentController.save(student3);
        studentController.save(student4);
        studentController.save(student5);
        studentController.save(student6);

        studentController.sortInLexicographicOrder();
        assertEquals(List.of(student4, student3, student1, student2, student5, student6), studentController.findAll());
    }

    @Test
    void findOne() {
        studentController.save(student1);
        studentController.save(student2);
        studentController.save(studentNoId1);

        assertEquals(student1, studentController.findOne((long) 1));
        assertEquals(student2, studentController.findOne((long) 2));
        assertEquals(studentNoId1, studentController.findOne((long) 0));

        // Students with this IDs don't exist
        assertNull(studentController.findOne((long) 100));
        assertNull(studentController.findOne((long) 200));
        assertNull(studentController.findOne((long) 10));

        try {
            studentController.findOne(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void findAll() {
        List<Student> students = new ArrayList<>();
        assertEquals(students, studentController.findAll()); // repo is empty for now

        students.add(student1);
        students.add(student2);
        studentController.save(student1);
        studentController.save(student2);
        assertEquals(students, studentController.findAll());
    }

    @Test
    void save() {
        assertEquals(student1, studentController.save(student1));
        assertEquals(student2, studentController.save(student2));
        assertEquals(studentNoId1, studentController.save(studentNoId1));
        assertNull(studentController.save(studentNoId2)); // studentNoId1 by default has Id 0, so studentNoId2 also has by default Id 0, can't insert sutdent with same id
        assertNull(studentController.save(student1));  // Student1 already exists
        assertNull(studentController.save(student2)); // Student2 already exists
        assertNull(studentController.save(studentNoId1)); // StuentNoId1 already exists

        student1.setStudentId(10);  // Changing the id of the entity "student1" also changes the Id of the student from the Repository
        assertNull(studentController.save(student1));  // ID of the student from Repo is also 10, so student already exists

        studentNoId1.setStudentId(100);
        assertNull(studentController.save(studentNoId1));  // studentNoId1 exists and has the Id 100 after set ( 0 before, by default)

        try {
            studentController.save(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void delete() {
        studentController.save(student1);
        studentController.save(student2);
        studentController.save(studentNoId1);
        assertEquals(student1, studentController.delete((long) 1));
        assertEquals(student2, studentController.delete((long) 2));
        assertEquals(studentNoId1, studentController.delete((long) 0));

        // Students with this IDs don't exist
        studentController.save(student1);
        assertNull(studentController.delete((long) 100));
        assertNull(studentController.delete((long) 200));

        try {
            studentController.delete(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void update() {
        studentController.save(student1);
        studentController.save(student2);
        studentController.save(studentNoId1);

        Student updateStudent1 = new Student(student1.getFirstName(), "new name");
        updateStudent1.setStudentId(1);
        assertEquals(updateStudent1, studentController.update(updateStudent1)); // Updating the lastName of the student with studentId 1

        // Students with this IDs don't exist
        Student noMatch = new Student("new", "student");
        noMatch.setStudentId(500);
        assertNull(studentController.update(noMatch));

        try {
            studentController.update(null);
        } catch (NullPointerException ignored) {
        }
    }
}