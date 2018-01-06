package org.usfirst.frc.team1619.robot;

public class Constants {

	public static final int DRIVE_LEFT_ID = 0;
	public static final int DRIVE_LEFT_2_ID = 1;
	public static final int DRIVE_RIGHT_ID = 15;
	public static final int DRIVE_RIGHT_2_ID = 14;
	public static final int INTAKE_ID = 12;
	public static final int HOPPER_ID = 3;
	public static final int ELEVATOR_ID = 4;
	public static final int TURRET_ID = 7;
	public static final int SHOOTER_ID = 5;
	public static final int SHOOTER_2_ID = 6;
	public static final int CLIMBER_ID = 8;
	public static final int GEAR_HANDLER_PIVOT_ID = 13;
	public static final int GEAR_HANDLER_INTAKE_ID = 2;

	public static final int COMPRESSOR_ID = 0;

	public static final int DRIVE_SHIFT_SOLENOID_ID = 0;
	public static final int INTAKE_RETRACT_SOLENOID_ID = 2;
	public static final int INTAKE_EXTEND_SOLENOID_ID = 5;
	public static final int HOPPER_EXTEND_EXTEND_SOLENOID_ID = 6;
	public static final int HOPPER_EXTEND_RETRACT_SOLENOID_ID = 1;

	public static final int VISION_LIGHT_ID = 0;
	public static final int GEAR_LIGHT_ID = 1;
	public static final int LED_STRIP_ID = 2;

	public static final int TURRET_LEFT_LIMIT_SWITCH_ID = 1;
	public static final int TURRET_RIGHT_LIMIT_SWITCH_ID = 0;
	public static final int GEAR_HANDLER_PRESENCE_ID = 9;

	public static final double TURRET_RANGE = 80.0;
	public static final int GEAR_HANDLER_PIVOT_ZERO_TIME = 100;
	public static final double TURRET_ZERO_OUTPUT = -0.25;
	public static final double TURRET_TIME_THERE = 0.1;
	public static final double TURRET_POSITION_IDLE = 36.25;
	public static final double TURRET_POSITION_IDLE_INTAKE_RETRACTED = TURRET_RANGE - 15.0;
	public static final double TURRET_POSITION_BUMP_AND_DUMP_SHOOT_RED = 42.0;
	public static final double TURRET_POSITION_BUMP_AND_DUMP_SHOOT_BLUE = 58.0;

	public static final double VISION_PIXEL_TO_ANGLE_RATIO = -0.07843913166;
	public static final double VISION_CAMERA_ANGLE_OFFSET = 1.5;
	public static final int VISION_STABILIZER_COUNT = 8;

	public static final double SHOOTING_ELEVATOR_FEED_SPEED = 5000;
	public static final double SHOOTING_ANGLE_ERROR_THRESHOLD_AUTO = 1.0;
	public static final double SHOOTING_ANGLE_ERROR_THRESHOLD_TELEOP = 1.0;
	public static final double SHOOTING_SPOOL_ERROR_THRESHOLD = 100.0;

	public static final double GEAR_HANDLER_PIVOT_ZERO_OUTPUT = -0.5;
	public static final double GEAR_HANDLER_PIVOT_IDLE = 29.0 - 3.75;
	public static final double GEAR_HANDLER_PIVOT_PLACEMENT = 65.0 - 1.5;
	public static final double GEAR_HANDLER_PIVOT_HOPPER_EXTENSION = 55.0 - 3.75;
	public static final double GEAR_HANDLER_PIVOT_INTAKE = 115.0 - 3.75;
	public static final int GEAR_HANDLER_PIVOT_RUN_DOWN_TIME = 500;
	public static final double GEAR_HANDLER_PIVOT_RUN_DOWN_OUTPUT = 0.25;
	public static final double GEAR_HANDLER_INTAKE_OUTPUT = 0.875;
	public static final double GEAR_HANDLER_PLACEMENT_PIVOT_OUTPUT = 0.25;
	public static final double GEAR_HANDLER_PLACEMENT_INTAKE_OUTPUT = -0.75;
	public static final int GEAR_HANDLER_AGITATE_TIME_UP = 150;
	public static final int GEAR_HANDLER_AGITATE_TIME_DOWN = 350;

