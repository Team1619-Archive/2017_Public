package org.usfirst.frc.team1619.robot.framework.IO;

import com.ctre.CANTalon;

import java.util.HashMap;
import java.util.Map;

public class Motors {

	private class MotorWrapper {

		public CANTalon motor;
		public boolean paused = false;
		private double output = 0.0;
		private boolean inverted;

		public MotorWrapper(int id, boolean brakeMode, boolean inverted) {
			this.motor = new CANTalon(id);
			System.out.println(this.motor.getBrakeEnableDuringNeutral());
			this.motor.enableBrakeMode(brakeMode);
			this.inverted = inverted;
		}

		public void set(double output) {
			this.output = (this.inverted ? -1.0 : 1.0) * output;
		}

		public void enforceLimit(boolean low, boolean high) {
			if (low && (this.inverted ? -1 : 1) * this.output < 0.0) {
				this.output = 0.0;
			}
			else if (high && (this.inverted ? -1 : 1) * this.output > 0.0) {
				this.output = 0.0;
			}
		}

		public void flushOutput() {
			this.motor.set(this.output);
			this.output = 0.0;
		}

	}

	private Map<Integer, MotorWrapper> motors = new HashMap<>();

	public void add(int id, boolean brakeMode, boolean inverted) {
		this.motors.put(id, new MotorWrapper(id, brakeMode, inverted));
	}

	public CANTalon get(int id) {
		return this.motors.get(id).motor;
	}

	public void set(int id, double output) {
		this.motors.get(id).set(output);
	}

	public void enforceLimit(int id, boolean low, boolean high) {
		this.motors.get(id).enforceLimit(low, high);
	}

	public void setPaused(int id, boolean paused) {
		MotorWrapper motor = this.motors.get(id);
		if (paused) {
			motor.set(0.0);
		}
		motor.paused = paused;
	}

	public void update() {
		for (MotorWrapper motor : this.motors.values()) {
			if (!motor.paused) {
				motor.flushOutput();
			}
		}
	}

	public void disable() {
		for (MotorWrapper motor : this.motors.values()) {
			if (!motor.paused) {
				motor.set(0.0);
			}
		}
	}

}
