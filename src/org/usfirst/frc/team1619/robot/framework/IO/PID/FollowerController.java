package org.usfirst.frc.team1619.robot.framework.IO.PID;

import com.ctre.CANTalon;
import org.usfirst.frc.team1619.robot.framework.IO.Out;

public class FollowerController {

	private Out out;
	public int motorID, targetMotorID;
	private CANTalon motor;

	public FollowerController(Out out, int motorID, int targetMotorID, boolean inverted) {
		this.out = out;
		this.motorID = motorID;
		this.motor = out.motors.get(this.motorID);
		this.motor.reverseOutput(inverted);
		this.targetMotorID = targetMotorID;
	}

	public void initialize() {
		this.out.motors.setPaused(this.motorID, true);
		this.motor.changeControlMode(CANTalon.TalonControlMode.Follower);
	}

	public void update() {
		this.motor.set(this.targetMotorID);
	}

	public void dispose() {
		this.motor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		this.out.motors.setPaused(this.motorID, false);
	}

}
