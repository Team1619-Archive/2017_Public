package org.usfirst.frc.team1619.robot.framework.IO.PID;

import com.ctre.CANTalon;
import org.usfirst.frc.team1619.robot.framework.IO.In;
import org.usfirst.frc.team1619.robot.framework.IO.Out;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.EncoderVelocitySensor;

public class TalonVelocityController {

	private Out out;
	private In in;
	private int motorID, sensorID;
	private CANTalon motor;
	public double setpoint = 0.0;

	public TalonVelocityController(Out out, In in, int motorID, int sensorID, CANTalon.FeedbackDevice feedbackDevice, boolean inverted, boolean sensorInverted) {
		assert in.getNumericSensor(sensorID) instanceof EncoderVelocitySensor : "Sensor must be of type EncoderVelocitySensor";

		this.out = out;
		this.in = in;
		this.motorID = motorID;
		this.sensorID = sensorID;
		this.motor = this.out.motors.get(this.motorID);
		this.motor.setFeedbackDevice(feedbackDevice);
		this.motor.SetVelocityMeasurementPeriod(CANTalon.VelocityMeasurementPeriod.Period_50Ms);
		this.motor.SetVelocityMeasurementWindow(32);
		this.motor.reverseOutput(inverted);
		this.motor.reverseSensor(sensorInverted);
	}

	public int addProfile(int profile, double f, double kp, double ki, double kd) {
		this.motor.setProfile(profile);
		this.motor.setF(f);
		this.motor.setP(kp);
		this.motor.setI(ki);
		this.motor.setD(kd);
		return profile;
	}

	public void setProfile(int profile) {
		this.motor.setProfile(profile);
	}

	public void set(double velocity) {
		this.setpoint = velocity;
	}

	public double getError() {
		return this.in.getNumeric(this.sensorID) - this.setpoint;
	}

	public void initialize() {
		this.out.motors.setPaused(this.motorID, true);
		this.motor.changeControlMode(CANTalon.TalonControlMode.Speed);
	}

	public void reset() {
		this.motor.clearIAccum();
	}

	public void update() {
		this.motor.set(this.setpoint);
	}

	public void dispose() {
		this.out.motors.setPaused(this.motorID, false);
		this.motor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}

}
