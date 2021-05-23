import com.github.javafaker.Faker;
import io.restassured.common.mapper.TypeRef;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class TransactionTests extends BaseTest {

    private final static Logger LOGGER = Logger.getLogger("TransactionTests");
    SoftAssert softAssertion = new SoftAssert();


    /**
     * Test to get transactions and if there is some data available use the delete endpoint to remove them.
     * @throws InterruptedException
     */
    @Test(priority = 0)
    public void getTransactionTest() throws InterruptedException {

        List<CreateTransactionResponse> responseList =
                given()
                        .when()
                        .get("transferMoney")
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .spec(BaseTest.defaultResponseSpecification()).extract().as(new TypeRef<List<CreateTransactionResponse>>() {});


        List<String> idList = responseList.stream().map(CreateTransactionResponse::getId).collect(Collectors.toList());

        if(!idList.isEmpty()){
            for (String index : idList) {
                given()
                        .when()
                        .delete("transferMoney/"+index)
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .spec(BaseTest.defaultResponseSpecification())
                        .extract()
                        .body()
                        .as(CreateTransactionResponse.class);
                Thread.currentThread().sleep(5000);
            }
        } else LOGGER.log(Level.INFO, "Endpoint is empty, no transactions registered yet");

    }

    /**
     * It is created a list of 20 different emails to create 20 transactions through a POST request
     * @throws InterruptedException
     */
    @Test(priority = 1)
    public void createTransactionTest() throws InterruptedException {


        Set<String> emails = Utils.getListUniqueEmail(20);

        CreateTransactionRequest transaction;
        for (String index : emails) {
            transaction = Utils.createRandomCustomer(index);
            CreateTransactionResponse transactionResponse =

                    given()
                            .when()
                            .body(transaction)
                            .post("transferMoney")
                            .then()
                            .statusCode(HttpStatus.SC_CREATED)
                            .spec(BaseTest.defaultResponseSpecification())
                            .extract()
                            .body()
                            .as(CreateTransactionResponse.class);

            softAssertion.assertEquals(transactionResponse.getId(), equalTo(transaction.getId()));
            softAssertion.assertEquals(transactionResponse.getName(), equalTo(transaction.getName()));
            softAssertion.assertEquals(transactionResponse.getTransactionType(), equalTo(transaction.getTransactionType()));
            Thread.currentThread().sleep(5000);
        }
    }

    /**
     * Negative test where it is performed a request to GET endpoint to retrieve the list of existing transactions.
     *The responseList is stored and mapped to get only email fields and store them in another email List.
     * It is done a code verification to make sure the fake email created to be used in the POST transaction creation
     * doesn't exist already in the email List.
     */
    @Test(priority = 2)
    public void negativeTest() {
        List<CreateTransactionResponse> responseList =
                 given()
                        .when()
                        .get("transferMoney")
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .spec(BaseTest.defaultResponseSpecification()).extract().as(new TypeRef<List<CreateTransactionResponse>>() {});


        List<String> emailList = responseList.stream().map(CreateTransactionResponse::getEmail).collect(Collectors.toList());

        Faker faker = Faker.instance();
        String emailFake = faker.internet().emailAddress();

        if (emailList.contains(emailFake)){
            LOGGER.log(Level.INFO, "The email that you are sending already exist");
        } else{
            CreateTransactionRequest transaction = Utils.createRandomCustomer(emailFake);
            System.out.println(responseList);

            CreateTransactionResponse transactionResponse =
                    given()
                            .when()
                            .body(transaction)
                            .post("transferMoney")
                            .then()
                            .statusCode(HttpStatus.SC_CREATED)
                            .spec(BaseTest.defaultResponseSpecification())
                            .extract()
                            .body()
                            .as(CreateTransactionResponse.class);

            softAssertion.assertEquals(transactionResponse.getId(), equalTo(transaction.getId()));
            softAssertion.assertEquals(transactionResponse.getName(), equalTo(transaction.getName()));
            softAssertion.assertEquals(transactionResponse.getTransactionType(), equalTo(transaction.getTransactionType()));
        }
    }

    /**
     * Test to update an existing transaction, the code verifies if there is at least one transaction to be updated
     * if so updates the first available transaction from response list obtained throug get request, if no, log that
     * there is no available transactions to update.
     */
    @Test(priority = 3)
    public void updateTransactionTest() {

        List<CreateTransactionResponse> responseList =
                given()
                        .when()
                        .get("transferMoney")
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .spec(BaseTest.defaultResponseSpecification()).extract().as(new TypeRef<List<CreateTransactionResponse>>() {});


        List<String> idList = responseList.stream().map(CreateTransactionResponse::getId).collect(Collectors.toList());

        if (!idList.isEmpty()){
            CreateTransactionResponse transactionResponse =
                    given()
                            .when()
                            .body(responseList.get(0))
                            .put("transferMoney/" + idList.get(0))
                            .then()
                            .statusCode(HttpStatus.SC_OK)
                            .spec(BaseTest.defaultResponseSpecification())
                            .extract()
                            .body()
                            .as(CreateTransactionResponse.class);
            softAssertion.assertEquals(transactionResponse.getId(), equalTo(responseList.get(0).getId()));
            softAssertion.assertEquals(transactionResponse.getName(), equalTo(responseList.get(0).getName()));
            softAssertion.assertEquals(transactionResponse.getTransactionType(), equalTo(responseList.get(0).getTransactionType()));
        } else LOGGER.log(Level.INFO, "No transactions available to update");
    }
}
