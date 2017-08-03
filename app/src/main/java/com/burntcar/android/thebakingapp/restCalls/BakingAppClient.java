package com.burntcar.android.thebakingapp.restCalls;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Harshraj on 30-07-2017.
 */

public interface BakingAppClient {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> recipesForApp();
}
