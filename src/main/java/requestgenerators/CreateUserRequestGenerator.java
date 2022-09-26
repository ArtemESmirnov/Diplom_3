package requestgenerators;

import io.restassured.specification.RequestSpecification;
import pojos.WholeUserBody;

import static io.restassured.RestAssured.given;

public class CreateUserRequestGenerator extends Request{
    public CreateUserRequestGenerator(RequestSpecification requestSpecification) {
        super(requestSpecification);
    }

    public static void createUserRequest(WholeUserBody createUserBody, String apiPath){
        given()
                .spec(setRequestSpecification())
                .body(createUserBody)
                .when()
                .post(apiPath);
    }
}
