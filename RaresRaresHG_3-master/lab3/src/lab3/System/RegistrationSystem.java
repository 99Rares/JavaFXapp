package lab3.System;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lab3.Exceptions.AssignationException;
import lab3.Exceptions.IDInUseException;
import lab3.Exceptions.NullIdException;
import lab3.Exceptions.NullObjectException;
import lab3.controller.CourseController;
import lab3.controller.StudentController;
import lab3.controller.TeacherController;
import lab3.model.Course;
import lab3.model.Student;
import lab3.model.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Used to sign up students for courses
 * Manipulates a studentsController, a teacherController and a courseController
 *
 * @author rares astilean
 */
public class RegistrationSystem {
    private StudentController studentController;
    private TeacherController teacherController;
    private CourseController courseController;

    public RegistrationSystem(StudentController studentController, TeacherController teacherController, CourseController courseController) {
        this.studentController = studentController;
        this.teacherController = teacherController;
        this.courseController = courseController;
    }

    /**
     * It enrolls a given student for a given course
     * The student can't be enrolled if:
     * - they are no more free places left for the course
     * - by adding this course the student will have more than 30 worth of credits
     *
     * @param student - represents the student which wants to be enrolled for the paramater "course"
     * @param course  - the course for which the student wants to sign up
     * @return true if student was enrolled for the course, false if student couldn't have been enrolled for the course
     */
    public boolean register(Student student, Course course) {
        if (courseController.findOne(course.getCourseID()) == course && studentController.findOne(student.getStudentId()) == student) // Checking if the student and course exist in the repository
            if (course.getStudentsEnrolled().size() < course.getMaxEnrollment()) // Checking if they are free places left for the course
                if (student.getTotalCredits() + course.getCredits() <= 30)  // Checking if the student won't have more than 30 credits by enrolling for this course
                    if (!student.getEnrolledCourses().contains(course.getCourseID())) {  // Checking if the student is already enrolled for this course
                        List<Long> studentCourses = student.getEnrolledCourses(); // Actual lists of courses
                        studentCourses.add(course.getCourseID()); // Adding the new course to the list
                        student.setEnrolledCourses(studentCourses);
                        student.setTotalCredits(student.getTotalCredits() + course.getCredits());  // Updating the number of credits
                        studentController.update(student);  // Updating the matching entity student from the repository

                        List<Long> courseStudents = course.getStudentsEnrolled();  // Actual lists of students enrolled for this course
                        courseStudents.add(student.getStudentId());  // Adding the new student  to the list
                        course.setStudentsEnrolled(courseStudents);
                        courseController.update(course);  // Updating the matching entity course from the repository

                        return true;
                    }

        return false;

    }

    /**
     * @return a List with all the Courses which still have free places left
     */
    public List<Course> retrieveCoursesWithFreePlaces() {
        List<Course> coursesWithFreePlaces = new ArrayList<>();
        courseController.findAll().forEach(course -> {
            if (course.getStudentsEnrolled().size() < course.getMaxEnrollment())  // If the number of students enrolled for the course is smaller than the maxEnrollment number
                coursesWithFreePlaces.add(course);  // Adding the course to the list with courses with free places left
        });

        return coursesWithFreePlaces;
    }

    /**
     * @param course - represents a Course to search by in the teachers courses
     * @return a List with all the students enrolled for the given course
     */
    public List<Long> retrieveStudentsEnrolledForACourse(Course course) {
        return courseController.findOne(course.getCourseID()).getStudentsEnrolled();
    }

    /**
     * @return A list with all the courses from the repository
     */
    public List<Course> getAllCourses() {
        List<Course> allCourses = new ArrayList<>();
        courseController.findAll().forEach(allCourses::add);

        return allCourses;
    }

    /**
     * @param teacher - represents a teacher who wants to delete a course
     * @param course  - represents a Course to search by in the repository with courses
     * @return true if the course was deleted, false otherwise
     * @author rares dan
     */
    public boolean deleteCourse(Teacher teacher, Course course) {
        for (Long i : teacher.getCourses())
            if (i == course.getCourseID()) {
                for (Long studentID : course.getStudentsEnrolled())
                    studentController.findOne(studentID).setTotalCredits(studentController.findOne(studentID).getTotalCredits() - course.getCredits());
                courseController.delete(i);

                return true;
            }
        return false;
    }

    /**
     * Creates and inserts a new student
     *
     * @param studentId student ID
     * @param firstName first name
     * @param lastName  last name
     * @throws Exception if file i/o fails
     */
    public void addStudent(long studentId, String firstName, String lastName) throws Exception {
        if (studentId >= 0) {
            Student student = new Student(firstName, lastName, studentId);
            if (this.studentController.save(student) == student)
                throw new IDInUseException("Id is already in use.");
        } else
            throw new NullIdException("Invalid id.");
    }

