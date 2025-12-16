import com.bib.Reference;
import com.bib.References;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchReferencesTest {

    private References references;
    private ByteArrayOutputStream out;
    private String output;

    @BeforeEach
    void setup() {
        references = new References();

        Map<String, String> data1 = new HashMap<>();
        data1.put("author", "Dan Brown");
        data1.put("title", "Da Vinci -koodi");
        data1.put("year", "2006");
        references.add(new Reference("Book", "BK00", "important", data1));

        Map<String, String> data2 = new HashMap<>();
        data2.put("author", "Alice Doe");
        data2.put("title", "Science Today");
        data2.put("year", "2019");
        references.add(new Reference("Article", "AR00", "research", data2));

        Map<String, String> data3 = new HashMap<>();
        data3.put("author", "Dan Brown"); // sama author kuin data1
        data3.put("title", "Inferno");
        data3.put("year", "2021");
        references.add(new Reference("Book", "BK01", "unfinished", data3));

        Map<String, String> data4 = new HashMap<>();
        data4.put("author", "Bob Brown");
        data4.put("conference", "ICSE");
        data4.put("year", "2019"); // sama year kuin data2
        references.add(new Reference("Inproceedings", "IP00", "important", data4));

        Map<String, String> data5 = new HashMap<>();
        data5.put("author", "Carol White");
        data5.put("title", "Algorithms");
        data5.put("year", "2022");
        references.add(new Reference("Book", "BK02", "favorite", data5));
    }

    @DisplayName("Searching by tag returns only references that match the given tag")
    @Test
    void testSearchByTag() {
        String tag = "important";

        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        references.printReferences(tag, "Tag");

        output = out.toString();

        for (Reference ref : references.getAll()) {
            if (ref.getTag().equalsIgnoreCase(tag)) {
                assertTrue(output.contains("tags: " + ref.getTag()));
            } else {
                assertFalse(output.contains("tags: " + ref.getTag()));
            }
        }
    }

    @DisplayName("Searching by type returns only references that match the given type")
    @Test
    void testSearchByType() {
        String type = "Book";

        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        references.printReferences(type, "Type");

        output = out.toString();

        for (Reference ref : references.getAll()) {
            if (ref.getType().equalsIgnoreCase(type)) {
                assertTrue(output.contains("type: " + ref.getType()));
            } else {
                assertFalse(output.contains("type: " + ref.getType()));
            }
        }
    }

    @DisplayName("Searching by data returns only references that contains the given data")
    @Test
    void testSearchByData() {
        String data = "Brown";

        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        references.printReferences(data, "Data");

        output = out.toString();

        for (Reference ref : references.getAll()) {
            String result = ref.getField("author") != null ? ref.getField("author") : "";

            if (result.contains(data)) {
                assertTrue(output.contains(result));
            } else  {
                assertFalse(output.contains(result));
            }
        }
    }

    @DisplayName("Search all references")
    @Test
    void testSearchAll() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        references.printReferences(null, null);

        output = out.toString();

        for (Reference ref : references.getAll()) {
            assertTrue(output.contains(ref.getKey()));
        }
    }

    @DisplayName("Valid message is shown if the search param does not match any references")
    @Test
    void testInvalidSearchParam() {
        String search = "Harry Potter";

        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        references.printReferences(search, "Data");

        output = out.toString();

        assertTrue(output.contains("References not found"));
    }
}
