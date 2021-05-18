package lab3.controller;

import junit.framework.TestCase;
import lab3.model.Teacher;
import lab3.repository.TeacherFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for TeacherController
 *
 * @author rares dan
 */
class TeacherControllerTest extends TestCase {
    TeacherFileRepository teacherFileRepository = new TeacherFileRepository();
    TeacherController teacherController = new TeacherController(teacherFileRepository);
    Teacher teacher1;
    Teacher teacher2;
    Teacher teacher3;

    @BeforeEach
    public void setUP() {
        teacher1 = new Teacher("teacher", "1", 1);
        teacher2 = new Teacher("teacher", "2", 2);
        teacher3 = new Teacher("teacher", "3");
    }

    @Test
    void findOne() {
        teacherController.save(teacher1);
        teacherController.save(teacher2);
        teacherController.save(teacher3);

        assertEquals(teacher1, teacherController.findOne((long) 1));
        assertEquals(teacher2, teacherController.findOne((long) 2));
        assertEquals(teacher3, teacherController.findOne((long) 0));

        assertNull(teacherController.findOne((long) 3));
        assertNull(teacherController.findOne((long) 4));
        assertNull(teacherController.findOne((long) 5));

        try {
            teacherController.findOne(null);
        } catch (NullPointerException ignored) {
        }

    }


    @Test
    void save() {
        assertEquals(teacher1, teacherController.save(teacher1));
        assertEquals(teacher2, teacherController.save(teacher2));
        assertEquals(teacher3, teacherController.save(teacher3));

        assertNull(teacherController.save(teacher1));
        assertNull(teacherController.save(teacher2));
        assertNull(teacherController.save(teacher3));

        teacher1.setTeacherID(4);
        assertNull(teacherController.save(teacher1));
        teacher3.setTeacherID(3);
        assertNull(teacherController.save(teacher3));

        try {
            teacherController.save(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void delete() {
        teacherController.save(teacher1);
        teacherController.save(teacher2);
        teacherController.save(teacher3);

        assertEquals(teacher1, teacherController.delete((long) 1));
        assertEquals(teacher2, teacherController.delete((long) 2));
        assertEquals(teacher3, teacherController.delete((long) 0));

        teacherController.save(teacher1);
        assertNull(teacherController.delete((long) 2));
        assertNull(teacherController.delete((long) 3));

        try {
            teacherController.delete(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void findAll() {
        List<Teacher> teachers = new ArrayList<>();
        assertEquals(teachers, teacherController.findAll());

        teachers.add(teacher1);
        teachers.add(teacher2);
        teacherController.save(teacher1);
        teacherController.save(teacher2);
        assertEquals(teachers, teacherController.findAll());
    }
}