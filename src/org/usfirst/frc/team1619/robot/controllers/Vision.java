package org.usfirst.frc.team1619.robot.controllers;

import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.util.stabilizers.BooleanStabilizer;
import org.usfirst.frc.team1619.robot.framework.util.stabilizers.NumericStabilizer;
import org.usfirst.frc.team1619.robot.framework.util.stabilizers.Stabilizer;

public class Vision {

	private static final Vision instance;

	static {
		instance = new Vision();
	}

	public static Vision getInstance() {
		return instance;
	}

	private Stabilizer<Boolean> targetFoundStabilizer;
	private Stabilizer<Double> heightStabilizer, horizontalOffsetStabilizer,
			yStabilizer;
	private double pixelToAngleRatio, angleOffset;

	public void configure(int targetFoundAndHeightStabilizerCount, int horizontalOffsetStabilizerCount, double pixelToAngleRatio, double angleOffset) {
		this.targetFoundStabilizer = new BooleanStabilizer(targetFoundAndHeightStabilizerCount);
		this.heightStabilizer = new NumericStabilizer(targetFoundAndHeightStabilizerCount);
		this.horizontalOffsetStabilizer = new NumericStabilizer(horizontalOffsetStabilizerCount);
		this.yStabilizer = new NumericStabilizer(targetFoundAndHeightStabilizerCount);
		this.pixelToAngleRatio = pixelToAngleRatio;
		this.angleOffset = angleOffset;
	}

	public void update() {
		this.targetFoundStabilizer.push(IO.network.boilerTargetFound);
		if (IO.network.boilerTargetFound) {
			this.heightStabilizer.push(IO.network.boilerHeight);
			this.yStabilizer.push(IO.network.boilerY);
		}
		else {
			this.heightStabilizer.reset();
			this.yStabilizer.reset();
		}
		this.horizontalOffsetStabilizer.push(IO.network.boilerHorizontalOffset);
	}

	public Stabilizer<Boolean> getTargetFoundStabilizer() {
		return this.targetFoundStabilizer;
	}

	public Stabilizer<Double> getHeightStabilizer() {
		return this.heightStabilizer;
	}

	public Stabilizer<Double> getYStabilizer() {
		return this.yStabilizer;
	}

	public Stabilizer<Double> getHorizontalOffsetStabilizer() {
		return this.horizontalOffsetStabilizer;
	}

	public boolean getTargetFound() {
		return this.targetFoundStabilizer.get();
	}

	public double getHeight() {
		return this.heightStabilizer.get();
	}

	public double getY() {
		return this.yStabilizer.get();
	}

	public double getAngleOffset() {
		return this.horizontalOffsetStabilizer.get() * this.pixelToAngleRatio + this.angleOffset;
	}

	public boolean getLiveTargetFound() {
		return IO.network.boilerTargetFound;
	}

	public double getLiveAngleOffset() {
		return IO.network.boilerHorizontalOffset * this.pixelToAngleRatio + this.angleOffset;
	}

}
