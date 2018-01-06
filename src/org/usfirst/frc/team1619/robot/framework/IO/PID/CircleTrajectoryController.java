package org.usfirst.frc.team1619.robot.framework.IO.PID;

import org.usfirst.frc.team1619.robot.framework.IO.In;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.EncoderPositionSensor;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.NavxHeadingSensor;
import org.usfirst.frc.team1619.robot.framework.util.CircleTrajectory;
import org.usfirst.frc.team1619.robot.framework.util.Point;

public class CircleTrajectoryController {

	private CircleTrajectory trajectory;
	private FollowerController leftFollowerController, rightFollowerController;
	private FeedforwardVelocityController leftVelocityController,
		rightVelocityController;
	private EncoderPositionSensor leftEncoderPosition, rightEncoderPosition;
	private NavxHeadingSensor heading;
	private boolean done;

	public CircleTrajectoryController(FollowerController leftFollowerController, FollowerController rightFollowerController, FeedforwardVelocityController leftVelocityController, FeedforwardVelocityController rightVelocityController, In in, int leftSensorID, int rightSensorID, int headingSensorID) {
		this.leftFollowerController = leftFollowerController;
		this.rightFollowerController = rightFollowerController;
		this.leftVelocityController = leftVelocityController;
		this.rightVelocityController = rightVelocityController;
		this.leftEncoderPosition = (EncoderPositionSensor) in.getNumericSensor(leftSensorID);
		this.rightEncoderPosition = (EncoderPositionSensor) in.getNumericSensor(rightSensorID);
		this.heading = (NavxHeadingSensor) in.getNumericSensor(headingSensorID);
	}

	public void initialize(CircleTrajectory trajectory) {
		this.done = false;

		this.trajectory = trajectory;
		this.leftFollowerController.initialize();
		this.rightFollowerController.initialize();
		this.leftEncoderPosition.zero();
		this.rightEncoderPosition.zero();
		this.heading.zero();
	}

	public void update() {
		double leftDistance = Math.max(Math.abs(this.leftEncoderPosition.get()), 0.005);
		double rightDistance = Math.max(Math.abs(this.rightEncoderPosition.get()), 0.005);
		if (!this.done && leftDistance >= this.trajectory.getLeftDistance() && rightDistance >= this.trajectory.getRightDistance()) {
			this.done = true;
		}

		Point leftPoint = this.trajectory.getLeftPoint(leftDistance);
		Point rightPoint = this.trajectory.getRightPoint(rightDistance);

		double headingError = Math.abs(this.heading.get()) - this.trajectory.getHeading((leftDistance + rightDistance) / 2.0);

		this.leftVelocityController.set(leftPoint);
		this.rightVelocityController.set(rightPoint);

		this.leftVelocityController.updateWithHeading(this.trajectory.isTurnLeft() ? -headingError : headingError, this.trajectory.isInverted());
		this.rightVelocityController.updateWithHeading(this.trajectory.isTurnLeft() ? headingError : -headingError, this.trajectory.isInverted());
		this.leftFollowerController.update();
		this.rightFollowerController.update();
	}

	public boolean isDone() {
		return this.done;
	}

	public void dispose() {
		this.leftFollowerController.dispose();
		this.rightFollowerController.dispose();
	}

}
