package lab3.controller;

import lab3.model.Student;
import lab3.repository.StudentFileRepository;

import java.util.ArrayList;
import java.util.List;


/**
 * Represent a Controller for the Student Repository
 * It implements the interface ICrudRepository
 * It calls the methods for CRUD operations on the repository of students
 *
 * @author rares astilean
 */
public class StudentController {
    /**
     * the repository which will be controlled trough this class
     */
    private StudentFileRepository studentFileRepository;

    public StudentController(StudentFileRepository studentFileRepository) {
        this.studentFileRepository = studentFileRepository;
    }

    public StudentFileRepository getStudentFileRepository() {
        return studentFileRepository;
    }

    public void setStudentFileRepository(StudentFileRepository studentFileRepository) {
        this.studentFileRepository = studentFileRepository;
    }

    /**
     * @param id -the id of the entity to be returned id must not be null
     * @return the entity with the specified id or null - if there is no entity with the given id
     */
    public Student findOne(Long id) {
        return studentFileRepository.findOne(id);
    }

    /**
     * @return all entities
     */
    public Iterable<Student> findAll() {
        return studentFileRepository.findAll();
    }

    /**
     * @param entity entity must be not null
     * @return null- if the given entity is saved otherwise returns the entity (id already exists)
     */
    public Student save(Student entity) {
        return studentFileRepository.save(entity);
    }

    /**
     * removes the entity with the specified id
     *
     * @param id id must be not null
     * @return the removed entity or null if there is no entity with the given id
     */
    public Student delete(Long id) {
        return studentFileRepository.delete(id);
    }

    /**
     * @param entity entity must not be null
     * @return null - if the entity is updated, otherwise returns the entity - (e.g id does not exist).
     */
    public Student update(Student entity) {
        return studentFileRepository.update(entity);
    }


    /**
     * Sorts the students in lexicographic order by lastName and firstName
     */
    public void sortInLexicographicOrder() {
        List<Student> students = new ArrayList<>();
        studentFileRepository.findAll().forEach(students::add);
        students.sort((o1, o2) -> {
            if (o1.getLastName().equals(o2.getLastName()))
                return o1.getFirstName().compareTo(o2.getFirstName());
            else
                return o1.getLastName().compareTo(o2.getLastName());
        });

        studentFileRepository.setStudents(students);
    }
}
