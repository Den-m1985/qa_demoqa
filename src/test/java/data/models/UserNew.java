package data.models;

import lombok.Data;

import java.util.List;

@Data
public class UserNew {

    private String userID,
            username;

    private List<Books> books;

}
