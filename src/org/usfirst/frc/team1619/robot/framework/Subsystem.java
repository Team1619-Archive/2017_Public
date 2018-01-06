package org.usfirst.frc.team1619.robot.framework;

import org.usfirst.frc.team1619.robot.SubsystemID;
import org.usfirst.frc.team1619.robot.framework.state.State;
import org.usfirst.frc.team1619.robot.framework.state.StateWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Subsystem {

	private SubsystemID id;

	private List<StateWrapper<?>> stateWrappers = new ArrayList<>();
	private State activeState = null;
	private int activeStateWrapperIndex = -1;

	public Subsystem(SubsystemID id) {
		Subsystem.subsystems.add(this);

		this.id = id;
	}

	public void addStateWrapper(StateWrapper<?> wrapper) {
		this.stateWrappers.add(wrapper);

		if (this.activeState == null) {
			this.activeStateWrapperIndex = this.stateWrappers.size();
		}

	}

	public void reset() {
		if (this.activeState != null) {
			this.activeState.disposeState();
		}

		this.stateWrappers = new ArrayList<>();
		this.activeState = null;
		this.activeStateWrapperIndex = -1;
	}

	public void update() {
		if (this.activeState != null && this.activeState.isDoneState()) {
			this.updateActiveState(null, -1);
		}

		for (int i = 0; i < this.activeStateWrapperIndex; i++) {
			StateWrapper<?> stateWrapper = this.stateWrappers.get(i);
			if (stateWrapper.isReady()) {
				try {
					this.updateActiveState(stateWrapper.createInstance(), i);
					break;
				}
				catch (IllegalAccessException | InstantiationException exception) {
					exception.printStackTrace();
				}
			}
		}

		if (this.activeState != null) {
			this.activeState.updateState(this.id);
		}

	}

	private void updateActiveState(State activeState, int activeStateWrapperIndex) {
		if (this.activeState != null) {
			this.activeState.disposeState();
		}

		this.activeState = activeState;
		if (this.activeState != null) {
			this.activeStateWrapperIndex = activeStateWrapperIndex;
			this.activeState.initializeState(this.id);
		}
		else {
			this.activeStateWrapperIndex = this.stateWrappers.size();
		}
	}

	private static Set<Subsystem> subsystems = new HashSet<Subsystem>();

	public static void resetAll() {
		for (Subsystem subsystem : Subsystem.subsystems) {
			subsystem.reset();
		}
	}

	public static void updateAll() {
		for (Subsystem subsystem : Subsystem.subsystems) {
			subsystem.update();
		}
	}

}
