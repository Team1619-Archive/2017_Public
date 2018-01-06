package org.usfirst.frc.team1619.robot.states.Turret;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.controllers.Vision;
import org.usfirst.frc.team1619.robot.framework.state.SequencerState;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.util.Timer;
import org.usfirst.frc.team1619.robot.states.WaitForStabilizer;

public class TurretAlign extends SequencerState {

	private class TurretAlignAfterStabilizer extends State {

		private Vision vision;
		private double threshold;
		private double idle, setpoint;
		private boolean continuous;
		private boolean done = false;
		private Timer timer = new Timer();

		public TurretAlignAfterStabilizer(boolean continuous, Vision vision, double threshold, double idle) {
			this.continuous = continuous;
			this.vision = vision;
			this.threshold = threshold;
			this.idle = idle;
			this.setpoint = this.idle;
		}

		@Override
		protected void initialize() {
			IO.turretController.setProfile(IO.TURRET_PROFILE_VISION);

			this.setpoint = in.getNumeric(IO.TURRET_POSITION);
			IO.turretPositionController.setAbsolute(this.setpoint);
			this.timer.start(0);
		}

		@Override
		protected void update() {
			if (this.done) {
				IO.turretPositionController.update();
				return;
			}

			if (this.vision.getTargetFound() && (IO.turretPositionController.getTimeThere() > 0.05 || this.timer.isDone())) {
				double offset = this.vision.getAngleOffset();
				if (Math.abs(offset) > this.threshold) {
					this.setpoint = in.getNumeric(IO.TURRET_POSITION) + offset * 0.5;
				}
				else if (Math.abs(offset) / 4 > this.threshold) {
					this.setpoint = in.getNumeric(IO.TURRET_POSITION) + offset * 0.5;
				}
				else {
					if (!this.continuous) {
						this.done = true;
					}
					this.setpoint = in.getNumeric(IO.TURRET_POSITION);
				}
				IO.turretPositionController.setAbsolute(this.setpoint);
				this.timer.start(500);
			}

			IO.turretPositionController.update();
		}

		@Override
		protected boolean isDone() {
			return false;
		}

	}

	private boolean continuous;
	private double threshold;
	private Timer timer = new Timer();

	private Vision vision = Vision.getInstance();

	public TurretAlign(boolean continuous, double threshold) {
		this.continuous = continuous;
		this.threshold = threshold;
	}

	@Override
	protected void initialize() {
		super.initialize();

		out.relays.set(IO.VISION_LIGHT, true);
	}

	@Override
	protected void update() {
		super.update();
		
		if (!this.timer.isStarted() && Math.abs(this.vision.getAngleOffset()) < 1.5) {
			this.timer.start(250);
		}
		else if (this.timer.isStarted() && this.vision.getAngleOffset() > 2.5) {
			this.timer.reset();
		}
	}
	
	@Override
	protected void dispose() {
		super.dispose();

		out.relays.set(IO.VISION_LIGHT, false);
	}

	@Override
	protected void addSequence() {
		this.add(new WaitForStabilizer(this.vision.getHorizontalOffsetStabilizer()));
		this.add(new TurretAlignAfterStabilizer(this.continuous, this.vision, this.threshold, Constants.TURRET_POSITION_IDLE));
	}

	public boolean getAligned() {
		return this.timer.isDone();
	}
	
}
