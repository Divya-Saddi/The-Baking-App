package com.burntcar.android.thebakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.burntcar.android.thebakingapp.restCalls.BakingAppClient;
import com.burntcar.android.thebakingapp.restCalls.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecipeNameAdapter.ListItemClickListener {


    @BindView(R.id.progress_bar)
     ProgressBar progressBar;

    @BindView(R.id.recyclerview_recipe_name)
     RecyclerView recyclerView;

    private RecipeNameAdapter recipeNameAdapter;

    private ArrayList<Recipe> recipes;

    private String baseUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recipeNameAdapter = new RecipeNameAdapter(this);

        baseUrl = getString(R.string.baseUrl);

        progressBar.setVisibility(View.VISIBLE);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        } else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerView.setLayoutManager(layoutManager);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recipeNameAdapter);


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        BakingAppClient client = retrofit.create(BakingAppClient.class);
        Call<List<Recipe>> call = client.recipesForApp();

        if (isNetworkAvailable()) {
            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    recipes = (ArrayList<Recipe>) response.body();
                    progressBar.setVisibility(View.INVISIBLE);
                    recipeNameAdapter.setData(recipes);

                    Toast.makeText(MainActivity.this, "Recipes loaded",
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Problem Loading data check connection",
                            Toast.LENGTH_LONG).show();


                }
            });
        }

        if (!isNetworkAvailable()) {
            Toast.makeText(getBaseContext(),
                    "No Internet connection available", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {

        Recipe recipe = recipes.get(clickedItemIndex);

        Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
        intent.putExtra("recipe", recipe);
        intent.putParcelableArrayListExtra("recipeList", recipes);
        startActivity(intent);
        //Update Widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
        BakingAppWidget.updateFromActivity(this, appWidgetManager, appWidgetIds, clickedItemIndex, recipes);
        Log.i("mainActivity", "intentSent");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
