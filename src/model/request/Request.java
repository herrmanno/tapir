package model.request;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Request {

	public final StringProperty method = new SimpleStringProperty();
	public final StringProperty location = new SimpleStringProperty();
	public final ObservableList<Header> header = FXCollections.observableArrayList(new Header("Content-Type", "text/html"));
	public final ObservableList<Cookie> cookies = FXCollections.observableArrayList(new Cookie("SessionID", "1234567"), new Cookie("", ""));
	public final ObservableList<Formdata> formData = FXCollections.observableArrayList(new Formdata("hello", "world"));
	
	//TODO add formdata
	
	public Request() {method.set("GET");}
	
	public Request(Request r) {
		method.set(r.method.get());
		location.set(r.location.get());
		header.clear();
		header.addAll(r.header);
		cookies.clear();
		cookies.addAll(r.cookies);
		formData.clear();
		formData.addAll(r.formData);
	}
	public StringProperty methodProperty() {return method;}
	public String getMethod() {return method.get();}
	public void setMethod(String m) {method.set(m);}
	
	public StringProperty locationProperty() {return location;}
	public String getLocation() {return location.get();}
	public void setLocation(String m) {location.set(m);}
	
	public ObservableList<Header> getHeader() {return header;}
	
	public ObservableList<Cookie> getCookies() {return cookies;}
	
	public ObservableList<Formdata> getFormData() {return formData;}
	
	public String getUrl() {
		String location = this.location.get();
		String loc = location.contains("://") ? location.substring(location.indexOf("://")+3) : location;
		String prot = location.contains("://") ? location.substring(0, location.indexOf("://")) : "HTTP";
		return prot + "://" + loc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cookies == null) ? 0 : cookies.hashCode());
		result = prime * result + ((formData == null) ? 0 : formData.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((location == null) ? 0 : location.get().hashCode());
		result = prime * result + ((method == null) ? 0 : method.get().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		if (cookies == null) {
			if (other.cookies != null)
				return false;
		} else if (!cookies.equals(other.cookies))
			return false;
		if (formData == null) {
			if (other.formData != null)
				return false;
		} else if (!formData.equals(other.formData))
			return false;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		if (location.get() == null) {
			if (other.location.get() != null)
				return false;
		} else if (!location.get().equals(other.location.get()))
			return false;
		if (method.get() == null) {
			if (other.method.get() != null)
				return false;
		} else if (!method.get().equals(other.method.get()))
			return false;
		return true;
	}

	
}

