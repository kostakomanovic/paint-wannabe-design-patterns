package main.io.in;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import main.io.Load;

public class LoadLog implements Load{

	@SuppressWarnings({ "resource" })
	@Override
	public List<Object> load(String path) {
		try {
			Stream<String> logs = Files.lines(Paths.get(path));						
			return logs.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
