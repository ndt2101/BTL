package CommandLine.Base;

import CommandLine.Main;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;


public class Dictionary extends Word{
    private ArrayList<Word> words ;

    public Dictionary(){
        words = new ArrayList<Word>();
    }

    public Word getWord(int index){
        try {
            return words.get(index);
        }
        catch (Exception e){
            System.out.println("Invalid index to get");
        }
        return null;
    }

    public int getSize(){
        return words.size();
    }

    public void addWord(String word_target, String word_explain){
        Word newWord = new Word(word_target, word_explain);
        words.add(newWord);
    }

    public void addWord(int index, Word word){
        Word newWord = new Word(word);
        words.add(index, newWord);
    }

    public void removeWord(int index){
        words.remove(index);
    }

    public void editWord(int index, String target, String explain){
        Word tempWord = words.get(index);
        tempWord.setWord_target(target);
        tempWord.setWord_explain(explain);
    }

    public void bubleSort(){
        if(words.size() > 1){
            for(int i = words.size() - 2; i >= 0; i--){
                if(words.get(i).getWord_target().compareTo(words.get(i + 1).getWord_target()) > 0){
                    swap(i, i + 1);
                }
                else break;
            }
        }
    }

    /**
     * Chua cai tien: dung insertionsort.
     * Cai tien:
     */
    public void sortDictionary(){
        System.out.println("-sortDictionary");
        for(int i = 0; i < words.size() - 1; i++){
            for(int j = i; j >= 0; j--){
                if(words.get(j).getWord_target().compareTo(words.get(j + 1).getWord_target()) > 0){
                    swap(j, j + 1);
                }
                else break;
            }
        }
    }

    public void swap(int a, int b){
        Word atA = words.get(a);     // con tro
        Word atB = words.get(b);
        Word temp = new Word(words.get(a).getWord_target(), words.get(a).getWord_explain());  // chu y khoi tao sao chep

        atA.setWord_target(atB.getWord_target());
        atA.setWord_explain(atB.getWord_explain());
        atB.setWord_target(temp.getWord_target());
        atB.setWord_explain(temp.getWord_explain());
    }


    /**
     * Chua cai tien: duyet full.
     * Cai tien: dung binary search.
     * @param target target
     * @return index of target
     */
    public int findTarget(String target){
//        for (int i = 0; i < words.size(); i++){
//            if (target.equals(words.get(i).getWord_target())) {
//                return i;
//            }
//        }
//        return -1;


        int low = 0;
        int hight = words.size() - 1;

        while (low <= hight){
            int mid = low + ((hight - low)/2);

            if(target.compareTo(words.get(mid).getWord_target()) > 0){
                low = mid + 1;
            }
            else if(target.compareTo(words.get(mid).getWord_target()) < 0){
                hight = mid - 1;
            }
            else return mid;
        }


        return -1;

    }

    /**
     * Chua cai tien: duyet full.
     * Cai tien: ung dung binary.
     * @param subString subString
     */
    public ArrayList<Word> dictionarySearcher(String subString){
        System.out.println("-dictionarySearcher: " + subString);
        ArrayList<Word> subList = new ArrayList<Word>();
//        for (int i = 0; i < words.size(); i++) {
//            Word tempWord = words.get(i);
//            if (tempWord.getWord_target().startsWith(subString)) {
////                System.out.println(tempWord.getWord_target() + " | " + tempWord.getWord_explain());
//                subList.add(tempWord);
//            }
//        }
//
//        return  subList;

        try{

            int index = biSearchSubstring(subString);
            //System.out.println("Return index : " + index );

            if(index == -1) {
                throw new Exception();
            }

            int start = unSlowStart(index, 8, subString);
            //System.out.println("\nStart : " + start);

            for(int i = start; i < words.size(); i++){
                if(words.get(i).getWord_target().indexOf(subString) == 0) {
                    subList.add(words.get(i));
                }
                else break;
                //words.get(i).print();

            }
        }
        catch(Exception e){
            System.out.println("Substring not found : " + e);
        }

        return subList;
    }

