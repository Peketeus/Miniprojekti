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
        references.add(new Reference("Book", "BK00", "important", new HashMap<>()));
        references.add(new Reference("Article", "AR00", "important",  new HashMap<>()));
        references.add(new Reference("Book", "BK01", "unfinished", new HashMap<>()));
        references.add(new Reference("Inproceedings", "IP00", "unfinished", new HashMap<>()));
        references.add(new Reference("Book", "BK02", "favorite", new HashMap<>()));
        references.add(new Reference("Article", "AR01", "important", new HashMap<>()));
        references.add(new Reference("Inproceedings", "IP01", "", new HashMap<>()));
        references.add(new Reference("My Own Type", "MOT00", "own", new HashMap<>()));
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
                Assertions.assertTrue(output.contains("Type: " + type));
            }
        }
    }

    @Then("I should not see references with other types")
    public void references_with_other_types() {
        for (Reference ref: references.getAll()) {
            if (!ref.getType().equalsIgnoreCase("Book")) {
                Assertions.assertTrue(!output.contains("Type: " + ref.getType()));
            }
        }
    }
}
