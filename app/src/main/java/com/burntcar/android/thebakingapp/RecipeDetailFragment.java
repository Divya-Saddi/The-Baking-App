package com.burntcar.android.thebakingapp;


import android.net.Uri;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;



import com.burntcar.android.thebakingapp.restCalls.Ingredient;
import com.burntcar.android.thebakingapp.restCalls.Step;

import com.google.android.exoplayer2.ExoPlayerFactory;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;


import java.util.ArrayList;



/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {


    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
   //public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
   // private DummyContent.DummyItem mItem;

    Step step;

    SimpleExoPlayer simpleExoPlayer;
    //SimpleExoPlayerView exoPlayerView;

    ArrayList<Ingredient> ingredients;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            step = (Step) getArguments().getParcelable("step");
        ingredients = getArguments().getParcelableArrayList("ingredients");

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(),new DefaultTrackSelector());




if(step != null){
    MediaSource mediaSource =new ExtractorMediaSource(Uri.parse(step.videoURL), new DefaultDataSourceFactory(
            getActivity(), "donno string"), new DefaultExtractorsFactory(), null, null);
    simpleExoPlayer.prepare(mediaSource);
    simpleExoPlayer.setPlayWhenReady(true);

}



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        if(ingredients != null){
            ((TextView) rootView.findViewById(R.id.recipe_detail)).setVisibility(View.GONE);
            ((SimpleExoPlayerView) rootView.findViewById(R.id.playerView)).setVisibility(View.GONE);
            int i =0;
            for(Ingredient ingredient:ingredients){
                ((TextView) rootView.findViewById(R.id.recipe_desc_tv)).append(++i+". "+ingredient.ingredient+" \n\n");

            }

        }

        // Show the dummy content as text in a TextView.
        if(step != null){
            ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(step.shortDescription);
            ((TextView) rootView.findViewById(R.id.recipe_desc_tv)).setText(step.description);

            if(step.videoURL != null && !step.videoURL.isEmpty()) {
                ((SimpleExoPlayerView) rootView.findViewById(R.id.playerView)).setPlayer(simpleExoPlayer);
            }else {
                ((SimpleExoPlayerView) rootView.findViewById(R.id.playerView)).setVisibility(View.GONE);
            }

        }


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        simpleExoPlayer.release();
    }
}
