package scenes.secondary;

import gui.Bindings;
import gui.RequestListCell;
import gui.headerrow.HeaderListCell;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.StageStyle;
import model.request.Cookie;
import model.request.Formdata;
import model.request.Header;
import model.request.Request;
import model.response.Response;
import scenes.main.components.MainData;
import service.RequestService;
import service.SceneService;
import stores.RequestStore;

import com.github.herrmanno.fx.components.badgepane.BadgePane;

public class SecondaryController implements Initializable {

	private RequestService requestService = RequestService.getInstance();
	private RequestStore store = RequestStore.getInstance();

	
	/*
	 * Requests List
	 */
	@FXML ListView<Request> list_requests;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupControls();
		
		bindRequestList(store.getRequests());
	}


	private void setupControls() {
		list_requests.setCellFactory((listview) -> new RequestListCell(list_requests));
	}


	private void bindRequestList(ObservableList<Request> requestList) {
		list_requests.getItems().clear();
		list_requests.setItems(requestList);
		list_requests.scrollTo(0);
	}


	public void doRequest() {
		doRequest(store.getRequest());
	}

	
	public void doRequest(Request r) {
		try {
			requestService.start();
		} catch(Exception e) {
			//TODO Custom Exception Bar
			//pane_badge.showBadge(e.getMessage());
		}
			
	}

}