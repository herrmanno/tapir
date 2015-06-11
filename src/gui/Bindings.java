package gui;

import java.util.function.Supplier;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Bindings {

	@SuppressWarnings("unchecked")
	public static <T> void bindTable(TableView<T> tableView, ObservableList<T> model, String... properties) {
		ObservableList<TableColumn<T,?>> columns = tableView.getColumns();
		for(int c = 0; c < columns.size(); c++) {
			TableColumn<T, String> col = (TableColumn<T, String>) columns.get(c);
			
			if(properties.length < c+1)
				continue;
			
			col.setCellValueFactory(new PropertyValueFactory<T, String>(properties[c]));
			col.setCellFactory(value -> new EditingCell<T>());
			//col.setCellFactory(TextFieldTableCell.forTableColumn());
		}
		
		tableView.setOnKeyPressed((KeyEvent event) -> {
			if(event.getCode() == KeyCode.DELETE) {
				ObservableList<T> selectedItems = tableView.getSelectionModel().getSelectedItems();
				ObservableList<T> items = tableView.getItems();
				selectedItems.forEach(it -> items.remove(it));
				System.out.println(items);
			}
		});
		
		model.addListener((Change<? extends T> c) -> {
			while(c.next()) {
				if(!c.wasAdded()) continue;
				int row = model.indexOf(c.getAddedSubList().get(0));
				TableColumn<T, ?> col = tableView.getColumns().get(0);
				tableView.edit(row, col);
			}
		}); 
		
		tableView.setItems(model);
	}
	

	public static <T> void bindAdd_Button(Button btn, ObservableList<T> data, Supplier<T> supplier) {
		btn.setOnAction(event -> { 
			data.add(supplier.get());
		});
	}
}
