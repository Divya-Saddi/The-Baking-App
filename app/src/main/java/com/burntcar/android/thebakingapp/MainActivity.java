package com.burntcar.android.thebakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.burntcar.android.thebakingapp.restCalls.BakingAppClient;
import com.burntcar.android.thebakingapp.restCalls.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecipeNameAdapter.ListItemClickListener {

    //@BindView(R.id.hello_world) TextView helloTv;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.recyclerview_recipe_name)
    RecyclerView recyclerView;

    RecipeNameAdapter recipeNameAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       ButterKnife.bind(this);
        recipeNameAdapter = new RecipeNameAdapter(this);
        progressBar.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recipeNameAdapter);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        BakingAppClient client = retrofit.create(BakingAppClient.class);
        Call<List<Recipe>> call = client.recipesForApp();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();

                /*for(Recipe recipe: recipes){
                    helloTv.append(recipe.toString());
                }*/
                progressBar.setVisibility(View.INVISIBLE);
                recipeNameAdapter.setData(recipes);

                Toast.makeText(MainActivity.this, "Data loaded ",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Data did not load"+t.toString(),
                        Toast.LENGTH_LONG).show();
               // helloTv.setText(t.toString());
            }
        });
    }

    @Override
    public void onListItemClicked(int clickedItemIndex) {
        Toast.makeText(MainActivity.this, "clicked Index"+clickedItemIndex,
                Toast.LENGTH_LONG).show();
    }
}
