package org.usfirst.frc.team1619.robot;

import org.usfirst.frc.team1619.robot.framework.IO.Network;

public class NetworkImpl extends Network {

	public boolean boilerTargetFound = false;
	public double boilerHorizontalOffset = 0.0;
	public double boilerHeight = 0.0;
	public double boilerY = 0.0;
	public int visionCodeCount = 0;

	public NetworkImpl(String name) {
		super(name);
	}

	@Override
	protected void handleValueChange(String key, Object value, boolean isNew) {
		switch (key) {
			case "boilerVision":
				double[] boilerVision = (double[]) value;
				this.visionCodeCount = (int) boilerVision[0];
				this.boilerTargetFound = boilerVision[1] == 1;
				this.boilerHorizontalOffset = boilerVision[2];
				this.boilerHeight = boilerVision[3];
				this.boilerY = boilerVision[4];
				break;
		}
	}

}
