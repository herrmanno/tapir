package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NameValueModel {
	public final StringProperty name = new SimpleStringProperty();
	public final StringProperty value = new SimpleStringProperty();
	
	@SuppressWarnings("unused")
	public NameValueModel() {
		this(null, null);
	}
	
	public NameValueModel(String name, String value) {
		this.name.set(name);
		this.value.set(value);
	}
	
	public String getName() {return name.get();}
	public void setName(String n) {name.set(n);}
	public StringProperty nameProperty() {return name;};
	
	public String getValue() {return value.get();}
	public void setValue(String n) {value.set(n);}
	public StringProperty valueProperty() {return value;};
	
	@Override
	public String toString() {
		return String.format("%s %s :: %s", getClass().getSimpleName(), name.get(), value.get());
	}
}