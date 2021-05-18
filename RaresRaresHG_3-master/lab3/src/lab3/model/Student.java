package lab3.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student, which has a StudentId ( Id for uni), a totalCredits number, which represents the sum of all the credits
 * from each course in which he is enrolled, and a list of his courses
 * It extends the Person class, so the Student gets a firstName and a lastName
 *
 * @author rares astilean
 */
public class Student extends Person {
    private long StudentId;
    private int totalCredits;
    private List<Long> enrolledCourses = new ArrayList<>();

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Student(String firstName, String lastName, long studentId) {
        super(firstName, lastName);
        StudentId = studentId;
    }

    public long getStudentId() {
        return StudentId;
    }

    public void setStudentId(long studentId) {
        StudentId = studentId;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public List<Long> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Long> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName=" + this.getFirstName() +
                ", lastName=" + this.getLastName() +
                ", StudentIt=" + StudentId +
                ", totalCredits=" + totalCredits +
                ", enrolledCourses=" + enrolledCourses +
                '}';
    }
}
