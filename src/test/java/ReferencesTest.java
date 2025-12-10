import com.bib.References;
import com.bib.Reference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;


public class ReferencesTest {

    References references;
    Reference ref;

    
    // Suoritetaan ennen jokaista testiä
    @BeforeEach
    public void setup() {
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
    public void testAdd() {
        assertEquals(0, references.getSize());
        references.add(ref);
        assertEquals(1, references.getSize());
    }

    @Nested
    @DisplayName("When one reference has been added")
    public class TestOneReferenceAlreadyExists {

        @BeforeEach
        public void setup() {
            references = new References();
            Map<String, String> data = new HashMap<>();

            String type = "book";
            String key = "DB06";
            String tag = "romaani";

            data.put("author", "Dan Brown");
            data.put("title", "Da Vinci -koodi");
            data.put("year", "2006");

            System.out.println(references.getSize());

            ref = new Reference(type, key, tag, data);
            references.add(ref);

            System.out.println(references.getSize());
        }


        @DisplayName("New reference can be added with unique key")
        @Test
        public void testAddNew() {
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
        public void testAddExisting() {
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
        

        @DisplayName("Reference can be removed")
        @Test
        public void testRemove() {
            assertEquals(1, references.getSize());
            assertEquals(true, references.delete(ref));
            assertEquals(0, references.getSize());
            assertEquals(false, references.delete(ref));
        }

        @DisplayName("References field can be edited")
        @Test
        public void testEditField() {
            assertEquals("Dan Brown", ref.getData().get("author"));
            assertEquals(1, references.getSize());

            Map<String, String> newData = new HashMap<>();
            newData.put("author", "Foo Bar");
            newData.put("title", "Da Vinci -koodi");
            newData.put("year", "2006");

            Reference edited = new Reference(
                ref.getType(), 
                ref.getKey(),
                ref.getTag(), 
                newData);
            references.edit(ref, edited);

            Reference result = references.findReferenceByKey(ref.getKey());

            assertEquals(1, references.getSize());
            assertEquals("Foo Bar", result.getField("author")); // muuttunut
            assertEquals("Da Vinci -koodi", result.getField("title")); // ei muuttunut
            assertEquals("2006", result.getField("year")); // ei muuttunut
        }

        @DisplayName("New field can be added when editing a reference")
        @Test
        public void testEditNewField() {
            assertNull(ref.getField("journal"));

            Map<String, String> newData = new HashMap<>();
            newData.put("author", "Dan Brown");
            newData.put("title", "Da Vinci -koodi");
            newData.put("year", "2006");
            newData.put("journal", "Journal");

            Reference edited = new Reference(
                ref.getType(), 
                ref.getKey(),
                ref.getTag(),
                newData);
            references.edit(ref, edited);

            Reference result = references.findReferenceByKey(ref.getKey());

            assertEquals("Journal", result.getField("journal"));
        }
    }
}
