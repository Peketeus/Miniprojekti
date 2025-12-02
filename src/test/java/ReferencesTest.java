import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;



public class ReferencesTest {

    References references;
    Reference ref;

    // Suoritetaan ennen jokaista testiä
    @BeforeEach
    public void setup() {
        references = new References();
        Map<String,String> data = new HashMap<>();

        String type = "book";
        String key = "DB06";

        data.put("author", "Dan Brown");
        data.put("title", "Da Vinci -koodi");
        data.put("year", "2006");

        ref = new Reference(type, key, data);
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
    public class testOneReferenceAlreadyExists {

        @BeforeEach
        public void setup() {
            references = new References();
            Map<String,String> data = new HashMap<>();

            String type = "book";
            String key = "DB06";

            data.put("author", "Dan Brown");
            data.put("title", "Da Vinci -koodi");
            data.put("year", "2006");

            System.out.println(references.getSize());

            ref = new Reference(type, key, data);
            references.add(ref);

            System.out.println(references.getSize());
        }

        @DisplayName("New reference can be added with unique key")
        @Test
        public void testAddNew() {
            String type = "article";
            String key = "AH12";
            Map<String,String> data = new HashMap<>();
            data.put("author", "Arto Hellas");
            data.put("title", "Foo Bar Foo");
            data.put("year", "2012");
            data.put("barfoo", "foobar");

            assertEquals(1, references.getSize());

            ref = new Reference(type, key, data);
            references.add(ref);

            assertEquals(2, references.getSize());
        }

        /* ei toimi vielä pitäisi tehdä tarkistus add metodiin
        @DisplayName("New reference can't be added if key already exists")
        @Test
        public void testAddExisting() {
            ByteArrayOutputStream outContent = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outContent));

            String type = "inproceedings";
            String key = "DB06"; // reference with this key already exists
            Map<String,String> data = new HashMap<>();
            data.put("author", "Foo Bar");

            assertEquals(1, references.getSize());

            ref = new Reference(type, key, data);
            references.add(ref);

            String output = outContent.toString();
            assertTrue(output.contains("already exists"));

            assertEquals(1, references.getSize());
        }
        */

        @DisplayName("Reference can be removed")
        @Test
        public void testRemove() {
            assertEquals(1, references.getSize());
            assertEquals(true, references.delete(ref));
            assertEquals(0, references.getSize());
            assertEquals(false, references.delete(ref));
        }

    }
}
