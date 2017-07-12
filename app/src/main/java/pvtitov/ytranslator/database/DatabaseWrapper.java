package pvtitov.ytranslator.database;

/**
 * Created by Павел on 25.06.2017.
 */

public class DatabaseWrapper {
    public static final class HistoryTable {
        public static final String NAME = "history";

        public static final class Collumns{
            public static final String WORD = "word";
            public static final String TRANSLATION = "translation";
        }
    }
}
