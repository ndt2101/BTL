package Graphics.Controller;

import CommandLine.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.scene.Node;

import CommandLine.Base.*;

import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class searchController extends DictionaryManagement implements Initializable{

    @FXML
    public static Stage primaryStage = null;

    @FXML
    public TextField inputText;

    @FXML
    public ListView<Word> listView;

    @FXML
    public TextField targetArea;

    @FXML
    public TextArea explainArea;

    @FXML
    public Button submitButton;

    @FXML
    public Button editWordButton;

    @FXML
    public Button removeWordButton;

    @FXML
    public Button favoriteWordButton;

    @FXML
    public Button voiceButton;

    @FXML
    public AnchorPane rightAnchorPane;

    public static Voice[] voices;

    protected ArrayList<Word> change;


    @Override
    public void initialize(URL location, ResourceBundle resources){
        System.out.println("initialize");

        rightAnchorPane.setVisible(false);
        inputText.setPromptText("Enter something...");
        targetArea.setEditable(false);
        explainArea.setEditable(false);
//        keyboardHandle();

        inputText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.isEmpty()){
                    rightAnchorPane.setVisible(false);

                    setListViewRecent();
                }
                else{
                    handleSearch(newValue);
                }

            }
        });

        inputText.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                targetArea.setText("");
                explainArea.setText("");

                if(keyEvent.getCode() == KeyCode.ENTER){
                    String target = inputText.getText();
                    if(Main.dictionary.findTarget(target) != -1){
                        String meaning = Main.dictionary.dictionaryLookup(inputText.getText());
                        addRecent(new Word(target, meaning));
                        meaning += "\n" + "Google translate: "+ TranslatorAPI.translate(target);
                        targetArea.setText(target);
                        explainArea.setText(meaning);
                    }
                    else{
                        targetArea.setText(target);
                        explainArea.setText("Not found");
                    }
                }

            }
        });

        setListViewRecent();
    }

    public void keyboardHandle(){
        inputText.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            String target = inputText.getText();

            @Override
            public void handle(KeyEvent key)
            {
                targetArea.setText("");
                explainArea.setText("");

                if(key.getCode() == KeyCode.ENTER) {
                    String target = inputText.getText();
                    if(Main.dictionary.findTarget(target) != -1){
                        String meaning = Main.dictionary.dictionaryLookup(inputText.getText());
                        meaning += "\n" + "Google translate: "+ TranslatorAPI.translate(target);
                        targetArea.setText(target);
                        explainArea.setText(meaning);
                    }
                    else{
                        targetArea.setText(target);
                        explainArea.setText("Not found");
                    }
                }
                else if(key.getCode() == KeyCode.BACK_SPACE || key.getCode() == KeyCode.DELETE){

                    if(!target.isEmpty()) {

                        target = target.substring(0, target.length() - 1);
                        handleSearch(target);

                    }
                    else {
                        rightAnchorPane.setVisible(false);
                    }
                }
                else {
                    String code = key.getText();
                    target += code;


                    handleSearch(target);

                }

            }

        });
    }

    public void handleSearch (ActionEvent event) {
        rightAnchorPane.setVisible(true);
        String target = inputText.getText();
        ArrayList<Word> change = Main.dictionary.dictionarySearcher(target);

        setListView(change);
    }

    public void handleSearch (String target){
        rightAnchorPane.setVisible(true);
        ArrayList<Word> change = Main.dictionary.dictionarySearcher(target);

        setListView(change);
    }

    public void setListView(ArrayList<Word> change){

        ObservableList<Word> word = FXCollections.observableArrayList(change);
        listView.setItems(word);

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rightAnchorPane.setVisible(true);

                Word selected = listView.getSelectionModel().getSelectedItem();

                targetArea.setText(selected.getWord_target());
                inputText.setText(selected.getWord_target());
                String target = selected.getWord_target();
                String meaning = "Google translate: " + TranslatorAPI.translate(target);
                explainArea.setText(selected.getWord_explain() + "\n" + meaning);
                addRecent(selected);
            }
        });
    }

    public void setListViewRecent(){
        ArrayList<Word> change = Main.recent.getWords();
        ObservableList<Word> word = FXCollections.observableArrayList(change);
        listView.setItems(word);

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rightAnchorPane.setVisible(true);

                Word selected = listView.getSelectionModel().getSelectedItem();

                targetArea.setText(selected.getWord_target());
                String target = selected.getWord_target();
                String meaning = "Google translate: " + TranslatorAPI.translate(target);
                explainArea.setText(selected.getWord_explain() + "\n" + meaning);
                addRecent(selected);
            }
        });
    }

    public void addRecent(Word word){
        System.out.println("-addRecent");
        while(Main.recent.getSize() > 30){
            Main.recent.removeWord(Main.recent.getSize() - 1);
        }
        removeRecent(word);
        Main.recent.addWord(0, word);
    }
    public void removeRecent(Word word){
        for(int i = 0; i < Main.recent.getSize(); i++){
            if(word.getWord_target().compareTo(Main.recent.getWord(i).getWord_target()) == 0){
                Main.recent.removeWord(i);
            }
        }
    }

    public void voice(String text){
        VoiceManager vm;

        vm = VoiceManager.getInstance();

        voices = vm.getVoices();

        Voice voice = vm.getVoice("kevin16");

        voice.allocate();

        voice.speak(text);

        voice.deallocate();
    }

    public void voiceButtonHandle(ActionEvent event) {
        voice(targetArea.getText());
    }


    public void editWordButtonHandle(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../FXML/editPane.fxml"));
        Parent root = loader.load();
        editController controller = loader.getController();
        String explain = explainArea.getText();
        if(explain.indexOf("Google translate") != -1){
            explain = explain.substring(0, explain.indexOf("Google translate"));
            explain = explain.trim() + "\n";
        }
        else{
            explain = explain.trim() + "\n";
        }
        Word selected = new Word(targetArea.getText(), explain);
        controller.setEditContend(selected);
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    public void removeWordButtonHandle(ActionEvent event){
        String explain = explainArea.getText();
        if(explain.indexOf("Google translate") != -1){
            explain = explain.substring(0, explain.indexOf("Google translate"));
            explain = explain.trim() + "\n";
        }
        else{
            explain = explain.trim() + "\n";
        }
        Word word = new Word(targetArea.getText(), explain);
        Main.dictionary.removeWord(word.getWord_target());
        targetArea.setText("");
        explainArea.setText("");
        handleSearch(new ActionEvent());
    }

    public void favoriteWordButtonHandle(ActionEvent event){
        String explain = explainArea.getText();
        if(explain.indexOf("Google translate") != -1){
            explain = explain.substring(0, explain.indexOf("Google translate"));
            explain = explain.trim() + "\n";
        }
        else{
            explain = explain.trim() + "\n";
        }
        Word word = new Word(targetArea.getText(), explain);
        Main.favorite.addWord(word.getWord_target(), word.getWord_explain());
    }

    public void searchButtonHandle(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("../FXML/searchPane.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addButtonHandle(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(this.getClass().getResource("../FXML/addPane.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void editButtonHandle(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("../FXML/editPane.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void favoriteButtonHandle(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("../FXML/favoritePane.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void quitButtonHandle(ActionEvent event){
        AlertNotification.AlertExit();
    }

}
