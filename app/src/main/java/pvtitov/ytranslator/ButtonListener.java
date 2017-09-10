package pvtitov.ytranslator;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pvtitov.ytranslator.http_client.HttpApiService;
import pvtitov.ytranslator.http_client.RetrofitLab;
import pvtitov.ytranslator.http_client.TranslationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Павел on 10.09.2017.
 */

public class ButtonListener implements OnClickButtonListener {

    private static final String BASE_URL = "https://translate.yandex.net";
    private static final String API_KEY = "trnsl.1.1.20170403T203024Z.e1c296e170563e6b.112043154a95d73055b48634e4607682bdd23817";


    private EditText inputField;
    private Context context;
    private RequestFragment.OnTranslationResponseListener callbackToActivity;

    public ButtonListener(Context context, EditText inputField, RequestFragment.OnTranslationResponseListener callbackToActivity){
        this.inputField = inputField;
        this.context = context;
        this.callbackToActivity = callbackToActivity;
    }


    @Override
    public void onClickButton() {

        String translationDirection = null;

        String word = inputField.getText().toString();
        if (TextUtils.isEmpty(word) || (!latin(word) && !cyrillic(word))) {
            errorToast();
            return;
        }
        if (latin(word)) translationDirection = "en-ru";
        if (cyrillic(word)) translationDirection = "ru-en";


        HttpApiService httpApiService = RetrofitLab.getSingleInstance(BASE_URL).create(HttpApiService.class);
        Call<TranslationModel> call = httpApiService.getTranslation(API_KEY, translationDirection, word);
        call.enqueue(new Callback<TranslationModel>() {

            @Override
            public void onResponse(Call<TranslationModel> call, Response<TranslationModel> response) {
                String translation = response.body().getText().get(0);
                WordLab.getSingleInstance(context).addNewWord(new Word(inputField.getText().toString(), translation));
                callbackToActivity.onTranslationResponse();
            }

            @Override
            public void onFailure(Call<TranslationModel> call, Throwable t) {
                errorToast();
            }

        });
    }

    private boolean cyrillic(String string) {
        Pattern pattern = Pattern.compile("^[а-яёЁА-Я]+$");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    private boolean latin(String string) {
        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    private void errorToast(){
        Toast.makeText(context, "oops", Toast.LENGTH_SHORT).show();
    }
}
