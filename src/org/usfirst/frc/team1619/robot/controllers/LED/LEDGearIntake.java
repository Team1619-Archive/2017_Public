package org.usfirst.frc.team1619.robot.controllers.LED;

public class LEDGearIntake extends LEDFlashSequence {

	public LEDGearIntake() {
		super(true);
	}

	@Override
	protected int[] getFlashSequence() {
		return new int[] { 100, 100, 250, 50 };
	}

}
