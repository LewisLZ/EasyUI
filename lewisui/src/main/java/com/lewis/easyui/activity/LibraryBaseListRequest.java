package com.lewis.easyui.activity;

import java.util.List;

/**
 *
 */
public interface LibraryBaseListRequest<DataSource> {
    void requestCallBack(List<DataSource> list, int page);
}
