import com.bib.Reference;
import com.bib.References;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class RemoveReferenceTest {

    References references;

    @BeforeEach
    public void setup() {
        references = new References();

        references.add(new Reference("article", "key1", "tag1", new HashMap<>()));
        references.add(new Reference("book", "key2", "tag2", new HashMap<>()));
    }

    @DisplayName("Reference can be removed")
    @Test
    void remove_with_valid_key() {
        assertEquals(2, references.getSize());
        boolean result = references.delete(references.findReferenceByKey("key1"));
        assertTrue(result);
        assertEquals(1, references.getSize());
        references.delete(references.findReferenceByKey("key2"));
        assertEquals(0, references.getSize());
    }

    @DisplayName("Reference can not be removed with invalid key")
    @Test
    void remove_with_invalid_key() {
        assertEquals(2, references.getSize());
        Reference invalid = references.findReferenceByKey("not exist");
        assertNull(invalid);

        boolean result = references.delete(invalid);
        assertFalse(result);
        assertEquals(2, references.getSize());
    }
}
