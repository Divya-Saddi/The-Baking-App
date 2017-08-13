package com.burntcar.android.thebakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.burntcar.android.thebakingapp.restCalls.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Harshraj on 30-07-2017.
 */

public class RecipeNameAdapter extends RecyclerView.Adapter<RecipeNameAdapter.RecipeNameAdapterViewHolder> {

    private List<Recipe> recipeArrayList;
    final private ListItemClickListener mListItemClickListener;

    public void setData(List<Recipe> recipeList) {
        recipeArrayList = recipeList;
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
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

        String imageUrl = recipe.image;

        if (!(imageUrl.contains(".jpeg") || imageUrl.contains(".png") || imageUrl.contains(".jpg"))) {
            //as the image url is empty I'm populating the ImageViews on My own!
            switch (position) {
                case 0:
                    holder.imageView.setImageResource(R.drawable.nutella_pie);
                    break;
                case 1:
                    holder.imageView.setImageResource(R.drawable.brownies);
                    break;
                case 2:
                    holder.imageView.setImageResource(R.drawable.moist_yellow_cake);
                    break;
                case 3:
                    holder.imageView.setImageResource(R.drawable.cheesecake);
                    break;
                default:
                    holder.imageView.setImageResource(R.drawable.cupcake);
            }

        } else {

            Picasso.with(holder.imageView.getContext())
                    .load(imageUrl)
                    .error(R.drawable.cupcake)
                    .placeholder(R.drawable.cupcake)
                    .into(holder.imageView);

        }
    }

    @Override
    public int getItemCount() {
        if (null == recipeArrayList) return 0;
        return recipeArrayList.size();
    }

    public class RecipeNameAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView RecipeNametextView;
        ImageView imageView;

        public RecipeNameAdapterViewHolder(View itemView) {
            super(itemView);

            RecipeNametextView = (TextView) itemView.findViewById(R.id.recipe_name_tv);
            imageView = (ImageView) itemView.findViewById(R.id.recipe_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mListItemClickListener.onListItemClicked(clickedPosition);
        }
    }

}
