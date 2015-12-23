package POPReceiver;

import com.sun.mail.pop3.POP3SSLStore;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.mail.*;
import java.util.Properties;

/**
 * Created by BALALAIKA on 22.12.2015.
 */
public class POPClient{
    private Main client = null;
    private BooleanProperty isConnecting = new SimpleBooleanProperty(false);

    public POPClient(Main client) {
        this.client = client;
    }

    public void connectTo(String email, String password) {
        client.getMessages().clear();
        isConnecting.setValue(true);

        (new Thread(new Runnable() {
            @Override
            public void run() {
                Properties props = new Properties();

                props.put("mail.pop3.host", "pop.gmail.com");
                props.put("mail.pop3.port", "995");
                props.put("mail.pop3.starttls.enable", "true");


                try {
                    Session session = Session.getDefaultInstance(props);
                    Store store = session.getStore("pop3s");
                    store.connect("pop.gmail.com", email, password);

                    Folder folder = store.getFolder("INBOX");
                    folder.open(Folder.READ_ONLY);
                    Message messages[] = folder.getMessages();
                    for (Message message : messages) {
                        addMessage(message);
                    }
                }
                catch (NoSuchProviderException e) {
                    e.printStackTrace();
                }
                catch (MessagingException e) {
                    e.printStackTrace();
                }

                isConnecting.setValue(false);
            }
        })).start();
    }

    public BooleanProperty getIsConnecting() {
        return isConnecting;
    }

    private void addMessage(Message message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                client.getMessages().add(message);
            }
        });
    }


}
