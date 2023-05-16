package platform.api.models.keycloack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import platform.api.models.locations.Location;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdate {
    private String id;
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> groupId;
    @JsonProperty("isActive")
    private boolean isActive;
    private List<Location> locations;
}
