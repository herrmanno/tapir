package stores;

import java.util.LinkedList;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.request.Request;
import model.response.Response;

public class RequestStore {

	private static class HOLDER {
		public static RequestStore INSTANCE = new RequestStore();
	}
	
	private RequestStore() {};
	
	public static RequestStore getInstance() {
		return HOLDER.INSTANCE;
	}
	
	
	private final ListProperty<Request> requests = new SimpleListProperty<>(FXCollections.observableList(new LinkedList<Request>()));
	public ListProperty<Request> requestsProperty() {return requests;}
	public ObservableList<Request> getRequests() {return requests.get();}
	public void setRequests(ObservableList<Request> a) {requests.set(a);}
	
	
	private final ObjectProperty<Request> request = new SimpleObjectProperty<>(new Request());
	public ObjectProperty<Request> requestProperty() {return request;}
	public Request getRequest() {return request.get();}
	public void setRequest(Request a) {request.set(a);}
	
	private final ObjectProperty<Response> response = new SimpleObjectProperty<>(new Response());
	public ObjectProperty<Response> responseProperty() {return response;}
	public Response getResponse() {return response.get();}
	public void setResponse(Response a) {response.set(a);}
}
