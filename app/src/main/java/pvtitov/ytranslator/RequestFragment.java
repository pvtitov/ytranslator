package pvtitov.ytranslator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Павел on 11.06.2017.
 */

public class RequestFragment extends Fragment implements MainActivity.OnClickButtonListener{
    private  static final String ARGUMENTS_WORD = "word";
    private static final String BASE_URL = "https://translate.yandex.net";
    private static final String API_KEY = "trnsl.1.1.20170403T203024Z.e1c296e170563e6b.112043154a95d73055b48634e4607682bdd23817";

    private String translationDirection;

    private EditText inputField;


    public static RequestFragment newInstance(String word){
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENTS_WORD, word);
        RequestFragment fragment = new RequestFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onClick() {
        String word = inputField.getText().toString();
        if (TextUtils.isEmpty(word) || (!latin(word) && !cyrillic(word))) {
            errorToast();
        }
        if (latin(word)) translationDirection = "en-ru";
        if (cyrillic(word)) translationDirection = "ru-en";


        HttpApiService httpApiService = RetrofitLab.getSingleInstance(BASE_URL).create(HttpApiService.class);
        Call<TranslationModel> call = httpApiService.getTranslation(API_KEY, translationDirection, word);
        call.enqueue(new Callback<TranslationModel>() {

            @Override
            public void onResponse(Call<TranslationModel> call, Response<TranslationModel> response) {
                String translation = response.body().getText().get(0);
                WordLab.getSingleInstance(getActivity()).addNewWord(new Word(inputField.getText().toString(), translation));
            }

            @Override
            public void onFailure(Call<TranslationModel> call, Throwable t) {
                errorToast();
            }

        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        inputField = view.findViewById(R.id.input_field);
        inputField.setText(getArguments().getString(ARGUMENTS_WORD));

        return view;
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
        Toast.makeText(getContext(), "oops", Toast.LENGTH_SHORT).show();
    }
}
