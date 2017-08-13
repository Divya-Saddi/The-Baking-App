package com.burntcar.android.thebakingapp;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.burntcar.android.thebakingapp.restCalls.BakingAppClient;
import com.burntcar.android.thebakingapp.restCalls.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Harshraj on 14-08-2017.
 */

public class DataDownloader {


    private static final int DELAY_MILLIS = 3000;

    static ArrayList<Recipe> recipes = new ArrayList<>();

    interface DelayerCallback {
        void onDone(ArrayList<Recipe> recipes);
    }


    static void downloadData(Context context, final DelayerCallback callback,
                             @Nullable final BakingIdlingResource idlingResource) {


        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        BakingAppClient client = retrofit.create(BakingAppClient.class);
        Call<List<Recipe>> call = client.recipesForApp();


        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes = (ArrayList<Recipe>) response.body();

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {


            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onDone(recipes);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }

}
