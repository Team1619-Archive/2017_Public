package org.usfirst.frc.team1619.robot.controllers.LED;

public class LEDGearPlacement extends LEDFlashSequence {

	public LEDGearPlacement() {
		super(true);
	}

	@Override
	protected int[] getFlashSequence() {
		return new int[] { 500, 500, 500, 100 };
	}

}
