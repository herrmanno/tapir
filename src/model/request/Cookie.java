package model.request;

import model.NameValueModel;

public class Cookie extends NameValueModel {
	public Cookie() {this("SessionID", "12345");}
	public Cookie(String name, String value) {super(name, value);}
}