package org.usfirst.frc.team1619.robot.framework;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.usfirst.frc.team1619.robot.IO;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class RobotBase extends IterativeRobot {

	private RobotUpdate robotUpdate = new RobotUpdate(this);
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	@Override
	public void robotInit() {
		IO.initialize();

		scheduler.scheduleAtFixedRate(this.robotUpdate, 8, 8, TimeUnit.MILLISECONDS);
	}

	@Override
	public void disabledInit() {
		this.robotUpdate.setMode(RobotMode.DISABLED);
	}

	@Override
	public void autonomousInit() {
		this.robotUpdate.setMode(RobotMode.AUTONOMOUS);
	}

	@Override
	public void teleopInit() {
		this.robotUpdate.setMode(RobotMode.TELEOP);
	}

	public void threadDisabledInit() {
		Subsystem.resetAll();
		IO.out.disable();
	}

	public void threadAutonomousInit() {
		this.robotUpdate.setMode(RobotMode.AUTONOMOUS);
	}

	public void threadTeleopInit() {
		this.robotUpdate.setMode(RobotMode.TELEOP);
	}

	public void threadTestInit() {}

	public void threadUpdate(RobotMode mode) {
		IO.in.update();
		IO.state.update();
		if (mode != RobotMode.DISABLED) {
			Subsystem.updateAll();
			IO.out.update();
		}
	}

}
