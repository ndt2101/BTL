package CommandLine;

import java.io.FileNotFoundException;

public class Word {
    private String word_target;
    private String word_explain;

    // constructor
    public Word(){

    }

    public Word(String word_target, String word_explain){
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public Word(Word word){
        word_target = word.getWord_target();
        word_explain = word.getWord_explain();
    }

    // getter
    public String getWord_target(){
        return word_target;
    }

    public String getWord_explain(){
        return word_explain;
    }

    // setter
    public void setWord_target(String word_target){
        this.word_target = word_target;
    }

    public void setWord_explain(String word_explain){
        this.word_explain = word_explain;
    }

    // method
    public void print(){
        System.out.println(word_target + "\t|" + word_explain);
    }

    @Override
    public String toString(){
        return word_target;
    }

    public static void main(String[] strings) throws FileNotFoundException {

    }
}
