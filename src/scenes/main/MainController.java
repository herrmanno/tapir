package scenes.main;

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

public class MainController implements Initializable {

	private RequestService requestService = RequestService.getInstance();
	private RequestStore store = RequestStore.getInstance();

	/*
	 * Main controls
	 */
	@FXML MainData mainData;
	
	/*
	 * Extra Controls
	 */
	//@FXML TableView<Header> tbl_header;
	//@FXML TableColumn<Header, String> col_header_name;
	//@FXML TableColumn<Header, String> col_header_value;
	@FXML ListView<Header> list_header;
	@FXML Button btn_add_header;
	@FXML TableView<Cookie> tbl_cookies;
	@FXML Button btn_add_cookie;
	
	@FXML TableView<Formdata> tbl_formdata;
	@FXML Button btn_add_formdata;
	
	@FXML TextArea text_payload;
	
	/*
	 * Result controls
	 */
	@FXML WebView webView;
	@FXML TextArea text_raw;
	
	/*
	 * Requests List
	 */
	@FXML ListView<Request> list_requests;
	
	/*
	 * Loading Screen
	 */
	@FXML public VBox overlay;
	@FXML ImageView img_overlay;
	
	/*
	 * Badge
	 */
//	@FXML Label badge;
	
	@FXML BadgePane pane_badge;
	
	//private Request model = new Request();

	public MainController() {
		SceneService.getInstance().mainController = this;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setupControls();
		
		bindRequest(store.getRequest());
		
		
		bindResponse(store.getResponse());
		
		
		bindRequestList(store.getRequests());
		
		
	}




	private void setupControls() {
		/*
		combo_method.getItems().addAll("GET", "POST");
		combo_method.setValue("GET");
		
		btn_send.defaultButtonProperty().bind(btn_send.focusedProperty());
		*/
	
		list_header.setCellFactory((listview) -> new HeaderListCell(listview));
		
		list_requests.setCellFactory((listview) -> new RequestListCell(list_requests));
		list_requests.getSelectionModel().selectedItemProperty().addListener(event -> {
			Request selectedItem = list_requests.getSelectionModel().getSelectedItem();
			store.setRequest(new Request(selectedItem));
		});
		
	
		
		Alert d = new Alert(AlertType.NONE, "", ButtonType.OK);
		d.initStyle(StageStyle.UTILITY);
		d.getDialogPane().getStylesheets().add(css.CSS.class.getResource("flat.css").toExternalForm());
		pane_badge.getBadge().setOnMouseClicked(event -> {
			d.setContentText(pane_badge.getBadge().getText());
			d.showAndWait();
		});
		
	}




	private  Object bindRequest(Request r) {
		if(r == null)
			return null;
		
		//------- unbind
		mainData.bind(r);
		//combo_method.valueProperty().unbind();
		//text_location.textProperty().unbind();
		tbl_cookies.getItems().clear();
		tbl_formdata.getItems().clear();
		
		
		//combo_method.valueProperty().bindBidirectional(r.getValue().methodProperty());
		//text_location.textProperty().bindBidirectional(r.getValue().locationProperty());
		
		
		Bindings.bindTable(tbl_cookies, r.cookies, "name", "value");
		Bindings.bindAdd_Button(btn_add_cookie, r.cookies, Cookie::new);
		
		Bindings.bindTable(tbl_formdata, r.formData, "name", "value");
		Bindings.bindAdd_Button(btn_add_formdata, r.formData, Formdata::new);
		
		
		//btn_send.disableProperty().bind(r.getValue().location.isEmpty());
		
		store.requestProperty().addListener((obs, oldV, newV) -> bindRequest(obs.getValue()));
		
		return r;
	}




	private Object bindResponse(Response resp) {
		webView.getEngine().loadContent(resp.getBody());
		text_raw.setText(resp.getBody());
		
		store.responseProperty().addListener((obs, oldV, newV) -> bindResponse(obs.getValue()));
		
		return resp;
	}


	private void bindRequestList(ObservableList<Request> requestList) {
		list_requests.getItems().clear();
		
		list_requests.setItems(requestList);
		list_requests.scrollTo(0);
		
		//Wofür war das wohl gedacht?
		//requestService.response.addListener((obs, oldV, newV) -> bindResponse(obs));
	}


	public void doRequest() {
		doRequest(store.getRequest());
	}

	
	public void doRequest(Request r) {
		try {
			requestService.start();
		} catch(Exception e) {
			pane_badge.showBadge(e.getMessage());
		}
			
	}
	/*
	private void showBadge(String text) {
		badge.setText(text);

		TranslateTransition in = new TranslateTransition(Duration.millis(300), badge);
		in.setToY(-20);
		
		TranslateTransition out = new TranslateTransition(Duration.millis(300), badge);
		out.setToY(50);
		out.setDelay(Duration.millis(2000));
		
		
		in.setOnFinished(event -> out.play());
		
		in.play();
	}
	*/
	/*
	public void doRequest(Request r) {
		
		
		FadeTransition ft1 = new FadeTransition(Duration.millis(200), overlay);
	    ft1.setFromValue(0);
	    ft1.setToValue(.5);
	    ft1.play();
	    Executors.newSingleThreadExecutor().execute(new Task<Void>(){
			@Override
			protected Void call() throws Exception {
				try {
					requestService.doRequest(r, Platform::runLater);
					Platform.runLater(() -> {
						FadeTransition fout = new FadeTransition(Duration.millis(200), overlay);
						fout.setFromValue(1);
						fout.setToValue(0);
						fout.play();
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	    
	}
	*/




}