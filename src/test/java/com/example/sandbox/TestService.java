package com.example.sandbox;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;

import java.util.*;

public class TestService extends Common {

    public Response getPetsByID(long id){
        return getUrl(petById.replace("{petId}", String.valueOf(id)));
    }
    public Response getPetsByID(String id){
        return getUrl(petById.replace("{petId}", id));
    }

    public Response getPetsByStatus(List<String> statuses){
        Map<String, String> queryParams = new TreeMap<>();
        StringBuilder queryString = new StringBuilder();
        statuses.forEach(status -> queryString.append(",").append(status));
        queryParams.put("status", queryString.substring(1));
        return getUrl(findByStatus, queryParams);
    }

    private List<Long> getAllPetIds() {
        return JsonPath.parse(this.getPetsByStatus(Arrays.asList("sold","pending","available")).getBody().asString()).read("$[*]['id']");
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
            id = (long) random.nextInt(0,Integer.MAX_VALUE);
        } while (petIDs.contains(id));
        return id;
    }
}
