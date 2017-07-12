package pvtitov.ytranslator.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import pvtitov.ytranslator.Word;
import pvtitov.ytranslator.database.DatabaseWrapper.HistoryTable.Collumns;

/**
 * Created by Павел on 26.06.2017.
 */

public class WordsCursorWrapper extends CursorWrapper {
    public WordsCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Word getWord(){
        String word = getString(getColumnIndex(Collumns.WORD));
        String translation = getString(getColumnIndex(Collumns.TRANSLATION));

        Word wordObject = new Word(word);
        wordObject.setWord(word);
        wordObject.setTranslation(translation);

        return wordObject;
    }
}
