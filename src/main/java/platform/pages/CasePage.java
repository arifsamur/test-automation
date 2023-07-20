package platform.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

public class CasePage {
    private final Page page;
    private Locator patientName;

    public CasePage(Page page) {
        this.page = page;
        page.waitForLoadState(LoadState.LOAD);
        patientName = page.getByTestId("patient-name-text-wrap");
        patientName.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    public boolean isOpened(){
        return patientName.isVisible();
    }
}
