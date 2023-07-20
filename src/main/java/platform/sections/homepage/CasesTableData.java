package platform.sections.homepage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CasesTableData {
    @JsonProperty("Patient Name")
    private String patientName;
    @JsonProperty("Date of Case")
    private String dateOfCase;
    @JsonProperty("Patient ID")
    private String patientID;
    @JsonProperty("Date of Birth")
    private String dateOfBirth;
    @JsonProperty("LoS")
    private String los;
    @JsonProperty("PJI")
    private String pji;
    @JsonProperty("PS")
    private String ps;
    @JsonProperty("PN")
    private String pn;
}
