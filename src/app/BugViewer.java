package app;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/*
 * This example covers properties and binding, using layout panes as well as using MVC with FXML and
 * Scene Builder to create the user interface. Please read the comments in the classes in the
 * following order:
 * - Bug
 * - BugList
 * - MainPanelController
 * - BugViewer
 * If you prefer hand-coding your user interface, the class MainPanel shows how you can get the same
 * results without FXML or Scene Builder. I also included a sample CSS file to show how CSS can be
 * used with JavaFX.
 */
public class BugViewer extends Application
{
    @Override
    public void start(final Stage stage) throws IOException
    {
        // Create the model.
        BugList model = new BugList();

        // This FXML loader will load the FXML file, create a node graph from it, as well as load the controller.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainPanel.fxml"));
        loader.load();

        // The controller class was mentioned in the FXML file, so the loader will have created one for us.
        // We ask for the created controller so we can attach a model to it.
        MainPanelController controller = loader.getController();
        controller.attachModel(model);

        // The root node in the FXML file was a BorderPane so we ask for it and use it as a root for the scene.
        BorderPane root = loader.getRoot();
        Scene scene = new Scene(root);
        
        stage.setTitle("Bug Viewer");
        stage.setScene(scene);
        
        // The stage will not get smaller than its preferred (initial) size.
        stage.setOnShown(event -> {
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
        });
        stage.show();
    }

    /*
     * If you want to run the hand-coded version, use this start method.
     */
//    @Override
//    public void start(final Stage stage)
//    {
//        BugList model = new BugList();
//        
//        BorderPane root = new MainPanel(model);
//        root.getStylesheets().add(getClass().getResource("/resources/styles.css").toExternalForm());
//        
//        Scene scene = new Scene(root);
//        
//        stage.setTitle("Bug Viewer");
//        stage.setScene(scene);
//        stage.setOnShown(event -> {
//            stage.setMinWidth(stage.getWidth());
//            stage.setMinHeight(stage.getHeight());
//        });
//        stage.show();
//    }
    
    public static void main(String... args)
    {
        Application.launch(BugViewer.class, args);
    }
}