	public static final int DRIVER_JOYSTICK_ID = 0;
	public static final int OPERATOR_CONTROLLER_ID = 1;

	public static final int DRIVER_BUTTON_DRIVE_WIGGLE = 4;
	public static final int DRIVER_BUTTON_HOPPER_FORWARD = 13;
	public static final int DRIVER_BUTTON_HOPPER_REVERSE = 14;
	public static final int DRIVER_BUTTON_ELEVATOR_FORWARD = 12;
	public static final int DRIVER_BUTTON_ELEVATOR_REVERSE = 15;
	public static final int DRIVER_BUTTON_SHOOTER_FORWARD = 11;
	public static final int DRIVER_BUTTON_SHOOTER_REVERSE = 16;
	public static final int DRIVER_BUTTON_HOPPER_EXTENSION_EXTEND = 6;
	public static final int DRIVER_BUTTON_INCREASE_SHOOTING_PERCENTAGE = 7;
	public static final int DRIVER_BUTTON_DECREASE_SHOOTING_PERCENTAGE = 8;
	public static final int DRIVER_BUTTON_TURRET_ZERO = 5;
	public static final int DRIVER_BUTTON_TURRET_ALIGN = 10;
	public static final int DRIVER_BUTTON_GEAR_HANDLER_ZERO = 9;
	public static final int DRIVER_BUTTON_AUTO_RED_TOGGLE = 3;
	public static final int DRIVER_BUTTON_AUTO_SEQUENCE_ID_TOGGLE = 4;
	public static final int AUTO_SEQUENCE_ID_CAP = 4;

	public static final int OPERATOR_LEFT_AXIS_X_ID = 0;
	public static final int OPERATOR_LEFT_AXIS_Y_ID = 1;
	public static final int OPERATOR_RIGHT_AXIS_X_ID = 4;
	public static final int OPERATOR_RIGHT_AXIS_Y_ID = 5;

	public static final int OPERATOR_BUTTON_SHOOT = IO.OPERATOR_BUTTON_RIGHT_TRIGGER;
	public static final int OPERATOR_BUTTON_INTAKE = IO.OPERATOR_BUTTON_LEFT_TRIGGER;
	public static final int OPERATOR_BUTTON_INTAKE_TOGGLE = IO.OPERATOR_BUTTON_LEFT_BUMPER;
	public static final int OPERATOR_INTAKE_REVERSE = IO.OPERATOR_BUTTON_BACK;
	public static final int OPERATOR_BUTTON_CLIMB = IO.OPERATOR_BUTTON_B;
	public static final int OPERATOR_BUTTON_GEAR_HANDLER_ZERO = IO.OPERATOR_BUTTON_START;
	public static final int OPERATOR_BUTTON_GEAR_HANDLER_TOGGLE = IO.OPERATOR_BUTTON_Y;
	public static final int OPERATOR_BUTTON_GEAR_HANDLER_TOGGLE_PLACEMENT = IO.OPERATOR_BUTTON_X;
	public static final int OPERATOR_BUTTON_MANUAL = IO.OPERATOR_BUTTON_RIGHT_BUMPER;
	public static final int OPERATOR_BUTTON_MANUAL_GEAR_INTAKE_OUT = IO.OPERATOR_BUTTON_Y;
	public static final int OPERATOR_BUTTON_MANUAL_GEAR_INTAKE_IN = IO.OPERATOR_BUTTON_A;

	// public static final double AUTO_BUMP_AND_DUMP_CIRCLE_RADIUS_RED = 76.66 /
	// 12.0;
	public static final double AUTO_BUMP_AND_DUMP_CIRCLE_RADIUS_RED = 78.75 / 12.0;
	public static final double AUTO_BUMP_AND_DUMP_CIRCLE_RADIUS_BLUE = 77.5 / 12.0;
	public static final double AUTO_BUMP_AND_DUMP_CIRCLE_INTERNAL_ANGLE = 93.0;
	// public static final double AUTO_BUMP_AND_DUMP_SHOT_RPM = 11550.0;
	// public static final double AUTO_BUMP_AND_DUMP_SHOT_RPM_RED = 11450.0;
	public static final double AUTO_BUMP_AND_DUMP_SHOT_RPM_RED = 9650;
	public static final double AUTO_BUMP_AND_DUMP_SHOT_RPM_BLUE = 9675;

}
