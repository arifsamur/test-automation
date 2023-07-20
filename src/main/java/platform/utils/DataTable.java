package platform.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataTable<TableData, TableColumns extends ColumnBase> {
    protected final Page page;
    protected List<Locator> tableRows;
    protected Locator table;
    protected List<Locator> tableHeaders;
    protected TableColumns columns;

//    protected String tableHeadersSelector = "table th";
    protected String tableRowsSelector = "tbody > tr";
    protected String tableCellSelector = "td";


    public DataTable(Page page, TableColumns columns) {
        this.page = page;
        this.columns=columns;
        page.waitForLoadState(LoadState.NETWORKIDLE);

        table = page.getByRole(AriaRole.TABLE);
        table.scrollIntoViewIfNeeded();
        tableHeaders = table.getByRole(AriaRole.COLUMNHEADER).all();
        tableRows = table.locator(tableRowsSelector).all();
    }

    @Step("Get list of the table headers")
    public List<Locator> getTableHeaders() {
        return tableHeaders;
    }

    @Step("Get table data as objects")
    public List<TableData> getTableData(Class<TableData> tableDataClass) {
        List<TableData> tableDataList = new ArrayList<>();
        List<String> tableHeadersText = getTableHeaders().stream().map(Locator::innerText).collect(Collectors.toList());
        getTableRows().forEach(row -> {
            List<String> rowData = row.stream().map(Locator::innerText).collect(Collectors.toList());
            Map<String, String> rowDataMap = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();
            for (int i = 0; i < rowData.size(); i++) {
                rowDataMap.put(tableHeadersText.get(i), rowData.get(i));
            }
            TableData data = mapper.convertValue(rowDataMap, tableDataClass);
            tableDataList.add(data);
        });
        return tableDataList;
    }

    @Step("Get List of Rows Locators")
    public List<List<Locator>> getTableRows(){
        return tableRows.stream().map(locator -> locator.locator(tableCellSelector).all()).collect(Collectors.toList());
    }

    @Step("Get table Columns Locators")
    public TableColumns getTableColumn(){
        columns.setPage(page);
        return this.columns;

    }

}
