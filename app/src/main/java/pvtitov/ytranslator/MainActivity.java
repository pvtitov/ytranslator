package pvtitov.ytranslator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;



public class MainActivity extends FragmentActivity
        implements ListFragment.onSelectWordListener, RequestFragment.OnTranslationResponseListener{

    private static final String REQUEST_FRAGMENT_TAG = "paveltitov.yprompter.request_fragment";
    public static final String LIST_FRAGMENT_TAG = "paveltitov.yprompter.list_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment requestFragment = fragmentManager.findFragmentById(R.id.request_fragment_container);
        if (requestFragment == null) {
            requestFragment = RequestFragment.newInstance(null);
            fragmentManager
                    .beginTransaction()
                    .add(R.id.request_fragment_container, requestFragment, REQUEST_FRAGMENT_TAG)
                    .commit();
        }

        final Fragment fragment = requestFragment;
        ImageButton button = findViewById(R.id.go_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickButtonListener onClickListener = (OnClickButtonListener) fragment;
                onClickListener.onClickButton();
            }
        });


        Fragment fragmentList = fragmentManager.findFragmentById(R.id.list_fragment_container);
        if (fragmentList == null) {
            fragmentList = new ListFragment();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.list_fragment_container, fragmentList, LIST_FRAGMENT_TAG)
                    .commit();
        }

        TextView linkToYandex = findViewById(R.id.link_to_yandex);
        linkToYandex.setMovementMethod(LinkMovementMethod.getInstance());
    }



    @Override
    public void onSelectingWord(Word word) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.request_fragment_container, RequestFragment.newInstance(word.getWord()), REQUEST_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResponse(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.list_fragment_container, new ListFragment(), LIST_FRAGMENT_TAG)
                .commit();
    }
}