package lab3.repository;

import lab3.model.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a repository for courses
 * It implements the interface ICrudRepository
 * It contains methods for CRUD operations on the list with courses
 *
 * @author rares dan
 */
public class CourseRepository implements ICrudRepository<Course> {

    /**
     * A list of all the courses
     */
    protected List<Course> courses = new ArrayList<>();

    /**
     * @param id -the id of the course to be returned. The id must not be null
     * @return the course with the specified id or null - if there is no course with the given id
     */
    @Override
    public Course findOne(Long id) {
        if (id == null)
            throw new NullPointerException("id soll nicht null sein");
        for (Course i : courses)// searching the list for an existing course with the courseId "id"
            if (i.getCourseID() == id)
                return i;// when found, return the course
        return null;
    }

    /**
     * @return all courses
     */
    @Override
    public Iterable<Course> findAll() {
        return courses;
    }

    /**
     * @param entity course must be not null
     * @return null- if the given course is saved otherwise returns the course (if it already exists)
     */
    @Override
    public Course save(Course entity) {
        if (entity == null)
            throw new NullPointerException("entity soll nicht null sein");
        for (Course i : courses)// searching the list for an existing course with the courseId "id"
            if (i.getCourseID() == entity.getCourseID())// If the course already exists
                return null;
        courses.add(entity);// If the list doesn't contain the course, add it to the list
        return entity;
    }

    /**
     * removes the course with the specified id
     *
     * @param id id must be not null
     * @return the removed course or null if there is no course with the given id
     */
    @Override
    public Course delete(Long id) {
        if (id == null)
            throw new NullPointerException("id soll nicht null sein");
        for (Course i : courses)// searching the list for an existing course with the courseId "id"
            if (i.getCourseID() == id) {// If the list does contain the course, remove it from the list
                courses.remove(i);
                return i;
            }
        return null;
    }

    /**
     * @param entity course must not be null
     * @return null - if the course is updated, otherwise returns the course
     */
    @Override
    public Course update(Course entity) {
        if (entity == null)
            throw new NullPointerException("entity soll nicht null sein");
        for (Course i : courses)// searching the list for an existing course with the courseId "id"
            if (i.getCourseID() == entity.getCourseID()) {// If the list does contain the teacher, update it
                courses.remove(i);//remove the old version
                courses.add(entity);//add the new version
                return entity;
            }
        return null;
    }

    /**
     * The function iterates trough the list of courses and concatenates the toString method of each course to the final string
     *
     * @return A string containing information about all the courses from the university
     */
    @Override
    public String toString() {
        StringBuilder CourseString = new StringBuilder();
        for (Course i : courses)
            CourseString.append(i.toString()).append('\n');
        return CourseString.toString();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
