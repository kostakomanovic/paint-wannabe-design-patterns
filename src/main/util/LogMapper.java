package main.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.command.add.AddShapeCmd;
import main.model.shape.Point;

public class LogMapper {

	private static Command mapLogToAddCommand(Object objLog, ShapesModel model) {
		String log = objLog.toString();
		String[] logSplit = log.split(",");

		if (log.contains("point")) {
			System.out.println("jeste point");
			return new AddShapeCmd(new Point(Integer.parseInt(logSplit[2]), Integer.parseInt(logSplit[3]), new Color(
					Integer.parseInt(logSplit[4]), Integer.parseInt(logSplit[5]), Integer.parseInt(logSplit[6]))),
					model);
		}

		return null;
	}

	public static List<Command> mapLogsToCommands(List<Object> logs, ShapesModel model) {

		List<Command> commands = new ArrayList<>();

		logs.forEach(log -> {
			if (log.toString().contains("add")) {
				commands.add(mapLogToAddCommand(log, model));
			}
		});

		return commands;
	}

}
