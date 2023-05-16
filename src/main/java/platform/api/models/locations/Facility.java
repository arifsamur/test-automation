package platform.api.models.locations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Facility {
    private String id;
    private String customer_id;
    private String division_id;
    private String name;
    private String address;
    private String address2;
    private String city;
    private String state_code;
    private String postal_code;
    private String timezone;
    private Boolean enabled;
    private String created;
    private String updated;
    private String deleted;
    private String abbreviation;
    private String country;
    private String cxi_location_id;
}
