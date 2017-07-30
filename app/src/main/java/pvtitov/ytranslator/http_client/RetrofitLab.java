package pvtitov.ytranslator.http_client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Павел on 22.07.2017.
 */

public class RetrofitLab {
    private static Retrofit retrofit = null;

    public static Retrofit getSingleInstance(String baseUrl){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

