package lab3.repository;

import junit.framework.TestCase;
import lab3.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for TeacherRepository
 *
 * @author rares dan
 */
class TeacherRepositoryTest extends TestCase {
    TeacherRepository teacherRepository = new TeacherRepository();
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
        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);
        teacherRepository.save(teacher3);

        assertEquals(teacher1, teacherRepository.findOne((long) 1));
        assertEquals(teacher2, teacherRepository.findOne((long) 2));
        assertEquals(teacher3, teacherRepository.findOne((long) 0));

        //teachers that don't exist
        assertNull(teacherRepository.findOne((long) 3));
        assertNull(teacherRepository.findOne((long) 4));
        assertNull(teacherRepository.findOne((long) 5));

        try {
            teacherRepository.findOne(null);
        } catch (NullPointerException ignored) {
        }

    }


    @Test
    void save() {
        assertEquals(teacher1, teacherRepository.save(teacher1));
        assertEquals(teacher2, teacherRepository.save(teacher2));
        assertEquals(teacher3, teacherRepository.save(teacher3));

        //teachers that already exist
        assertNull(teacherRepository.save(teacher1));
        assertNull(teacherRepository.save(teacher2));
        assertNull(teacherRepository.save(teacher3));

        //updating the id of the  teachers also changes the id of the teachers in the Repository
        teacher1.setTeacherID(4);
        assertNull(teacherRepository.save(teacher1));
        teacher3.setTeacherID(3);
        assertNull(teacherRepository.save(teacher3));

        try {
            teacherRepository.save(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void delete() {
        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);
        teacherRepository.save(teacher3);

        assertEquals(teacher1, teacherRepository.delete((long) 1));
        assertEquals(teacher2, teacherRepository.delete((long) 2));
        assertEquals(teacher3, teacherRepository.delete((long) 0));
        //teachers that don't exist
        teacherRepository.save(teacher1);
        assertNull(teacherRepository.delete((long) 2));
        assertNull(teacherRepository.delete((long) 3));

        try {
            teacherRepository.delete(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void findAll() {
        List<Teacher> teachers = new ArrayList<>();
        assertEquals(teachers, teacherRepository.findAll());//empty repository

        teachers.add(teacher1);
        teachers.add(teacher2);
        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);
        assertEquals(teachers, teacherRepository.findAll());
    }
}
