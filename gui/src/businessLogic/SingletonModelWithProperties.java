package businessLogic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class SingletonModelWithProperties {
    private StringProperty textProperty=new SimpleStringProperty();
    private IntegerProperty lengthProperty=new SimpleIntegerProperty();
    private SingletonModelWithProperties(){
        this.textProperty.addListener((observable, oldValue, newValue) -> this.lengthProperty.set(newValue.length()));
        this.textProperty.set("default");
    }
    public StringProperty textProperty(){return this.textProperty;}
    public String getText(){
        System.out.println("getText called ("+this.textProperty.get()+")");
        return this.textProperty.get();
    }
    public void setText(String value){
        this.textProperty.set(value);
        this.lengthProperty.setValue(this.textProperty.get().length());
    }
    public IntegerProperty lengthProperty(){return this.lengthProperty;}
    public Integer getLength(){
        return this.lengthProperty.get();
    }

    private static final SingletonModelWithProperties instance = new SingletonModelWithProperties();
    public static SingletonModelWithProperties getInstance() { return instance; }
}
