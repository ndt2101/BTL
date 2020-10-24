import CommandLine.DictionaryCommandline;
import CommandLine.Word;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public void virtual() throws FileNotFoundException {
        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();

        File file = new File("src/CommandLine/Data/virtualData.txt");
        dictionaryCommandline.insertFromFile(file);

        ArrayList<Word> subList = dictionaryCommandline.dictionarySearcher("bix");
        dictionaryCommandline.showAllWords(subList);
    }

    public static void main(String[] strings) throws FileNotFoundException {
        DictionaryCommandline dictionary = new DictionaryCommandline();

        dictionary.dictionaryBasic();

        dictionary.dictionaryAdvanced();

        System.out.println(dictionary.dictionaryLookup("torn"));

        dictionary.dictionaryExportToFile();
    }
}
