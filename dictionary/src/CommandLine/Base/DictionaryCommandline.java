package CommandLine.Base;

import java.io.FileNotFoundException;


public class DictionaryCommandline extends DictionaryManagement{

    public void showAllWords(){
        System.out.println("-showAllWords");
        System.out.println("No\t| English\t| Vietnamese");
        for (int i = 0; i < getSize(); i++){
            Word newWord = getWord(i);
            System.out.print(i + 1);
            System.out.printf("\t|%s\t\t|%s\n" , newWord.getWord_target(), newWord.getWord_explain());
        }
    }

    public void dictionaryBasic(){
        System.out.println("-dictionaryBasic");
        insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAdvanced() throws FileNotFoundException{
        System.out.println("-dictionaryAdvanced");
        insertFromFile();
        //sortDictionary();
        showAllWords();
        //dictionaryLookup("hello");
    }

//    public static void dictionarySearcher(String subSting){
//        System.out.println("-dictionarySearcher");
//
//        Dictionary.dictionarySearcher(subSting);
//    }




    // linh tinh
    public void testModule(String target, String explain){
        addWord("green", "mau xanh luc");
        super.removeWord("hell");

        editWord(target, explain);

        showAllWords();


    }

//    public static void main(String[] strings) throws FileNotFoundException{
//        DictionaryCommandline dic = new DictionaryCommandline();
//        dic.dictionaryBasic();
//        dic.dictionaryAdvanced();
//        //dic.testModule("world", "sei kai");
//        dic.dictionarySearcher("h");
//
//        //dic.addManagement();
//
//        dic. dictionaryExportToFile();
//    }
}
