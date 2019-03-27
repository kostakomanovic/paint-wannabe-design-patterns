package main.io;

import java.util.List;

public class SaveManager implements Save {
	
	private Save save;
	
	public SaveManager(Save save) {
		this.save = save;
	}

	@Override
	public void save(List<Object> objects, String path) {
		this.save.save(objects, path);
	}
	
	

}
