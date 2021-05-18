package lab3.repository;

import lab3.model.Student;
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
 * Extends StudentRepository
 * Contains methods:
 * - to serialize the data from the repository into Json Files
 * - to deserialize the data from Json Files
 */
public class StudentFileRepository extends StudentRepository implements FileRepository<Student> {

    private static FileWriter file;

    /**
     * Serializes the object list into a Json File
     *
     * @param listWithStudents list to be serialized
     */
    public void writeData(Iterable<Student> listWithStudents) {
        JSONObject object = new JSONObject();
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        ObjectMapper mapper = new ObjectMapper(jsonFactory);

        try {
            file = new FileWriter("students.json");
            JSONArray students = new JSONArray();
            listWithStudents.forEach(students::add);

            object.put("Students", students);
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
    public List<Student> loadData() throws IOException {
        List<Student> students = new ArrayList<>();

        Reader reader = new BufferedReader(new FileReader("students.json"));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode pm : parser.path("Students")) {
            Student s = new Student(pm.path("firstName").asText(), pm.path("lastName").asText());
            s.setStudentId(pm.path("studentId").asLong());
            s.setTotalCredits(pm.path("totalCredits").asInt());

            List<Long> coursesId = new ArrayList<>();
            for (JsonNode node : pm.path("enrolledCourses"))
                coursesId.add(node.asLong());
            s.setEnrolledCourses(coursesId);

            students.add(s);
        }

        reader.close();

        return students;
    }

    /**
     * Deserializes the list of students
     *
     * @throws IOException all sort of input/output runtime errors
     */
    public void populateListWithStudents() throws IOException {
        students = loadData();
    }
}
