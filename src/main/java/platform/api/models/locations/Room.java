package platform.api.models.locations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room {
    private String id;
    private String facility_id;
    private String name;
    private String room_number;
    private String created;
    private String updated;
    private String deleted;
    private String abbreviation;
    private String cxi_location_id;
}
