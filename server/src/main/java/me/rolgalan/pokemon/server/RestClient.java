package me.rolgalan.pokemon.server;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import me.rolgalan.pokemon.server.model.PokemonResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Roldán Galán on 03/11/2017.
 */

public class RestClient {

    private final static String BASE_URL = "http://pokeapi.co/api/v2/";
    private static ApiInterface apiInterface;

    synchronized static ApiInterface getClient() {

        if (apiInterface == null) {
            final OkHttpClient clientAux = new OkHttpClient.Builder().build();

            OkHttpClient.Builder builder = clientAux.newBuilder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {
                builder.addNetworkInterceptor(new StethoInterceptor());
            }

            //Uncomment to add a Fake interceptor to mock the server response for testing purposes
            //if (BuildConfig.DEBUG) okClient.interceptors().add(new FakeInterceptor());

            Retrofit client = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiInterface = client.create(ApiInterface.class);
        }

        return apiInterface;
    }

    interface ApiInterface {

        @GET("pokemon/{id}")
        Call<PokemonResponse> getPokemon(@Path("id") int id);
    }
}
