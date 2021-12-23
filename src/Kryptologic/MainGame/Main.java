package Kryptologic.MainGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private Stage mainWindow;
    private Scene mainScene;
    private Parent root;


    /*
    * @TODO DEC 8
    *   B4_ActualGamePlay: ADD function to back to main menu button         // DONE !
    *   DO HIGH SCORE FUNCTION                                              // REMOVED !
    *   SHOW SCORE WHEN GAME OVER : SORTED                                  // DONE !
    *   DESIGN ABOUT WINDOW                                                 // DONE !
    *   sounds <optional>
        list view edit format of actual game state                          // DONE !
    *   IN KRYPTO CHECKER, USER SHOULD BE ALLOWED TO CHOOSE "KRYPTO AI""    // DONE !
    *   as player to update
    *   ALERT WHEN GOING BACK TO MENU                                       // DONE !
     */


    @Override
    public void start(Stage primaryStage) throws Exception{
        mainWindow = primaryStage;
        root = FXMLLoader.load(getClass().getResource("A_DLSU.fxml"));
        mainWindow.setTitle("Kryptologic");
        mainScene = new Scene(root,1280,800);
        mainWindow.setScene(mainScene);
        mainWindow.initStyle(StageStyle.UNDECORATED);
        mainWindow.setFullScreen(false);
        mainWindow.setFullScreenExitHint("");
        mainWindow.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}





