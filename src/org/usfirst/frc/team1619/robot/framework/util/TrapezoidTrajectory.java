package org.usfirst.frc.team1619.robot.framework.util;

public class TrapezoidTrajectory {

	private double distance, maxVelocity, acceleration, deceleration, startVelocity, endVelocity;
	private boolean inverted;
	private Point[] points;
	private int index;
	private Point point, nextPoint;

	public TrapezoidTrajectory(double distance, double maxVelocity, double acceleration, boolean inverted) {
		this(distance, maxVelocity, acceleration, acceleration, inverted);
	}

	public TrapezoidTrajectory(double distance, double maxVelocity, double acceleration, double deceleration, boolean inverted) {
		this(distance, maxVelocity, acceleration, deceleration, inverted, 0.0, 0.0);
	}

	public TrapezoidTrajectory(double distance, double maxVelocity, double acceleration, double deceleration, boolean inverted, double startVelocity, double endVelocity) {
		this.distance = distance;
		this.maxVelocity = maxVelocity;
		this.acceleration = acceleration;
		this.deceleration = deceleration;
		this.inverted = inverted;
		this.startVelocity = startVelocity;
		this.endVelocity = endVelocity;
	}

	public void calculate(int resolution) {
		assert resolution >= 1;
		this.points = new Point[resolution + 1];

		double deltaDistance = this.distance / resolution;
		double velocity = this.startVelocity;
		this.points[0] = new Point(0.0, velocity);
		for (int i = 1; i < resolution + 1; i++) {
			double distance = deltaDistance * i;
			double maxAchievableVelocity = Math.sqrt(Math.pow(velocity, 2) + 2 * this.acceleration * deltaDistance);    // vf^2 = vi^2 + 2ad
			velocity = Math.min(maxAchievableVelocity, this.maxVelocity);

			this.points[i] = new Point(distance, velocity);
		}

		velocity = this.endVelocity;
		this.points[resolution].velocity = velocity;
		for (int i = resolution - 1; i > 0; i--) {
			double maxAchievableVelocity = Math.sqrt(Math.pow(velocity, 2) + 2 * this.deceleration * deltaDistance);
			velocity = Math.min(maxAchievableVelocity, points[i].velocity);

			this.points[i].velocity = velocity;
		}

		Point previous = this.points[0];
		for (int i = 1; i < resolution + 1; i++) {
			Point current = this.points[i];
			previous.acceleration = (Math.pow(current.velocity, 2) - Math.pow(previous.velocity, 2)) / (2.0 * deltaDistance);

			previous = current;
		}
		previous.acceleration = 0.0;

		this.reset();
	}

	public void reset() {
		assert this.points.length >= 2;

		this.index = 0;
		this.point = this.points[this.index];
		this.nextPoint = this.points[this.index + 1];
	}

	public Point getPoint(double distance) {
		if (this.index < this.points.length && distance >= this.distance) {
			this.index = this.points.length;
		}
		if (this.index == this.points.length) {
			return new Point(distance, this.endVelocity, 0.0);
		}

		if (distance > this.nextPoint.distance) {
			do {
				this.index++;
				this.nextPoint = this.points[this.index + 1];
			} while (distance > this.nextPoint.distance);
			this.point = this.points[this.index];
		}

		double velocity = (this.nextPoint.velocity - this.point.velocity) / (this.nextPoint.distance - this.point.distance) * (distance - this.point.distance) + this.point.velocity;
		double acceleration = (this.nextPoint.acceleration - this.point.acceleration) / (this.nextPoint.distance - this.point.distance) * (distance - this.point.distance) + this.point.acceleration;
		return new Point(distance, velocity, acceleration);
	}

	public double getDistance() {
		return this.distance;
	}

	public boolean isInverted() {
		return this.inverted;
	}

}
