package main.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.model.ShapesModel;
import main.model.command.Command;
import main.model.command.add.AddShapeCmd;
import main.model.shape.Circle;
import main.model.shape.HexagonAdapter;
import main.model.shape.Line;
import main.model.shape.Point;
import main.model.shape.Rectangle;
import main.model.shape.Square;

public class LogMapper {

	private static Command mapLogToAddCommand(Object objLog, ShapesModel model) {
		String log = objLog.toString();
		String[] logSplit = log.split(",");

		String shape = logSplit[1].split(":")[0];
		System.out.println(shape);
		if (shape.equals("point")) {
			return new AddShapeCmd(new Point(Integer.parseInt(logSplit[2]), Integer.parseInt(logSplit[3]), new Color(
					Integer.parseInt(logSplit[4]), Integer.parseInt(logSplit[5]), Integer.parseInt(logSplit[6]))),
					model);
		} else if (shape.equals("line")) {
			String[] split = log.split(":");
			String[] points = split[1].split("-");
			return new AddShapeCmd(getLine(getPoint(points[0]), getPoint(points[1]), split[2]), model);
		} else if (shape.equals("square")) {
			String[] split = log.split(":");
			return new AddShapeCmd(getSquare(getPoint(split[1]), split[2], split[3], split[4]), model);
		} else if (shape.equals("rectangle")) {
			String[] split = log.split(":");
			return new AddShapeCmd(getRectangle(getPoint(split[1]), split[2], split[3], split[4], split[5]), model);
		} else if (shape.equals("circle")) {
			String[] split = log.split(":");
			return new AddShapeCmd(getCircle(getPoint(split[1]), split[2], split[3], split[4]), model);
		} else if (shape.equals("hexagon")) {
			String[] split = log.split(":");
			return new AddShapeCmd(getHexagon(getPoint(split[1]), split[2], split[3], split[4]), model);
		}

		return null;
	}
	
	private static HexagonAdapter getHexagon(Point point, String radius, String color, String fillColor) {
		String[] rgb = color.split(",");
		String[] fillRgb = fillColor.split(",");

		return new HexagonAdapter(point, Integer.parseInt(radius),
				new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])),
				new Color(Integer.parseInt(fillRgb[0]), Integer.parseInt(fillRgb[1]), Integer.parseInt(fillRgb[2])));
	}

	private static Circle getCircle(Point point, String radius, String color, String fillColor) {
		String[] rgb = color.split(",");
		String[] fillRgb = fillColor.split(",");

		return new Circle(point, Integer.parseInt(radius),
				new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])),
				new Color(Integer.parseInt(fillRgb[0]), Integer.parseInt(fillRgb[1]), Integer.parseInt(fillRgb[2])));
	}

	private static Rectangle getRectangle(Point point, String width, String height, String color, String fillColor) {
		String[] rgb = color.split(",");
		String[] fillRgb = fillColor.split(",");

		return new Rectangle(point, Integer.parseInt(width), Integer.parseInt(height),
				new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])),
				new Color(Integer.parseInt(fillRgb[0]), Integer.parseInt(fillRgb[1]), Integer.parseInt(fillRgb[2])));
	}

	private static Square getSquare(Point point, String width, String color, String fillColor) {
		String[] rgb = color.split(",");
		String[] fillRgb = fillColor.split(",");

		return new Square(point, Integer.parseInt(width),
				new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])),
				new Color(Integer.parseInt(fillRgb[0]), Integer.parseInt(fillRgb[1]), Integer.parseInt(fillRgb[2])));
	}

	private static Point getPoint(String point) {
		String[] array = point.split(",");
		return new Point(Integer.parseInt(array[1]), Integer.parseInt(array[2]),
				new Color(Integer.parseInt(array[3]), Integer.parseInt(array[4]), Integer.parseInt(array[5])));
	}

	private static Line getLine(Point one, Point two, String colors) {
		String[] rgb = colors.split(",");
		return new Line(one, two,
				new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));
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
