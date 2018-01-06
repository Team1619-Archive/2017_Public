package org.usfirst.frc.team1619.robot.states.Drive;

import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.ParallelState;
import org.usfirst.frc.team1619.robot.framework.state.SequencerState;
import org.usfirst.frc.team1619.robot.framework.util.CircleTrajectory;
import org.usfirst.frc.team1619.robot.framework.util.TrapezoidTrajectory;
import org.usfirst.frc.team1619.robot.states.TimedState;

public class DriveBumpAndDump2 extends SequencerState {

	private boolean red;

	public DriveBumpAndDump2(boolean red) {
		this.red = red;
	}

	@Override
	protected void addSequence() {
		// this.add(new DriveStraight(new TrapezoidTrajectory(95.0 / 12.0, 7.5,
		// 7.5, false)));
		// this.add(new DriveCircleSmooth(new CircleTrajectory(42.0 / 12.0,
		// 95.0, false, 31.0 / 12.0, 8.0, 10.0, 10.0, false)));
		this.add(new DriveStraight(new TrapezoidTrajectory(this.red ? (49.0 / 12.0) : (47.5 / 12.0), 7.5, 7.5, !this.red)));
		this.add(new ParallelState() {

			@Override
			protected void addStates() {
				this.addState(new TimedState(2625));
				this.addBackgroundState(new DriveCircleSmooth(new CircleTrajectory(red ? (38.0 / 12.0) : (38.0 / 12.0), red ? 102.5 : 105.0, !red, 31.0 / 12.0, 8.0, 10.0, 10.0, !red)));
			}

			@Override
			protected SubsystemID[] getSubsystemIDs() {
				return new SubsystemID[0];
			}

		});
		if (!this.red) {
			this.add(new TimedState(100));
			this.add(new DriveRotate(8.75));
		}
	}

}
