package apitesterFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scenes.main.MainScene;

public class Main extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scene scene = MainScene.create();
		primaryStage.setScene(scene);
		//primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		new Main().launch(args);
	}

}