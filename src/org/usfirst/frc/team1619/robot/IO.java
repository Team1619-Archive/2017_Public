package org.usfirst.frc.team1619.robot;

import org.usfirst.frc.team1619.robot.controllers.ShooterController;
import org.usfirst.frc.team1619.robot.framework.IO.Drive;
import org.usfirst.frc.team1619.robot.framework.IO.In;
import org.usfirst.frc.team1619.robot.framework.IO.Out;
import org.usfirst.frc.team1619.robot.framework.IO.PID.BoundedPositionController;
import org.usfirst.frc.team1619.robot.framework.IO.PID.CircleTrajectoryController;
import org.usfirst.frc.team1619.robot.framework.IO.PID.FeedforwardVelocityController;
import org.usfirst.frc.team1619.robot.framework.IO.PID.FollowerController;
import org.usfirst.frc.team1619.robot.framework.IO.PID.PIDController;
import org.usfirst.frc.team1619.robot.framework.IO.PID.PositionController;
import org.usfirst.frc.team1619.robot.framework.IO.PID.RotationController;
import org.usfirst.frc.team1619.robot.framework.IO.PID.TalonVelocityController;
import org.usfirst.frc.team1619.robot.framework.IO.PID.TrapezoidTrajectoryController;
import org.usfirst.frc.team1619.robot.framework.IO.PID.VelocityController;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.ControllerAxisSensor;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.ControllerButtonSensor;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.DigitalInputSensor;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.EncoderPositionSensor;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.EncoderVelocitySensor;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.JoystickAxisSensor;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.JoystickButtonSensor;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.JoystickButtonSensorFactory;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.JoystickThrottleSensor;
import org.usfirst.frc.team1619.robot.framework.IO.sensors.NavxHeadingSensor;
import org.usfirst.frc.team1619.robot.framework.state.State;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class IO {

	public static In in;
	public static Out out;
	public static NetworkImpl network;
	public static RobotState state;

	public static Drive drive;

	public static final int DRIVE_LEFT = Constants.DRIVE_LEFT_ID;
	public static final int DRIVE_LEFT_2 = Constants.DRIVE_LEFT_2_ID;
	public static final int DRIVE_RIGHT = Constants.DRIVE_RIGHT_ID;
	public static final int DRIVE_RIGHT_2 = Constants.DRIVE_RIGHT_2_ID;
	public static final int INTAKE = Constants.INTAKE_ID;
	public static final int HOPPER = Constants.HOPPER_ID;
	public static final int ELEVATOR = Constants.ELEVATOR_ID;
	public static final int TURRET = Constants.TURRET_ID;
	public static final int SHOOTER = Constants.SHOOTER_ID;
	public static final int SHOOTER_2 = Constants.SHOOTER_2_ID;
	public static final int CLIMBER = Constants.CLIMBER_ID;
	public static final int GEAR_HANDLER_PIVOT = Constants.GEAR_HANDLER_PIVOT_ID;
	public static final int GEAR_HANDLER_INTAKE = Constants.GEAR_HANDLER_INTAKE_ID;

	public static int DRIVE_SHIFT_SOLENOID;
	public static int INTAKE_SOLENOID;
	public static int HOPPER_EXTEND_SOLENOID;

	public static final int VISION_LIGHT = Constants.VISION_LIGHT_ID;
	public static final int GEAR_LIGHT = Constants.GEAR_LIGHT_ID;
	public static final int LED_STRIP = Constants.LED_STRIP_ID;

	public static int DRIVER_Y;
	public static int DRIVER_Z;
	public static int DRIVER_THROTTLE;

	public static int[] DRIVER_BUTTONS = new int[17];

	public static int OPERATOR_LEFT_AXIS_X, OPERATOR_LEFT_AXIS_Y,
			OPERATOR_RIGHT_AXIS_X, OPERATOR_RIGHT_AXIS_Y;

	public static int OPERATOR_BUTTON_A;
	public static int OPERATOR_BUTTON_X;
	public static int OPERATOR_BUTTON_Y;
	public static int OPERATOR_BUTTON_B;
	public static int OPERATOR_BUTTON_START;
	public static int OPERATOR_BUTTON_BACK;
	public static int OPERATOR_BUTTON_LEFT_BUMPER;
	public static int OPERATOR_BUTTON_RIGHT_BUMPER;
	public static int OPERATOR_BUTTON_LEFT_TRIGGER;
	public static int OPERATOR_BUTTON_RIGHT_TRIGGER;

	public static int NAVX_HEADING;

	public static int TURRET_LEFT_LIMIT_SWITCH;
	public static int TURRET_RIGHT_LIMIT_SWITCH;
	public static int GEAR_HANDLER_PRESENCE;

	public static int DRIVE_LEFT_POSITION;
	public static int DRIVE_RIGHT_POSITION;
	public static int DRIVE_LEFT_VELOCITY;
	public static int DRIVE_RIGHT_VELOCITY;
	public static int ELEVATOR_VELOCITY;
	public static int TURRET_VELOCITY;
	public static int TURRET_POSITION;
	public static int SHOOTER_VELOCITY;
	public static int GEAR_HANDLER_PIVOT_POSITION;
	public static int GEAR_HANDLER_PIVOT_VELOCITY;

	public static final PIDController elevatorController = new PIDController(),
			driveLeftController = new PIDController(),
			driveRightController = new PIDController(),
			headingController = new PIDController(),
			turretController = new PIDController(),
			gearHandlerController = new PIDController(),
			drivePIDRotationController = new PIDController();

	public static VelocityController elevatorVelocityController;
	public static int ELEVATOR_PROFILE_SPOOL, ELEVATOR_PROFILE_DEFAULT;

	public static FeedforwardVelocityController driveLeftVelocityController,
			driveRightVelocityController;
	public static int HEADING_PROFILE_DEFAULT, HEADING_PROFILE_DAMPENED,
			DRIVE_LEFT_PROFILE_DEFAULT, DRIVE_RIGHT_PROFILE_DEFAULT,
			DRIVE_PID_ROTATION_DEFAULT;

	public static FollowerController driveLeftFollowerController,
			driveRightFollowerController;
	public static int SHOOTER_PROFILE_SPOOL, SHOOTER_PROFILE_SHOOT;

	public static TalonVelocityController shooterVelocityController;

	public static PositionController turretPositionController,
			gearHandlerPositionController;
	public static int TURRET_PROFILE_DEFAULT, TURRET_PROFILE_VISION,
			GEAR_HANDLER_PIVOT_PROFILE_DEFAULT;

	public static TrapezoidTrajectoryController driveStraightController;
	public static CircleTrajectoryController driveCircleController;
	public static RotationController driveRotationController;

	public static void initialize() {
		in = new In();
		out = new Out(Constants.COMPRESSOR_ID);
		network = new NetworkImpl("1619-2017");
		state = new RobotState();

		State.link(in, out, network, state);

		out.motors.add(DRIVE_LEFT, true, true);
		out.motors.add(DRIVE_LEFT_2, true, true);
		out.motors.add(DRIVE_RIGHT, true, false);
		out.motors.add(DRIVE_RIGHT_2, true, false);
		out.motors.add(INTAKE, false, true);
		out.motors.add(HOPPER, false, false);
		out.motors.add(ELEVATOR, true, true);
		out.motors.add(TURRET, true, true);
		out.motors.add(SHOOTER, false, false);
		out.motors.add(SHOOTER_2, false, false);
		out.motors.add(CLIMBER, true, false);
		out.motors.add(GEAR_HANDLER_PIVOT, true, true);
		out.motors.add(GEAR_HANDLER_INTAKE, false, true);

		DRIVE_SHIFT_SOLENOID = out.solenoids.addSingle(Constants.DRIVE_SHIFT_SOLENOID_ID);
		INTAKE_SOLENOID = out.solenoids.addDual(Constants.INTAKE_EXTEND_SOLENOID_ID, Constants.INTAKE_RETRACT_SOLENOID_ID);
		HOPPER_EXTEND_SOLENOID = out.solenoids.addDual(Constants.HOPPER_EXTEND_EXTEND_SOLENOID_ID, Constants.HOPPER_EXTEND_RETRACT_SOLENOID_ID);

		out.relays.add(VISION_LIGHT);
		out.relays.add(GEAR_LIGHT);
		out.relays.add(LED_STRIP);

		Joystick driverJoystick = new Joystick(Constants.DRIVER_JOYSTICK_ID);

		DRIVER_Y = in.addNumeric(new JoystickAxisSensor(driverJoystick, Joystick.AxisType.kY, true), false);
		DRIVER_Z = in.addNumeric(new JoystickAxisSensor(driverJoystick, Joystick.AxisType.kZ, true), false);
		DRIVER_THROTTLE = in.addNumeric(new JoystickThrottleSensor(driverJoystick), false);

		JoystickButtonSensor[] driverButtons = JoystickButtonSensorFactory.getButtonSensors(driverJoystick, 16);
		for (int i = 1; i < driverButtons.length; i++) {
			DRIVER_BUTTONS[i] = in.addBoolean(driverButtons[i], true);
		}

		XboxController operatorController = new XboxController(Constants.OPERATOR_CONTROLLER_ID);
		out.linkXboxController(operatorController);

		OPERATOR_LEFT_AXIS_X = in.addNumeric(new ControllerAxisSensor(operatorController, Constants.OPERATOR_LEFT_AXIS_X_ID), false);
		OPERATOR_LEFT_AXIS_Y = in.addNumeric(new ControllerAxisSensor(operatorController, Constants.OPERATOR_LEFT_AXIS_Y_ID), false);
		OPERATOR_RIGHT_AXIS_X = in.addNumeric(new ControllerAxisSensor(operatorController, Constants.OPERATOR_RIGHT_AXIS_X_ID), false);
		OPERATOR_RIGHT_AXIS_Y = in.addNumeric(new ControllerAxisSensor(operatorController, Constants.OPERATOR_RIGHT_AXIS_Y_ID), false);

		OPERATOR_BUTTON_A = in.addBoolean(new ControllerButtonSensor(operatorController, ControllerButtonSensor.ControllerButton.A), true);
		OPERATOR_BUTTON_X = in.addBoolean(new ControllerButtonSensor(operatorController, ControllerButtonSensor.ControllerButton.X), true);
		OPERATOR_BUTTON_Y = in.addBoolean(new ControllerButtonSensor(operatorController, ControllerButtonSensor.ControllerButton.Y), true);
		OPERATOR_BUTTON_B = in.addBoolean(new ControllerButtonSensor(operatorController, ControllerButtonSensor.ControllerButton.B), true);
		OPERATOR_BUTTON_START = in.addBoolean(new ControllerButtonSensor(operatorController, ControllerButtonSensor.ControllerButton.START), true);
		OPERATOR_BUTTON_BACK = in.addBoolean(new ControllerButtonSensor(operatorController, ControllerButtonSensor.ControllerButton.BACK), true);
		OPERATOR_BUTTON_LEFT_BUMPER = in.addBoolean(new ControllerButtonSensor(operatorController, ControllerButtonSensor.ControllerButton.LEFT_BUMPER), true);
		OPERATOR_BUTTON_RIGHT_BUMPER = in.addBoolean(new ControllerButtonSensor(operatorController, ControllerButtonSensor.ControllerButton.RIGHT_BUMPER), true);
		OPERATOR_BUTTON_LEFT_TRIGGER = in.addBoolean(new ControllerButtonSensor(operatorController, ControllerButtonSensor.ControllerButton.LEFT_TRIGGER), true);
		OPERATOR_BUTTON_RIGHT_TRIGGER = in.addBoolean(new ControllerButtonSensor(operatorController, ControllerButtonSensor.ControllerButton.RIGHT_TRIGGER), true);

		NAVX_HEADING = in.addNumeric(new NavxHeadingSensor(), false);

		TURRET_LEFT_LIMIT_SWITCH = in.addBoolean(new DigitalInputSensor(Constants.TURRET_LEFT_LIMIT_SWITCH_ID, true), false);
		TURRET_RIGHT_LIMIT_SWITCH = in.addBoolean(new DigitalInputSensor(Constants.TURRET_RIGHT_LIMIT_SWITCH_ID, true), false);
		GEAR_HANDLER_PRESENCE = in.addBoolean(new DigitalInputSensor(Constants.GEAR_HANDLER_PRESENCE_ID, true), false);

		DRIVE_LEFT_POSITION = in.addNumeric(new EncoderPositionSensor(out.motors.get(DRIVE_LEFT), 100, 1.0 / 5.06, false), false);
		DRIVE_RIGHT_POSITION = in.addNumeric(new EncoderPositionSensor(out.motors.get(DRIVE_RIGHT), 100, 1.0 / 5.06, true), false);
		DRIVE_LEFT_VELOCITY = in.addNumeric(new EncoderVelocitySensor(out.motors.get(DRIVE_LEFT), 100, 1.0 / 5.06, false), false);
		DRIVE_RIGHT_VELOCITY = in.addNumeric(new EncoderVelocitySensor(out.motors.get(DRIVE_RIGHT), 100, 1.0 / 5.06, true), false);
		ELEVATOR_VELOCITY = in.addNumeric(new EncoderVelocitySensor(out.motors.get(ELEVATOR), 3, 60.0, false), false);
		TURRET_VELOCITY = in.addNumeric(new EncoderVelocitySensor(out.motors.get(TURRET), 1024, 11.0 / 260.0 * 360.0, true), false);
		TURRET_POSITION = in.addNumeric(new EncoderPositionSensor(out.motors.get(TURRET), 1024, 11.0 / 260.0 * 360.0, true), false);
		SHOOTER_VELOCITY = in.addNumeric(new EncoderVelocitySensor(out.motors.get(SHOOTER), 3, 60.0, false), false);
		GEAR_HANDLER_PIVOT_POSITION = in.addNumeric(new EncoderPositionSensor(out.motors.get(GEAR_HANDLER_PIVOT), 1024, 1.0 / 10.0 * 360.0, true), false);
		GEAR_HANDLER_PIVOT_VELOCITY = in.addNumeric(new EncoderVelocitySensor(out.motors.get(GEAR_HANDLER_PIVOT), 1024, 1.0 / 10.0 * 360.0, true), false);

		drive = new Drive(out, DRIVE_LEFT, DRIVE_LEFT_2, DRIVE_RIGHT, DRIVE_RIGHT_2);

		ELEVATOR_PROFILE_SPOOL = elevatorController.addProfile(1.0 / 15000.0, -1.0 / 50000.0, 0.0, 0.0);
		ELEVATOR_PROFILE_DEFAULT = elevatorController.addProfile(1.0 / 14000.0, 1.0 / 22500.0, 0.0, 1.0 / 20000.0);
		elevatorVelocityController = new VelocityController(elevatorController, out, in, ELEVATOR, ELEVATOR_VELOCITY, 500.0);

		HEADING_PROFILE_DEFAULT = headingController.addProfile(0.0, 0.075, 0.0, 0.0);
		HEADING_PROFILE_DAMPENED = headingController.addProfile(0.0, 0.065, 0.0, 0.0);

		DRIVE_PID_ROTATION_DEFAULT = drivePIDRotationController.addProfile(0.0, 0.09, 0.00001, 0.0, 0.25, 1000, 1.5);

		DRIVE_LEFT_PROFILE_DEFAULT = driveLeftController.addProfile(0.0, 0.3, 0.0, 0.15);
		driveLeftVelocityController = new FeedforwardVelocityController(driveLeftController, out, in, DRIVE_LEFT, DRIVE_LEFT_VELOCITY, 1.0 / 7.0, 1.0 / 35.0, headingController);
		DRIVE_RIGHT_PROFILE_DEFAULT = driveRightController.addProfile(0.0, 0.3, 0.0, 0.15);
		driveRightVelocityController = new FeedforwardVelocityController(driveRightController, out, in, DRIVE_RIGHT, DRIVE_RIGHT_VELOCITY, 1.0 / 7.0, 1.0 / 35.0, headingController);

		FollowerController shooterFollowerController = new FollowerController(out, SHOOTER_2, SHOOTER, false);
		shooterVelocityController = new ShooterController(out, in, SHOOTER, SHOOTER_VELOCITY, CANTalon.FeedbackDevice.QuadEncoder, true, true, shooterFollowerController);
		SHOOTER_PROFILE_SPOOL = shooterVelocityController.addProfile(0, 2.6, 2.5, 0.0, 2500.0);
		SHOOTER_PROFILE_SHOOT = shooterVelocityController.addProfile(0, 2.6, 50.0, 0.0, 250.0);

		driveLeftFollowerController = new FollowerController(out, DRIVE_LEFT_2, DRIVE_LEFT, false);
		driveRightFollowerController = new FollowerController(out, DRIVE_RIGHT_2, DRIVE_RIGHT, false);

		TURRET_PROFILE_DEFAULT = turretController.addProfile(0.0, 1.0 / (Constants.TURRET_RANGE / 4.0), 1.0 / (Constants.TURRET_RANGE * 2.0), 1.0 / Constants.TURRET_RANGE, 1.0, 7.5, 2.0);
		TURRET_PROFILE_VISION = turretController.addProfile(0.0, 1.0 / (Constants.TURRET_RANGE / 4.0), 1.0 / (Constants.TURRET_RANGE * 2.0), 1.0 / Constants.TURRET_RANGE, 0.5, 7.5, 2.0);

		turretPositionController = new BoundedPositionController(turretController, out, in, TURRET, TURRET_POSITION, 1.0, 2.0, TURRET_LEFT_LIMIT_SWITCH, TURRET_RIGHT_LIMIT_SWITCH, 2.5, Constants.TURRET_RANGE - 2.5);

		GEAR_HANDLER_PIVOT_PROFILE_DEFAULT = gearHandlerController.addProfile(0.0, 1.0 / 40.0, 0.0, 1.0 / 150.0, 0.5, 0.0, 0.0);
		gearHandlerPositionController = new BoundedPositionController(gearHandlerController, out, in, GEAR_HANDLER_PIVOT, GEAR_HANDLER_PIVOT_POSITION, 1.5, 1.5, -1, -1, 2.5, 118.0);

		driveStraightController = new TrapezoidTrajectoryController(driveLeftFollowerController, driveRightFollowerController, driveLeftVelocityController, driveRightVelocityController, in, DRIVE_LEFT_POSITION, DRIVE_RIGHT_POSITION, NAVX_HEADING);
		driveCircleController = new CircleTrajectoryController(driveLeftFollowerController, driveRightFollowerController, driveLeftVelocityController, driveRightVelocityController, in, DRIVE_LEFT_POSITION, DRIVE_RIGHT_POSITION, NAVX_HEADING);
		driveRotationController = new RotationController(driveLeftFollowerController, driveRightFollowerController, driveLeftVelocityController, driveRightVelocityController, drivePIDRotationController, in, NAVX_HEADING);
	}

}
