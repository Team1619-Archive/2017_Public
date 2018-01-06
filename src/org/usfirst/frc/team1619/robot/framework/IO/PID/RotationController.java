package org.usfirst.frc.team1619.robot.framework.IO.PID;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.IO.In;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.NavxHeadingSensor;
import org.usfirst.frc.team1619.robot.framework.util.Point;
import org.usfirst.frc.team1619.robot.framework.util.Timer;
import org.usfirst.frc.team1619.robot.framework.util.TrapezoidTrajectory;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RotationController {

	private TrapezoidTrajectory trajectory;
	private boolean left;
	private FollowerController leftFollowerController, rightFollowerController;
	private FeedforwardVelocityController leftVelocityController,
			rightVelocityController;
	private PIDController drivePIDRotationController;
	private NavxHeadingSensor heading;
	private boolean almostThere;
	private Timer timer = new Timer();
	private Timer thereTimer = new Timer();

	public RotationController(FollowerController leftFollowerController, FollowerController rightFollowerController, FeedforwardVelocityController leftVelocityController, FeedforwardVelocityController rightVelocityController, PIDController drivePIDRotationController, In in, int sensorID) {
		this.leftFollowerController = leftFollowerController;
		this.rightFollowerController = rightFollowerController;
		this.leftVelocityController = leftVelocityController;
		this.rightVelocityController = rightVelocityController;
		this.drivePIDRotationController = drivePIDRotationController;
		this.heading = (NavxHeadingSensor) in.getNumericSensor(sensorID);
	}

	public void initialize(boolean left, TrapezoidTrajectory trajectory) {
		this.timer.reset();
		this.thereTimer.reset();
		this.almostThere = false;

		this.left = left;
		this.trajectory = trajectory;
		this.leftFollowerController.initialize();
		this.rightFollowerController.initialize();
		this.drivePIDRotationController.setProfile(IO.DRIVE_PID_ROTATION_DEFAULT);
		this.heading.zero();
	}

	public void update() {
		double heading = Math.max(Math.abs(this.heading.get()), 1.0);
		double error = this.trajectory.getDistance() - heading;
		if (!this.almostThere && Math.abs(error) < 2.5) {
			this.leftFollowerController.dispose();
			this.rightFollowerController.dispose();
			this.almostThere = true;
		}

		if (this.almostThere) {
			if (!this.thereTimer.isStarted()) {
				this.thereTimer.start(500);
			}

			SmartDashboard.putNumber("rotationError", Math.abs(error));
			if (Math.abs(error) < 0.75) {
				if (!this.timer.isStarted()) {
					this.timer.start(200);
				}
			}
			else if (this.timer.isStarted()) {
				this.timer.reset();
			}

			double output = this.drivePIDRotationController.get(error);
			IO.out.motors.set(IO.DRIVE_LEFT, output * (this.left ? 1 : -1));
			IO.out.motors.set(IO.DRIVE_LEFT_2, output * (this.left ? 1 : -1));
			IO.out.motors.set(IO.DRIVE_RIGHT, output * (this.left ? -1 : 1));
			IO.out.motors.set(IO.DRIVE_RIGHT_2, output * (this.left ? -1 : 1));
		}
		else {
			Point point = this.trajectory.getPoint(heading);

			if (this.left) {
				this.leftVelocityController.set(point);
				this.rightVelocityController.set(point);
			}
			else {
				this.leftVelocityController.set(point);
				this.rightVelocityController.set(point);
			}

			this.leftVelocityController.update(this.left ? true : false);
			this.rightVelocityController.update(this.left ? false : true);
			this.leftFollowerController.update();
			this.rightFollowerController.update();
		}
	}

	public boolean isDone() {
		return this.thereTimer.isDone() || this.timer.isDone();
	}

	public void dispose() {
		this.leftFollowerController.dispose();
		this.rightFollowerController.dispose();
	}

}
