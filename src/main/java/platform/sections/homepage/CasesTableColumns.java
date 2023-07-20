package platform.sections.homepage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import lombok.Data;
import platform.utils.ColumnBase;

import java.util.List;

@Data
public class CasesTableColumns extends ColumnBase {

    private List<Locator> patientName;
    private List<Locator> dateOfCase;
    private List<Locator> patientID;
    private List<Locator> dateOfBirth;
    private List<Locator> los;
    private List<Locator> pji;
    private List<Locator> ps;
    private List<Locator> pn;
    private Locator tableSpinner;

    @Override
    public void setPage(Page page) {
        this.page = page;
        tableSpinner = page.getByTestId("worklist-spinner");
        tableSpinner.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        patientName = page.getByTestId("patientNameId").all();
        dateOfCase = page.getByTestId("dateOfCaseId").all();
        patientID = page.getByTestId("patientIdDataTestId").all();
        dateOfBirth = page.getByTestId("birthDateId").all();
        los = page.getByTestId("lengthOfStayAtRisk").all();
        pji = page.getByTestId("pJointInfectionAtRisk").all();
        ps = page.getByTestId("patientSatisfactionAtRisk").all();
        pn = page.getByTestId("painAtRisk").all();
    }
}
