import static org.junit.Assert.assertEquals;

import org.junit.*;

public class ReferencesTest {
    
    @Test
    public void testAdd() {
        References refrences = new References();
        Article art = new Article("key", "author", "title", "journal", "year", "volume", "pages");

        assertEquals(0, refrences.getSize());

        refrences.add(art);

        assertEquals(1, refrences.getSize());
    }

    @Test
    public void testDelete() {
        References refrences = new References();
        Article art = new Article("key", "author", "title", "journal", "year", "volume", "pages");

        assertEquals(false, refrences.delete(art));

        refrences.add(art);

        assertEquals(true, refrences.delete(art));
        assertEquals(false, refrences.delete(art));

        
    }


}
