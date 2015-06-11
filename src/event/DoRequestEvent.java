package event;

import model.request.Request;
import javafx.event.Event;
import javafx.event.EventType;

public class DoRequestEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1307931534185790313L;

	
	public static final EventType<DoRequestEvent> DO_REQUEST = new EventType<>("DO_REQUEST");


	public final Request request;
	
	
	public DoRequestEvent(Request r) {
		super(DO_REQUEST);
		this.request = r;
	}

}
