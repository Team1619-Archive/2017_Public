package org.usfirst.frc.team1619.robot;

import org.usfirst.frc.team1619.robot.controllers.Vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotState {

	private Vision vision = Vision.getInstance();

	public boolean initialized = false;
	public boolean intakeExtended = false;
	public boolean gearHandlerExtended = false;
	public double shootingPercentage = 1.0;
	public boolean driveInverted = false;

	public boolean autoRed = true;
	public int autoSequenceID = 1;

	public void update() {
		SmartDashboard.putBoolean("leftLimitSwitch", IO.in.getBoolean(IO.TURRET_LEFT_LIMIT_SWITCH));
		SmartDashboard.putBoolean("rightLimitSwitch", IO.in.getBoolean(IO.TURRET_RIGHT_LIMIT_SWITCH));
		SmartDashboard.putNumber("driveLeftPosition", IO.in.getNumeric(IO.DRIVE_LEFT_POSITION));
		SmartDashboard.putNumber("driveRightPosition", IO.in.getNumeric(IO.DRIVE_RIGHT_POSITION));
		SmartDashboard.putNumber("driveLeftVelocity", IO.in.getNumeric(IO.DRIVE_LEFT_VELOCITY));
		SmartDashboard.putNumber("driveRightVelocity", IO.in.getNumeric(IO.DRIVE_RIGHT_VELOCITY));
		SmartDashboard.putNumber("elevatorVelocity", IO.in.getNumeric(IO.ELEVATOR_VELOCITY));
		SmartDashboard.putNumber("shooterVelocity", IO.in.getNumeric(IO.SHOOTER_VELOCITY));
		SmartDashboard.putNumber("turretPosition", IO.in.getNumeric(IO.TURRET_POSITION));
		SmartDashboard.putNumber("heading", IO.in.getNumeric(IO.NAVX_HEADING));
		SmartDashboard.putBoolean("targetFound", vision.getTargetFound());
		SmartDashboard.putNumber("angleOffset", vision.getAngleOffset());
		SmartDashboard.putNumber("liveAngleOffset", vision.getLiveAngleOffset());
		SmartDashboard.putNumber("height", 0.333 * (IO.network.boilerHeight - 30.0) / 16.667 + 0.667 * IO.network.boilerY);
		SmartDashboard.putNumber("gearHandlerPivotPosition", IO.in.getNumeric(IO.GEAR_HANDLER_PIVOT_POSITION));
		SmartDashboard.putNumber("gearHandlerPivotVelocity", IO.in.getNumeric(IO.GEAR_HANDLER_PIVOT_VELOCITY));
		SmartDashboard.putBoolean("gearHandlerPresence", IO.in.getBoolean(IO.GEAR_HANDLER_PRESENCE));
		SmartDashboard.putNumber("xAxis", IO.in.getNumeric(IO.OPERATOR_LEFT_AXIS_X));
		SmartDashboard.putNumber("yAxis", IO.in.getNumeric(IO.OPERATOR_LEFT_AXIS_Y));
		SmartDashboard.putBoolean("intakeExtended", IO.state.intakeExtended);
		SmartDashboard.putBoolean("autoRed", IO.state.autoRed);
		SmartDashboard.putNumber("autoSequenceID", IO.state.autoSequenceID);
		SmartDashboard.putNumber("visionCodeCount", IO.network.visionCodeCount);
		SmartDashboard.putNumber("shootingPercentage", this.shootingPercentage);
	}

	public void updateAuto() {
		if (IO.in.getBooleanAndDelta(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_AUTO_RED_TOGGLE], true)) {
			IO.state.autoRed = !IO.state.autoRed;
		}
		if (IO.in.getBooleanAndDelta(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_AUTO_SEQUENCE_ID_TOGGLE], true)) {
			IO.state.autoSequenceID++;
			if (IO.state.autoSequenceID > Constants.AUTO_SEQUENCE_ID_CAP) {
				IO.state.autoSequenceID = 1;
			}
		}
	}

}
