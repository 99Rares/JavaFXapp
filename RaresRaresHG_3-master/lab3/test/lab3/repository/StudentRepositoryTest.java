package lab3.repository;

import junit.framework.TestCase;
import lab3.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for StudentRepository
 *
 * @author rares astilean
 */
class StudentRepositoryTest extends TestCase {

    StudentRepository studentRepository = new StudentRepository();

    Student student1 = new Student("rares", "astilean");
    Student student2 = new Student("rares", "dan");
    Student studentNoId1 = new Student("no", "id");  // studentNoId1 is the same with studentNoId2
    Student studentNoId2 = new Student("no", "id");  //

    @BeforeEach
    public void setUp() {
        student1.setStudentId(1);
        student2.setStudentId(2);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void findOne() {
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(studentNoId1);

        assertEquals(student1, studentRepository.findOne((long) 1));
        assertEquals(student2, studentRepository.findOne((long) 2));
        assertEquals(studentNoId1, studentRepository.findOne((long) 0));

        // Students with this IDs don't exist
        assertNull(studentRepository.findOne((long) 100));
        assertNull(studentRepository.findOne((long) 200));
        assertNull(studentRepository.findOne((long) 10));

        try {
            studentRepository.findOne(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void findAll() {
        List<Student> students = new ArrayList<>();
        assertEquals(students, studentRepository.findAll()); // repo is empty for now

        students.add(student1);
        students.add(student2);
        studentRepository.save(student1);
        studentRepository.save(student2);
        assertEquals(students, studentRepository.findAll());
    }

    @Test
    void save() {
        assertEquals(student1, studentRepository.save(student1));
        assertEquals(student2, studentRepository.save(student2));
        assertEquals(studentNoId1, studentRepository.save(studentNoId1));

        assertNull(studentRepository.save(studentNoId2)); // studentNoId1 by default has Id 0, so studentNoId2 also has by default Id 0, can't insert sutdent with same id
        assertNull(studentRepository.save(student1));  // Student1 already exists
        assertNull(studentRepository.save(student2)); // Student2 already exists
        assertNull(studentRepository.save(studentNoId1)); // StuentNoId1 already exists

        student1.setStudentId(10);  // Changing the id of the entity "student1" also changes the Id of the student from the Repository
        assertNull(studentRepository.save(student1));  // ID of the student from Repo is also 10, so student already exists

        studentNoId1.setStudentId(100);
        assertNull(studentRepository.save(studentNoId1));  // studentNoId1 exists and has the Id 100 after set ( 0 before, by default)

        try {
            studentRepository.save(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void delete() {
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(studentNoId1);
        assertEquals(student1, studentRepository.delete((long) 1));
        assertEquals(student2, studentRepository.delete((long) 2));
        assertEquals(studentNoId1, studentRepository.delete((long) 0));

        // Students with this IDs don't exist
        studentRepository.save(student1);
        assertNull(studentRepository.delete((long) 100));
        assertNull(studentRepository.delete((long) 200));

        try {
            studentRepository.delete(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void update() {
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(studentNoId1);

        Student updateStudent1 = new Student(student1.getFirstName(), "new name");
        updateStudent1.setStudentId(1);
        assertEquals(updateStudent1, studentRepository.update(updateStudent1)); // Updating the lastName of the student with studentId 1

        // Students with this IDs don't exist
        Student noMatch = new Student("new", "student");
        noMatch.setStudentId(500);
        assertNull(studentRepository.update(noMatch));

        try {
            studentRepository.update(null);
        } catch (NullPointerException ignored) {
        }
    }
}