package repository.myfridge;

import android.content.Context;

import java.util.Random;

import repository.myfridge.Listeners.RandomResponseListener;
import repository.myfridge.Models.RandomResponseApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }
    public void getRandomRecipes(RandomResponseListener listener)
    {

        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomResponseApi> call = callRandomRecipes.callRandomRecipes(context.getString(R.string.api_key), "10");
        call.enqueue(new Callback<RandomResponseApi>()
        {
            @Override
            public void onResponse(Call<RandomResponseApi> call, Response<RandomResponseApi> response) {

            }

            @Override
            public void onFailure(Call<RandomResponseApi> call, Throwable t) {

            }

        })


    ;}
    private interface RandomRecipies{
        @GET("recipes/random")
        Call<RandomResponseApi> RandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number
        );
    }
}
