package event;

import javafx.event.Event;
import javafx.event.EventType;

public class OverlayEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1307931534185790313L;

	public enum Fade {IN, OUT};
	
	public static final EventType<OverlayEvent> ALL = new EventType<>("ALL");
	
	public final Fade fade;
	
	public OverlayEvent(Fade fade) {
		super(ALL);
		this.fade = fade;
	}

}
