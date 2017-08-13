package com.burntcar.android.thebakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.burntcar.android.thebakingapp.restCalls.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements RecipeNameAdapter.ListItemClickListener, DataDownloader.DelayerCallback {

    @Nullable
    private BakingIdlingResource mIdlingResource;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.recyclerview_recipe_name)
    RecyclerView recyclerView;

    private RecipeNameAdapter recipeNameAdapter;

    private ArrayList<Recipe> recipes;

    @BindView(R.id.snackbarlocation)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getIdlingResource();

        recipeNameAdapter = new RecipeNameAdapter(this);


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


        if (isNetworkAvailable()) {
            DataDownloader.downloadData(this, MainActivity.this, mIdlingResource);
        }

        if (!isNetworkAvailable()) {

            Snackbar bar = Snackbar.make(coordinatorLayout, "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
            bar.show();

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
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new BakingIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    public void onDone(ArrayList<Recipe> recipeList) {
        recipes = recipeList;
        progressBar.setVisibility(View.INVISIBLE);
        recipeNameAdapter.setData(recipes);

        Toast.makeText(MainActivity.this, "Recipes loaded",
                Toast.LENGTH_LONG).show();
    }


}
