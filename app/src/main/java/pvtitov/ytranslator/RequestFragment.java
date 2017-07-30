package pvtitov.ytranslator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pvtitov.ytranslator.http_client.HttpApiService;
import pvtitov.ytranslator.http_client.RetrofitLab;
import pvtitov.ytranslator.http_client.TranslationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Павел on 11.06.2017.
 */

public class RequestFragment extends Fragment{
    private  static final String ARGUMENTS_WORD = "word";
    private static final String BASE_URL = "https://translate.yandex.net";
    private static final String API_KEY = "trnsl.1.1.20170403T203024Z.e1c296e170563e6b.112043154a95d73055b48634e4607682bdd23817";
    private static final String TRANSLATION_DIRECTION = "en-ru";

    private EditText inputField;
    private ImageButton button;

    public static RequestFragment newInstance(String word){
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENTS_WORD, word);
        RequestFragment fragment = new RequestFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        inputField = view.findViewById(R.id.input_field);
        inputField.setText(getArguments().getString(ARGUMENTS_WORD));
        button = view.findViewById(R.id.go_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String word = inputField.getText().toString();

                final HttpApiService httpApiService = RetrofitLab.getSingleInstance(BASE_URL).create(HttpApiService.class);
                Call<TranslationModel> call = httpApiService.getTranslation(API_KEY, TRANSLATION_DIRECTION, word);
                call.enqueue(new Callback<TranslationModel>() {
                    @Override
                    public void onResponse(Call<TranslationModel> call, Response<TranslationModel> response) {
                        String translation = response.body().getText().get(0);
                        WordLab.getSingleInstance(getActivity()).addNewWord(new Word(word, translation));
                        Intent intent = MainActivity.createIntent(getActivity(), word, translation);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<TranslationModel> call, Throwable t) {
                        Toast.makeText(getContext(), "oops", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}
