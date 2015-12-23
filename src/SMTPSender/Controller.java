package SMTPSender;

import com.sun.xml.internal.org.jvnet.mimepull.MIMEMessage;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Controller {
    @FXML
    private TextField emailField;

    @FXML
    private TextField themeField;

    @FXML
    private TextArea letterArea;

    @FXML
    private Button sendButton;

    @FXML
    private void send() {
        Properties props = System.getProperties();


        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");


        Session session = Session.getDefaultInstance(props, null);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("stalker20210@gmail.com"));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailField.getText()));
            message.setSubject(themeField.getText());

            message.setText(letterArea.getText());

            Transport transport = session.getTransport("smtp");

            transport.connect("smtp.gmail.com", 25, "stalker20210@gmail.com", "Atyllen20210");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Main main = null;
    public void setMain(Main main) {
        this.main = main;
    }
}
