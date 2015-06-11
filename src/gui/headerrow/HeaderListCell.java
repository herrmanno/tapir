package gui.headerrow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.request.Header;
import resources.Resources;

public class HeaderListCell extends ListCell<Header> {
	
	private HeaderListCellComponent component = new HeaderListCellComponent();
	private ListView<?> listview = null;
	
	public HeaderListCell(ListView<?> listview) {
		this.listview  = listview;
	}

	@Override
	protected void updateItem(Header item, boolean empty) {
		super.updateItem(item, empty);
		
		component.setData(item);
		setGraphic(component);
	}

	public class HeaderListCellComponent extends HBox implements Initializable {
	
		@FXML TextField text1;
		@FXML TextField text2;
		@FXML Button button;
		private Header data;
		
		public HeaderListCellComponent() {
			 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TableRow.fxml"));
	         fxmlLoader.setRoot(this);
	         fxmlLoader.setController(this);
	
	         try {
	             fxmlLoader.load();
	         } catch (IOException exception) {
	             throw new RuntimeException(exception);
	         }
		}
		
		public void setData(Header h) {
			this.data = h;
			if(h != null) {
				text1.setText(h.name.get());
				text1.setEditable(true);
				text1.focusedProperty().addListener((observable, oldV, newV) -> {
					if(!newV) h.name.set(text1.getText());
				});
				h.value.bindBidirectional(text2.textProperty());
				
				button.setOnAction(event -> HeaderListCell.this.listview.getItems().remove(this.data));
			} else {
				text1.textProperty().unbind();
				text2.textProperty().unbind();
			}
			
		}

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			button.setGraphic(new ImageView(new Image(Resources.class.getResourceAsStream("ic_label_outline_black_24dp.png"))));
		}
		
	}

}
