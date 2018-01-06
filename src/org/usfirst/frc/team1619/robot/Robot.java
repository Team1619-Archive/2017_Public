package org.usfirst.frc.team1619.robot;

import org.usfirst.frc.team1619.robot.controllers.Shot;
import org.usfirst.frc.team1619.robot.controllers.Shots;
import org.usfirst.frc.team1619.robot.controllers.Vision;
import org.usfirst.frc.team1619.robot.controllers.LED.LEDs;
import org.usfirst.frc.team1619.robot.framework.RobotBase;
import org.usfirst.frc.team1619.robot.framework.RobotMode;
import org.usfirst.frc.team1619.robot.framework.Subsystem;
import org.usfirst.frc.team1619.robot.states.Climber.ClimberManual;
import org.usfirst.frc.team1619.robot.states.Drive.DriveManual;
import org.usfirst.frc.team1619.robot.states.Drive.DriveWiggleTeleop;
import org.usfirst.frc.team1619.robot.states.Elevator.ElevatorManual;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlerIdle;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlerIntake;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlerPlacement;
import org.usfirst.frc.team1619.robot.states.GearHandler.GearHandlerZero;
import org.usfirst.frc.team1619.robot.states.Hopper.HopperManual;
import org.usfirst.frc.team1619.robot.states.HopperExtension.HopperExtensionIdle;
import org.usfirst.frc.team1619.robot.states.Intake.IntakeExtend;
import org.usfirst.frc.team1619.robot.states.Intake.IntakeManual;
import org.usfirst.frc.team1619.robot.states.Intake.IntakeRetract;
import org.usfirst.frc.team1619.robot.states.Multi.MultiAutoSequence;
import org.usfirst.frc.team1619.robot.states.Multi.MultiHopperExtensionExtend;
import org.usfirst.frc.team1619.robot.states.Multi.MultiInitialize;
import org.usfirst.frc.team1619.robot.states.Multi.MultiOperatorManual;
import org.usfirst.frc.team1619.robot.states.Shooter.ShooterManual;
import org.usfirst.frc.team1619.robot.states.Shooter2.BoilerVision;
import org.usfirst.frc.team1619.robot.states.Shooter2.MultiShootSequenceTeleop;
import org.usfirst.frc.team1619.robot.states.Turret.TurretAlignTeleop;
import org.usfirst.frc.team1619.robot.states.Turret.TurretIdle;
import org.usfirst.frc.team1619.robot.states.Turret.TurretIdleIntakeRetracted;
import org.usfirst.frc.team1619.robot.states.Turret.TurretZero;

public class Robot extends RobotBase {

	private Vision vision;
	private BoilerVision boilerVision;
	private LEDs leds;
	private Subsystem drive, intake, hopper, elevator, turret, shooter, climber, gearHandler, hopperExtension;

	@Override
	public void robotInit() {
		super.robotInit();

		this.vision = Vision.getInstance();
		this.vision.configure(Constants.VISION_STABILIZER_COUNT, Constants.VISION_STABILIZER_COUNT, Constants.VISION_PIXEL_TO_ANGLE_RATIO, Constants.VISION_CAMERA_ANGLE_OFFSET);

		this.boilerVision = BoilerVision.getInstance();

		this.leds = LEDs.getInstance();

		this.drive = new Subsystem(SubsystemID.DRIVE);
		this.intake = new Subsystem(SubsystemID.INTAKE);
		this.hopper = new Subsystem(SubsystemID.HOPPER);
		this.elevator = new Subsystem(SubsystemID.ELEVATOR);
		this.turret = new Subsystem(SubsystemID.TURRET);
		this.shooter = new Subsystem(SubsystemID.SHOOTER);
		this.climber = new Subsystem(SubsystemID.CLIMBER);
		this.gearHandler = new Subsystem(SubsystemID.GEAR_HANDLER);
		this.hopperExtension = new Subsystem(SubsystemID.HOPPER_EXTENSION);
	}

