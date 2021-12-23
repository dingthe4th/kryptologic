package Kryptologic.MainGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TEST extends Application {

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Stage mainWindow = primaryStage;
        GridPane root = new GridPane();


        GridPane hehe = new GridPane();
        int count = 0;

        Label a = new Label("XD");
        Label b = new Label("DX");
        hehe.add(a,0,0);
        hehe.add(b,1,0);
        int i=0, j=0;
        Label xd = (Label) (hehe.getChildren().get(i*5+j));

        System.out.print(xd.getText());

        mainWindow.setTitle("Test");
        Scene mainScene = new Scene(root,1280,800);
        mainWindow.setScene(mainScene);
        mainWindow.initStyle(StageStyle.UNDECORATED);
        mainWindow.show();
    }
}
