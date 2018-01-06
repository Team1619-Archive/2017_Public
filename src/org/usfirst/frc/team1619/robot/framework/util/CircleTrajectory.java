package org.usfirst.frc.team1619.robot.framework.util;

public class CircleTrajectory {

	private boolean turnLeft;
	private double interiorAngle;
	private TrapezoidTrajectory leftTrajectory, rightTrajectory;

	public CircleTrajectory(double radius, double interiorAngle, boolean turnLeft, double drivetrainWidth, double maxVelocity, double acceleration, boolean inverted) {
		this(radius, interiorAngle, turnLeft, drivetrainWidth, maxVelocity, acceleration, acceleration, inverted);
	}

	public CircleTrajectory(double radius, double interiorAngle, boolean turnLeft, double drivetrainWidth, double maxVelocity, double acceleration, double deceleration, boolean inverted) {
		this.turnLeft = inverted ? !turnLeft : turnLeft;
		this.interiorAngle = interiorAngle;
		double innerRatio = (radius - drivetrainWidth / 2.0) / (radius + drivetrainWidth / 2.0);
		double outerDistance = 2.0 * Math.PI * (radius + drivetrainWidth / 2.0) * this.interiorAngle / 360.0;
		TrapezoidTrajectory outerTrajectory = new TrapezoidTrajectory(outerDistance, maxVelocity, acceleration, deceleration, inverted);
		TrapezoidTrajectory innerTrajectory = new TrapezoidTrajectory(outerDistance * innerRatio, maxVelocity * innerRatio, acceleration * innerRatio, deceleration * innerRatio, inverted);
		this.leftTrajectory = this.turnLeft ? innerTrajectory : outerTrajectory;
		this.rightTrajectory = this.turnLeft ? outerTrajectory : innerTrajectory;
	}

	public void calculate(int resolution) {
		this.leftTrajectory.calculate(resolution);
		this.rightTrajectory.calculate(resolution);
	}

	public void reset() {
		this.leftTrajectory.reset();
		this.rightTrajectory.reset();
	}

	public Point getLeftPoint(double distance) {
		return this.leftTrajectory.getPoint(distance);
	}

	public Point getRightPoint(double distance) {
		return this.rightTrajectory.getPoint(distance);
	}

	public double getHeading(double distance) {
		return distance / ((this.getLeftDistance() + this.getRightDistance()) / 2.0) * this.interiorAngle;
	}

	public double getLeftDistance() {
		return this.leftTrajectory.getDistance();
	}

	public double getRightDistance() {
		return this.rightTrajectory.getDistance();
	}

	public double getInteriorAngle() {
		return this.interiorAngle;
	}

	public boolean isTurnLeft() {
		return this.turnLeft;
	}

	public boolean isInverted() {
		return this.leftTrajectory.isInverted() && this.rightTrajectory.isInverted();
	}

}
