import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bib.Reference;
import com.bib.References;

/**
 * Testiluokka References-luokan readFile()-metodille
 *
 * Testeillä varmistetaan, että:
 * - references.bib löydetään oikeasta paikasta
 * - viitteet luetaan ja lisätään listaan
 * - kentät ja tagi parsitaan oikein
 * - tyhjä tiedosto ei riko ohjelmaa
 */
public class ReadFileTest {

    /**
     * Laskee saman projektijuuren polun kuin readFile(),
     * ja rakentaa siitä polun src/data/references.bib-tiedostoon
     */
    private File getBibFilePath() {
        String path = new File("testi.txt").getAbsolutePath();

        int idx = path.indexOf("Miniprojekti");
        if (idx != -1) {
            path = path.substring(0, idx + 13);
        } else {
            path = "";
        }

        return new File(path + "src/data/references.bib");
    }


    /**
     * Kirjoittaa annettuun references.bib -tiedostoon annetut rivit
     */
    private void writeBibFile(String... lines) throws Exception {
        File bib = getBibFilePath();
        bib.getParentFile().mkdirs();

        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(bib), StandardCharsets.UTF_8))) {

            for (String line : lines) {
                pw.println(line);
            }
        }
    }


     /**
     * Varmistetaan, että readFile() lisää vähintään yhen viitteen,
     * kun tiedostossa on yksi @book-viite tagilla
     */
    @DisplayName("readFile() lukee vähintään yhden viitteen")
    @Test
    void atLeastOneReference() throws Exception {
        // Luodaan yksinkertainen BibTeX-tiedosto testiä varten
        writeBibFile(
            "@book{book1,",
            "\ttitle = {Titanic},",
            "\tyear = {1997},",
            "\tauthor = {Nicola Pierce},",
            "\tTags = {romaani},",
            "}"
        );

        // Luetaan tiedosto
        References references = new References();
        references.readFile();

        // Varmistetaan, että vähintään yksi lisätty
        assertTrue(references.getSize() > 0,
            "readFile() ei lisännyt yhtään viitettä");
    }


    /**
     * Testataan, että readFile() parsii mm. viitteen tyypin, avaimen, tagin
     * ja kentät oikein
     */
    @DisplayName("readFile() parsii kentät ja tagin oikein")
    @Test
    void parsesCorrectly() throws Exception {
        writeBibFile(
            "@book{book1,",
            "\ttitle = {Titanic},",
            "\tyear = {1997},",
            "\tauthor = {Nicola Pierce},",
            "\tTags = {romaani},",
            "}"
        );

        References references = new References();
        references.readFile();

        assertEquals(1, references.getSize(), "Viitteitä pitäisi olla tasan yksi");

        Reference ref = references.getAll().get(0);

        // Tarkistetaan type, key ja tag
        assertEquals("book", ref.getType());
        assertEquals("book1", ref.getKey());
        assertEquals("romaani", ref.getTag());

        // Tarkistetaan muut kentät
        assertEquals("Titanic", ref.getField("title"));
        assertEquals("1997", ref.getField("year"));
        assertEquals("Nicola Pierce", ref.getField("author"));
    }


    /**
     * Testataan sitä, jos references.bib on tyhjä,
     * readFile() ei lisää yhtään viitteitä
     * @throws Exception
     */
    @DisplayName("readFile() ei lisää mitään, jos tiedosto tyhjä")
    @Test
    void emptyFileDoesNothing() throws Exception {
        // Kirjoitetaa tyhjä tiedosto
        writeBibFile();

        References references = new References();
        references.readFile();

        assertEquals(0, references.getSize(),
            "Tyhjästä tiedostosta ei saisi tulla viitteitä");
    }


    /**
     * Testataan, että readFile() toimii silloinkin,
     * kun BibTeX-viitteessä ei ole tagsejä
     * @throws Exception
     */
    @DisplayName("readFile() toimii myös ilman Tags-riviä")
    @Test
    void WorksWithoutTags() throws Exception {
        writeBibFile(
            "@article{art1,",
            "\ttitle = {Punarutto},",
            "\tyear = {2025},",
            "\tauthor = {Ilkka Remes},",
            "}"
        );

        References references = new References();
        references.readFile();

        assertEquals(1, references.getSize());
        Reference ref = references.getAll().get(0);

        assertEquals("article", ref.getType());
        assertEquals("art1", ref.getKey());
        assertEquals("Punarutto", ref.getField("title"));
        assertEquals("2025", ref.getField("year"));
        assertEquals("Ilkka Remes", ref.getField("author"));

        // Tagia ei ole määritelty-> pitäisi olla null/tyhjä
        assertTrue(ref.getTag() == null || ref.getTag().isEmpty(),
            "Tagin pitäisi olla null/tyhjä, jos Tags-rivi puuttuu");
    }


    /**
     * Testataan, että readFile() osaa lueka useamman viitteen
     * 
     * Tiedostossa on 2 viitettä (@book ja @article), ja molemmat pitäisi
     * päätyä rlistaan erillisinä olioina
     * @throws Exception
     */
    @DisplayName("readFile() lukee useamman viitteen samasta tiedostosta")
    @Test
    void multipleReferences() throws Exception {
        writeBibFile(
            "@book{book1,",
            "\ttitle = {Titanic},",
            "\tyear = {1997},",
            "\tauthor = {Nicola Pierce},",
            "}",
            "",
            "@article{art1,",
            "\ttitle = {Punarutto},",
            "\tyear = {2025},",
            "\tauthor = {Ilkka Remes},",
            "}"
    );

    References references = new References();
    references.readFile();

    assertEquals(2, references.getSize(),
        "Kahdesta viitteestä pitäisi syntyä kaksi Reference-oliota");
}

}
