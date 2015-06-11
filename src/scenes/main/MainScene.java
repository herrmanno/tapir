package scenes.main;

import gui.ResponsiveScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class MainScene extends ResponsiveScene {

	public MainScene(Parent root) {
		super(root);
		String cssMain = css.CSS.class.getResource("main.css").toExternalForm();
		getStylesheets().add(cssMain);
		
		String cssFlat = css.CSS.class.getResource("flat.css").toExternalForm();
		getStylesheets().add(cssFlat);
		
	}

	public static MainScene create() throws Exception {
		FXMLLoader loader = new FXMLLoader(MainScene.class.getResource("MainScene.fxml"));
		Pane root = (Pane) loader.load();
		
		return new MainScene(root);
	}
	
}
