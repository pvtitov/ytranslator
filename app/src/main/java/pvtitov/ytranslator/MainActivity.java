package pvtitov.ytranslator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {

    private static final String EXTRA_WORD = "paveltitov.ytranslator.word";
    private static final String EXTRA_TRANSLATION = "paveltitov.ytranslator.translation";
    private static final String EXTRA_SHOW_DETAIL = "paveltitov.ytranslator.show_detail";
    private static final String REQUEST_FRAGMENT_TAG = "paveltitov.yprompter.request_fragment";
    public static final String LIST_FRAGMENT_TAG = "paveltitov.yprompter.list_fragment";


    public static Intent createIntent(Context packageContext, String word, String translation){
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(EXTRA_WORD, word);
        intent.putExtra(EXTRA_TRANSLATION, translation);
        return intent;
    }

    public static Intent createIntent(Context packageContext, String word, boolean showDetail){
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(EXTRA_WORD, word);
        intent.putExtra(EXTRA_SHOW_DETAIL, showDetail);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getIntent().getBooleanExtra(EXTRA_SHOW_DETAIL, false)){
            setContentView(R.layout.container_activity_detail);
        } else {
            setContentView(R.layout.container_activity);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment requestFragment = fragmentManager.findFragmentById(R.id.request_fragment_container);
        if (requestFragment == null) {
            requestFragment = createRequestFragment();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.request_fragment_container, requestFragment, REQUEST_FRAGMENT_TAG)
                    .commit();
        }

        Fragment fragmentList = fragmentManager.findFragmentById(R.id.list_fragment_container);
        if (fragmentList == null) {
            fragmentList = createListFragment();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.list_fragment_container, fragmentList, LIST_FRAGMENT_TAG)
                    .commit();
        }

        TextView linkToYandex = findViewById(R.id.link_to_yandex);
        linkToYandex.setMovementMethod(LinkMovementMethod.getInstance());
    }


    protected Fragment createRequestFragment() {
        String word = getIntent().getStringExtra(EXTRA_WORD);
        return RequestFragment.newInstance(word);
    }

    protected Fragment createListFragment() {
        return new ListFragment();
    }
}