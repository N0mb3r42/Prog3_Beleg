package businessLogic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public final class SlowModel {
    public static long time=5000;
    private StringProperty textProperty=new SimpleStringProperty();
    private SlowModel(){ this.setText("default"); }
    public StringProperty textProperty(){return this.textProperty;}
    public String getText(){
        return this.textProperty.get();
    }
    public void setText(String value){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {}
        this.textProperty.set("<"+value+">");
    }
    private static final SlowModel instance = new SlowModel();
    public static SlowModel getInstance() { return instance; }
}
