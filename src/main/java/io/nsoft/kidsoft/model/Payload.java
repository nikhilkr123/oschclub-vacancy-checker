package io.nsoft.kidsoft.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Payload {
    @JsonProperty("Rooms")
    public List<Room> rooms = null;

    @JsonProperty("RedirectURL")
    public String redirectUrl = null;

    private Map<String, Object> additionalProperties = new HashMap<>();
}