package pvtitov.ytranslator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Павел on 11.06.2017.
 */

public class RequestFragment extends Fragment{
    private  static final String ARGUMENTS_WORD = "word";

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
        inputField = (EditText) view.findViewById(R.id.input_field);
        inputField.setText(getArguments().getString(ARGUMENTS_WORD));
        button = (ImageButton) view.findViewById(R.id.go_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word(inputField.getText().toString());
                WordLab.getOrCreateWordLab(getActivity()).addNewWord(word);
                Intent intent = MainActivity.createIntent(getActivity(), word.getWord());
                startActivity(intent);
            }
        });

        return view;
    }
}
