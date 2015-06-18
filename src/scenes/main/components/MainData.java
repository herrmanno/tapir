package scenes.main.components;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.request.Request;

import com.github.herrmanno.fx.components.Helper;

public class MainData extends HBox implements Initializable {

	@FXML TextField text_location;	
	@FXML ComboBox<String> combo_method;
	@FXML Button btn_send;
	
	public final ObjectProperty<EventHandler<ActionEvent>> onAction = new SimpleObjectProperty<>();
	public ObjectProperty<EventHandler<ActionEvent>> onActionProperty() {return onAction;}
	public EventHandler<ActionEvent> getOnAction() {return onAction.get();}
	public void setOnAction(EventHandler<ActionEvent> a) {onAction.set(a);}
	
	public MainData() {
		Helper.load(this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		combo_method.getItems().addAll("GET", "POST");
		combo_method.setValue("GET");
		
		btn_send.defaultButtonProperty().bind(btn_send.focusedProperty());
		
		btn_send.setOnAction(this::action);
		text_location.setOnAction(this::action);
	}
	
	public void bind(Request r) {
		combo_method.valueProperty().unbind();
		text_location.textProperty().unbind();
		btn_send.disableProperty().unbind();
		
		
		combo_method.valueProperty().bindBidirectional(r.methodProperty());
		text_location.textProperty().bindBidirectional(r.locationProperty());
		
		btn_send.disableProperty().bind(r.location.isEmpty());
	}
	
	protected void action(ActionEvent e) {
		if(text_location.textProperty().isEmpty().get())
			return;
		else
			onAction.get().handle(e);
	}
}
