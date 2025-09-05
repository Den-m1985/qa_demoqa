package data.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookDto(
        String isbn,
        String title,
        String subTitle,
        String author,
        String publisher,
        String description,
        String website,

        @JsonProperty("publish_date")
        String publishDate,

        Integer pages
) {

}
