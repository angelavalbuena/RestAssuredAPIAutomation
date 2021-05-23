import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseTest {

    private final static java.util.logging.Logger LOGGER = Logger.getLogger("TransactionTests");

    /**
     * Initial configuration to the request, it sets some configurations and preconditions for test
     * like base uri and base path, and the content type for the request.
     */
    @BeforeClass
    public static void setup(){
        LOGGER.log(Level.INFO, "Initializing the configuration");
        RestAssured.requestSpecification = defaultRequestSpecification();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        LOGGER.log(Level.INFO,"Configuration successful");
    }

    protected static  RequestSpecification defaultRequestSpecification(){
        List<Filter> filters = new ArrayList<>();
        filters.add(new RequestLoggingFilter());
        filters.add(new ResponseLoggingFilter());

        return new RequestSpecBuilder().setBaseUri("https://6085cb8fd14a8700175784bd.mockapi.io")
                .setBasePath("/api/v1")
                .addFilters(filters)
                .setContentType(ContentType.JSON)
                .build();
    }

    protected static ResponseSpecification defaultResponseSpecification(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .build();
    }
}