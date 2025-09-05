package data.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserDto(
        @JsonProperty("userID")
        String userId,
        String username,
        List<BookDto> books
) {
}
