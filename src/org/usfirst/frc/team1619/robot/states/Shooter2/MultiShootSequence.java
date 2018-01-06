package org.usfirst.frc.team1619.robot.states.Shooter2;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.IO;
import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.controllers.Shot;
import org.usfirst.frc.team1619.robot.controllers.Shots;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.framework.util.Promise;
import org.usfirst.frc.team1619.robot.states.Turret.TurretAlign;

public class MultiShootSequence extends ParallelState {

	private Shots shots = Shots.getInstance();
	private Shot shot;
	private Shot noVisionShot;
	private BoilerVision vision = BoilerVision.getInstance();
	private Promise<Double> height;

	private MultiFeed multiFeed;
	private MultiSpoolAndShoot multiSpoolAndShoot;
	private TurretAlign turretAlign = new TurretAlign(true, 1.0);

	public MultiShootSequence() {
		this(0.9, Constants.SHOOTING_ELEVATOR_FEED_SPEED);
	}

	public MultiShootSequence(Shot noVisionShot) {
		this(0.9, Constants.SHOOTING_ELEVATOR_FEED_SPEED, noVisionShot);
	}
	
	public MultiShootSequence(double hopperOutput, double elevatorOutput) {
		this(hopperOutput, elevatorOutput, null);
	}

	public MultiShootSequence(double hopperOutput, double elevatorOutput, Shot noVisionShot) {
		this.multiFeed = new MultiFeed(hopperOutput, 0.0);
		this.multiSpoolAndShoot = new MultiSpoolAndShoot(elevatorOutput);
		this.noVisionShot = noVisionShot;
	}
	
	private void onHeight(Double height) {
		if (in.getBoolean(IO.DRIVER_BUTTONS[3])) {
			this.shot = new Shot(0.0, (state.autoRed ? Constants.AUTO_BUMP_AND_DUMP_SHOT_RPM_RED : Constants.AUTO_BUMP_AND_DUMP_SHOT_RPM_BLUE) * state.shootingPercentage, false);
		}
		else {
			this.shot = this.shots.getShot(height);
			this.shot.rpm *= state.shootingPercentage;
		}
		this.multiSpoolAndShoot.setShot(this.shot);
		SmartDashboard.putNumber("_______HEIGHT", height);
		SmartDashboard.putNumber("_______SHOT", this.shot.rpm);
	}

	private void onError(String error) {
		this.height = this.vision.getHeight(true);
	}

	private void onFinally(Promise<Double> promise) {
		this.height = null;
	}

	protected boolean getSpooling() {
		return this.multiSpoolAndShoot.getSpooling() && IO.elevatorVelocityController.getTimeThere() > 250;
	}

	protected boolean getTurretAligned() {
		return this.turretAlign.getAligned();
	}

	@Override
	protected void update() {
		if (this.shot != null) {
			if (IO.in.getBooleanAndDelta(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_INCREASE_SHOOTING_PERCENTAGE], true)) {
				this.shot.rpm += 50;
				this.multiSpoolAndShoot.setShot(this.shot, false);
			}
			else if (IO.in.getBooleanAndDelta(IO.DRIVER_BUTTONS[Constants.DRIVER_BUTTON_DECREASE_SHOOTING_PERCENTAGE], true)) {
				this.shot.rpm -= 50;
				this.multiSpoolAndShoot.setShot(this.shot, false);
			}
		}

		out.relays.set(IO.VISION_LIGHT, true);

		if (in.getBooleanAndDelta(Constants.OPERATOR_BUTTON_INTAKE_TOGGLE, true)) {
			state.intakeExtended = !state.intakeExtended;
		}
		out.solenoids.set(IO.INTAKE_SOLENOID, state.intakeExtended);

		if (this.height != null) {
			this.height.then(this::onHeight, this::onError, this::onFinally);
		}

		boolean spooling = this.getSpooling();
		boolean aligned = this.getTurretAligned();
		SmartDashboard.putBoolean("_______SPOOLING", spooling);
		SmartDashboard.putBoolean("_______ALIGNED", aligned);
		if (aligned && this.shot == null && this.height == null) {
			if (this.noVisionShot == null) {
				this.height = this.vision.getHeight(true);
			}
			else {
				this.shot = this.noVisionShot;
				this.shot.rpm *= state.shootingPercentage;
				this.multiSpoolAndShoot.setShot(this.shot);
			}
		}
		else if (!aligned && this.shot != null) {
//			this.shot = null;
//			this.multiSpoolAndShoot.setShot(null);
//			this.height = null;
		}

		this.multiFeed.setFeeding(!spooling && aligned && this.shot != null);

		super.update();
	}

	@Override
	protected void dispose() {
		out.relays.set(IO.VISION_LIGHT, false);

		super.dispose();
	}

	@Override
	protected void addStates() {
		this.addState(this.multiFeed);
		this.addState(this.multiSpoolAndShoot);
		this.addState(this.turretAlign);
	}

	@Override
	protected SubsystemID[] getSubsystemIDs() {
		return new SubsystemID[] { SubsystemID.INTAKE, SubsystemID.HOPPER, SubsystemID.ELEVATOR, SubsystemID.TURRET, SubsystemID.SHOOTER };
	}

}
