package Graphics.Controller;

import CommandLine.Base.*;
import CommandLine.Main;
import CommandLine.TranslatorAPI;
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
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class favoriteController implements Initializable{
    @FXML
    public static Stage primaryStage = null;

    @FXML
    public ListView<Word> listView;

    @FXML
    public TextField targetArea;

    @FXML
    public TextArea explainArea;

    @FXML
    public TextField inputText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputText.setPromptText("Enter something...");
        targetArea.setPromptText("Nothing to show...");
        explainArea.setPromptText("Nothing to show...");
        targetArea.setEditable(false);
        explainArea.setEditable(false);

        inputText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                submitButtonHandle(newValue);
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
                        meaning += "\n" + "Google translate: " + TranslatorAPI.translate(target);
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

        ObservableList<Word> word = FXCollections.observableArrayList(Main.favorite.getWords());

        listView.setItems(word);

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Word selected = listView.getSelectionModel().getSelectedItem();
                targetArea.setText(selected.getWord_target());
                String meaning = "Google translate: " + TranslatorAPI.translate(targetArea.getText());

                explainArea.setText(selected.getWord_explain() + "\n" + meaning);
            }
        });
    }

    public void submitButtonHandle(String target){

        ArrayList<Word> change = Main.favorite.dictionarySearcher(target);
        ObservableList<Word> word = FXCollections.observableArrayList(change);
        listView.setItems(word);

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Word selected = listView.getSelectionModel().getSelectedItem();
                targetArea.setText(selected.getWord_target());
                String meaning = "Google translate: " + TranslatorAPI.translate(target);

                explainArea.setText(selected.getWord_explain() + "\n" + meaning);
            }
        });
    }

    public void submitButtonHandle(ActionEvent event){
        String target = inputText.getText();
        ArrayList<Word> change = Main.favorite.dictionarySearcher(target);
        ObservableList<Word> word = FXCollections.observableArrayList(change);
        listView.setItems(word);

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Word selected = listView.getSelectionModel().getSelectedItem();
                targetArea.setText(selected.getWord_target());
                String meaning = "Google translate: " + TranslatorAPI.translate(target);

                explainArea.setText(selected.getWord_explain() + "\n" + meaning);
            }
        });
    }

    public void voiceButtonHandle(ActionEvent event){
        voice(targetArea.getText());
    }

    public void voice(String text){
        VoiceManager vm;

        vm = VoiceManager.getInstance();

        Voice[] voices = vm.getVoices();

        Voice voice = vm.getVoice("kevin16");

        voice.allocate();

        voice.speak(text);

        voice.deallocate();
    }

    public void dislikeButtonHandle(ActionEvent event){
        Word selected = listView.getSelectionModel().getSelectedItem();
        Main.favorite.removeWord(selected.getWord_target());
        targetArea.setText("");
        explainArea.setText("");
        submitButtonHandle(new ActionEvent());
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
