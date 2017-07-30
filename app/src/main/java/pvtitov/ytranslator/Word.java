package pvtitov.ytranslator;


import java.util.ArrayList;

/**
 * Created by Павел on 07.06.2017.
 */

public class Word{
    private String word;
    private String translation;

    public Word(String word){
        setWord(word);
    }

    public Word(String word, String translation){
        setWord(word);
        setTranslation(translation);
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation){
        this.translation = translation;
    }
}
