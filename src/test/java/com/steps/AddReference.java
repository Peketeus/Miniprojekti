package com.steps;

import com.bib.References;
import com.bib.Reference;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.jupiter.api.Assertions;

import java.util.HashMap;

public class AddReference {

    private References references;

    @Given("the reference list is empty")
    public void the_reference_list_is_empty() {
        references = new References();
    }

    @When("I add a new reference with type {string} and key {string} and title {string}")
    public void i_add_a_new_reference_with_valid_data(String type, String key, String title) {
        HashMap<String, String> data = new HashMap<>();
        data.put("title", title);

        Reference ref = new Reference(type, key, "", data);
        references.add(ref);
    }

    @Then("the reference list should contain reference with key {string}")
    public void the_reference_list_should_contain_it(String key) {
        Reference result = references.findReferenceByKey(key);
        Assertions.assertNotNull(result, "Reference with key " + key + " not found");
        Assertions.assertEquals(result.getKey(), key, "Keys are not equal");
    }
}
