import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;

import org.junit.*;

public class ReferencesTest {
    
    @Test
    public void testAdd() {
        References refrences = new References();
        Map<String,String> data = new HashMap<>();
        data.put("author", "Dan Brown");
        data.put("title", "Da Vinci -koodi");
        data.put("year","2006");
        Reference ref = new Reference("dbrown06", "book", data);

        assertEquals(0, refrences.getSize());

        refrences.add(ref);

        assertEquals(1, refrences.getSize());
    }

    @Test
    public void testDelete() {
        References refrences = new References();
        Map<String,String> data = new HashMap<>();
        data.put("author", "Dan Brown");
        data.put("title", "Da Vinci -koodi");
        data.put("year","2006");
        Reference ref = new Reference("dbrown06", "book", data);

        assertEquals(false, refrences.delete(ref));

        refrences.add(ref);

        assertEquals(true, refrences.delete(ref));
        assertEquals(false, refrences.delete(ref)); 
    }

    /* 
    @Test
    public void testEdit() {
        References refrences = new References();
        Article art = new Article("key", "author", "title", "journal", "year", "volume", "pages");
        refrences.add(art);
        String[] test = {"key", "author", "title", "journal", "year", "volume", "pages"};
        assertArrayEquals(test, refrences.information(art));

        refrences.edit(art, "author", "toimii");
        String[] testMuokattu = {"key", "toimii", "title", "journal", "year", "volume", "pages"};
        assertArrayEquals(testMuokattu, refrences.information(art));

    } */
}
