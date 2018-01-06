package org.usfirst.frc.team1619.robot.framework.util;

public class Point {

	public double distance, velocity, acceleration;

	public Point(double distance, double velocity) {
		this(distance, velocity, 0.0);
	}

	public Point(double distance, double velocity, double acceleration) {
		this.distance = distance;
		this.velocity = velocity;
		this.acceleration = acceleration;
	}

	public static Point invert(Point point) {
		return new Point(point.distance, -point.velocity, -point.acceleration);
	}

}
