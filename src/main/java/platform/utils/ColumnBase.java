package platform.utils;

import com.microsoft.playwright.Page;

public abstract class ColumnBase {
    protected Page page;

    public abstract void setPage(Page page);
}
