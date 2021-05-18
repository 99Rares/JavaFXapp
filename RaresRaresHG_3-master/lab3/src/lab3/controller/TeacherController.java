package lab3.controller;

import lab3.model.Teacher;
import lab3.repository.TeacherFileRepository;


/**
 * Represent a Controller for the Teacher Repository
 * It implements the interface ICrudRepository
 * It calls the methods for CRUD operations on the repository of teachers
 *
 * @author rares dan
 */
public class TeacherController {
    /**
     * the repository which will be controlled trough this class
     */
    private TeacherFileRepository teacherFileRepository;

    public TeacherFileRepository getTeacherFileRepository() {
        return teacherFileRepository;
    }

    public void setTeacherFileRepository(TeacherFileRepository teacherFileRepository) {
        this.teacherFileRepository = teacherFileRepository;
    }

    public TeacherController(TeacherFileRepository teacherFileRepository) {
        this.teacherFileRepository = teacherFileRepository;
    }

    /**
     * @param id -the id of the teacher to be returned. The id must not be null
     * @return the teacher with the specified id or null - if there is no teacher with the given id
     */
    public Teacher findOne(Long id) {
        return teacherFileRepository.findOne(id);
    }

    /**
     * @return all teachers
     */
    public Iterable<Teacher> findAll() {
        return teacherFileRepository.findAll();
    }

    /**
     * @param entity teacher must be not null
     * @return null- if the given teacher is saved otherwise returns the teacher (if he/she already exists)
     */
    public Teacher save(Teacher entity) {
        return teacherFileRepository.save(entity);
    }

    /**
     * removes the teacher with the specified id
     *
     * @param id id must be not null
     * @return the removed teacher or null if there is no teacher with the given id
     */
    public Teacher delete(Long id) {
        return teacherFileRepository.delete(id);
    }

    /**
     * @param entity teacher must not be null
     * @return null - if the teacher is updated, otherwise returns the teacher
     */
    public Teacher update(Teacher entity) {
        return teacherFileRepository.update(entity);
    }

}
