package main.io;

import java.util.List;

public class LoadManager implements Load {
	
	private Load load;
	
	public LoadManager(Load load) {
		this.load = load;
	}

	@Override
	public List<Object> load(String path) {
		return this.load.load(path);
	}
	
	

}
