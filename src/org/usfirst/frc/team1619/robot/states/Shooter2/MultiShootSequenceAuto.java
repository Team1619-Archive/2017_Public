package org.usfirst.frc.team1619.robot.states.Shooter2;

import org.usfirst.frc.team1619.robot.controllers.Shot;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class MultiShootSequenceAuto extends MultiShootSequence {

	private Timer timer = new Timer();
	private int time;

	private boolean spooling = true;

	public MultiShootSequenceAuto(int time) {
		super();

		this.time = time;
	}

	public MultiShootSequenceAuto(double hopperOutput, double elevatorOutput, Shot noVisionShot, int time) {
		super(hopperOutput, elevatorOutput, noVisionShot);

		this.time = time;
	}

	@Override
	protected boolean getSpooling() {
		// Once spooled always return true
		if (this.spooling && !super.getSpooling()) {
			this.spooling = false;
		}
		return this.spooling;
	}

	@Override
	protected boolean getTurretAligned() {
		return true;
	}

	@Override
	protected void update() {
		if (!this.timer.isStarted() && !this.getSpooling() && this.time > 0) {
			this.timer.start(this.time);
		}

		super.update();
	}

	@Override
	protected boolean isDone() {
		return this.timer.isDone();
	}

}