    /**
     * Creates and inserts a new teacher
     *
     * @param teacherId teacher ID
     * @param firstName first name
     * @param lastName  last name
     * @throws Exception if file i/o fails
     */
    public void addTeacher(long teacherId, String firstName, String lastName) throws Exception {
        if (teacherId >= 0) {
            Teacher teacher = new Teacher(firstName, lastName, teacherId);
            if (this.teacherController.save(teacher) == teacher)
                throw new IDInUseException("Id is already in use.");
        } else
            throw new NullIdException("Invalid id.");
    }

    /**
     * Creates an observable list of a teacher's students
     *
     * @param id teacher ID
     * @return list of students
     */
    public ObservableList<Student> observableStudents(long id) {
        Teacher teacher = teacherController.findOne(id);
        ObservableList<Student> students = FXCollections.observableArrayList();
        studentController.findAll().forEach(student -> {
            AtomicBoolean intersection = new AtomicBoolean(false);
            student.getEnrolledCourses().forEach(course -> {
                if (teacher.getCourses().contains(course))
                    intersection.set(true);
            });
            if (intersection.get())
                students.add(student);
        });
        return students;
    }

    /**
     * Creates an observable list of all courses
     *
     * @return list of courses
     */
    public ObservableList<Course> observableCourses() {
        ObservableList<Course> courses = FXCollections.observableArrayList();
        courseController.findAll().forEach(courses::add);
        return courses;
    }

    /**
     * Removes a student from a course
     *
     * @param studentId student ID
     * @param courseId  course ID
     * @throws Exception if file i/o fails
     */
    public void removeStudentFromCourse(long studentId, long courseId) throws Exception {
        Student student = studentController.findOne(studentId);
        Course course = courseController.findOne(courseId);
        if (student != null && course != null) {
            if (!student.getEnrolledCourses().contains(courseId))
                throw new AssignationException("This student is not enrolled in this course.");
            else {
                List<Long> courses = student.getEnrolledCourses();
                courses.remove(courseId);
                student.setEnrolledCourses(courses);

                List<Long> students = course.getStudentsEnrolled();
                students.remove(studentId);
                course.setStudentsEnrolled(students);

                student.setTotalCredits(student.getTotalCredits() - course.getCredits());

                this.studentController.update(student);
                this.courseController.update(course);
            }

        } else {
            throw new NullIdException("Invalid course or student ID.");
        }
    }

    /**
     * Checks if a teacher exists
     *
     * @param teacherId teacher ID
     * @param firstName first name
     * @param lastName  last name
     * @return true if found, false otherwise
     */
    public boolean checkTeacher(long teacherId, String firstName, String lastName) {
        Teacher teacher = this.teacherController.findOne(teacherId);
        if (teacher != null) {
            return teacher.getFirstName().equals(firstName) && teacher.getLastName().equals(lastName);
        }
        return false;
    }

    /**
     * Checks if a student exists
     *
     * @param studentId student ID
     * @param firstName first name
     * @param lastName  last name
     * @return true if found, false otherwise
     */
    public boolean checkStudent(long studentId, String firstName, String lastName) {
        Student student = this.studentController.findOne(studentId);
        if (student != null) {
            return student.getFirstName().equals(firstName) && student.getLastName().equals(lastName);
        }
        return false;
    }

    /**
     * Assigns a student to a course
     *
     * @param studentId student ID
     * @param courseId  course ID
     * @throws Exception if file i/o fails
     */
    public void assignStudentToCourse(long studentId, long courseId) throws Exception {
        Student student = studentController.findOne(studentId);
        Course course = courseController.findOne(courseId);
        if (student != null && course != null) {
            if (student.getEnrolledCourses().contains(courseId) || course.getStudentsEnrolled().contains(studentId))
                throw new AssignationException("This student is already enrolled in this course.");
            else if (course.getCredits() + student.getTotalCredits() > 30)
                throw new AssignationException("The total credit count is too high.");
            else if (course.getMaxEnrollment() - course.getStudentsEnrolled().size() == 0)
                throw new AssignationException("This course is already fully enrolled.");
            else {
                List<Long> courses = student.getEnrolledCourses();
                courses.add(courseId);
                student.setEnrolledCourses(courses);

                List<Long> students = course.getStudentsEnrolled();
                students.add(studentId);
                course.setStudentsEnrolled(students);

                student.setTotalCredits(student.getTotalCredits() + course.getCredits());

                this.studentController.update(student);
                this.courseController.update(course);
            }

        } else {
            throw new NullObjectException("Invalid course or student ID.");
        }
    }


    public StudentController getStudentController() {
        return studentController;
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
    }

    public TeacherController getTeacherController() {
        return teacherController;
    }

    public void setTeacherController(TeacherController teacherController) {
        this.teacherController = teacherController;
    }

    public CourseController getCourseController() {
        return courseController;
    }

    public void setCourseController(CourseController courseController) {
        this.courseController = courseController;
    }
}
