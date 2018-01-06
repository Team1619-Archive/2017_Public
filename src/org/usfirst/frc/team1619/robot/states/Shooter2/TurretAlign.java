package org.usfirst.frc.team1619.robot.states.Shooter2;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.util.Promise;

public class TurretAlign extends State {

	private BoilerVision vision = BoilerVision.getInstance();
	private Promise<Double> horizontalOffset = null;

	private boolean aligned = false;

	public boolean getAligned() {
		return this.aligned;
	}

	private void onHorizontalOffset(Double horizontalOffset) {
		double angleOffset = this.vision.getHorizontalOffsetAngleFromPixels(horizontalOffset);
		this.aligned = angleOffset < Constants.SHOOTING_ANGLE_ERROR_THRESHOLD_AUTO;
		IO.turretPositionController.setRelative(angleOffset * 0.9);
	}

	private void onFinally(Promise<Double> promise) {
		this.horizontalOffset = null;
	}

	@Override
	protected void initialize() {
		IO.turretController.setProfile(IO.TURRET_PROFILE_VISION);
		IO.turretPositionController.setAbsolute(in.getNumeric(IO.TURRET_POSITION));
	}

	@Override
	protected void update() {
		if (this.horizontalOffset != null) {
			this.horizontalOffset.then(this::onHorizontalOffset, null, this::onFinally);
		}
		else if (IO.turretPositionController.getTimeThere() > 0.5) {
			this.horizontalOffset = this.vision.getHorizontalOffset(true);
		}

		IO.turretPositionController.update();
	}

	@Override
	protected boolean isDone() {
		return false;
	}

}
