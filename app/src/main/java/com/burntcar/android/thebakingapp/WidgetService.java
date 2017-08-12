package com.burntcar.android.thebakingapp;

import android.content.Intent;
import android.widget.RemoteViewsService;



/**
 * Created by Harshraj on 09-08-2017.
 */

public class WidgetService extends RemoteViewsService {



    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {


        return new WidgetFactory(this.getApplicationContext(), intent);
    }
}
