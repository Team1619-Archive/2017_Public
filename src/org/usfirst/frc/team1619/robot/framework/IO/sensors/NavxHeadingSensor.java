package org.usfirst.frc.team1619.robot.framework.IO.sensors;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class NavxHeadingSensor implements Sensor<Double> {

	private AHRS navx = new AHRS(SPI.Port.kMXP);
	private double zero;

	public NavxHeadingSensor() {
		this.zero();
	}

	@Override
	public Double get() {
		return ((this.navx.getFusedHeading() + (360.0 - this.zero)) % 360.0 + 180.0) % 360.0 - 180.0;
	}

	public void zero() {
		this.zero = this.navx.getFusedHeading();
	}

}
