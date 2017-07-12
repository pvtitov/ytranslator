package pvtitov.ytranslator;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class MainActivity extends AbstractFragmentActivity {

    public static final String EXTRA_WORD_FROM_HISTORY = "paveltitov.ytranslator.word_to_translate";

    public static Intent createIntent(Context packageContext, String word){
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(EXTRA_WORD_FROM_HISTORY, word);
        return intent;
    }

    @Override
    protected Fragment createRequestFragment() {
        return new RequestFragment();
    }

    @Override
    protected Fragment createListFragment() {
        return new ListFragment();
    }
}