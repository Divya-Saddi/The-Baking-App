package com.burntcar.android.thebakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.burntcar.android.thebakingapp.restCalls.BakingAppClient;
import com.burntcar.android.thebakingapp.restCalls.Ingredient;
import com.burntcar.android.thebakingapp.restCalls.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Harshraj on 09-08-2017.
 */

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int mAppWidgetId;
    List<Recipe> recipes = null ;

    public WidgetFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        BakingAppClient client = retrofit.create(BakingAppClient.class);
        Call<List<Recipe>> call = client.recipesForApp();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                recipes = response.body();

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
        
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        //recipes.clear();
    }

    @Override
    public int getCount() {

        if(recipes != null )

        return recipes.size();

        else return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Recipe recipe = recipes.get(position);
        List<Ingredient> ingredients = recipe.ingredients;
        String x = "";
        for(Ingredient ingredient: ingredients){
            x = x + ingredient.ingredient +" \n";
        }


        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_lv_item);
        rv.setTextViewText(R.id.tv1, "dfsf");
        /*rv.setTextViewText(R.id.tv2, x);*/

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
