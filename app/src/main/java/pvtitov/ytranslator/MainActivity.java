package pvtitov.ytranslator;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class MainActivity extends AbstractFragmentActivity {

    private static final String EXTRA_WORD = "paveltitov.ytranslator.word";

    public static Intent createIntent(Context packageContext, String word){
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(EXTRA_WORD, word);
        return intent;
    }

    @Override
    protected Fragment createRequestFragment() {
        String word = (String) getIntent().getStringExtra(EXTRA_WORD);
        return RequestFragment.newInstance(word);
    }

    @Override
    protected Fragment createListFragment() {
        return new ListFragment();
    }
}