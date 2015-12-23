package POPReceiver;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.mail.Message;
import java.io.IOException;

/**
 * Created by BALALAIKA on 22.12.2015.
 */
public class Main extends Application {

    private POPClient client = null;
    private ObservableList<Message> messages = FXCollections.observableArrayList();

    private Stage primaryStage = null;
    private GridPane rootLayout = null;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("abcdefghijklmnopqrstuvwxyz");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("POPGui.fxml"));
        rootLayout = fxmlLoader.load();

        Scene scene = new Scene(rootLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        client = new POPClient(this);

        Controller controller = fxmlLoader.getController();
        controller.setMain(this);
    }

    public ObservableList<Message> getMessages() {
        return messages;
    }

    public POPClient getClient() {
        return client;
    }

    public static void main(String[] argc) {
        launch(argc);
    }
}
