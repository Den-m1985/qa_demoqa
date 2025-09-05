package data.models;

import lombok.Data;

@Data
public class Token {

    private String token,
            expires,
            status,
            result;

}
