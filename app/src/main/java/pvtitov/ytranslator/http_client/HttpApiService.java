package pvtitov.ytranslator.http_client;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Павел on 21.07.2017.
 */

public interface HttpApiService {
    @GET("/api/v1.5/tr.json/translate")
    Call<TranslationModel> getTranslation (@Query("key") String key,
                                           @Query("lang") String lang,
                                           @Query("text") String text);
}
