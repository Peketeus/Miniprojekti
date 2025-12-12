import com.bib.Reference;
import com.bib.References;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class EditReferenceTest {

    private References references;

    @BeforeEach
    void setUp() {
        references = new References();

        // ilman dataa
        references.add(new Reference("Article", "AR11", "", new HashMap<>()));

        // datalla
        HashMap<String, String> data = new HashMap<>();
        data.put("Author", "John Doe");
        data.put("Year", "2020");
        data.put("Title", "Sample Title One");
        references.add(new  Reference("Book", "BK11", "important", data));
    }

    @DisplayName("References field can be edited")
    @Test
    void testEditField() {
        Reference toEdit = references.findReferenceByKey("BK11");
        assertNotNull(toEdit);

        // vuoden editointi
        HashMap<String, String> newData = new HashMap<>();
        newData.put("Author", toEdit.getField("Author"));
        newData.put("Year", "1999");
        newData.put("Title", "Sample Title One");
        Reference updated = new Reference(toEdit.getType(), toEdit.getKey(), toEdit.getTag(), newData);

        assertEquals(2, references.getSize());
        references.edit(toEdit, updated);
        assertEquals(2, references.getSize());

        for (Reference ref: references.getAll()) {
            assertNotSame(toEdit, ref);
            if (ref.getKey().equals(toEdit.getKey())) {
                assertEquals(toEdit.getType(), ref.getType());
                assertEquals(toEdit.getTag(), ref.getTag());
                assertEquals(toEdit.getKey(), ref.getKey());
                assertEquals(toEdit.getField("Author"), ref.getField("Author"));
                assertNotEquals(toEdit.getField("Year"), ref.getField("Year"));
                assertEquals(toEdit.getField("Title"), ref.getField("Title"));
            }
        }
    }

    @DisplayName("New field can be added when editing a reference")
    @Test
    void testEditNewField() {
        Reference toEdit = references.findReferenceByKey("AR11");
        assertEquals(0, toEdit.getData().size());

        HashMap<String, String> newData = new HashMap<>();
        newData.put("Author", "John Doe");

        Reference updated = new Reference(toEdit.getType(), toEdit.getKey(), toEdit.getTag(), newData);
        assertEquals(1, updated.getData().size());

        references.edit(toEdit, updated);

        assertNotEquals(toEdit.getField("Author"), updated.getField("Author"));
    }

    @DisplayName("Existing field can be removed when editing reference")
    @Test
    void testRemoveField() {
        Reference toEdit = references.findReferenceByKey("BK11");
        assertEquals(3, toEdit.getData().size());

        HashMap<String, String> newData = new HashMap<>();
        newData.put("Author", toEdit.getField("Author"));
        newData.put("Year", "2020");

        Reference updated = new Reference(toEdit.getType(), toEdit.getKey(), toEdit.getTag(), newData);
        assertEquals(2, updated.getData().size());

        assertNotEquals(toEdit.getField("Title"), updated.getField("Title"));
    }

}
