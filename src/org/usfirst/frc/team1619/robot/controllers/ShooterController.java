package org.usfirst.frc.team1619.robot.controllers;

import com.ctre.CANTalon;
import org.usfirst.frc.team1619.robot.framework.IO.In;
import org.usfirst.frc.team1619.robot.framework.IO.Out;
import org.usfirst.frc.team1619.robot.framework.IO.PID.FollowerController;
import org.usfirst.frc.team1619.robot.framework.IO.PID.TalonVelocityController;

public class ShooterController extends TalonVelocityController {

	private FollowerController followerController;

	public ShooterController(Out out, In in, int motorID, int sensorID, CANTalon.FeedbackDevice feedbackDevice, boolean inverted, boolean sensorInverted, FollowerController followerController) {
		super(out, in, motorID, sensorID, feedbackDevice, inverted, sensorInverted);

		this.followerController = followerController;
	}

	@Override
	public void initialize() {
		super.initialize();

		this.followerController.initialize();
	}


	@Override
	public void update() {
		super.update();

		this.followerController.update();
	}

	@Override
	public void dispose() {
		super.dispose();

		this.followerController.dispose();
	}

}
