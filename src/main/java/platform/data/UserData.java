package platform.data;

import com.github.javafaker.Faker;
import lombok.Data;

import java.util.Random;
import java.util.stream.Collectors;

@Data
public class UserData {

    private String title;
    private String firstName;
    private String lastName;
    private String fullName;
    private String userName;
    private String password;
    private String initialPassword;
    private String email;
    private String id;
    private String role="USER";
    private boolean isReceiveMediaUpdates;
    private boolean isReceiveAdditionalUpdates;

    public static UserData getRandomUser(){
        Faker faker =new Faker();
        UserData randomUserData = new UserData();
        randomUserData.setFirstName("Aut_"+faker.name().firstName());
        randomUserData.setLastName("Aut_"+faker.name().lastName());
        randomUserData.setFullName(randomUserData.getFirstName()+" "+ randomUserData.getLastName());
        randomUserData.setUserName(randomUserData.getFirstName());
        randomUserData.setEmail(randomUserData.getFirstName()+"@m.mm");
        randomUserData.setPassword(getRandomPassword());
    return randomUserData;
    }

    public static String getRandomPassword(){
        return new Random().ints(13, 33, 122)
                .mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining()).concat("1@Qw");

    }
}
