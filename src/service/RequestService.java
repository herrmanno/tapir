package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Function;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import model.request.Request;
import event.OverlayEvent;
import event.OverlayEvent.Fade;

public class RequestService {

	public final ListProperty<Request> requests = new SimpleListProperty<>(FXCollections.observableList(new LinkedList<Request>()));
	public final ObjectProperty<Request> request = new SimpleObjectProperty<>(new Request());
	public final StringProperty response = new SimpleStringProperty();

	//------- Singleton
	private static RequestService instance;
	private RequestService() {}
	public static RequestService getInstance() {
		if(instance == null)
			instance = new RequestService();
		return instance;
	}


	public void doRequest(Request r) throws IOException {
		
		URL url = new URL(r.getUrl());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(2000);
		
		con.setRequestMethod(r.getMethod());
		
		//con.setRequestProperty("User-Agent", USER_AGENT);
		
		con.connect();
		
		String response = "";
		try {
			StringBuffer sb = new StringBuffer();
			try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					sb.append(inputLine);
				}
			}
			response = sb.toString();
		} catch(Exception e) {}
		
		if(response.matches(".*<base\\s*href=\".*")) {
			response = response.replaceFirst("<base\\s*href=\"\\s+\"", "<base href=\"" + url.toString() + "\"");
		} else if(response.matches(".*<head>.*")) {
			response = response.replaceFirst("<head>", "<head><base href='" + url.toString() + "'>");
		}
		
		Request copy = new Request(r);
		if(this.requests.contains(copy))
			this.requests.remove(copy);
		this.requests.add(0, copy);
		
		this.response.set(response);
		this.request.set(new Request());
		
	}
	
	public void doRequest(Request r, Consumer<Runnable> exe) throws IOException {
		
		URL url = new URL(r.getUrl());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(2000);
		
		con.setRequestMethod(r.getMethod());
		
		//con.setRequestProperty("User-Agent", USER_AGENT);
		
		con.connect();
		
		/*
		 * Execute on Application thread for Bindings
		 */
		exe.accept(() -> {
			
			String response = "";
			try {
				StringBuffer sb = new StringBuffer();
				try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						sb.append(inputLine);
					}
				}
				response = sb.toString();
			} catch(Exception e) {}
			
			if(response.matches(".*<base\\s*href=\".*")) {
				response = response.replaceFirst("<base\\s*href=\"\\s+\"", "<base href=\"" + url.toString() + "\"");
			} else if(response.matches(".*<head>.*")) {
				response = response.replaceFirst("<head>", "<head><base href='" + url.toString() + "'>");
			}
		
			Request copy = new Request(r);
			this.requests.remove(copy);
			this.requests.add(0, copy);
			
			this.response.set(response);
			this.request.set(new Request());
		});
		
	}
	
	

	
	
}
