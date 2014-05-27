/*
 * MyWebBrowser
 */

package mywebbrowser;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author 
 */
public class MyWebBrowser extends Application {

    public static final String DEFAULT_URL = "http://www.belluzzifioravanti.it";
    
    private void init(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 1024, 768);
        primaryStage.setScene(scene);
        WebView webView = new WebView();
        webView.setPrefSize(1024, 768);
        final WebEngine webEngine = webView.getEngine();
        webEngine.load(DEFAULT_URL);
        
        final TextField locationField = new TextField(DEFAULT_URL);
        webEngine.locationProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                locationField.setText(newValue);
            }
        });
        EventHandler<ActionEvent> goAction = new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                webEngine.load(locationField.getText().startsWith("http://") 
                        ? locationField.getText() 
                        : "http://" + locationField.getText());
            }
        };
        locationField.setOnAction(goAction);
        
        Button goButton = new Button("Go");
        goButton.setDefaultButton(true);
        goButton.setOnAction(goAction);
        
        // Layout logic
        HBox hBox = new HBox(5);
        hBox.getChildren().setAll(locationField, goButton);
        HBox.setHgrow(locationField, Priority.ALWAYS);
        
        VBox vBox = new VBox(5);
        vBox.getChildren().setAll(hBox, webView);
        VBox.setVgrow(webView, Priority.ALWAYS);

        root.getChildren().add(vBox);
    }

    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
  
}
