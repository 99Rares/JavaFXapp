package lab3.repository;

import lab3.model.Course;
import lab3.model.Teacher;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Extends CourseRepository
 * Contains methods:
 * - to serialize the data from the repository into Json Files
 * - to deserialize the data from Json Files
 */
public class CourseFileRepository extends CourseRepository implements FileRepository<Course> {

    private static FileWriter file;

    /**
     * Serializes the object list into a Json File
     *
     * @param listWithCourses list to be serialized
     */
    @Override
    public void writeData(Iterable<Course> listWithCourses) {
        JSONObject object = new JSONObject();
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        ObjectMapper mapper = new ObjectMapper(jsonFactory);
        try {
            file = new FileWriter("courses.json");
            JSONArray courses = new JSONArray();
            listWithCourses.forEach(courses::add);

            object.put("Courses", courses);
            mapper.writeValue(file, object);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Deserializes the data from a Json File into objects
     *
     * @return List with the deserialized objects form Json File
     * @throws IOException case reading goes wrong
     */
    @Override
    public List<Course> loadData() throws IOException {
        List<Course> courses = new ArrayList<>();
        Reader reader = new BufferedReader(new FileReader("courses.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode i : parser.path("Courses")) {
            JsonNode teacher1 = i.path("teacher");
            Teacher teacher = new Teacher(teacher1.path("firstName").asText(), teacher1.path("lastName").asText(), teacher1.path("teacherID").asLong());

            List<Long> students = new ArrayList<>();
            for (JsonNode node : i.path("studentsEnrolled"))
                students.add(node.asLong());

            Course course = new Course(i.path("name").asText(), teacher, i.path("maxEnrollment").asInt(), i.path("credits").asInt(), i.path("courseID").asLong());
            course.setStudentsEnrolled(students);

            courses.add(course);
        }
        reader.close();
        return courses;
    }

    public void populateListWithCourses() throws IOException {
        courses = loadData();
    }
}
