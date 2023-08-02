package platform.api.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import platform.api.models.locations.Location;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean enabled = true;
    private boolean emailVerified = true;
    private Attribute attributes;
    private List<String> groups;
    private List<Location> locations;
}