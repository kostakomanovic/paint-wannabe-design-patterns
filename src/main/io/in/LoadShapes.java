package main.io.in;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import main.io.Load;

public class LoadShapes implements Load {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> load(String path) {
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
			return (List<Object>) ois.readObject();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
