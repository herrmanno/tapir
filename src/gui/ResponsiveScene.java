package gui;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ResponsiveScene extends Scene {

	public static class CSS {
		public static final String xs = "width-xs";
		public static final String s = "width-s";
		public static final String m = "width-m";
		public static final String l = "width-l";
		public static final String[] classes = {xs, s, m, l};
	}
	
	
	public ResponsiveScene(Parent root) {
		super(root);
		widthProperty().addListener((value, oldValue, newValue) -> widthChanged(value, oldValue, newValue));
		heightProperty().addListener((value, oldValue, newValue) -> heightChanged(value, oldValue, newValue));
	}

	protected Object heightChanged(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
		return null;
	}

	protected Object widthChanged(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
		ObservableList<String> style = this.getRoot().getStyleClass();
		int width = newValue.intValue();
		
		for(String cls : CSS.classes)
			style.remove(cls);
		
		if(width <= 540)
			style.add(CSS.xs);
		else if(width <= 960)
			style.add(CSS.s);
		else if(width <= 1200)
			style.add(CSS.m);
		else  if(width > 1200)
			style.add(CSS.l);
		
		return newValue;
	}

}
