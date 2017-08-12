package com.burntcar.android.thebakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.Toast;


import com.burntcar.android.thebakingapp.restCalls.Ingredient;
import com.burntcar.android.thebakingapp.restCalls.Recipe;
import com.burntcar.android.thebakingapp.restCalls.Step;

import java.util.ArrayList;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    Step step;
    ArrayList<Ingredient> ingredients;
    ArrayList<Step> stepsList;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


        ingredients = getIntent().getParcelableArrayListExtra("ingredients");
        step = getIntent().getExtras().getParcelable("step");
        position = getIntent().getExtras().getInt("position");
        stepsList = getIntent().getExtras().getParcelableArrayList("stepsList");

        /*Toast.makeText(RecipeDetailActivity.this, "position " + position + " recipeList : " + stepsList.size(),
                Toast.LENGTH_LONG).show();*/
        //setTitle("Step " + (step.id+1));
        if (ingredients != null) {
            setTitle("Ingredients");
        }
        if (step != null) {
            setTitle("Recipe Steps");
        }

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        /*Toast.makeText(RecipeDetailActivity.this, "RecipeDetailActivity"+step.toString() ,
                Toast.LENGTH_LONG).show();*/
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            /*Bundle arguments = new Bundle();
            arguments.putString(RecipeDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID));*/


            RecipeDetailFragment fragment = new RecipeDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("step", step);
            bundle.putParcelableArrayList("ingredients", ingredients);
            bundle.putParcelableArrayList("stepsList",stepsList);
            bundle.putInt("position",position);

            fragment.setArguments(bundle);
            //fragment.setStep(step);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, RecipeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
