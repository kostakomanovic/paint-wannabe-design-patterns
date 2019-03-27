package main.io.out;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import main.io.Save;

public class SaveLog implements Save {

	@Override
	public void save(List<Object> objects, String path) {
		PrintWriter printWriter;
		try {
			printWriter = new PrintWriter(path);
			objects.stream().forEach(command -> printWriter.write(command.toString() + "\n"));
			printWriter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
