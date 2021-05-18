package lab3.controller;

import junit.framework.TestCase;
import lab3.model.Course;
import lab3.model.Teacher;
import lab3.repository.CourseFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for CourseRepository
 *
 * @author rares dan
 */
class CourseControllerTest extends TestCase {
    CourseFileRepository courseFileRepository = new CourseFileRepository();
    CourseController courseController = new CourseController(courseFileRepository);
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
    void sortByCredits() {
        courseController.sortByCredits();
        assertEquals(List.of(), courseController.findAll());

        Course course3 = new Course("course3", teacher1, 2, 10, 3);
        Course course4 = new Course("course3", teacher1, 2, 11, 4);
        Course course5 = new Course("course3", teacher1, 2, 12, 5);
        Course course6 = new Course("course3", teacher1, 2, 9, 6);
        Course course7 = new Course("course3", teacher1, 2, 0, 7);
        Course course8 = new Course("course3", teacher1, 2, 5, 8);

        courseController.save(course1);
        courseController.save(course2);
        courseController.save(course3);
        courseController.save(course4);
        courseController.save(course5);
        courseController.save(course6);
        courseController.save(course7);
        courseController.save(course8);

        courseController.sortByCredits();
        assertEquals(List.of(course5, course4, course3, course6, course1, course2, course8, course7), courseController.findAll());
    }

    @Test
    void findOne() {
        courseController.save(course1);
        courseController.save(course2);

        assertEquals(course1, courseController.findOne((long) 1));
        assertEquals(course2, courseController.findOne((long) 2));

        assertNull(courseController.findOne((long) 3));
        assertNull(courseController.findOne((long) 4));
        assertNull(courseController.findOne((long) 0));
        try {
            courseController.findOne(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void save() {
        assertEquals(course1, courseController.save(course1));
        assertEquals(course2, courseController.save(course2));


        assertNull(courseController.save(course1));
        assertNull(courseController.save(course2));


        course1.setCourseID(4);
        assertNull(courseController.save(course1));


        try {
            courseController.save(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void delete() {
        courseController.save(course1);
        courseController.save(course2);

        assertEquals(course1, courseController.delete((long) 1));
        assertEquals(course2, courseController.delete((long) 2));

        courseController.save(course1);
        assertNull(courseController.delete((long) 2));
        assertNull(courseController.delete((long) 3));

        try {
            courseController.delete(null);
        } catch (NullPointerException ignored) {
        }
    }

    @Test
    void findAll() {
        List<Course> courses = new ArrayList<>();
        assertEquals(courses, courseController.findAll());

        courses.add(course1);
        courses.add(course2);
        courseController.save(course1);
        courseController.save(course2);
        assertEquals(courses, courseController.findAll());
    }
@Test
    void filter(){

    Course course3 = new Course("course3", teacher1, 2, 10, 3);
    Course course4 = new Course("course4", teacher1, 2, 11, 4);
    Course course5 = new Course("course5", teacher1, 2, 12, 5);
    Course course6 = new Course("course6", teacher1, 2, 9, 6);
    Course course7 = new Course("course7", teacher1, 2, 6, 7);
    Course course8 = new Course("course8", teacher1, 2, 5, 8);

    courseController.save(course1);
    courseController.save(course2);
    courseController.save(course3);
    courseController.save(course4);
    courseController.save(course5);
    courseController.save(course6);
    courseController.save(course7);
    courseController.save(course8);
    assertEquals(List.of(course1,course2,course6,course7,course8), courseController.filterByCredits(10));
    assertEquals(List.of(course1,course2,course3,course4,course5,course6,course7,course8),courseController.filterByCredits(20));
    assertEquals(List.of(course8),courseController.filterByCredits(6));
}
}