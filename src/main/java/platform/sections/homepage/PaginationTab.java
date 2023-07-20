package platform.sections.homepage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;

import java.util.List;

public class PaginationTab {
    private final Page page;
    private Locator paginationExpandButton;
    private Locator paginationOptionsList;
    private Locator previousPage;
    private Locator nextPage;
    private Locator paginationPages;
    private List<Locator> getPaginationPagesList;
    private Locator spinner;

    public PaginationTab(Page page) {
        this.page = page;
        spinner = page.getByTestId("worklist-spinner");
        spinner.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        paginationExpandButton = page.locator("[id*='headlessui-listbox-button']");
        paginationExpandButton.scrollIntoViewIfNeeded();
        paginationOptionsList = page.locator("[id*='headlessui-listbox-option-']");
        previousPage = page.getByTestId("previousPage");
        nextPage = page.getByTestId("nextPage");
    }

    public PaginationTab showAllCases(){
        expandPaginationList();
        selectShowListOptionByIndex(2);
        return this;
    }

    public PaginationTab expandPaginationList(){
        paginationExpandButton.click();
        return this;
    }

    public PaginationTab selectShowListOptionByIndex(int i){
        paginationOptionsList.nth(i).click();
        return this;
    }

    public PaginationTab navigateTablePageByPage(int pageNumber){
        paginationPages = page.locator(String.format("[data-testid^='page%d']",pageNumber));
        paginationPages.click();
        return this;
    }

    public List<Locator> getGetPaginationPagesList(){
        getPaginationPagesList = page.locator("[data-testid^='page']").all();
        return getPaginationPagesList;
    }

    public PaginationTab navigateToLastPage(){
        List<Locator> paginationPagesList = getGetPaginationPagesList();
        paginationPagesList.get(paginationPagesList.size()-1).click();
        return this;
    }

    public PaginationTab moveToNextPage(){
        assertButtonIsActive(nextPage);
        nextPage.click();
        spinner.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        return this;
    }

    public PaginationTab moveToPreviousPage(){
        assertButtonIsActive(previousPage);
        previousPage.click();
        spinner.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        return this;
    }

    public Locator getNextPageButton() {
        return nextPage;
    }

    public Locator getPreviousPageButton() {
        return previousPage;
    }

    public void assertButtonIsActive(Locator button){
        Assert.assertTrue(button.isEnabled(),"The button is disabled");
    }
}
