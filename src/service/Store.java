package service;

import java.util.Collection;
import java.util.HashSet;

public class Store {

	Collection<StoreListener<Store>> listeners = new HashSet<StoreListener<Store>>();
	
	public void register(StoreListener<Store> listener) {
		listeners.add(listener);
	}
	
	public void unRegister(StoreListener<Store> listener) {
		listeners.remove(listener);
	}
	
	public void update() {
		update(this, null);
	}
	
	public void update(Object... args) {
		listeners.forEach(l -> l.onUpdate(this, args));
	}
	
	public interface StoreListener<T extends Store> {
		public void onUpdate(T store, Object... args);
	}
}
