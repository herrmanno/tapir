package model.request;

import model.NameValueModel;

public class Header extends NameValueModel {
	public Header() {this("Content-Type", "text/xml");}
	public Header(String name, String value) {super(name, value);}
}