package gui;

import java.io.InputStream;
import java.util.Collection;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import resources.Resources;


public class DeleteCell<T> extends TableCell<T, String> {

	private Button button = new Button();
	
	public DeleteCell() {
		InputStream stream = Resources.class.getResourceAsStream("ic_label_outline_black_24dp.png");
		button.setGraphic(new ImageView(new Image(stream)));
		button.setOnAction(event -> {
			T current = (T) getTableView().getItems().get(getIndex());
	    	getTableView().getItems().remove(current);
		});
	} 
	 
	@Override protected void updateItem(String item, boolean empty) {
	      super.updateItem(item, empty);
	      if (!empty) {
	        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	        setGraphic(button);
	      } else {
	        setGraphic(null);
	      }
	}
	
}
