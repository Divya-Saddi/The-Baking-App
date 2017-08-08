package com.burntcar.android.thebakingapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Harshraj on 08-08-2017.
 */

public class ListViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewFactory(this.getApplicationContext(), intent);
    }
}
