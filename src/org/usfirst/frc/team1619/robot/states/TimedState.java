package org.usfirst.frc.team1619.robot.states;

import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class TimedState extends State {

	private Timer timer = new Timer();
	private int timeout;

	public TimedState(int timeout) {
		this.timeout = timeout;
	}

	@Override
	protected void initialize() {
		this.timer.start(this.timeout);
	}

	@Override
	protected void update() {}

	@Override
	protected boolean isDone() {
		return this.timer.isDone();
	}

}
