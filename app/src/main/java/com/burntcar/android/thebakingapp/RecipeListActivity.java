package com.burntcar.android.thebakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;



import com.burntcar.android.thebakingapp.restCalls.Recipe;
import com.burntcar.android.thebakingapp.restCalls.Step;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.fragment;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    Recipe recipe;
    ArrayList<Step> steps;
    ArrayList<Recipe> recipeList;

    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipe = getIntent().getExtras().getParcelable("recipe");
        recipeList = getIntent().getExtras().getParcelableArrayList("recipeList");
        steps = recipe.steps;
        cardView = (CardView) findViewById(R.id.ingred_card_view);
        setTitle(recipe.name);

       /* Toast.makeText(RecipeListActivity.this, "ArrayList recived size:"+recipeList.size(),
                Toast.LENGTH_LONG).show();*/

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mTwoPane) {
                    RecipeDetailFragment fragment = new RecipeDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("ingredients",recipe.ingredients);

                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment)
                            .commit();

                }else {

                    Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
                    intent.putParcelableArrayListExtra("ingredients", recipe.ingredients);
                    startActivity(intent);
                }
            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(steps));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        //private final List<DummyContent.DummyItem> mValues;
        private final List<Step> mRecipeSteps;


        public SimpleItemRecyclerViewAdapter(List<Step> items) {
            mRecipeSteps = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            //holder.mItem = mValues.get(position);
            holder.stepTV.setText("Step "+(position+1)+" : " +mRecipeSteps.get(position).shortDescription);
            //holder.mIdView.setText(mValues.get(position).id);
            //holder.mContentView.setText(mValues.get(position).content);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        /*Bundle arguments = new Bundle();
                        arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        RecipeDetailFragment fragment = new RecipeDetailFragment();*/
                        RecipeDetailFragment fragment = new RecipeDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("step",steps.get(position));
                        bundle.putBoolean("twoPane",true);
                        //bundle.putParcelableArrayList("ingredients",ingredients);

                        fragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipe_detail_container, fragment)
                                .commit();
                    } else {
                        /*Toast.makeText(RecipeListActivity.this, "clicked Index :"+holder.stepTV.getText().toString()+position,
                                Toast.LENGTH_LONG).show();*/
                        Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
                        intent.putExtra("step", mRecipeSteps.get(position));
                        intent.putExtra("position",position);
                        intent.putParcelableArrayListExtra("stepsList",steps);
                        startActivity(intent);

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return steps.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView stepTV;
            //public final TextView mContentView;
           // public DummyContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                stepTV = (TextView) view.findViewById(R.id.recipe_steps_tv);
                /*mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);*/
            }

           /* @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }*/
        }
    }
}