	@Override
	public void threadTeleopInit() {
		super.threadTeleopInit();

		Shots shots = Shots.getInstance();
		shots.reset();
		shots.addShot(new Shot(0.296, 10900.0, false));
		shots.addShot(new Shot(0.33, 10380.0, false));
		shots.addShot(new Shot(0.43, 9820.0, false));
		shots.addShot(new Shot(0.49, 9680.0, false));
		shots.addShot(new Shot(0.525, 9547.0, false));
		shots.addShot(new Shot(0.67, 9350.0, false));
		
		this.drive.addStateWrapper(DriveWiggleTeleop.WRAPPER);
		this.drive.addStateWrapper(DriveManual.WRAPPER);

		this.turret.addStateWrapper(MultiInitialize.WRAPPER);
		this.turret.addStateWrapper(MultiOperatorManual.WRAPPER);
		this.turret.addStateWrapper(TurretZero.WRAPPER);
		this.turret.addStateWrapper(MultiShootSequenceTeleop.WRAPPER);
		this.turret.addStateWrapper(TurretAlignTeleop.WRAPPER);
		this.turret.addStateWrapper(TurretIdleIntakeRetracted.WRAPPER);
		this.turret.addStateWrapper(TurretIdle.WRAPPER);

		this.intake.addStateWrapper(MultiInitialize.WRAPPER);
		this.intake.addStateWrapper(MultiShootSequenceTeleop.WRAPPER);
		this.intake.addStateWrapper(IntakeRetract.WRAPPER);
		this.intake.addStateWrapper(IntakeExtend.WRAPPER);
		this.intake.addStateWrapper(IntakeManual.WRAPPER);

		this.hopper.addStateWrapper(MultiShootSequenceTeleop.WRAPPER);
		this.hopper.addStateWrapper(HopperManual.WRAPPER);

		this.elevator.addStateWrapper(MultiShootSequenceTeleop.WRAPPER);
		this.elevator.addStateWrapper(ElevatorManual.WRAPPER);

		this.shooter.addStateWrapper(MultiShootSequenceTeleop.WRAPPER);
		this.shooter.addStateWrapper(ShooterManual.WRAPPER);

		this.climber.addStateWrapper(ClimberManual.WRAPPER);

		this.gearHandler.addStateWrapper(MultiInitialize.WRAPPER);
		this.gearHandler.addStateWrapper(MultiOperatorManual.WRAPPER);
		this.gearHandler.addStateWrapper(GearHandlerZero.WRAPPER);
		this.gearHandler.addStateWrapper(GearHandlerPlacement.WRAPPER);
		this.gearHandler.addStateWrapper(GearHandlerIntake.WRAPPER);
		this.gearHandler.addStateWrapper(MultiHopperExtensionExtend.WRAPPER);
		this.gearHandler.addStateWrapper(GearHandlerIdle.WRAPPER);

		this.hopperExtension.addStateWrapper(MultiInitialize.WRAPPER);
		this.hopperExtension.addStateWrapper(MultiHopperExtensionExtend.WRAPPER);
		this.hopperExtension.addStateWrapper(HopperExtensionIdle.WRAPPER);
	}

	@Override
	public void threadAutonomousInit() {
		super.threadAutonomousInit();

		Shots shots = Shots.getInstance();
		shots.reset();
		shots.addShot(new Shot(39.0, 10000.0, false));

		this.drive.addStateWrapper(MultiAutoSequence.WRAPPER);
		this.intake.addStateWrapper(MultiAutoSequence.WRAPPER);
		this.hopper.addStateWrapper(MultiAutoSequence.WRAPPER);
		this.elevator.addStateWrapper(MultiAutoSequence.WRAPPER);
		this.shooter.addStateWrapper(MultiAutoSequence.WRAPPER);
		this.turret.addStateWrapper(MultiAutoSequence.WRAPPER);
		this.gearHandler.addStateWrapper(MultiAutoSequence.WRAPPER);
		this.hopperExtension.addStateWrapper(MultiAutoSequence.WRAPPER);
		this.hopperExtension.addStateWrapper(HopperExtensionIdle.WRAPPER);
	}

	@Override
	public void threadTestInit() {
		super.threadTestInit();

		IO.out.relays.set(IO.VISION_LIGHT, true);
	}

	@Override
	public void threadUpdate(RobotMode mode) {
		super.threadUpdate(mode);

		if (mode == RobotMode.DISABLED) {
			IO.state.updateAuto();
		}

		this.vision.update();
		this.boilerVision.update();
		this.leds.update();
	}

}
