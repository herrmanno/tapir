package apitesterFX;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.annotation.Resources;
import javax.imageio.ImageIO;

import scenes.main.MainScene;
import scenes.secondary.SecondaryScene;

public class Main extends Application {


	private Stage primaryStage;
	private Stage secondaryStage = new Stage(StageStyle.UNDECORATED);

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		Scene scene = MainScene.create();
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
		secondaryStage.setScene(SecondaryScene.create());
		
		addAppToTray();
	}
	
	private void showStage() {
		primaryStage.show();
	}
	
	 private void addAppToTray() {
	        try {
	            java.awt.Toolkit.getDefaultToolkit();
	 
	            if (!java.awt.SystemTray.isSupported()) {
	                System.out.println("No system tray support!");
	                return;
	            }
	 
	            // set up a system tray icon.
	            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
	            URL imageLoc = resources.Resources.class.getResource("ic_label_outline_black_24dp.png");
	            java.awt.Image image = ImageIO.read(imageLoc);
	            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);
	 
	            // if the user double-clicks on the tray icon, show the main app stage.
	            trayIcon.addActionListener(event -> 
	            Platform.runLater(this::showStage));
	 
	            // if the user selects the default menu item (which includes the app name), 
	            // show the main app stage.
	            java.awt.MenuItem openItem = new java.awt.MenuItem("Show Window");
	            openItem.addActionListener(event -> Platform.runLater(this::showStage));
	 
	            // to really exit the application, the user must go to the system tray icon
	            // and select the exit option, this will shutdown JavaFX and remove the
	            // tray icon (removing the tray icon will also shut down AWT).
	            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
	            exitItem.addActionListener(event -> {
	                Platform.exit();
	                tray.remove(trayIcon);
	                System.exit(0);
	            });
	 
	            // setup the popup menu for the application.
	            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
	            popup.add(openItem);
	            popup.addSeparator();
	            popup.add(exitItem);
	            trayIcon.setPopupMenu(popup);
	 
	            tray.add(trayIcon);

	            Platform.setImplicitExit(false);
	        } catch (java.awt.AWTException | IOException e) {
	            System.out.println("Unable to init system tray");
	            e.printStackTrace();
	        }
	    }

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		new Main().launch(args);
	}

}
