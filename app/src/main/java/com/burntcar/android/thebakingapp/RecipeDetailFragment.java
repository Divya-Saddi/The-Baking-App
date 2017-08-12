package com.burntcar.android.thebakingapp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.burntcar.android.thebakingapp.restCalls.Ingredient;
import com.burntcar.android.thebakingapp.restCalls.Recipe;
import com.burntcar.android.thebakingapp.restCalls.Step;

import com.google.android.exoplayer2.ExoPlayerFactory;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.button;



public class RecipeDetailFragment extends Fragment {


    private Step step;
    private  SimpleExoPlayer simpleExoPlayer;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> stepsList;
    private int position;
    private boolean videoplaying;
    private boolean twoPane = false;

    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            step = (Step) getArguments().getParcelable("step");
        ingredients = getArguments().getParcelableArrayList("ingredients");
        stepsList = getArguments().getParcelableArrayList("stepsList");
        position = getArguments().getInt("position");
        twoPane = getArguments().getBoolean("twoPane");
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
        final View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        if(ingredients != null){
            ((TextView) rootView.findViewById(R.id.recipe_detail)).setVisibility(View.GONE);
            ((SimpleExoPlayerView) rootView.findViewById(R.id.playerView)).setVisibility(View.GONE);
            int i =0;
            for(Ingredient ingredient:ingredients){
                ((TextView) rootView.findViewById(R.id.recipe_desc_tv)).append(++i+". "+ingredient.ingredient.substring(0,1).toUpperCase()+ingredient.ingredient.substring(1)+" \n\n");

            }

        }

        if(step != null){

            ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(step.shortDescription);
            ((TextView) rootView.findViewById(R.id.recipe_desc_tv)).setText(step.description);

            if(step.thumbnailURL.contains(".jpeg") || step.thumbnailURL.contains(".jpg") || step.thumbnailURL.contains("png")) {
                URL url = null;
                try {
                    url = new URL(step.thumbnailURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap bmp = null;
                try {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ImageView imageView = (ImageView) rootView.findViewById(R.id.thumbnail_img);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bmp);

            }else {
                rootView.findViewById(R.id.no_thumbnail_tv).setVisibility(View.VISIBLE);
            }

            if(step.videoURL != null && !step.videoURL.isEmpty()) {
                videoplaying= true;
                ((SimpleExoPlayerView) rootView.findViewById(R.id.playerView)).setPlayer(simpleExoPlayer);
            }else {
                videoplaying = false;
                ((SimpleExoPlayerView) rootView.findViewById(R.id.playerView)).setVisibility(View.GONE);
            }
            Button button = (Button) rootView.findViewById(R.id.next_step_btn);
            if(twoPane) {
                button.setVisibility(View.GONE);
            }else {

                if (position + 1 == stepsList.size()) {
                    button.setVisibility(View.GONE);
                }else{
                    button.setVisibility(View.VISIBLE);
                }


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if (videoplaying) {
                            simpleExoPlayer.release();
                        }

                        position++;


                        if (position + 1 == stepsList.size()) {
                            ((Button) rootView.findViewById(R.id.next_step_btn)).setVisibility(View.GONE);
                        }

                        Step currStep = stepsList.get(position);

                        if(currStep.thumbnailURL.contains(".jpeg") || currStep.thumbnailURL.contains(".jpg") || currStep.thumbnailURL.contains("png")) {
                            URL url = null;
                            try {
                                url = new URL(currStep.thumbnailURL);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            Bitmap bmp = null;
                            try {
                                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            ImageView imageView = (ImageView) rootView.findViewById(R.id.thumbnail_img);
                            imageView.setVisibility(View.VISIBLE);
                            imageView.setImageBitmap(bmp);

                        }else {
                            rootView.findViewById(R.id.no_thumbnail_tv).setVisibility(View.VISIBLE);
                        }


                        ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(currStep.shortDescription);
                        ((TextView) rootView.findViewById(R.id.recipe_desc_tv)).setText(currStep.description);

                        if (currStep.videoURL != null && !currStep.videoURL.isEmpty()) {

                            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector());

                            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(currStep.videoURL), new DefaultDataSourceFactory(
                                    getActivity(), "donno string"), new DefaultExtractorsFactory(), null, null);
                            simpleExoPlayer.prepare(mediaSource);
                            simpleExoPlayer.setPlayWhenReady(true);
                            videoplaying = true;
                            ((SimpleExoPlayerView) rootView.findViewById(R.id.playerView)).setPlayer(simpleExoPlayer);
                            ((SimpleExoPlayerView) rootView.findViewById(R.id.playerView)).setVisibility(View.VISIBLE);
                        } else {
                            videoplaying = false;
                            ((SimpleExoPlayerView) rootView.findViewById(R.id.playerView)).setVisibility(View.GONE);
                        }


                    }
                });

            }

        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        simpleExoPlayer.release();
    }


    @Override
    public void onStop() {
        super.onStop();
        simpleExoPlayer.release();
    }
}
