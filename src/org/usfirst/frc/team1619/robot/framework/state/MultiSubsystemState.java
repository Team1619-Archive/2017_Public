package org.usfirst.frc.team1619.robot.framework.state;

import org.usfirst.frc.team1619.robot.SubsystemID;

import java.util.HashSet;
import java.util.Set;

public abstract class MultiSubsystemState extends State {

	private Set<SubsystemID> requiredSubsystems = new HashSet<>();
	private SubsystemID lastSubsystemID;
	private boolean active = false;
	private boolean interrupted = false;

	public MultiSubsystemState() {
		for (SubsystemID id : this.getSubsystemIDs()) {
			this.requiredSubsystems.add(id);
		}
	}

	protected abstract SubsystemID[] getSubsystemIDs();

	@Override
	public void initializeState(SubsystemID id) {
		this.requiredSubsystems.remove(id);
		if (this.requiredSubsystems.isEmpty()) {
			this.active = true;
			this.lastSubsystemID = id;
			super.initializeState(id);
		}
	}

	/**
	 * Bypass MultiSubsystemState initializeState logic requiring multiple calls for all required SubsystemID values
	 * NOTE: must call bypassUpdateState to update
	 */
	public void bypassInitializeState() {
		this.requiredSubsystems.clear();
		this.active = true;
		super.initializeState(null);
	}

	@Override
	public void updateState(SubsystemID id) {
		if (this.active && id == lastSubsystemID) {
			super.updateState(id);
		}
	}

	/**
	 * Bypass MultiSubsystemState updateState logic requiring multiple calls for all required SubsystemID values
	 */
	public void bypassUpdateState() {
		super.updateState(null);
	}

	@Override
	public boolean isDoneState() {
		return this.interrupted || super.isDoneState();
	}

	@Override
	public void disposeState() {
		if (!this.interrupted) {
			this.interrupted = true;
			super.disposeState();
		}
	}

}
