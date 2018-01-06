package org.usfirst.frc.team1619.robot.controllers.LED;

public class LEDClimb extends LEDFlashSequence {

	public LEDClimb() {
		super(true);
	}

	@Override
	protected int[] getFlashSequence() {
		return new int[] { 50, 100, 200, 50, 300, 50 };
	}

}
