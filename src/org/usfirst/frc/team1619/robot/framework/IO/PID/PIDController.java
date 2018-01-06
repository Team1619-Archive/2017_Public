package org.usfirst.frc.team1619.robot.framework.IO.PID;

import java.util.ArrayList;
import java.util.List;

public class PIDController {

	private class Profile {

		private double f, kp, ki, kd;
		private double maxOutput;
		private double integral, maxIntegral, integralRange;

		public Profile(double f, double kp, double ki, double kd, double maxOutput, double maxIntegral, double integralRange) {
			this.f = f;
			this.kp = kp;
			this.ki = ki;
			this.kd = kd;
			this.maxOutput = maxOutput;
			this.maxIntegral = maxIntegral;
			this.integralRange = integralRange;
		}

		public void accumulate(double error, double amount) {
			if (this.integralRange > 0.0 && Math.abs(error) > this.integralRange) {
				return;
			}

			this.integral += amount;
			if (this.maxIntegral > 0.0) {
				this.integral = (this.integral < 0.0 ? -1.0 : 1.0) * Math.min(Math.abs(this.integral), this.maxIntegral);
			}
		}

		public double getOutput(double setpoint, double error, double derivative) {
			double output = this.f * setpoint + this.kp * error + this.ki * this.integral + this.kd * derivative;
			return (output < 0 ? -1 : 1) * Math.min(Math.abs(output), this.maxOutput);
		}

	}

	private List<Profile> profiles = new ArrayList<>();
	private Profile profile = null;
	private double setpoint = 0.0;
	private double previousTime, previousError;

	public int addProfile(double f, double kp, double ki, double kd) {
		return this.addProfile(f, kp, ki, kd, 1.0, 0.0, 0.0);
	}

	public int addProfile(double f, double kp, double ki, double kd, double maxOutput, double maxIntegral, double integralRange) {
		this.profiles.add(new Profile(f, kp, ki, kd, maxOutput, maxIntegral, integralRange));
		return this.profiles.size() - 1;
	}

	public void setProfile(int index) {
		this.profile = this.profiles.get(index);
		this.resetProfile();
	}

	public void resetProfile() {
		this.profile.integral = 0.0;
		this.previousTime = -1.0;
		this.previousError = 0.0;
	}

	public void set(double setpoint) {
		this.setpoint = setpoint;
	}

	public double get(double measuredValue) {
		double time = System.currentTimeMillis() / 1000.0;
		if (this.previousTime < 0) {
			this.previousTime = time;
		}

		double deltaTime = time - this.previousTime;
		double error = this.setpoint - measuredValue;
		this.profile.accumulate(error, error * deltaTime);
		double derivative = deltaTime > 0.0 ? (error - this.previousError) / deltaTime : 0.0;

		double output = this.profile.getOutput(this.setpoint, error, derivative);

		this.previousError = error;

		return output;
	}

	public double getSetpoint() {
		return this.setpoint;
	}

	public double getError(double measuredValue) {
		return measuredValue - this.setpoint;
	}

}
