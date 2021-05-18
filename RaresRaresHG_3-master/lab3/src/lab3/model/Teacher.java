package lab3.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a teacher, which has a teacherId ( id for the university), and a list of his courses
 * It extends the Person class, so the Teacher gets a firstName and a lastName
 *
 * @author rares dan
 */
public class Teacher extends Person {
    private List<Long> coursesId = new ArrayList<>();
    private long teacherID;

    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Teacher(String firstName, String lastName, long teacherID) {
        super(firstName, lastName);
        this.teacherID = teacherID;
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    public List<Long> getCourses() {
        return coursesId;
    }

    public long getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(long teacherID) {
        this.teacherID = teacherID;
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    public void setCourses(List<Long> courses) {
        this.coursesId = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "firstName=" + this.getFirstName() +
                ", lastName=" + this.getLastName() +
                ", TeacherID=" + teacherID +
                ", courses=" + coursesId +
                '}';
    }

}
