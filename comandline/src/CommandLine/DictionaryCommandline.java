package CommandLine;

import java.io.FileNotFoundException;
import java.util.ArrayList;


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

    public void showAllWords(ArrayList<Word> word){
        System.out.println("-showAllWords");
        System.out.println("No\t| English\t| Vietnamese");
        for (int i = 0; i < word.size(); i++){
            Word newWord = word.get(i);
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
        showAllWords();
    }

    /**
     * Chua cai tien: duyet full.
     * Cai tien: ung dung binary.
     * @param subString subString
     */
    public ArrayList<Word> dictionarySearcher(String subString){
        System.out.println("-dictionarySearcher: " + subString);
        ArrayList<Word> subList = new ArrayList<Word>();

        try{

            int index = biSearchSubstring(subString);

            if(index == -1) {
                throw new Exception();
            }

            int start = unSlowStart(index, 8, subString);

            for(int i = start; i < words.size(); i++){
                if(words.get(i).getWord_target().indexOf(subString) == 0) {
                    subList.add(words.get(i));
                }
                else break;

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
    }

    public int biSearchSubstring(String subStg){
        int low = 0;
        int hight = words.size() - 1;

        while (low <= hight){
            int mid = low + Math.round((hight - low) / 2);
            String target = words.get(mid).getWord_target();

            if(target.length() < subStg.length()){
                String sub = subStg.substring(0, target.length());
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


    public static void main(String[] strings) throws FileNotFoundException{

    }
}
