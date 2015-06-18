package scenes.secondary;

import gui.ResponsiveScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class SecondaryScene extends ResponsiveScene {

	public SecondaryScene(Parent root) {
		super(root);
		String cssMain = css.CSS.class.getResource("main.css").toExternalForm();
		getStylesheets().add(cssMain);
		
		String cssFlat = css.CSS.class.getResource("flat.css").toExternalForm();
		getStylesheets().add(cssFlat);
		
	}

	public static SecondaryScene create() throws Exception {
		FXMLLoader loader = new FXMLLoader(SecondaryScene.class.getResource("SecondaryScene.fxml"));
		Pane root = (Pane) loader.load();
		
		return new SecondaryScene(root);
	}
	
}
