package lab3.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course from the uni
 * A course has a name, a teacher who teaches the course, a max Number of students that can enroll, a list with all the
 * students, which have signed up for the course and a number of credits
 *
 * @author rares astilean
 */
public class Course {
    private String name;
    private Person teacher;
    private int maxEnrollment;
    private List<Long> studentsEnrolled = new ArrayList<>();
    private int credits;
    private long courseID;

    public Course(String name, Person teacher, int maxEnrollment, int credits, long courseID) {
        this.name = name;
        this.teacher = teacher;
        this.maxEnrollment = maxEnrollment;
        this.credits = credits;
        this.courseID = courseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getTeacher() {
        return teacher;
    }

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public List<Long> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public void setStudentsEnrolled(List<Long> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", maxEnrollment=" + maxEnrollment +
                ", credits=" + credits +
                ", courseID=" + courseID +
                '}';
    }
}
