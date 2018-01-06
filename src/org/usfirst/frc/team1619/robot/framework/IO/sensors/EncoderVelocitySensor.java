package org.usfirst.frc.team1619.robot.framework.IO.sensors;

import com.ctre.CANTalon;

public class EncoderVelocitySensor implements Sensor<Double> {

	private CANTalon motor;
	private int codesPerRev;
	private double ratio;
	private boolean inverted;

	public EncoderVelocitySensor(CANTalon motor, int codesPerRev, double ratio, boolean inverted) {
		this.motor = motor;
		this.codesPerRev = codesPerRev;
		this.ratio = ratio;

		this.motor.configEncoderCodesPerRev(this.codesPerRev);
		this.inverted = inverted;
	}

	@Override
	public Double get() {
		return (this.inverted ? -1.0 : 1.0) * this.motor.getSpeed() * this.ratio / 60.0;
	}

	public double convert(double input) {
		return input / this.ratio * 60.0;
	}

	public int convertToNative(double input) {
		return (int) Math.round(this.convert(input) * this.codesPerRev);
	}

}
