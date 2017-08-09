package com.burntcar.android.thebakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.burntcar.android.thebakingapp.restCalls.BakingAppClient;
import com.burntcar.android.thebakingapp.restCalls.Ingredient;
import com.burntcar.android.thebakingapp.restCalls.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Harshraj on 09-08-2017.
 */

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory  {

    private Context mContext;
    private int mAppWidgetId;
   // List<Recipe> recipes;
     List<Recipe> recipes ;

    public WidgetFactory(Context applicationContext, Intent intent) {

        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        Log.e("WidgetFactory","WidgetFactory");


        Log.e("WidgetFactory","WidgetFactoryEnd!");
    }

    @Override
    public void onCreate() {
        new AsyncClass().execute();
        SystemClock.sleep(1000);
        /*Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        BakingAppClient client = retrofit.create(BakingAppClient.class);
        Call<List<Recipe>> call = client.recipesForApp();

        try {
            recipes =  call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*call.enqueue(new Callback<List<Recipe>>() {



            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                recipes= response.body();


                Log.e("length", String.valueOf(recipes));


                // recipes.addAll(response.body())  ;

                *//*for(Recipe recipe: recipes){
                    Log.e("onCreate",recipe.name);
                }*//*

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });*/

        
    }

    @Override
    public void onDataSetChanged() {
        Log.e("onDataSetChanged","onDataSetChanged");

    }

    @Override
    public void onDestroy() {
        //recipes.clear();
        Log.e("onDestroy","onDestroy");
    }

    @Override
    public int getCount() {

        Log.e("getCount","getCount "+recipes);
        if(recipes != null)
        {
            Log.e("getCount","getCount "+recipes.size());
            return recipes.size();

        }
        else{
            Log.e("getCount","getCount ");
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.e("getViewAt"," "+position);
        Recipe recipe = recipes.get(position);
        List<Ingredient> ingredients = recipe.ingredients;
        String x = "";
        int i =0;
        for(Ingredient ingredient: ingredients){
            x = x + (++i)+". "+ ingredient.ingredient +" \n";
        }


        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_lv_item);
        rv.setTextViewText(R.id.tv1, recipe.name);
        rv.setTextViewText(R.id.tv2, x);
        Log.e("getViewAtdf",""+position);
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

    class AsyncClass extends AsyncTask<Void,Void,List<Recipe>>{

        @Override
        protected List<Recipe> doInBackground(Void... params) {
            List<Recipe> xyz = null;

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();

            BakingAppClient client = retrofit.create(BakingAppClient.class);
            Call<List<Recipe>> call = client.recipesForApp();

            try {
                xyz =  call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return xyz;
        }

        @Override
        protected void onPostExecute(List<Recipe> recipeList) {
          recipes =  recipeList;
            Log.e("onPostExecute",""+recipes);

        }
    }
}
