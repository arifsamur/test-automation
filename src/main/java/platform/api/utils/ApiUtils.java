package platform.api.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.specification.RequestSpecification;

public class ApiUtils {

public static RequestSpecification getRequestSpec(String cookie){
        return new RequestSpecBuilder()
                .setContentType("application/hal+json")
//                .setBaseUri(getHost())
//                .setBasePath(BASE_PATH)
                .addHeader("Cookie", cookie)
                .addFilter(new ErrorLoggingFilter())
                .build();
    }

}
