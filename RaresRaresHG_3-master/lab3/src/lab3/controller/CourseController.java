package lab3.controller;

import lab3.model.Course;
import lab3.repository.CourseFileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represent a Controller for the course Repository
 * It implements the interface ICrudRepository
 * It calls the methods for CRUD operations on the repository of courses
 *
 * @author rares dan
 */
public class CourseController {
    /**
     * the repository which will be controlled trough this class
     */
    private CourseFileRepository courseFileRepository;

    public CourseFileRepository getCourseFileRepository() {
        return courseFileRepository;
    }

    public void setCourseFileRepository(CourseFileRepository courseFileRepository) {
        this.courseFileRepository = courseFileRepository;
    }

    public CourseController(CourseFileRepository courseFileRepository) {
        this.courseFileRepository = courseFileRepository;
    }

    /**
     * @param id -the id of the course to be returned. The id must not be null
     * @return the course with the specified id or null - if there is no course with the given id
     */
    public Course findOne(Long id) {
        return courseFileRepository.findOne(id);
    }

    /**
     * @return all courses
     */
    public Iterable<Course> findAll() {
        return courseFileRepository.findAll();
    }

    /**
     * @param entity course must be not null
     * @return null- if the given course is saved otherwise returns the course (if it already exists)
     */
    public Course save(Course entity) {
        return courseFileRepository.save(entity);
    }

    /**
     * removes the course with the specified id
     *
     * @param id id must be not null
     * @return the removed course or null if there is no course with the given id
     */
    public Course delete(Long id) {
        return courseFileRepository.delete(id);
    }

    /**
     * @param entity course must not be null
     * @return null - if the course is updated, otherwise returns the course
     */
    public Course update(Course entity) {
        return courseFileRepository.update(entity);
    }

    /**
     * Sorts the list of course by the number of credits
     */
    public void sortByCredits() {
        List<Course> courses = new ArrayList<>();
        courseFileRepository.findAll().forEach(courses::add);
        courses.sort((o1, o2) -> o2.getCredits() - o1.getCredits());

        courseFileRepository.setCourses(courses);
    }

    public List<Course> filterByCredits(int credits) {
        List<Course> courses = new ArrayList<>();
        courseFileRepository.findAll().forEach(courses::add);
        return courses.stream().filter(course -> course.getCredits() < credits).collect(Collectors.toList());
    }
}
