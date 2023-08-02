package platform.api.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileModel {
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String newPassword = "";
    private String oldPassword = "";
    private String profileImage = "";
    private String title = "mr";
}
