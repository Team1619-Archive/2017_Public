package org.usfirst.frc.team1619.robot.controllers;

public class Shot {

	public double height, rpm;
	public boolean hoodDeployed;

	public Shot(double height, double rpm, boolean hoodDeployed) {
		this.height = height;
		this.rpm = rpm;
		this.hoodDeployed = hoodDeployed;
	}

}
