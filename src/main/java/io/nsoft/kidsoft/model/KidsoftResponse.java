package io.nsoft.kidsoft.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KidsoftResponse {

    @JsonProperty("Payload")
    public Payload payload;

    @JsonProperty("Status")
    public String status;

    @JsonProperty("RequestID")
    public String requestID;
}
