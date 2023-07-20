package platform.kpi;

import app.getxray.xray.testng.annotations.XrayTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import platform.InitTest;
import platform.pages.LoginPage;

import java.util.List;
import java.util.stream.Collectors;

public class KpiTest extends InitTest {

    private String userName ="mohammed.gesreha@caresyntax.com";
    private String password ="Gesreha#7107";

    @Test(description = "Test Kpi Clinical Alphabetical Sorting")
    @XrayTest(key = "PLATFORM-788")
    public void testClinicalKpiAlphabeticalSorting(){
        List<String> kpiHeadersList = new LoginPage(page)
                .navigate()
                .login(userName, password)
                .waitKPILoading()
                .openKpiFilter()
                .clearAllClinicalKPIs()
                .selectAllClinicalKPIs()
                .closeKpiFilter()
                .waitKPILoading()
                .getKpiWidgets()
                .assertClinicalKPIWidgetsAreVisible()
                .getClinicalKPIsHeadersList();

        System.out.println(kpiHeadersList);
        List<String> sortedKpiHeadersList= kpiHeadersList.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(kpiHeadersList,sortedKpiHeadersList, "Kpis aren't sorted alphabetically as expected");
    }

    @Test(description = "Test Kpi Clinical Alphabetical Sorting")
    @XrayTest(key = "PLATFORM-789")
    public void testOperationalKpiAlphabeticalSorting(){
        List<String> kpiHeadersList = new LoginPage(page)
                .navigate()
                .login(userName, password)
                .waitKPILoading()
                .openKpiFilter()
                .clearAllOperationalKPIs()
                .selectAllOperationalKPIs()
                .closeKpiFilter()
                .waitKPILoading()
                .getKpiWidgets()
                .assertOperationalKPIWidgetsAreVisible()
                .getOperationalKPIsHeadersList();

        System.out.println(kpiHeadersList);
        List<String> sortedKpiHeadersList= kpiHeadersList.stream().sorted().collect(Collectors.toList());
        Assert.assertEquals(kpiHeadersList,sortedKpiHeadersList, "Kpis aren't sorted alphabetically as expected");
    }
}
