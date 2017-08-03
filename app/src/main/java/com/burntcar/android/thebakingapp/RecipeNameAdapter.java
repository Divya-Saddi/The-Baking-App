package com.burntcar.android.thebakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.burntcar.android.thebakingapp.restCalls.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harshraj on 30-07-2017.
 */

public class RecipeNameAdapter extends RecyclerView.Adapter<RecipeNameAdapter.RecipeNameAdapterViewHolder>{

    private List<Recipe> recipeArrayList;
    final private ListItemClickListener mListItemClickListener;

    public void setData(List<Recipe> recipeList){
        recipeArrayList = recipeList;
        notifyDataSetChanged();
    }

    public interface ListItemClickListener{
        void onListItemClicked(int clickedItemIndex);
    }

    public RecipeNameAdapter(ListItemClickListener listItemClickListener) {
        mListItemClickListener = listItemClickListener;
    }

    @Override
    public RecipeNameAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);




        View recipeTextView = inflater.inflate(R.layout.recipe_name_list_item, parent, false);



        RecipeNameAdapterViewHolder moviesAdapterViewHolder = new RecipeNameAdapterViewHolder(recipeTextView);

        return moviesAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeNameAdapterViewHolder holder, int position) {

        Recipe recipe = recipeArrayList.get(position);

        String recipeName = recipe.name;

        holder.RecipeNametextView.setText(recipeName);

    }

    @Override
    public int getItemCount() {
        if (null == recipeArrayList) return 0;
        return recipeArrayList.size();
    }

    public class RecipeNameAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView RecipeNametextView;

        public RecipeNameAdapterViewHolder(View itemView) {
            super(itemView);

            RecipeNametextView = (TextView) itemView.findViewById(R.id.recipe_name_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClicked(clickedPosition);
        }
    }

}
