package lab3.repository;

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
 * Extends TeacherRepository
 * Contains methods:
 * - to serialize the data from the repository into Json Files
 * - to deserialize the data from Json Files
 */
public class TeacherFileRepository extends TeacherRepository implements FileRepository<Teacher> {

    private static FileWriter file;

    /**
     * Serializes the object list into a Json File
     *
     * @param listWithTeachers list to be serialized
     */
    @Override
    public void writeData(Iterable<Teacher> listWithTeachers) {
        JSONObject object = new JSONObject();
        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        ObjectMapper mapper = new ObjectMapper(jsonFactory);

        try {
            file = new FileWriter("teachers.json");
            JSONArray teachers = new JSONArray();
            listWithTeachers.forEach(teachers::add);

            object.put("Teachers", teachers);
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
    public List<Teacher> loadData() throws IOException {
        List<Teacher> teachers = new ArrayList<>();

        Reader reader = new BufferedReader(new FileReader("teachers.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parser = objectMapper.readTree(reader);

        for (JsonNode i : parser.path("Teachers")) {
            Teacher teacher = new Teacher(i.path("firstName").asText(), i.path("lastName").asText());
            teacher.setTeacherID(i.path("teacherID").asLong());
            List<Long> coursesId = new ArrayList<>();
            for (JsonNode node : i.path("courses"))
                coursesId.add(node.asLong());
            teacher.setCourses(coursesId);

            teachers.add(teacher);
        }
        reader.close();
        return teachers;
    }

    public void populateListWithTeachers() throws IOException {
        teachers = loadData();
    }
}
