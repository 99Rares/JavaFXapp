package lab3.repository;

import junit.framework.TestCase;
import lab3.model.Course;
import lab3.model.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for TeacherRepository
 *
 * @author rares dan
 */
class CourseRepositoryTest extends TestCase {
    CourseRepository courseRepository = new CourseRepository();
    Course course1;
    Course course2;
    Teacher teacher1;

    @BeforeEach
    public void setUP() {

        teacher1 = new Teacher("teacher", "1", 1);
        course1 = new Course("course1", teacher1, 2, 6, 1);
        course2 = new Course("course2", teacher1, 2, 6, 2);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void findOne() {
        courseRepository.save(course1);
        courseRepository.save(course2);

        assertEquals(course1, courseRepository.findOne((long) 1));
        assertEquals(course2, courseRepository.findOne((long) 2));

        //courses that don't exist
        assertNull(courseRepository.findOne((long) 3));
        assertNull(courseRepository.findOne((long) 4));
        assertNull(courseRepository.findOne((long) 0));
        try {
            courseRepository.findOne(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void save() {
        assertEquals(course1, courseRepository.save(course1));
        assertEquals(course2, courseRepository.save(course2));

        //courses that already exist
        assertNull(courseRepository.save(course1));
        assertNull(courseRepository.save(course2));

        //updating the id of the courses also changes the id of the courses in the Repository
        course1.setCourseID(4);
        assertNull(courseRepository.save(course1));


        try {
            courseRepository.save(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void delete() {
        courseRepository.save(course1);
        courseRepository.save(course2);

        assertEquals(course1, courseRepository.delete((long) 1));
        assertEquals(course2, courseRepository.delete((long) 2));

        //courses that don't exist
        courseRepository.save(course1);
        assertNull(courseRepository.delete((long) 2));
        assertNull(courseRepository.delete((long) 3));

        try {
            courseRepository.delete(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void findAll() {
        List<Course> courses = new ArrayList<>();
        assertEquals(courses, courseRepository.findAll());//empty repository

        courses.add(course1);
        courses.add(course2);
        courseRepository.save(course1);
        courseRepository.save(course2);
        assertEquals(courses, courseRepository.findAll());
    }

}