package multithreadedclient;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class serverGui extends Application {

    private final BorderPane MainPane = new BorderPane();
    private final GridPane firstPane = new GridPane();
    private final GridPane userMessagePane = new GridPane();
    private final HBox spacingBox = new HBox();
    private TextField tfName = new TextField("");
    private TextField tfHost = new TextField("");
    private TextField tfPort = new TextField();
    private TextField tfMessageToSend = new TextField();
    private TextArea msg_Area = new TextArea();
    private final Label name = new Label("Name:");
    private final Label host = new Label("Host:");
    private final Label port = new Label("Port:");
    private final Button submitButton = new Button("Submit");
    private final Button sendButton = new Button("Send");
    private boolean bFirstWindowClosed = false;

    private String sName;
    private String sHost;
    private int nPort;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void createMainPane() {
        MainPane.setCenter(msg_Area);
        MainPane.setBottom(userMessagePane);
    }

    public void createUserMessagePane() {
        msg_Area.setEditable(false);
        tfMessageToSend.setPrefWidth(500);
        userMessagePane.setPadding(new Insets(10, 10, 10, 10));
        sendButton.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, null, null)));
        spacingBox.setPadding(new Insets(0, 5, 0, 5));
        userMessagePane.add(tfMessageToSend, 0, 0);
        userMessagePane.add(spacingBox, 1, 0);
        userMessagePane.add(sendButton, 2, 0);
    }

    public void createFirstWindow() {
        firstPane.add(name, 0, 0);
        firstPane.add(tfName, 1, 0);
        firstPane.add(host, 0, 1);
        firstPane.add(tfHost, 1, 1);
        firstPane.add(port, 0, 2);
        firstPane.add(tfPort, 1, 2);
        firstPane.add(submitButton, 1, 3);
    }

    public void SubmitButtonAction(Stage stage) {
        submitButton.setOnAction((ActionEvent e) -> {
            try{
            sName = tfName.getText();
            }catch(NullPointerException ec){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please enter a name");
                alert.show();
            }    
            sHost = tfHost.getText();
            try {
                nPort = Integer.parseInt(tfPort.getText());
            }
            catch (NumberFormatException exc) {
                //this will display an error message to the console 
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("NonInteger value entered");
                alert.show();
            }
            if (nPort <= 1024) {
                Alert invalidPortNumber = new Alert(Alert.AlertType.ERROR);
                invalidPortNumber.setContentText("The port number must be greater than 1024. Please try again.");
                invalidPortNumber.show();
                tfPort.setText("");
                
            }
            //stage.close();
            bFirstWindowClosed = true;
        });

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        createFirstWindow();
        Scene firstscene = new Scene(firstPane, 400, 300);
        Stage firststage = new Stage();
        SubmitButtonAction(firststage);
        firststage.setTitle("RaiderPlanner chat information");
        firststage.setScene(firstscene);
        firststage.showAndWait();

//        Scene mainScene = new Scene(MainPane, 600, 500);
//        createUserMessagePane();
//        createMainPane();
//        primaryStage.setTitle("RaiderPlanner Chat");
//        primaryStage.setScene(mainScene);
//        primaryStage.show();
    }

    public void setsName(String sUserName) {
        sName = sUserName;
    }

    public void setsHost(String sUserHost) {
        sHost = sUserHost;
    }

    public void setsPort(int nUserPort) {
        nPort = nUserPort;
    }

    public String getsName() {
        return sName;
    }

    public String getsHost() {
        return sHost;
    }

    public int getsPort() {
        return nPort;
    }

}
