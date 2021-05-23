import com.github.javafaker.Faker;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public final class Utils {

    private Utils(){ }

    /**
     * Class created to generate random transactions to be used on random post creation test
     * @param email
     * @return
     */
    public static CreateTransactionRequest createRandomCustomer(String email){

        Faker faker = Faker.instance();

        CreateTransactionRequest customerInformationPojo = new CreateTransactionRequest();
        customerInformationPojo.setId(""+faker.number().randomNumber());
        customerInformationPojo.setName(faker.name().firstName());
        customerInformationPojo.setLastName(faker.name().lastName());
        customerInformationPojo.setAccountNumber(getRandomNumber());
        customerInformationPojo.setAmount(""+faker.number().randomDouble(2,10, 99));
        customerInformationPojo.setActive(true);
        customerInformationPojo.setTransactionType(faker.business().creditCardType());
        customerInformationPojo.setEmail(email);
        customerInformationPojo.setCountry(faker.address().country());
        customerInformationPojo.setTelephone(""+faker.phoneNumber().cellPhone());
        return customerInformationPojo;
    }

    public static String getRandomNumber(){
        Random random = new Random();
        return String.valueOf(random.nextInt(1000));
    }

    public static Set<String> getListUniqueEmail(int total){
        Faker faker = Faker.instance();
        Set<String> emails = new HashSet<>();
        while(emails.size()< total){
            emails.add(faker.internet().emailAddress());
        }
        return emails;
    }

}
