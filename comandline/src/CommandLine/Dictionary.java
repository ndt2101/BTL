package CommandLine;

import java.io.FileNotFoundException;
import java.util.*;


public class Dictionary extends Word{
    protected ArrayList<Word> words ;

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


    public ArrayList<Word> getWords(){
        return words;
    }


    public static void main(String[] strings) throws FileNotFoundException {

    }
}
