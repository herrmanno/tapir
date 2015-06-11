package service;

import scenes.main.MainController;

public class SceneService {

	/*
	 * Singleton
	 */
	private static SceneService instance;
	private SceneService() {}
	public static SceneService getInstance() {
		if(instance == null)
			instance = new SceneService();
		return instance;
	}
	
	public MainController mainController;
	
	
}
