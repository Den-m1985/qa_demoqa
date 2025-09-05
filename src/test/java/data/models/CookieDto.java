package data.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CookieDto(
        String userId,
        String username,
        String password,
        String token,
        String expires,

        @JsonProperty("created_date")
        String createdDate,

        @JsonProperty("isActive")
        boolean isActive
) {
}
