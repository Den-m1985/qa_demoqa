package data.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.BODY;

public class ResponseSpecs {

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(BODY)
            .build();

}
