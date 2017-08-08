package com.burntcar.android.thebakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.burntcar.android.thebakingapp.restCalls.BakingAppClient;
import com.burntcar.android.thebakingapp.restCalls.Ingredient;
import com.burntcar.android.thebakingapp.restCalls.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.media.CamcorderProfile.get;

/**
 * Created by Harshraj on 08-08-2017.
 */

class ListViewFactory implements RemoteViewsService.RemoteViewsFactory {

    List<Recipe> recipes;
    Context mContext;
     int mAppWidgetId;

    public ListViewFactory(Context context, Intent intent) {
        mContext = context;
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
                Log.v("widget","sdfdsf"+recipes.size());

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.v("widget","sdfasdsaddsf");
            }
        });

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(recipes != null)
            return recipes.size();

        else  return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.list_view_item);
        String recipeName = recipes.get(position).name;
        List<Ingredient> ingredients = recipes.get(position).ingredients;
        String x = "";
        int i = 0;
        for(Ingredient ingredient:ingredients){
            x = x +""+ (++i)+". "+ ingredient.ingredient+"\n";
        }
        rv.setTextViewText(R.id.list_view_item_heading_tv,recipeName);

        rv.setTextViewText(R.id.list_view_item_tv,x);

        Log.v("widget","getViewAt "+position);
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
        return false;
    }
}
