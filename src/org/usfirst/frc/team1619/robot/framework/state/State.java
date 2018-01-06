package org.usfirst.frc.team1619.robot.framework.state;

import org.usfirst.frc.team1619.robot.NetworkImpl;
import org.usfirst.frc.team1619.robot.RobotState;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.IO.In;
import org.usfirst.frc.team1619.robot.framework.IO.Out;

public abstract class State {

	protected void initialize() { }

	protected abstract void update();

	protected abstract boolean isDone();

	protected void dispose() { }

	public void initializeState(SubsystemID id) {
		this.initialize();
	}

	public void updateState(SubsystemID id) {
		this.update();
	}

	public boolean isDoneState() {
		return this.isDone();
	}

	public void disposeState() {
		this.dispose();
	}

	protected static In in;
	protected static Out out;
	protected static NetworkImpl network;
	protected static RobotState state;

	public static void link(In in, Out out, NetworkImpl network, RobotState state) {
		State.in = in;
		State.out = out;
		State.network = network;
		State.state = state;
	}

}
