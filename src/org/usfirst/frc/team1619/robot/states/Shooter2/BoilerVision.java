package org.usfirst.frc.team1619.robot.states.Shooter2;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.util.Promise;
import org.usfirst.frc.team1619.robot.framework.util.stabilizers.Stabilizer2;

public class BoilerVision {

	private static final BoilerVision instance;

	static {
		instance = new BoilerVision();
	}

	public static BoilerVision getInstance() {
		return instance;
	}

	private int visionCodeCount = -1;

	private Stabilizer2 targetFound = new Stabilizer2(Constants.VISION_STABILIZER_COUNT);
	private Stabilizer2 horizontalOffset = new Stabilizer2(Constants.VISION_STABILIZER_COUNT);
	private Stabilizer2 height = new Stabilizer2(Constants.VISION_STABILIZER_COUNT);

	public void update() {
		if (IO.network.visionCodeCount != this.visionCodeCount) {
			if (IO.network.boilerTargetFound) {
				this.targetFound.push(1.0);
				this.horizontalOffset.push(IO.network.boilerHorizontalOffset);
				this.height.push(0.333 * (IO.network.boilerHeight - 30.0) / 16.667 + 0.667 * IO.network.boilerY);
			}
			else {
				this.targetFound.push(-1.0);
			}

			this.visionCodeCount = IO.network.visionCodeCount;
		}
	}

	public Promise<Double> getTargetFound(boolean reset) {
		if (reset) {
			this.targetFound.reset();
		}
		return this.targetFound.get();
	}

	public Promise<Double> getHorizontalOffset(boolean reset) {
		if (reset) {
			this.horizontalOffset.reset();
		}
		return this.horizontalOffset.get();
	}

	public Promise<Double> getHeight(boolean reset) {
		if (reset) {
			this.height.reset();
		}
		return this.height.get();
	}

	public double getHorizontalOffsetAngleFromPixels(double pixelOffset) {
		return pixelOffset * Constants.VISION_PIXEL_TO_ANGLE_RATIO + Constants.VISION_CAMERA_ANGLE_OFFSET;
	}

}
