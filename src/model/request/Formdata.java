package model.request;

import model.NameValueModel;

public class Formdata extends NameValueModel {
	public Formdata() {this("email", "test");}
	public Formdata(String name, String value) {super(name, value);}
}