package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/*
 * This class builds the same user interface as described in MainPanel.fxml. Note that I did not
 * separate out the controller code again, because the bindings tend to stay in the view anyway. So
 * when using the hand-coded approach, your MVC style looks more like a Supervising Controller
 * pattern, whereas when using the FXML approach, you end up using more of a Passive View pattern.
 *
 * This class extends BorderPane, so the MainPanel is already a BorderPane.
 */
public class MainPanel extends BorderPane
{
    private final BugList model;

    private Button previousBtn;
    private Button nextBtn;
    
    private TextField id;
    private TextField submitter;
    private TextArea description;
    
    public MainPanel(BugList model)
    {
        this.model = model;
        
        // Adding nodes to a BorderPane is done using the setTop, setBottom, setCenter, setLeft and setRight methods.
        setTop(createHeader());
        setCenter(createGrid());
        setBottom(createToolBar());
        
        // What follows are the same bindings we used in the MainPanelController.
        
        previousBtn.disableProperty().bind(model.selectedIndexProperty().isEqualTo(0));
        nextBtn.disableProperty().bind(model.selectedIndexProperty().isEqualTo(model.sizeProperty().subtract(1)));

        model.selectedBugProperty().addListener((selectedBugProperty, previouslySelectedBug, newlySelectedBug) ->
        {
            submitter.textProperty().unbindBidirectional(previouslySelectedBug.submitterProperty());
            description.textProperty().unbindBidirectional(previouslySelectedBug.descriptionProperty());
            setBugBindings(newlySelectedBug);
        });
        
        setBugBindings(model.getSelectedBug());
    }
    
    private void setBugBindings(Bug selectedBug)
    {
        id.textProperty().bind(selectedBug.idProperty().asString());
        submitter.textProperty().bindBidirectional(selectedBug.submitterProperty());
        description.textProperty().bindBidirectional(selectedBug.descriptionProperty());
    }
    
    private AnchorPane createHeader()
    {
        AnchorPane header = new AnchorPane();
        
        Text text = new Text("Bug Details");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        
        // AnchorPane uses the general approach of laying out components.
        // That is: configuring the layout is done using static methods in the AnchorPane class.
        
        AnchorPane.setTopAnchor(text, 20.0);
        AnchorPane.setLeftAnchor(text, 10.0);
        
        ImageView logo = new ImageView(getClass().getResource("/resources/bug.png").toExternalForm());
        
        AnchorPane.setTopAnchor(logo, 10.0);
        AnchorPane.setBottomAnchor(logo, 10.0);
        AnchorPane.setRightAnchor(logo, 10.0);
        
        // With the general approach you add nodes to a parent using getChildren().add()/addAll().
        header.getChildren().addAll(text, logo);
        
        return header;
    }
    
    private GridPane createGrid()
    {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        
        // A GridPane has methods to add a node in a particular place in the grid.
        // This is simply a shortcut to using the static layout methods.
        
        grid.add(new Label("ID:"), 0, 0);
        grid.add(new Label("Submitter:"), 0, 1);
        grid.add(new Label("Description:"), 0, 2);
        
        id = new TextField();
        id.setDisable(true);
        submitter = new TextField();
        description = new TextArea();
        
        grid.add(id, 1, 0);
        grid.add(submitter, 1, 1);
        grid.add(description, 1, 2);
        
        // Instead of configuring each node individually, rows and columns can also be configured as a whole.
        // This is done using row and column constraints.
        
        ColumnConstraints columnOne = new ColumnConstraints();
        columnOne.setHalignment(HPos.RIGHT);
        
        ColumnConstraints columnTwo = new ColumnConstraints();
        columnTwo.setHgrow(Priority.ALWAYS);
        
        RowConstraints rowOne = new RowConstraints();
        RowConstraints rowTwo = new RowConstraints();
       
        RowConstraints rowThree = new RowConstraints();
        rowThree.setValignment(VPos.TOP);
        rowThree.setVgrow(Priority.ALWAYS);
        
        grid.getColumnConstraints().addAll(columnOne, columnTwo);
        grid.getRowConstraints().addAll(rowOne, rowTwo, rowThree);
        
        return grid;
    }
    
    private ToolBar createToolBar()
    {
        ToolBar toolBar = new ToolBar();
        
        // To add items to a toolbar, use getItems().add() instead of getChildren().add().
        toolBar.getItems().add(new Label("Actions:"));
        
        previousBtn = new Button("Previous");
        previousBtn.setOnAction(this::previous);
        
        nextBtn = new Button("Next");
        nextBtn.setOnAction(this::next);
        
        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(this::delete);
        
        Button newBtn = new Button("New");
        newBtn.setOnAction(this::add);
        
        toolBar.getItems().addAll(previousBtn, nextBtn, deleteBtn, newBtn);
        
        return toolBar;
    }
    
    /*
     * What follows are the same event handlers we used in MainPanelController.
     */
    
    private void previous(ActionEvent event)
    {
        model.setSelectedIndex(model.getSelectedIndex() - 1);
    }

    private void next(ActionEvent event)
    {
        model.setSelectedIndex(model.getSelectedIndex() + 1);
    }

    private void delete(ActionEvent event)
    {
        model.deleteSelectedBug();
    }

    private void add(ActionEvent event)
    {
        model.addAndSelectNewBug();
    }

}
