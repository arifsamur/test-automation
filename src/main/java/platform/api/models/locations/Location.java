package platform.api.models.locations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private Organization organization;
    private List<Division> divisions;
    private List<Facility> facilities;
    private List<Department> departments;
    private List<Room> rooms;
}
