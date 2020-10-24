package Graphics.Controller;

import CommandLine.Base.Dictionary;
import CommandLine.Base.DictionaryManagement;
import CommandLine.Base.Word;
import CommandLine.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class editController implements Initializable{
    @FXML
    public static Stage primaryStage = null;

    @FXML
    public TextField oldTargetTextField;

    @FXML
    public TextField newTargetTextField;

    @FXML
    public TextArea newExplainTextField;

    @FXML
    public Label messageLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oldTargetTextField.setPromptText("Enter old target.");
        newTargetTextField.setPromptText("Enter new target.");
        newExplainTextField.setPromptText("Enter new explain.");
    }

    public void setEditContend(Word word){
        oldTargetTextField.setText(word.getWord_target());
        newTargetTextField.setText(word.getWord_target());
        newExplainTextField.setText(word.getWord_explain());

    }

    public void submitButtonHandle(ActionEvent event){
        String oldTarget = oldTargetTextField.getText().trim();
        String newTarget = newTargetTextField.getText().trim();
        String newExplain = newExplainTextField.getText().trim() + "\n";

        if(!isEmpty()){
            if(oldTarget.compareTo(newTarget) == 0){
                Main.dictionary.editWord(oldTarget, newExplain);
            }
            else{
                Main.dictionary.editWord(oldTarget, newTarget, newExplain);
            }

            done();
        }
    }

    public boolean isEmpty(){
        if (oldTargetTextField.getText().isEmpty()){
            messageLabel.setText("'Old target' is empty");
            return true;
        }
        else if (newExplainTextField.getText().isEmpty()){
            messageLabel.setText("'New explain' is empty");
            return true;
        }
        return false;
    }

    public void done(){
        oldTargetTextField.setText("");
        newTargetTextField.setText("");
        newExplainTextField.setText("");

        messageLabel.setText("Edit word complete");
    }

    public void fillButtonHandle(){
        String oldTarget = oldTargetTextField.getText();

        if(Main.dictionary.findTarget(oldTarget) == -1) {
            messageLabel.setText("Word is not exist");
            return;
        }
        else{
            String oldExplain = Main.dictionary.dictionaryLookup(oldTarget);
            newExplainTextField.setText(oldExplain);
            newTargetTextField.setText(oldTarget);
        }
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
