package SMTPSender;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage = null;
    private GridPane rootLayout = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("abcdefghijklmnopqrstuvwxyz");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("SMTPGui.fxml"));
        rootLayout = fxmlLoader.load();

        Scene scene = new Scene(rootLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        Controller controller = fxmlLoader.getController();
        controller.setMain(this);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
