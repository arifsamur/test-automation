package platform.api.models.locations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationResponse {
    private boolean success;
    private List<Location> locations;
}
