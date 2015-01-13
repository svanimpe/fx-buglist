package app;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * This is a simple domain class built using JavaFX properties.
 * Such a property consists of the following parts:
 * - A backing attribute of a property type. This is always an object (not just
 *   a primitive) and has its own getter, setter, and listeners (observers)
 * - An old-school getter to get the underlying value of the property.
 * - An old-school setter to set the underlying value of the property.
 * - A property getter to get the property itself.
 * Obviously, the setter can be left out for read-only properties.
 * And yes, JavaFX properties have way too much boilerplate code ;)
 */
public class Bug
{
    /* Id */
    
    private final IntegerProperty id = new SimpleIntegerProperty(1);

    public int getId()
    {
        return id.get();
    }

    public void setId(int value)
    {
        id.set(value);
    }

    public IntegerProperty idProperty()
    {
        return id;
    }
    
    /* Submitter */
    
    private final StringProperty submitter = new SimpleStringProperty("Unknown");

    public String getSubmitter()
    {
        return submitter.get();
    }

    public void setSubmitter(String value)
    {
        submitter.set(value);
    }

    public StringProperty submitterProperty()
    {
        return submitter;
    }
    
    /* Description */
    
    private final StringProperty description = new SimpleStringProperty("");

    public String getDescription()
    {
        return description.get();
    }

    public void setDescription(String value)
    {
        description.set(value);
    }

    public StringProperty descriptionProperty()
    {
        return description;
    }
}
