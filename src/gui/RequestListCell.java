package gui;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import model.request.Request;
import resources.Resources;
import service.RequestService;
import service.SceneService;

public class RequestListCell extends ListCell<Request> {

	private static RequestService requestService = RequestService.getInstance();
	private static SceneService sceneService = SceneService.getInstance();
	
	private static final String cls_bb = "border-bottom";
	
	private final ListView listView;
	
	private HBox grid = new HBox() {{setAlignment(Pos.CENTER); setSpacing(5);}};
	private Label lbl_method = new Label() {{
		setId("MethodLabel");
	}};
	private Label lbl_location = new Label() {{
		
	}};
	private Pane spacer = new Pane();
	private Button btn_req = new Button(null, new ImageView(new Image(Resources.class.getResourceAsStream("ic_label_outline_black_24dp.png"))));
	
	
	public RequestListCell(ListView list) {
		this.listView = list;
		
		this.lbl_location.setTextOverrun(OverrunStyle.ELLIPSIS);
		/*
		InputStream icn_res = Resources.class.getResourceAsStream("ic_label_outline_black_24dp.png");
		ImageView imageView = new ImageView(new Image(icn_res));
		btn_req.setGraphic(imageView);
		*/
		
		/*
		grid.setHgap(10);
        grid.setVgap(4);
        grid.setPadding(new Insets(10, 10, 10, 10));
		
		grid.add(lbl_method, 0, 0);
		grid.add(lbl_location, 1, 0);
		grid.add(btn_req, 2, 0);
		*/
		
		grid.getChildren().addAll(lbl_method, lbl_location, spacer, btn_req);
		HBox.setHgrow(spacer, Priority.ALWAYS);

		prefWidthProperty().bind(list.widthProperty());
	}
	
	@Override
	protected void updateItem(Request r, boolean empty) {
		super.updateItem(r, empty);
		
		 if (empty) {
			 clear();
		 } else {
			 fill(r);
		 }
	}

	private void fill(Request r) {
		lbl_method.setText(r.getMethod());
		lbl_method.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
		lbl_method.getStyleClass().removeAll(getAllCssMethodClasses());
		lbl_method.getStyleClass().add(getCssClassForMethod(r.getMethod()));
		
		lbl_location.setText(r.getUrl());
		btn_req.setVisible(true);
		
		btn_req.setOnAction(event -> 
			sceneService.mainController.doRequest(r)
		);
		
		
		this.getStyleClass().add(cls_bb);
		setGraphic(grid);
	}

	private void clear() {
		lbl_method.setText(null);
		lbl_method.getStyleClass().clear();
		lbl_location.setText(null);
		btn_req.setVisible(false);
		
		this.getStyleClass().remove(cls_bb);
	}

	private static Collection<String> getAllCssMethodClasses() {
		return new ArrayList<String>() {{
			add("green");
			add("red");
		}};
	}
	
	private static String getCssClassForMethod(String method) {
		switch (method.toLowerCase()) {
			case "get": return "green";
			case "post": return "red";
			default: return null;
		}
	}
}
