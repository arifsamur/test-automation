package platform.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import platform.sections.homepage.*;
import platform.utils.DataTable;

import java.util.List;

public class HomePage {
    private final Page page;
    private List<Locator> kpiLoading;
    private Locator kpiFilterButton;
    private Locator casesTableFilterButton;

    public HomePage(Page page) {
        this.page = page;
        page.waitForLoadState(LoadState.NETWORKIDLE);
        kpiLoading = page.getByTestId("spinner").all();
        kpiFilterButton = page.getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("KPIs"));
        casesTableFilterButton = page.getByTestId("toggle-filter-button");
    }

    public boolean isOpened(){
        return kpiFilterButton.isVisible();
    }

    @Step("Wait KPI Loading")
    public HomePage waitKPILoading() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
        kpiLoading.forEach(locator -> locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN).setTimeout(10000)));
        return this;
    }

    @Step("Open KPI Filter")
    public KpiFilter openKpiFilter() {
        kpiFilterButton.click();
        return new KpiFilter(page);
    }

    @Step("Access KPI Widgets")
    public KpiWidgets getKpiWidgets() {
        return new KpiWidgets(page);
    }

    @Step("Access Cases Table")
    public DataTable<CasesTableData, CasesTableColumns> getCasesTable() {
        return new DataTable<>(page, new CasesTableColumns());
    }

    @Step("Open Cases Page")
    public CasePage openCasePage(Locator caseLocator){
        caseLocator.click();
        return new CasePage(page);
    }

    @Step("Access pagination tab")
    public PaginationTab getPaginationObject() {
        return new PaginationTab(page);
    }

    @Step("Access table filter")
    public CasesTableFilter geCasesTableFilter(){
        return new CasesTableFilter(page);
    }

    @Step("Access user profile widget")
    public UserProfile getUserProfileWidget(){
        return new UserProfile(page);
    }
}
