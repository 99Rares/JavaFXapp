package lab3.repository;

import lab3.Exceptions.NullIdException;
import lab3.Exceptions.NullObjectException;
import lab3.model.Student;

import java.util.ArrayList;
import java.util.List;


/**
 * Represent repository for students
 * It implements the interface ICrudRepository
 * It contains methods for CRUD operations on the list with students
 *
 * @author rares astilean
 */
public class StudentRepository implements ICrudRepository<Student> {
    /**
     * A list of all the students
     */
    protected List<Student> students = new ArrayList<>();

    /**
     * @param id -the id of the entity to be returned id must not be null
     * @return the entity with the specified id or null - if there is no entity with the given id
     */
    @Override
    public Student findOne(Long id) {
        try {
            if (id == null)
                throw new NullIdException("id most not be null");

            for (Student student : students)  // searching the list for the student with studentId "id"
                if (student.getStudentId() == id)  // when found, return the student
                    return student;
        } catch (NullIdException e) {
            e.printStackTrace();
        }

        return null;  // if not found
    }

    /**
     * @return all entities
     */
    @Override
    public Iterable<Student> findAll() {
        return students;
    }

    /**
     * @param entity entity must be not null
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     */
    @Override
    public Student save(Student entity) {
        try {
            if (entity == null)
                throw new NullObjectException("entity must not be null");

            for (Student student : students)  // Searching for an already existing entity by id
                if (student.getStudentId() == entity.getStudentId())  // If the entity already exists
                    return null;

            students.add(entity);  // If the list doesn't contain the student "entity", add it to the list
            return entity;  // and return it
        } catch (NullObjectException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * removes the entity with the specified id
     *
     * @param id id must be not null
     * @return the removed entity or null if there is no entity with the given id
     */
    @Override
    public Student delete(Long id) {
        try {
            if (id == null)
                throw new NullIdException("id most not be null");

            for (Student student : students)  // Searching for the student with studentId "id"
                if (student.getStudentId() == id) {
                    students.remove(student);  // If the lists contains the student with studentId "id", remove it from the list
                    return student;  // and return the removed student
                }
        } catch (NullIdException e) {
            e.printStackTrace();
        }

        return null;  // If the list doesn't contain the student with studentId "id"
    }


    /**
     * @param entity entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity - (e.g id does not exist).
     */
    @Override
    public Student update(Student entity) {
        try {
            if (entity == null)
                throw new NullObjectException("entity must not be null");

            for (Student student : students) // Searching for the student in the list, Matching every student with entity by studentId
                if (student.getStudentId() == entity.getStudentId()) {  // If a match was found
                    students.remove(student);  // delete the current student
                    students.add(entity);  // add the student with same studentId, but modified attributes
                    return entity;  // and return it
                }
        } catch (NullObjectException e) {
            e.printStackTrace();
        }

        return null;  // If no match was found
    }

    /**
     * The function iterates trough the list with students and concatenates the toString method of each student to the final string
     *
     * @return A string containing information about all the students from the uni
     */
    @Override
    public String toString() {
        StringBuilder studentsString = new StringBuilder();
        for (Student student : students)
            studentsString.append(student.toString()).append("\n");
        return studentsString.toString();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
