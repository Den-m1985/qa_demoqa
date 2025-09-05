package data.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Books {

    private String isbn,
            title,
            subTitle,
            author,
            publisher,
            description,
            website;

    @JsonProperty("publish_date")
    private String publishDate;

    private int pages;
}
