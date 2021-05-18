package lab3.repository;

import lab3.model.Teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a repository for teachers
 * It implements the interface ICrudRepository
 * It contains methods for CRUD operations on the list with teachers
 *
 * @author rares dan
 */
public class TeacherRepository implements ICrudRepository<Teacher> {
    /**
     * A list of all the teachers
     */
    protected List<Teacher> teachers = new ArrayList<>();

    /**
     * @param id -the id of the teacher to be returned. The id must not be null
     * @return the teacher with the specified id or null - if there is no teacher with the given id
     */
    @Override
    public Teacher findOne(Long id) {
        if (id == null)
            throw new NullPointerException("id soll nicht 0 sein");
        for (Teacher i : teachers) // searching the list for an existing teacher with the teacherId "id"
            if (i.getTeacherID() == id)
                return i;// when found, return the teacher
        return null;// if not found
    }

    /**
     * @return all teachers
     */
    @Override
    public Iterable<Teacher> findAll() {
        return teachers;
    }

    /**
     * @param entity teacher must be not null
     * @return null- if the given teacher is saved otherwise returns the teacher (if he/she already exists)
     */
    @Override
    public Teacher save(Teacher entity) {
        if (entity == null)
            throw new NullPointerException("entity soll nicht null sein");
        for (Teacher i : teachers)// searching the list for  an existing teacher with the teacherId "id"
            if (i.getTeacherID() == entity.getTeacherID()) // If the teacher already exists
                return null;
        teachers.add(entity);// If the list doesn't contain the teacher, add it to the list
        return entity;
    }

    /**
     * removes the teacher with the specified id
     *
     * @param id id must be not null
     * @return the removed teacher or null if there is no teacher with the given id
     */
    @Override
    public Teacher delete(Long id) {
        if (id == null)
            throw new NullPointerException("id soll nicht null sein");
        for (Teacher i : teachers)// searching the list for an existing teacher with the teacherId "id"
            if (i.getTeacherID() == id) {// If the list does contain the teacher, remove it from the list
                teachers.remove(i);
                return i;
            }
        return null;
    }

    /**
     * @param entity teacher must not be null
     * @return null - if the teacher is updated, otherwise returns the teacher
     */
    @Override
    public Teacher update(Teacher entity) {
        if (entity == null)
            throw new NullPointerException("entity soll nicht null sein");
        for (Teacher i : teachers)// searching the list for an existing teacher with the teacherId "id"
            if (i.getTeacherID() == entity.getTeacherID()) {// If the list does contain the teacher, update it
                teachers.remove(i);//remove the old version
                teachers.add(entity);//add the new version
            }
        return entity;
    }

    /**
     * The function iterates trough the list of teachers and concatenates the toString method of each teacher to the final string
     *
     * @return A string containing information about all the teachers from the university
     */
    @Override
    public String toString() {
        StringBuilder TeacherString = new StringBuilder();
        for (Teacher i : teachers)
            TeacherString.append(i.toString()).append('\n');
        return TeacherString.toString();
    }
}
