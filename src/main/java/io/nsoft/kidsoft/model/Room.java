package io.nsoft.kidsoft.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Room {

    @JsonProperty("RoomID")
    public Integer roomID;

    @JsonProperty("Name")
    public String name;

    @JsonProperty("OpeningHours")
    public List<Object> openingHours = null;

    @JsonProperty("DateVacancies")
    public Map<String, Map> dateVacancies;

}