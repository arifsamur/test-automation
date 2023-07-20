package platform.homepage;

import app.getxray.xray.testng.annotations.XrayTest;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import platform.InitTest;
import platform.pages.HomePage;
import platform.pages.LoginPage;
import platform.sections.homepage.CasesTableColumns;
import platform.sections.homepage.CasesTableData;
import platform.sections.homepage.PaginationTab;
import platform.utils.DataTable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CasesTableTest extends InitTest {

    private String userName ="mohammed.gesreha@caresyntax.com";
    private String password ="Gesreha#7107";

    //Sorting Testing
    @Test(description = "Test the cases table default sorting is by Date Of Case and descending")
    @XrayTest(key = "1125")
    public void testCasesTableDefaultSortingIsByDateOfCaseAndDescending(){

        List<Locator> dateOfCase = new LoginPage(page)
                .navigate()
                .login(userName, password)
                .getCasesTable()
                .getTableColumn()
                .getDateOfCase();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        List<LocalDateTime> dateOfCaseColumn = dateOfCase.stream().map(Locator::innerText).map(dateStr -> LocalDateTime.parse(dateStr, formatter)).collect(Collectors.toList());

        List<LocalDateTime> sortedDateOfCaseColumn = dateOfCaseColumn.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        Assert.assertEquals(dateOfCaseColumn,sortedDateOfCaseColumn,"Table isn't sorted descending as expected");
    }

    @Test(description = "Test the cases table ascending sorting by Date Of Case")
    @XrayTest(key = "1126")
    public void testCasesTableAscendingSortingByDateOfCase(){
        DataTable<CasesTableData, CasesTableColumns> casesTable = new LoginPage(page)
                .navigate()
                .login(userName, password)
                .getCasesTable();

        Locator dateOfCaseLocator = casesTable.getTableHeaders().stream().filter(locator -> "Date of Case".equals(locator.innerText()))
                .collect(Collectors.toList()).get(0);

        List<Locator> dateOfCase = casesTable.getTableColumn().getDateOfCase();

        dateOfCaseLocator.click();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        List<LocalDateTime> dateOfCaseColumn = dateOfCase.stream().map(Locator::innerText).map(dateStr -> LocalDateTime.parse(dateStr, formatter)).collect(Collectors.toList());
        List<LocalDateTime> sortedDateOfCaseColumn = dateOfCaseColumn.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(dateOfCaseColumn,sortedDateOfCaseColumn,"Table isn't sorted ascending as should be");
    }

    @Test(description = "Test the cases table ascending sorting by Date Of Birth")
    @XrayTest(key = "1127")
    public void testCasesTableAscendingSortingByDateOfBirth(){
        DataTable<CasesTableData, CasesTableColumns> casesTable = new LoginPage(page)
                .navigate()
                .login(userName, password)
                .getCasesTable();

        Locator dateOfBirthLocator = casesTable.getTableHeaders().stream().filter(locator -> "Date of Birth".equals(locator.innerText()))
                .collect(Collectors.toList()).get(0);

        List<Locator> dateOfBirth = casesTable.getTableColumn().getDateOfBirth();

        dateOfBirthLocator.click();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        List<LocalDateTime> dateOfBirthColumn = dateOfBirth.stream().map(Locator::innerText).map(dateStr -> LocalDateTime.parse(dateStr, formatter)).collect(Collectors.toList());

        System.out.println(dateOfBirthColumn);
        List<LocalDateTime> sortedDateOfBirtheColumn = dateOfBirthColumn.stream().sorted().collect(Collectors.toList());
        System.out.println(sortedDateOfBirtheColumn);
        Assert.assertEquals(dateOfBirthColumn, sortedDateOfBirtheColumn,"Table isn't sorted ascending as expected");
    }

    @Test(description = "Test the cases table descending sorting by Date Of Birth")
    @XrayTest(key = "1128")
    public void testCasesTableDescendingSortingByDateOfBirth(){
        DataTable<CasesTableData, CasesTableColumns> casesTable = new LoginPage(page)
                .navigate()
                .login(userName, password)
                .getCasesTable();

        Locator dateOfBirthLocator = casesTable.getTableHeaders().stream().filter(locator -> "Date of Birth".equals(locator.innerText()))
                .collect(Collectors.toList()).get(0);

        List<Locator> dateOfBirth = casesTable.getTableColumn().getDateOfBirth();

        dateOfBirthLocator.click();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        List<LocalDateTime> dateOfBirthColumn = dateOfBirth.stream().map(Locator::innerText).map(dateStr -> LocalDateTime.parse(dateStr, formatter)).collect(Collectors.toList());

        System.out.println(dateOfBirthColumn);
        List<LocalDateTime> sortedDateOfBirtheColumn = dateOfBirthColumn.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        System.out.println(sortedDateOfBirtheColumn);
        Assert.assertEquals(dateOfBirthColumn, sortedDateOfBirtheColumn,"Table isn't sorted descending as expected");
    }

    //Pagination Testing
    @Test(description = "Test the cases table preview the first page as a default page")
    @XrayTest(key = "1129")
    public void testCasesTableDefaultPage(){
        HomePage homePage = new LoginPage(page)
                .navigate()
                .login(userName, password);

        DataTable<CasesTableData, CasesTableColumns> casesTable = homePage.getCasesTable();
        List<Locator> paginationPagesList = homePage.getPaginationObject().getGetPaginationPagesList();
        Locator page1Button = paginationPagesList.get(0);
        String page1ButtonClassAttribute = page1Button.getAttribute("Class");
        Assert.assertTrue(page1ButtonClassAttribute.contains("box-border"),"Page 1 is not the default previewed page");

        List<String> nonDefaultPagesClassAttributes = paginationPagesList.stream().skip(1).map(locator -> locator.getAttribute("Class")).collect(Collectors.toList());
        Assert.assertTrue(nonDefaultPagesClassAttributes.stream().noneMatch(s->s.contains("box-border")),"Some of the cases table page include box-border class attribute");
    }


    @Test(description = "Test table pages content is matching the all cases preview")
    @XrayTest(key = "1130")
    public void testCasesTablePaginationContent(){
        HomePage homePage = new LoginPage(page)
                .navigate()
                .login(userName, password);

        DataTable<CasesTableData, CasesTableColumns> casesTable = homePage.getCasesTable();
        List<String> dateOfCase = new ArrayList<>();
        PaginationTab paginationObject = homePage.getPaginationObject();

        paginationObject.getGetPaginationPagesList().forEach(page->{
            page.click();
            dateOfCase.addAll(casesTable.getTableColumn().getDateOfCase().stream().map(Locator::innerText).collect(Collectors.toList()));});
        paginationObject.showAllCases();
        List<String> dateOfCaseAggregated=casesTable.getTableColumn().getDateOfCase().stream().map(Locator::innerText).collect(Collectors.toList());
        Assert.assertEquals(dateOfCase,dateOfCaseAggregated,"Cases tables pages content doesn't match All cases preview");
    }

    @Test(description = "Test the table page has number of cases match the selected pagination Option")
    @XrayTest(key = "1131")
    public void testCasesTableNoOfCasesPerPage(){
        HomePage homePage = new LoginPage(page)
                .navigate()
                .login(userName, password);

        DataTable<CasesTableData, CasesTableColumns> casesTable = homePage.getCasesTable();
        homePage.getPaginationObject()
                .expandPaginationList()
                .selectShowListOptionByIndex(0);

        Assert.assertEquals(casesTable.getTableColumn().getDateOfCase().size(),20,"Number Of Cases in the page is not as selected");
    }

    @Test(description = "Test the cases table's last page is not empty")
    @XrayTest(key = "1132")
    public void testLastPageOfCasesTableNotEmpty(){
        HomePage homePage = new LoginPage(page)
                .navigate()
                .login(userName, password);

        DataTable<CasesTableData, CasesTableColumns> casesTable = homePage.getCasesTable();
        homePage.getPaginationObject().navigateToLastPage();

        Assert.assertFalse(casesTable.getTableColumn().getDateOfCase().isEmpty(),"The last page in the table has no cases");
    }

    @Test(description = "Test navigation buttons functionality")
    @XrayTest(key = "1135")
    public void testNextAndPreviousPageButtonFunctionality(){
        HomePage homePage = new LoginPage(page)
                .navigate()
                .login(userName, password);

        DataTable<CasesTableData, CasesTableColumns> casesTable = homePage.getCasesTable();
        PaginationTab paginationObject = homePage.getPaginationObject();
        List<Locator> paginationPagesList = paginationObject.moveToNextPage().getGetPaginationPagesList();
        Locator page2 = paginationPagesList.get(1);

        Assert.assertTrue(page2.getAttribute("class").contains("box-border"),"next page button didn't navigate to the second page");
    }

    @Test(description = "Navigation to Cases Page")
    @XrayTest(key = "1136")
    public void testNavigationToCasePage(){
        HomePage homePage = new LoginPage(page)
                .navigate()
                .login(userName, password);

        Locator firstCaseLocator = homePage.getCasesTable().getTableColumn().getDateOfCase().get(0);
        Assert.assertTrue(homePage.openCasePage(firstCaseLocator)
                .isOpened(),"The Case page didn't open");
    }

    @Test(description = "Test table filter with exact date of case period")
    @XrayTest(key = "1137")
    public void testFilterWithExactPeriod(){
        HomePage homePage = new LoginPage(page)
                .navigate()
                .login(userName, password);

        homePage.geCasesTableFilter()
                .open()
                .expandPeriodList()
                .selectExactPeriod()
                .setExactPeriod("2021-01-01","2021-12-31")
                .applySelectedFilters();

        homePage.getPaginationObject().showAllCases();

        List<String> dateOfCaseValuesList = homePage.getCasesTable().getTableColumn().getDateOfCase().stream().map(Locator::innerText).collect(Collectors.toList());
        Assert.assertTrue(dateOfCaseValuesList.stream().allMatch(d->d.contains("2021")),"The selected filter is not applied correctly");
    }

    @Test(description = "Test the filter clear all button")
    @XrayTest(key = "1138")
    public void testFilterClearAll(){
        HomePage homePage = new LoginPage(page)
                .navigate()
                .login(userName, password);

        DataTable<CasesTableData, CasesTableColumns> casesTable = homePage.getCasesTable();
        List<String> dateOfCase = new ArrayList<>();
        PaginationTab paginationObject = homePage.getPaginationObject();

        paginationObject.showAllCases();
        int tableSizeBeforeFilter = casesTable.getTableColumn().getDateOfCase().size();

        homePage.geCasesTableFilter()
                .open()
                .expandPeriodList()
                .selectExactPeriod()
                .setExactPeriod("2021-01-01","2021-12-31")
                .applySelectedFilters();

        List<String> dateOfCaseValuesList = homePage.getCasesTable().getTableColumn().getDateOfCase().stream().map(Locator::innerText).collect(Collectors.toList());
        Assert.assertTrue(dateOfCaseValuesList.stream().allMatch(d->d.contains("2021")),"The selected filter is not applied correctly");

        homePage.geCasesTableFilter().clearAllFilters();

        int tableSizeAfterFilter = casesTable.getTableColumn().getDateOfCase().size();

        Assert.assertEquals(tableSizeAfterFilter,tableSizeBeforeFilter,"The filter is not cleared");
    }

    //TODO
    @Ignore
    @Test(description = "Test empty state")
    @XrayTest(key = "")
    public void testEmptyTableState(){}
}
