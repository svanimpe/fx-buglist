package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/*
 * Acts as a controller for MainPanel.fxml.
 */
public class MainPanelController
{
    /*
     * Nodes with an fx:id declared in the FXML file can be injected into the controller. This is
     * done by declaring an attribute of the correct type and annotating it with @FXML (or giving it
     * public visibility).
     */

    @FXML
    private TextField id;

    @FXML
    private TextField submitter;
    
    @FXML
    private TextArea description;
    
    @FXML
    private Button previousBtn;
    
    @FXML
    private Button nextBtn;

    private BugList model;
    
    /*
     * Sets the model and binds the view to the model.
     */
    public void attachModel(BugList model)
    {
        this.model = model;
        
        // Here the Fluent API (using chained method calls) is used to create bindings.
        
        // This binding will automatically disable the previousBtn when the selectedIndexProperty
        // of the model is equal to zero, and enable it when it's not.
        previousBtn.disableProperty().bind(model.selectedIndexProperty().isEqualTo(0));
        
        // This binding will automatically disable the nextBtn when the selectedIndexProperty
        // of the model is equal to the size of bugs list minus one, and enable it when it's not.
        nextBtn.disableProperty().bind(model.selectedIndexProperty().isEqualTo(model.sizeProperty().subtract(1)));
        
        // We attach a change listener to the selectedBugProperty of the model so we can rebind the
        // text fields whenever a new bug has to be shown.
        model.selectedBugProperty().addListener((selectedBugProperty, previouslySelectedBug, newlySelectedBug) ->
        {
            // We first unbind the bidirectionally bound fields...
            submitter.textProperty().unbindBidirectional(previouslySelectedBug.submitterProperty());
            description.textProperty().unbindBidirectional(previouslySelectedBug.descriptionProperty());

            // ...then rebind all of them.
            setBugBindings(newlySelectedBug);
        });
        
        setBugBindings(model.getSelectedBug());
    }
    
    private void setBugBindings(Bug selectedBug)
    {
        // The text in the id field will always be equal to the idProperty of the selected bug, formatted as a String.
        id.textProperty().bind(selectedBug.idProperty().asString());
        
        // The text in the submitter field will always be equal to the submitterProperty of the selected bug.
        // This goes both ways, so changing the text in the field automatically updates the bug as well!
        submitter.textProperty().bindBidirectional(selectedBug.submitterProperty());
        
        // Same goes for the description field and the descriptionProperty of the selected bug.
        description.textProperty().bindBidirectional(selectedBug.descriptionProperty());
    }
    
    /*
     * The following methods are used as event handlers. Just like attributes, they can be either
     * private and annotated with @FXML or public. They can have either no parameters or a parameter
     * of the correct event type (eg. for an onAction handler, that would be an ActionEvent). Which
     * method is called for which action on which node is specified in the FXML file.
     */
    
    public void onPrevious()
    {
        model.setSelectedIndex(model.getSelectedIndex() - 1);
    }
    
    public void onNext()
    {
        model.setSelectedIndex(model.getSelectedIndex() + 1);
    }
    
    public void onDelete()
    {
        model.deleteSelectedBug();
    }

    public void onNew()
    {
        model.addAndSelectNewBug();
    }
}
