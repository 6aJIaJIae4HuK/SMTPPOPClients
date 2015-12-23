package POPReceiver;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebView;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

/**
 * Created by BALALAIKA on 22.12.2015.
 */
public class Controller {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ListView<Message> letterList;

    @FXML
    private WebView letterArea;

    @FXML
    private Button connectButton;

    @FXML
    private void connect() {
        String user = emailField.getText();
        String password = passwordField.getText();
        main.getClient().connectTo(user, password);
    }

    @FXML
    private void initialize() {

    }

    private Main main = null;

    public void setMain(Main main) {
        this.main = main;
        letterList.setItems(main.getMessages());
        connectButton.disableProperty().bind(main.getClient().getIsConnecting());

        letterList.setCellFactory(list -> new ListCell<Message>() {
            @Override
            public void updateItem(Message item, boolean empty) {
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String line = "";
                    try {
                        line += item.getSentDate().toString() + " ";
                        line += item.getFrom()[0].toString() + "; ";
                        line += "subject: " + item.getSubject();
                    }
                    catch (MessagingException e) {
                        line = "Can't get info";
                    }
                    setText(line);
                }
            }
        });

        letterList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Message>() {
            @Override
            public void changed(ObservableValue<? extends Message> observable, Message oldValue, Message newValue) {
                try {
                    letterArea.getEngine().loadContent(getText(newValue));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getText(Part part) throws MessagingException, IOException{
        if (part.isMimeType("text/*")) {
            return (String)part.getContent();
        }
        if (part.isMimeType("multipart/alternative")) {
            Multipart mp = (Multipart)part.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }
        return null;
    }
}
