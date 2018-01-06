package org.usfirst.frc.team1619.robot.states;

import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.util.stabilizers.Stabilizer;

public class WaitForStabilizer extends State {

	private Stabilizer stabilizer;
	private int referencePoint;

	public WaitForStabilizer(Stabilizer stabilizer) {
		this.stabilizer = stabilizer;
	}

	@Override
	protected void initialize() {
		this.referencePoint = this.stabilizer.getReferencePoint();
	}

	@Override
	protected void update() {}

	@Override
	protected boolean isDone() {
		return this.stabilizer.isExpired(this.referencePoint);
	}

}
