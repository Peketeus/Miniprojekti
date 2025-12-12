import com.bib.References;
import com.bib.Reference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;


public class AddReferenceTest {

    References references;
    Reference ref;

    
    // Suoritetaan ennen jokaista testiä
    @BeforeEach
    void setup() {
        references = new References();
        Map<String, String> data = new HashMap<>();

        String type = "book";
        String key = "DB06";
        String tag = "romaani";

        data.put("author", "Dan Brown");
        data.put("title", "Da Vinci -koodi");
        data.put("year", "2006");

        ref = new Reference(type, key, tag, data);
    }


    @DisplayName("Reference can be added")
    @Test
    void testAdd() {
        assertEquals(0, references.getSize());
        references.add(ref);
        assertEquals(1, references.getSize());
    }

    @Nested
    @DisplayName("When one reference has been added")
    public class TestOneReferenceAlreadyExists {

        @BeforeEach
        void setup() {
            references = new References();
            Map<String, String> data = new HashMap<>();

            String type = "book";
            String key = "DB06";
            String tag = "romaani";

            data.put("author", "Dan Brown");
            data.put("title", "Da Vinci -koodi");
            data.put("year", "2006");

            ref = new Reference(type, key, tag, data);
            references.add(ref);
        }


        @DisplayName("New reference can be added with unique key")
        @Test
        void testAddNew() {
            String type = "article";
            String key = "AH12";
            String tag = "news";
            Map<String, String> data = new HashMap<>();
            data.put("author", "Arto Hellas");
            data.put("title", "Foo Bar Foo");
            data.put("year", "2012");
            data.put("barfoo", "foobar");

            assertEquals(1, references.getSize());

            ref = new Reference(type, key, tag, data);
            references.add(ref);

            assertEquals(2, references.getSize());
        }

        
        @DisplayName("New reference can't be added if key already exists")
        @Test
        void testAddExisting() {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outContent));

            String type = "inproceedings";
            String key = "DB06"; // reference with this key already exists
            String tag = "";
            Map<String, String> data = new HashMap<>();
            data.put("author", "Foo Bar");

            assertEquals(1, references.getSize());

            ref = new Reference(type, key, tag, data);
            references.add(ref);

            String output = outContent.toString();
            assertTrue(output.contains("already exists"));

            assertEquals(1, references.getSize());
        }
    }
}
