package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import stores.RequestStore;
import model.request.Request;
import model.response.Response;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class RequestService extends Service<Void> {

	/*
	 * Singleton Pattern
	 */
	private static class HOLDER {
		private static RequestService INSTANCE = new RequestService();
	}
	public static RequestService getInstance() {
		return HOLDER.INSTANCE;
	}
	private RequestService() {
		setExecutor(executor);
	}
	
	
	private Executor executor = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t ;
    });
	
	private RequestStore store = RequestStore.getInstance();
	
	@Override
	protected void succeeded() {
		super.succeeded();
		reset();
	}
	
	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				Request r = store.getRequest();
				
				URL url = new URL(r.getUrl());
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(2000);
				
				con.setRequestMethod(r.getMethod());
				
				//con.setRequestProperty("User-Agent", USER_AGENT);
				
				con.connect();
				
				String body = "";
				try {
					StringBuffer sb = new StringBuffer();
					try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
						String inputLine;
						while ((inputLine = in.readLine()) != null) {
							sb.append(inputLine);
						}
					}
					body = sb.toString();
				} catch(Exception e) {}
				
				if(body.matches(".*<base\\s*href=\".*")) {
					body = body.replaceFirst("<base\\s*href=\"\\s+\"", "<base href=\"" + url.toString() + "\"");
				} else if(body.matches(".*<head>.*")) {
					body = body.replaceFirst("<head>", "<head><base href='" + url.toString() + "'>");
				}
				final String bodyFinal = body;
				
				
				Request copy = new Request(r);
				Platform.runLater(()-> {
					if(store.getRequests().contains(copy))
						store.getRequests().remove(copy);
					store.getRequests().add(0, copy);
					
					store.setResponse(new Response(bodyFinal));
					store.setRequest(new Request());
				});
				return null;
			}
		};
	}

}
