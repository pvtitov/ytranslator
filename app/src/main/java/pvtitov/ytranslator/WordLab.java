package pvtitov.ytranslator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pvtitov.ytranslator.database.DatabaseHelper;
import pvtitov.ytranslator.database.DatabaseWrapper.HistoryTable;
import pvtitov.ytranslator.database.DatabaseWrapper.HistoryTable.Collumns;
import pvtitov.ytranslator.database.WordsCursorWrapper;

/**
 * Created by Павел on 07.06.2017.
 */

public class WordLab {
    private static WordLab wordLab;
    private Context context;
    private SQLiteDatabase database;

    public static WordLab getOrCreateWordLab(Context context){
        if (wordLab == null) wordLab = new WordLab(context);
        return wordLab;
    }

    private WordLab(Context context) {
        this.context = context.getApplicationContext();
        database = new DatabaseHelper(this.context).getWritableDatabase();
    }

    public List<Word> getListOfWords() {
        List<Word> words = new ArrayList<>();
        WordsCursorWrapper cursor = queryWords(null, null);
        try{
            cursor.moveToLast();
            while (!cursor.isBeforeFirst()){
                words.add(cursor.getWord());
                cursor.moveToPrevious();
            }
        } finally {
            cursor.close();
        }
        return words;
    }

    public Word getWord(String word) {
        WordsCursorWrapper cursor = queryWords(
                Collumns.WORD + " = ? ",
                new String[] {word});
        try{
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getWord();
        } finally {
            cursor.close();
        }
    }

    public void addNewWord(Word word){
        ContentValues values = getContentValues(word);
        database.insert(HistoryTable.NAME, null, values);
    }

    private static ContentValues getContentValues(Word word){
        ContentValues values = new ContentValues();
        values.put(Collumns.WORD, word.getWord());
        values.put(Collumns.TRANSLATION, word.getTranslation());
        return values;
    }

    public void updateExsistingWord(Word word){
        ContentValues values = getContentValues(word);
        database.update(HistoryTable.NAME, values, Collumns.WORD + " = ? ", new String[]{word.getWord()});
    }

    private WordsCursorWrapper queryWords(String whereClause, String[] whereArgs){
        Cursor cursor = database.query(
                HistoryTable.NAME,
                null,
                whereClause,
                whereArgs,
                null, null, null);
        return new WordsCursorWrapper(cursor);
    }
}
