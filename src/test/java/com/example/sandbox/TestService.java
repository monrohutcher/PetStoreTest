package com.example.sandbox;

import com.example.sandbox.util.constans.Tags;
import com.example.sandbox.util.constans.TestData;
import com.example.sandbox.util.swagger.definitions.Item;
import com.example.sandbox.util.swagger.definitions.PetBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;

import java.util.*;

public class TestService extends Common {

    public Response getPetsByID(long id) {
        return getUrl(petById.replace("{petId}", String.valueOf(id)));
    }

    public Response getPetsByID(String id) {
        return getUrl(petById.replace("{petId}", id));
    }

    public Response getPetsByStatus(List<String> statuses) {
        Map<String, String> queryParams = new TreeMap<>();
        StringBuilder queryString = new StringBuilder();
        statuses.forEach(status -> queryString.append(",").append(status));
        queryParams.put("status", queryString.substring(1));
        return getUrl(findByStatus, queryParams);
    }

    public Response getPetsByStatus(String status) {
        Map<String, String> queryParams = new TreeMap<>();
        queryParams.put("status", status);
        return getUrl(findByStatus, queryParams);
    }

    public Response getPetsByStatus() {
        return getUrl(findByStatus);
    }

    private List<Long> getAllPetIds() {
        return JsonPath.parse(this.getPetsByStatus(Arrays.asList("sold", "pending", "available")).getBody().asString()).read("$[*]['id']");
    }

    public Long getValidPetId() {
        List<Long> petIDs = this.getAllPetIds();
        return petIDs.stream().findFirst().orElseThrow(NoSuchFieldError::new);
    }

    public Long getInvalidPetId() {
        Random random = new Random();
        Long id;
        List<Long> petIDs = this.getAllPetIds();
        do {
            id = (long) random.nextInt(0, Integer.MAX_VALUE);
        } while (petIDs.contains(id));
        return id;
    }

    public Integer getNonUsedPetId() {
        Random random = new Random();
        int id;
        List<Long> petIDs = this.getAllPetIds();
        do {
            id = random.nextInt(0, Integer.MAX_VALUE);
        } while (petIDs.contains(id));
        return id;
    }

    public PetBody buildPetBody(String name, Item category, List<String> photos, List<Item> tags, String status) {
            return PetBody.builder()
                    .id(this.getNonUsedPetId())
                    .name(name)
                    .category(category)
                    .photoUrls(photos)
                    .tags(tags)
                    .status(status)
                    .build();
    }

    public PetBody modifyPetBody(Integer id, String name, Item category, List<String> photos, List<Item> tags, String status) {
        return PetBody.builder()
                .id(id)
                .name(name)
                .category(category)
                .photoUrls(photos)
                .tags(tags)
                .status(status)
                .build();
    }

    public String getJsonStringFromObject(Object object) {
        String objectString;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectString = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return objectString;
    }

    public Response deletePetsByID(int id, String apiKey) {
        Map<String, String> headers = new TreeMap<>();
        headers.put("api_key", apiKey);
        return deleteUrl(petById.replace("{petId}", String.valueOf(id)), headers);
    }

    public Response deletePetsByID(int id) {
        return deleteUrl(petById.replace("{petId}", String.valueOf(id)));
    }
}