package scenes.main;

import gui.ResponsiveScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class MainScene extends ResponsiveScene {

	public MainScene(Parent root) {
		super(root);
		String cssPath = css.CSS.class.getResource("main.css").toExternalForm();
		getStylesheets().add(cssPath);
		
	}

	public static MainScene create() throws Exception {
		FXMLLoader loader = new FXMLLoader(MainScene.class.getResource("MainScene.fxml"));
		Pane root = (Pane) loader.load();
		
		return new MainScene(root);
	}
	
}
