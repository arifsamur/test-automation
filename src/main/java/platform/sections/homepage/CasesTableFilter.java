package platform.sections.homepage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CasesTableFilter {
    private final Page page;
    private Locator casesTableFilterButton;
    private Locator periodButton;
    private Locator applyFilterButton;
    private Locator exactPeriodOption;
    private Locator startDate;
    private Locator endDate;
    private Locator clearAll;
    private String startDateSelector = "[name='date-start']";
    private String endDateSelector = "[name='date-end']";

    public CasesTableFilter(Page page) {
        this.page = page;
        casesTableFilterButton = page.getByTestId("toggle-filter-button");
        applyFilterButton = page.getByTestId("apply-filter-button");
//        applyFilterButton.scrollIntoViewIfNeeded();
        periodButton = page.getByTestId("periodSelect");
        exactPeriodOption = page.locator("[id*='headlessui-listbox-option-']").getByText("Exact");
        clearAll = page.getByText("Clear All");
        startDate = page.locator(startDateSelector);
        endDate = page.locator(endDateSelector);
    }

    public CasesTableFilter open(){
        casesTableFilterButton.click();
        return this;
    }

    public CasesTableFilter expandPeriodList(){
        periodButton.click();
        return this;
    }

    public CasesTableFilter selectExactPeriod(){
        exactPeriodOption.click();
        return this;
    }

    public CasesTableFilter setExactPeriod(String startDateValue, String endDateValue){
        startDate.focus();
        startDate.fill(startDateValue);
        endDate.focus();
        endDate.fill(endDateValue);
        return this;
    }

    public CasesTableFilter applySelectedFilters(){
        applyFilterButton.click();
        return this;
    }

    public CasesTableFilter clearAllFilters(){
        clearAll.focus();
        clearAll.click();
        return this;
    }

}
