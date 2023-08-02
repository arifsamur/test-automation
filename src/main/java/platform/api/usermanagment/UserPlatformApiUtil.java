package platform.api.usermanagment;

import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import platform.api.models.locations.LocationResponse;
import platform.api.models.user.UserProfileModel;
import platform.api.models.user.UserUpdate;

public class UserPlatformApiUtil {
    private static final String KEYCLOAK_BASE_URL = "https://identity.dev.happysurgeon.com";
    private static final String CUSTOMER_BASE_URL = "https://customer-management-api.dev.happysurgeon.com/api/v1/";
    private static final String USER_BASE_URL = "https://user-management-api.dev.happysurgeon.com/api/v1/";
    private static final String REALM = "Platform";
    private static final String USERS = "users/";
    private static final String USER = "user";
    private static final String LOCATIONS= "locations";
    private static final String CLIENT_ID = "cx-platform-user-management";
    private static final String CLIENT_SECRET = "e6f7f525-b782-4f03-922d-2ee2116afe89";

    public static String getAccessToken() {
        Response tokenResponse = RestAssured.given()
                .urlEncodingEnabled(true)
                .param("grant_type", "password")
                .param("client_id", CLIENT_ID)
                .param("client_secret", CLIENT_SECRET)
                .param("username", "sergey.huba@caresyntax.com")
                .param("password", "Bazinga10!")
                .when()
                .post(KEYCLOAK_BASE_URL + "/realms/" + REALM + "/protocol/openid-connect/token");

        return tokenResponse.jsonPath().getString("access_token");
    }

    public static String login(String user, String password) {
        Response tokenResponse = RestAssured.given()
                .urlEncodingEnabled(true)
                .param("grant_type", "password")
                .param("client_id", CLIENT_ID)
                .param("client_secret", CLIENT_SECRET)
                .param("username", user)
                .param("password", password)
                .when()
                .post(KEYCLOAK_BASE_URL + "/realms/" + REALM + "/protocol/openid-connect/token");

        return tokenResponse.jsonPath().getString("access_token");
    }

    public static RequestSpecification givenAuth(String accessToken) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json")
                .filter(new ErrorLoggingFilter());
    }

    public static void updateUserLocation(String accessToken,UserUpdate user){
        givenAuth(accessToken)
                .body(user)
                .when().log().all()
                .put(USER_BASE_URL +USERS+user.getId())
                .then()
                .statusCode(200);
    }

    public static LocationResponse getLocations(String accessToken){
        return givenAuth(accessToken)
                .when()
                .get(CUSTOMER_BASE_URL + LOCATIONS)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(LocationResponse.class);
    }
    //TODO get locations API

    public static boolean updateUserProfile(String accessToken, UserProfileModel userProfile){
        return givenAuth(accessToken)
                .body(userProfile).log().all()
                .when()
                .post(USER_BASE_URL + USER)
                .then().log().all()
                .statusCode(200)
                .extract().body()
                .jsonPath().getBoolean("success");
    }
}
