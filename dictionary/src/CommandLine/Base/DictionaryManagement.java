package CommandLine.Base;

import CommandLine.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

        File input = new File("src/CommandLine/Base/dictionaries.txt");
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

    public void insertFormFilePro(File file){
        System.out.println("-insertFormFilePro");
        try {
            Scanner scanner = new Scanner(file);

            String target = "";
            String explain = "";
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
//                System.out.println(line);
                if(line.charAt(0) == '@'){
                    super.addWord(target, explain);

                    target = line.substring(1, line.length());
                    String[] str = target.split(" /");
                    explain = "";

                    if(str.length == 1){
                        target = str[0];
                        explain = explain + "/null/ \n";
                    }
                    else{
                        target = str[0];
                        explain = explain + "/" + str[1] + "\n";
                    }
                    //System.out.println(target + " : " + explain);
                }
                else{
                    explain = explain + line + "\n";
                }

            }
            super.addWord(target, explain);
            removeWord(0);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    public void dictionaryLookup(String target){
//        System.out.println("-dictionaryLookup");
//
//        int index = findTarget(target);
//        if(index == -1){
//            System.out.println( "Not found target" );
//        }
//        else {
//            System.out.println(getWord(index).getWord_explain());
//        }
//    }

    public String dictionaryLookup(String target){
        System.out.println("-dictionaryLookup: " + target);

        int index = findTarget(target);
        if(index == -1){
            return "Not found target";
        }

        return getWord(index).getWord_explain();

    }


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

        super.dictionaryExportToFile();
    }


    public static void main(String[] strings) throws FileNotFoundException {
        DictionaryManagement dic = new DictionaryManagement();
        dic.insertFromCommandline();
        dic.insertFromFile();
        System.out.println(dic.dictionaryLookup("hello"));

    }
}
