package pvtitov.ytranslator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * Created by Павел on 11.06.2017.
 */

public class RequestFragment extends Fragment{
    private  static final String ARGUMENTS_WORD = "word";

    public EditText inputField;
    private OnClickButtonListener buttonListener;


    public static RequestFragment newInstance(String word){
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENTS_WORD, word);
        RequestFragment fragment = new RequestFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public OnClickButtonListener getButtonListener() {
        return buttonListener;
    }


    public interface OnTranslationResponseListener{
        void onTranslationResponse();
    }
    OnTranslationResponseListener callbackToActivity;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbackToActivity = (OnTranslationResponseListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement onSelectWordListener interface");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        inputField = view.findViewById(R.id.input_field);
        inputField.setText(getArguments().getString(ARGUMENTS_WORD));

        buttonListener = new ButtonListener(getActivity(), inputField, callbackToActivity);

        return view;
    }

}
