package pvtitov.ytranslator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by Павел on 06.06.2017.
 */

public abstract class AbstractFragmentActivity extends FragmentActivity {
    private static final String REQUEST_FRAGMENT_TAG = "paveltitov.yprompter.request_fragment";
    public static final String LIST_FRAGMENT_TAG = "paveltitov.yprompter.list_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_for_fragment);

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
    }


    protected abstract Fragment createRequestFragment();
    protected abstract Fragment createListFragment();
}
