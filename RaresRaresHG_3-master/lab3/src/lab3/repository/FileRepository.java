package lab3.repository;

import java.io.IOException;
import java.util.List;

/**
 * @param <E> generic parameter, each repository who uses files should implement this class
 */
public interface FileRepository<E> {
    /**
     * @param list List that needs to be serialzied in a json file
     * @throws IOException case serializing goes wrong
     */
    void writeData(Iterable<E> list) throws IOException;

    /**
     * @return the deserialized data in form of a list
     * @throws IOException case deserializing goes wrong
     */
    List<E> loadData() throws IOException;
}
