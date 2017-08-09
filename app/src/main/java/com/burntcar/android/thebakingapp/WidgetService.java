package com.burntcar.android.thebakingapp;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.burntcar.android.thebakingapp.restCalls.BakingAppClient;
import com.burntcar.android.thebakingapp.restCalls.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.burntcar.android.thebakingapp.BakingAppWidget.recipes;

/**
 * Created by Harshraj on 09-08-2017.
 */

public class WidgetService extends RemoteViewsService {



    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {



       // Log.e("onCreate", String.valueOf(recipes==null));

        return new WidgetFactory(this.getApplicationContext(), intent);
    }
}
