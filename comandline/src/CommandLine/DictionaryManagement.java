package CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;


public class DictionaryManagement extends Dictionary {

    public void insertFromCommandline() {
        System.out.println("-insertFromCommandline");

        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        scan.nextLine();
        for (int i = 0; i < n; i++) {
            String word_target = scan.nextLine();
            String word_explain = scan.nextLine();

            super.addWord(word_target, word_explain);

        }
    }

    public void insertFromFile() throws FileNotFoundException{
        System.out.println("-insertFromFile");

        File input = new File("src/CommandLine/Data/dictionaries.txt");
        Scanner scan = new Scanner(input);
        while (scan.hasNextLine()){
            String line = scan.nextLine();

            String[] newWord = line.split(" = ");
            super.addWord(newWord[0], newWord[1]);
        }

    }

    public void insertFromFile(File input) throws FileNotFoundException{
        System.out.println("-insertFromFile");

        Scanner scan = new Scanner(input);

        while (scan.hasNextLine()){
            String line = scan.nextLine();

            String[] newWord = line.split(" = ");
            super.addWord(newWord[0], newWord[1]);
        }

    }


    public String dictionaryLookup(String target){
        System.out.println("-dictionaryLookup: " + target);

        int index = findTarget(target);
        if(index == -1){
            return "Not found target";
        }

        return getWord(index).getWord_explain();

    }

    /**
     * From commandline
     */
    public void addWord(){
        System.out.println("-addManagement");

        Scanner scan = new Scanner(System.in);
        String word_target = scan.nextLine();
        String word_explain = scan.nextLine();

        super.addWord(word_target, word_explain);

    }

    public void addWord(String target, String explain){
        System.out.println("-addManagement: " + target);
        try {
            if(super.findTarget(target) != -1){
                throw new Exception();
            }

            super.addWord(target, explain);
            super.bubleSort();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void removeWord(String target){
        System.out.println("-removeManagement: " + target);
        try {
            int index = super.findTarget(target);

            if(index == -1){
                throw new Exception();
            }

            super.removeWord(index);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void editWord(String target, String explain){
        System.out.println("-editManagement: " + target);
        try {
            int index = super.findTarget(target);
            if(index == -1){
                throw new Exception();
            }

            super.editWord(index ,target, explain);
        }
        catch (Exception e){
            System.out.println(e);
        }

    }


    public void editWord(String oldTarget, String newTarget, String newExplain){
        System.out.println("-editManagement: " + oldTarget + ", " + newTarget);
        try {
            int index = super.findTarget(oldTarget);
            if(index == -1){
                throw new Exception();
            }

            super.editWord(index, newTarget, newExplain);
        }
        catch (Exception e){
            System.out.println(e);
        }

    }


    public void dictionaryExportToFile(){
        System.out.println("-dictionaryExportToFile");

        try {
            FileWriter file = new FileWriter("src/CommandLine/Data/dictionaries.txt");
            for(Word newWord : words){
                file.write(newWord.getWord_target());
                file.write(" = ");
                file.write(newWord.getWord_explain());
                file.write("\n");
            }
            file.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }


    public static void main(String[] strings) throws FileNotFoundException {

    }
}
