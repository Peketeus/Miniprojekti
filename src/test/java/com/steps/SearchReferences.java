package com.steps;

import com.bib.Reference;
import com.bib.References;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.jupiter.api.Assertions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SearchReferences {

    private References references;
    private ByteArrayOutputStream out;
    private String output;

    // suoritetaan ennen jokaista testiä (voisi myöhemmin laittaa toimimaan lukemalla tiedostosta)
    @Before
    public void setUp() {
        references = new References();

        HashMap<String, String> data1 = new HashMap<>();
        data1.put("Author", "John Smith");
        data1.put("Year", "2020");
        data1.put("Publisher", "Springer");
        references.add(new Reference("Book", "BK00", "important", data1));

        HashMap<String, String> data2 = new HashMap<>();
        data2.put("Author", "Alice Doe");
        data2.put("Year", "2019");
        data2.put("Journal", "Science Today");
        references.add(new Reference("Article", "AR00", "important", data2));

        HashMap<String, String> data3 = new HashMap<>();
        data3.put("Author", "John Smith");
        data3.put("Year", "2021");
        data3.put("Publisher", "O'Reilly");
        references.add(new Reference("Book", "BK01", "unfinished", data3));

        HashMap<String, String> data4 = new HashMap<>();
        data4.put("Author", "Bob Brown");
        data4.put("Year", "2018");
        data4.put("Conference", "ICSE");
        references.add(new Reference("Inproceedings", "IP00", "unfinished", data4));

        HashMap<String, String> data5 = new HashMap<>();
        data5.put("Author", "Carol White");
        data5.put("Year", "2022");
        data5.put("Publisher", "Pearson");
        references.add(new Reference("Book", "BK02", "favorite", data5));

        HashMap<String, String> data6 = new HashMap<>();
        data6.put("Author", "David Green");
        data6.put("Year", "2021");
        data6.put("Journal", "Nature");
        references.add(new Reference("Article", "AR01", "important", data6));

        HashMap<String, String> data7 = new HashMap<>();
        data7.put("Author", "Bob Brown");
        data7.put("Year", "2020");
        data7.put("Conference", "SIGMOD");
        references.add(new Reference("Inproceedings", "IP01", "", data7));

        HashMap<String, String> data8 = new HashMap<>();
        data8.put("Author", "Frank Yellow");
        data8.put("Year", "2023");
        data8.put("Publisher", "MyPublisher");
        references.add(new Reference("My Own Type", "MOT00", "own", data8));
    }

    // Tagillä haku testi
    @Given("multiple references exists with various tags")
    public void multiple_references_exists_with_various_tags() {
        Set<String> tags = new HashSet<>();
        for (Reference ref: references.getAll()) {
            tags.add(ref.getTag());
        }
        if (tags.size() < 2) {
            throw new AssertionError("Expected multiple tags");
        }
    }

    @When("I search for references with tag {string}")
    public void search_references_by_tag(String tag) {
        // kaapataan konsolin tulostus
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        references.printReferences(tag, "Tag");

        output = out.toString();
    }

    @Then("I should only see references tagged with {string}")
    public void references_tagged_with(String tag) {
        for (Reference ref: references.getAll()) {
            if (ref.getTag().equalsIgnoreCase(tag)) {
                Assertions.assertTrue(output.contains(ref.getKey()));
            }
        }
    }

    @Then("I should not see references with other tags")
    public void references_tagged_with_other_tags() {
        for (Reference ref: references.getAll()) {
            if (!ref.getTag().equalsIgnoreCase("important")) {
                Assertions.assertTrue(!output.contains(ref.getKey()),
                        "Unexpected reference shown: " + ref);
            }
        }
    }


    // Tyypillä tehdyt haut testi
    @Given("multiple references exits with various types")
    public void multiple_references_exits_with_various_types() {
        Set<String> types = new HashSet<>();
        for (Reference ref: references.getAll()) {
            types.add(ref.getType());
        }
        if (types.size() < 2) {
            throw new AssertionError("Expected multiple types");
        }
    }

    @When("I search for references with type {string}")
    public void search_references_by_type(String type) {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        references.printReferences(type, "Type");

        output = out.toString();
    }

    @Then("I should only see references with type {string}")
    public void references_with_type(String type) {
        for (Reference ref: references.getAll()) {
            if (ref.getType().equalsIgnoreCase(type)) {
                Assertions.assertTrue(output.contains("type: " + type));
            }
        }
    }

    @Then("I should not see references with other types")
    public void references_with_other_types() {
        for (Reference ref: references.getAll()) {
            if (!ref.getType().equalsIgnoreCase("Book")) {
                Assertions.assertTrue(!output.contains("type: " + ref.getType()));
            }
        }
    }


    // Datalla haun testaus
    @Given("multiple references with different data exists")
    public void multiple_references_with_different_data_exists() {
        // kokeillaan vaan että dataa jokaisessa
        boolean result = true;
        for (Reference ref: references.getAll()) {
            if (ref.getData().isEmpty()) {
                result = false;
                break;
            }
        }

        if (!result) {
            throw new AssertionError("Expected multiple references with different data");
        }
    }

    @When("I search for references that has author {string}")
    public void search_references_by_data(String data) {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        references.printReferences(data, "Data");

        output = out.toString();
    }

    @Then("I should only see references with author {string}")
    public void references_with_data(String data) {
        for (Reference ref: references.getAll()) {
            if (ref.getField("Author").equalsIgnoreCase(data)) {
                Assertions.assertTrue(output.contains("Author: " + data));
            }
        }
    }

    @Then("I should not see references with other authors")
    public void references_with_other_data() {
        for (Reference ref: references.getAll()) {
            if (!ref.getField("Author").equalsIgnoreCase("John Smith")) {
                Assertions.assertTrue(!output.contains("Author: " + ref.getField("Author")));
            }
        }
    }
}
