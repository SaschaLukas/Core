package net.teamlife.lifecore.utils;

public class MapObject<K, V> {

	private Object key, value, object;

	public MapObject(Object key, Object value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return (K) key;
	}

	public V getValue() {
		return (V) value;
	}

	public Object getObject() {
		return object;
	}

	public K setKey(K k) {
		this.key = k;
		return (K) key;
	}

	public V setValue(V v) {
		this.value = v;
		return (V) value;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}