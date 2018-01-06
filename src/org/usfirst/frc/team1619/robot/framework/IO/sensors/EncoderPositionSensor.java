package org.usfirst.frc.team1619.robot.framework.IO.sensors;

import com.ctre.CANTalon;

public class EncoderPositionSensor implements Sensor<Double> {

	private CANTalon motor;
	private int codesPerRev;
	private double ratio;
	private boolean inverted;
	private double zero = 0.0;

	public EncoderPositionSensor(CANTalon motor, int codesPerRev, double ratio, boolean inverted) {
		this.motor = motor;
		this.codesPerRev = codesPerRev;
		this.ratio = ratio;

		this.motor.configEncoderCodesPerRev(this.codesPerRev);
		this.inverted = inverted;
	}

	public void zero() {
		this.zero += this.get();
	}

	@Override
	public Double get() {
		return (this.inverted ? -1.0 : 1.0) * this.motor.getPosition() * this.ratio - this.zero;
	}

	public double convert(double input) {
		return input / this.ratio;
	}

	public int convertToNative(double input) {
		return (int) Math.round(this.convert(input) * this.codesPerRev);
	}

}
