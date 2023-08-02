package platform.api.usermanagment;

import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import platform.api.models.user.Group;
import platform.api.models.user.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserKeycloakApiUtil {
    private static final String SERVER_URL = "https://identity.dev.happysurgeon.com";
    private static final String REALM = "Platform";
    private static final String CLIENT_ID = "cx-platform-user-management";
    private static final String CLIENT_SECRET = "e6f7f525-b782-4f03-922d-2ee2116afe89";

    public static String getAccessToken() {
//        RequestSpecification requestSpecification = ApiUtils.getRequestSpec();
        Response tokenResponse = RestAssured.given().log().all()
                .urlEncodingEnabled(true)
                .param("grant_type", "client_credentials")
                .param("client_id", CLIENT_ID)
                .param("client_secret", CLIENT_SECRET)
                .when()
                .post(SERVER_URL + "/realms/" + REALM + "/protocol/openid-connect/token");

        tokenResponse.then().log().all();

        return tokenResponse.jsonPath().getString("access_token");
    }

    public static RequestSpecification givenAuth() {
        return RestAssured.given()
                .header("Authorization", "Bearer " + getAccessToken())
                .contentType("application/json")
                .filter(new ErrorLoggingFilter());
    }

    public static List<User> getAllUsers() {
        Response usersResponse = givenAuth().when()
                .get(SERVER_URL + "/admin/realms/" + REALM + "/users");
        return usersResponse.jsonPath().getList(".", User.class);
    }

    public static List<User> searchForUsers(String searchQuery) {
        return Arrays.asList(givenAuth()
                .queryParam("briefRepresentation","true")
                .queryParam("first","0")
                .queryParam("max","11")
                .queryParam("search", searchQuery).log().all()
                .get(SERVER_URL + "/admin/realms/" + REALM + "/admin-ui-brute-force-user")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(User[].class));
    }

    public static User getUser(String userId){
        return givenAuth()
                .when()
                .get(SERVER_URL + "/admin/realms/" + REALM + "/users/" + userId)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(User.class);
    }

    public static String createUser(User user) {
        Response createUserResponse = givenAuth()
                .body(user)
                .when()
                .post(SERVER_URL + "/admin/realms/" + REALM + "/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        String locationHeader = createUserResponse.getHeader("Location");
        String userId = locationHeader.substring(locationHeader.lastIndexOf("/") + 1);

        return userId;
    }

    public static void setPassword(String userId, String newPassword) {
        // Prepare the password credentials
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", newPassword);
        credentials.put("temporary", false);

        // Set the password for the user
        Response response = givenAuth()
                .body(credentials).log().all()
                .put(SERVER_URL + "/admin/realms/" + REALM + "/users/" + userId + "/reset-password")
                .then()
                .statusCode(204)
                .extract()
                .response();
    }

    public static void deleteUser(String userId) {
        givenAuth()
                .delete(SERVER_URL + "/admin/realms/" + REALM + "/users/" + userId)
                .then()
                .statusCode(204);
    }

    public static List<Group> getGroups(){
        return Arrays.asList(givenAuth()
                .get(SERVER_URL + "/admin/realms/" + REALM + "/groups")
                .then()
                .statusCode(200)
                .extract()
                .body().as(Group[].class));
    }

    public static List<Group> getDefaultSurgeonGroup(){
        List<Group> groups = getGroups().stream().filter(group -> group.getName().equals("Surgeon")).collect(Collectors.toList());
        return groups;
    }
}
