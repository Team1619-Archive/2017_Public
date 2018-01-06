package org.usfirst.frc.team1619.robot.states.GearHandler;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

public class GearHandlePlaceAuto extends State {

	private Timer timer = new Timer();
	private int timeout;

	public GearHandlePlaceAuto() {
		this(1000);
	}

	public GearHandlePlaceAuto(int timeout) {
		this.timeout = timeout;
	}

	@Override
	protected void initialize() {
		this.timer.start(this.timeout);
	}

	@Override
	protected void update() {
		out.motors.set(IO.GEAR_HANDLER_INTAKE, Constants.GEAR_HANDLER_PLACEMENT_INTAKE_OUTPUT);
		if (in.getNumeric(IO.GEAR_HANDLER_PIVOT_POSITION) < 100.0) {
			out.motors.set(IO.GEAR_HANDLER_PIVOT, Constants.GEAR_HANDLER_PLACEMENT_PIVOT_OUTPUT);
		}
	}

	@Override
	protected boolean isDone() {
		return this.timer.isDone();
	}

}
