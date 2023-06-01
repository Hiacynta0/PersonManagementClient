package com.example.restrsiclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class PersonClient {
    private final WebClient webClient;
    Traverson traverson;

    public PersonClient(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
        try {
            this.traverson = new Traverson(new URI(baseUrl + "/persons"), MediaTypes.HAL_JSON);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> getAllPersons() {
        String res = webClient.get().uri("/persons").retrieve().bodyToMono(String.class).block();

        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsonNode;
        try {
            jsonNode = om.readTree(res);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String test = jsonNode.at("/_embedded/personList").toString();

        try {
            List<Person> list = om.readValue(test, List.class);
            return list;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public int getSize() {
        return webClient.get()
                .uri("/persons/size")
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }

    public Person getPerson(int id) {
        return webClient.get()
                .uri("/persons/{id}", id)
                .retrieve()
                .bodyToMono(Person.class)
                .block();
    }

    public boolean deletePerson(int id) {
        return webClient.delete()
                .uri("/persons/{id}", id)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    public Person addPerson(Person person) {
        return webClient.post()
                .uri("/persons")
                .bodyValue(person)
                .retrieve()
                .bodyToMono(Person.class)
                .block();
    }

    public Person updatePerson(Person person) {
        return webClient.put()
                .uri("/persons")
                .bodyValue(person)
                .retrieve()
                .bodyToMono(Person.class)
                .block();
    }
}
