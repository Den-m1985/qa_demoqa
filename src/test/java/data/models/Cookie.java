package data.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Cookie {

    private String userId,
            username,
            password,
            token,
            expires;

    @JsonProperty("created_date")
    private String createdDate;

    @JsonProperty("isActive")
    private boolean isActive;
}
