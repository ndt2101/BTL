package CommandLine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Graphics.Controller.*;

import CommandLine.Base.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main extends Application{
    public static DictionaryCommandline dictionary = new DictionaryCommandline();
    public static DictionaryCommandline favorite = new DictionaryCommandline();
    public static DictionaryCommandline recent = new DictionaryCommandline();

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        File inputDictionary = new File("src/CommandLine/Base/Data/dictionaries.txt");
        dictionary.insertFormFilePro(inputDictionary);
        dictionary.sortDictionary();

        File inputFavorite = new File("src/CommandLine/Base/Data/favorites.txt");
        favorite.insertFormFilePro(inputFavorite);
        favorite.sortDictionary();

        File inputRecent = new File("src/CommandLine/Base/Data/recents.txt");
        recent.insertFormFilePro(inputRecent);

        TranslatorAPI.translate("");

        Parent root = FXMLLoader.load(getClass().getResource("../Graphics/FXML/searchPane.fxml"));
        Scene scene = new Scene(root, 520, 400);
        stage.setTitle("Dictionary");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        searchController.primaryStage = stage;
        addController.primaryStage = stage;
        editController.primaryStage = stage;
        favoriteController.primaryStage = stage;
    }

    @Override
    public void stop() throws IOException {
        System.out.println("STOP!!!");

        FileWriter outputDictionary = new FileWriter("src/CommandLine/Base/Data/dictionaries.txt");
        dictionary.dictionaryExportToFile(outputDictionary);
        FileWriter outputFavorite = new FileWriter("src/CommandLine/Base/Data/favorites.txt");
        favorite.dictionaryExportToFile(outputFavorite);
        FileWriter outputRecent = new FileWriter("src/CommandLine/Base/Data/recents.txt");
        recent.dictionaryExportToFile(outputRecent);
    }
}
