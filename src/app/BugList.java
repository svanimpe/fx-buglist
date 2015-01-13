package app;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * The model class for this application.
 */
public class BugList
{
    /*
     * The model holds a list of bugs. Instead of a regular List, we use an ObservableList, which
     * wraps a list with the same listener functionality a JavaFX property has.
     */
    private final ObservableList<Bug> bugs = FXCollections.observableArrayList();

    /*
     * The model will never be empty, so we start by adding a single bug to the list.
     */
    public BugList()
    {
        bugs.add(new Bug());
    }
    
    /*
     * Adds a new bug to the end of the list then selects it.
     */
    public void addAndSelectNewBug()
    {
        Bug newBug = new Bug();
        
        // Give the new bug an id one higher than the current highest id.
        int currentHighestId = bugs.get(bugs.size() - 1).getId();
        newBug.setId(currentHighestId + 1);
        
        // Add it to the list then select it (now the last element in the list).
        bugs.add(newBug);
        setSelectedIndex(bugs.size() - 1);
    }
    
    /*
     * Deletes the currently selected bug. The resulting model will never be empty, so there will
     * always be a bug selected.
     */
    public void deleteSelectedBug()
    {        
        if (bugs.size() == 1) {
            
            // If we're deleting the last remaining bug, we first add a new bug...
            bugs.add(new Bug());
            
            // ...then remove the old one. This way the selection will never be null.
            bugs.remove(0);
            
        } else if (getSelectedIndex() == bugs.size() - 1) {
            
            // If we're deleting the bug in last position, we first select the previous one...
            setSelectedIndex(getSelectedIndex() - 1);
            
            // ...then remove the old one. This way the selection will never be null.
            bugs.remove(getSelectedIndex() + 1);
            
        } else {
            
            // Otherwise we simply delete the currently selected bug.
            // The next bug in line will take its place, so the selected index will still be valid.
            bugs.remove(getSelectedIndex());
        }
    }
    
    /* Selected index */
    
    private final IntegerProperty selectedIndex = new SimpleIntegerProperty(0);

    public int getSelectedIndex()
    {
        return selectedIndex.get();
    }

    public void setSelectedIndex(int value)
    {
        selectedIndex.set(value);
    }

    public IntegerProperty selectedIndexProperty()
    {
        return selectedIndex;
    }
    
    /*
     * Selected bug. This property is somewhat special as its underlying value can change with the
     * selection. A binding is set up using the Bindings class so that the selected bug will always
     * be equal to the bug at the selected index. A binding can be seen as a dynamic expression
     * whose value changes automatically as soon as one of the properties used in the expression
     * changes its value. Here the selected bug property will automatically update if either the
     * bugs list or the selected index property are modified. Because bindings are expressions, they
     * are read-only, so we don't supply a setter.
     */
    
    private final ObjectBinding<Bug> selectedBug = Bindings.valueAt(bugs, selectedIndex);
    
    public Bug getSelectedBug()
    {
        return selectedBug.get();
    }
    
    public ObjectBinding<Bug> selectedBugProperty()
    {
        return selectedBug;
    }
    
    /*
     * Size property. Here the same binding technique is used to keep the size property
     * automatically equal to the size of the bugs list.
     */
    
    private final IntegerBinding size = Bindings.size(bugs);
    
    public int getSize()
    {
        return size.get();
    }
    
    public IntegerBinding sizeProperty()
    {
        return size;
    }
    
    /*
     * Note: here we used the static utility methods in the Bindings class to create bindings. It's
     * often easier to create bindings using the Fluent API, which is used in the controller class.
     * It's also possible to create custom bindings by subclassing one of the Binding classes, but
     * we don't do that in this example. See the JavaFX Tutorial for more information:
     * http://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm
     */
}