    /**
     * De quy tim start.
     * Lap tim start.
     * @param index bat dau tu index
     * @param step buoc nhay
     * @param subString subString
     * @return start
     */
    public int unSlowStart(int index, int step, String subString){
        for(; index >= 0;){
            //System.out.print(index + "|" + step + " ");
            if(step == 0) break;
            if((int)(index - Math.pow(2, step)) < 0){
                step = step - 1;
                continue;
            }

            String target = words.get((int)(index - Math.pow(2, step))).getWord_target();
            if(target.length() < subString.length()){
                step = step - 1;
                continue;
            }
            if(target.length() > subString.length()){
                if(target.substring(0, subString.length()).compareTo(subString) != 0){
                    step = step - 1;
                    continue;
                }
            }
            else{
                if(target.compareTo(subString) != 0){
                    step = step - 1;
                    continue;
                }
            }
            index = (int)(index - Math.pow(2, step));
        }

        step = 1;
        for(; index >= 0;){
            //System.out.print(index + ":" + step + " ");
            if(step == 0) return index;
            if(index - step < 0){
                step = step - 1;
                continue;
            }

            String target = words.get(index - step).getWord_target();
            if(target.length() < subString.length()){
                step = step - 1;
                continue;
            }
            if(target.length() > subString.length()){
                if(target.substring(0, subString.length()).compareTo(subString) != 0){
                    step = step - 1;
                    continue;
                }
            }
            else{
                if(target.compareTo(subString) != 0){
                    step = step - 1;
                    continue;
                }
            }
            index = index - step;
        }

        return index;

//        System.out.print(index + " ");
//        if(step == 0) return index;
//        if(index - step < 0){
//            return unSlowStart(index, step - 1, subString);
//        }
//
//        String target = words.get(index - step).getWord_target();
//        if(target.length() < subString.length()){
//            return unSlowStart(index, step - 1, subString);
//        }
//        if(target.length() > subString.length()){
//            if(target.substring(0, subString.length()).compareTo(subString) != 0){
//                return unSlowStart(index, step - 1, subString);
//            }
//        }
//        else{
//            if(target.compareTo(subString) != 0){
//                return unSlowStart(index, step - 1, subString);
//            }
//        }
//
//        return unSlowStart(index - step, step, subString);
    }

    public int biSearchSubstring(String subStg){
        int low = 0;
        int hight = words.size() - 1;

        while (low <= hight){
            int mid = low + Math.round((hight - low) / 2);
            String target = words.get(mid).getWord_target();
            //System.out.println("Searcher, Mid : " + mid + " Target : " + target);

            if(target.length() < subStg.length()){
                String sub = subStg.substring(0, target.length());
                //System.out.println("Sub : " + sub + " Target : " + target);
                if(sub.compareTo(target) > 0){
                    low = mid + 1;
                }
                else if(sub.compareTo(target) < 0){
                    hight = mid - 1;
                }
                else {
                    low = mid + 1;
                }
            }
            else if (target.length() > subStg.length()){
                String subTarget = target.substring(0, subStg.length());

                if(subStg.compareTo(subTarget) > 0){
                    low = mid + 1;
                }
                else if(subStg.compareTo(subTarget) < 0){
                    hight = mid - 1;
                }
                else return mid;
            }
            else{
                if(subStg.compareTo(target) > 0){
                    low = mid + 1;
                }
                else if(subStg.compareTo(target) < 0){
                    hight = mid - 1;
                }
                else return mid;
            }

        }

        return -1;
    }


    public void dictionaryExportToFile(){
        try {
            FileWriter file = new FileWriter("src/CommandLine/dictionaries.txt");
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

    public void dictionaryExportToFile(FileWriter file){
        System.out.println("-dictionaryExportToFile ");
        try {

            for(Word newWord : words){
                file.write("@" + newWord.getWord_target());
                if(newWord.getWord_explain().startsWith("/")){
                    file.write(" " + newWord.getWord_explain());
                }
                else{
                    file.write(" /null/" + "\n" + newWord.getWord_explain());
                }
            }
            file.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public ArrayList<Word> getWords(){
        return words;
    }


    public static void main(String[] strings) throws FileNotFoundException {

    }
}
