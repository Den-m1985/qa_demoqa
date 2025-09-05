package data.models;

public record TokenDto(
        String token,
        String expires,
        String status,
        String result
) {
}
