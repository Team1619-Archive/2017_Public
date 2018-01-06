package org.usfirst.frc.team1619.robot.framework.IO;

public class Drive {

	private Out out;
	private int leftDrive, leftDrive2, rightDrive, rightDrive2;

	private double scalar, accumulator, previousTurn;

	public Drive(Out out, int leftDrive, int leftDrive2, int rightDrive, int rightDrive2) {
		this.out = out;
		this.leftDrive = leftDrive;
		this.leftDrive2 = leftDrive2;
		this.rightDrive = rightDrive;
		this.rightDrive2 = rightDrive2;

		this.reset();
	}

	public void setScalar(double scalar) {
		this.scalar = scalar;
		this.reset();
	}

	public void update(double movement, double turn) {
		double negativeInertia = turn - this.previousTurn;
		this.previousTurn = turn;

		this.accumulator += negativeInertia * this.scalar;
		//		turn += this.accumulator;

		if (this.accumulator > 1.0) {
			this.accumulator -= 1.0;
		}
		else if (this.accumulator < -1.0) {
			this.accumulator += 1.0;
		}
		else {
			this.accumulator = 0.0;
		}

		double left = movement - turn;
		double right = movement + turn;

		this.out.motors.set(this.leftDrive, left);
		this.out.motors.set(this.leftDrive2, left);
		this.out.motors.set(this.rightDrive, right);
		this.out.motors.set(this.rightDrive2, right);
	}

	public void reset() {
		this.accumulator = 0.0;
		this.previousTurn = 0.0;
	}

}
