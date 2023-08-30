package platform.sections.homepage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import platform.pages.HomePage;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class KpiFilter {
    private final Page page;
    private Locator selectAllClinicalKPIsButton;
    private Locator clearAllClinicalKPIsButton;
    private Locator selectAllOperationalKPIsButton;
    private Locator clearAllOperationalKPIsButton;
    private Locator kpiFilterButton;
    private List<Locator> clinicalKpiFilterCheckBoxList;
    private List<Locator> operationalKpiFilterCheckBoxList;

    public KpiFilter(Page page) {
        this.page = page;
        selectAllClinicalKPIsButton = page.getByText("Clinical KPIs").getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("select all"));
        clearAllClinicalKPIsButton = page.getByText("Clinical KPIs").getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("clear all"));
        selectAllOperationalKPIsButton = page.getByText("Operational KPIs").getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("select all"));
        clearAllOperationalKPIsButton = page.getByText("Operational KPIs").getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("clear all"));
        clinicalKpiFilterCheckBoxList = page.locator("[id*=ClinicalKPIId]").all();
        operationalKpiFilterCheckBoxList = page.locator("[id*=OperationalKPIId]").all();
        kpiFilterButton = page.getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("KPIs"));
    }

    @Step("Select all Operational KPIs")
    public KpiFilter selectAllOperationalKPIs(){
        selectAllOperationalKPIsButton.click();
        operationalKpiFilterCheckBoxList.stream().forEach(locator -> assertThat(locator).isChecked());
        return this;
    }

    @Step("Clear all Operational KPIs")
    public KpiFilter clearAllOperationalKPIs(){
        clearAllOperationalKPIsButton.click();
        operationalKpiFilterCheckBoxList.stream().forEach(locator -> assertThat(locator).not().isChecked());
        return this;
    }

    @Step("Select all clinical KPIs")
    public KpiFilter selectAllClinicalKPIs(){
        selectAllClinicalKPIsButton.click();
        clinicalKpiFilterCheckBoxList.stream().forEach(locator -> assertThat(locator).isChecked());

        return this;
    }

    @Step("Clear all clinical KPIs")
    public KpiFilter clearAllClinicalKPIs(){
        clearAllClinicalKPIsButton.click();
        clinicalKpiFilterCheckBoxList.stream().forEach(locator -> assertThat(locator)
                .isChecked(new LocatorAssertions.IsCheckedOptions().setChecked(false)));
        return this;
    }

    @Step("Close KPI Filter")
    public HomePage closeKpiFilter(){
        kpiFilterButton.click();
        return new HomePage(page);
    }
}
