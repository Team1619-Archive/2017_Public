package org.usfirst.frc.team1619.robot.framework.state;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class ParallelState extends MultiSubsystemState {

	private Set<State> states = new HashSet<>();
	private Set<State> backgroundStates = new HashSet<>();

	protected abstract void addStates();

	protected void addState(State state) {
		this.states.add(state);
	}

	protected void addBackgroundState(State state) {
		this.backgroundStates.add(state);
	}

	@Override
	protected void initialize() {
		this.addStates();

		for (State state : this.states) {
			if (state instanceof MultiSubsystemState) {
				((MultiSubsystemState) state).bypassInitializeState();
			}
			else {
				state.initializeState(null);
			}
		}
		for (State state : this.backgroundStates) {
			if (state instanceof MultiSubsystemState) {
				((MultiSubsystemState) state).bypassInitializeState();
			}
			else {
				state.initializeState(null);
			}
		}
	}

	@Override
	protected void update() {
		Iterator<State> iterator = this.states.iterator();
		while (iterator.hasNext()) {
			State state = iterator.next();
			if (state.isDoneState()) {
				state.disposeState();
				iterator.remove();
				continue;
			}

			if (state instanceof MultiSubsystemState) {
				((MultiSubsystemState) state).bypassUpdateState();
			}
			else {
				state.updateState(null);
			}
		}

		for (State state : this.backgroundStates) {
			if (state instanceof MultiSubsystemState) {
				((MultiSubsystemState) state).bypassUpdateState();
			}
			else {
				state.updateState(null);
			}
		}
	}

	@Override
	protected void dispose() {
		for (State state : this.states) {
			state.disposeState();
		}
		for (State state : this.backgroundStates) {
			state.disposeState();
		}
	}

	@Override
	protected boolean isDone() {
		return this.states.isEmpty();
	}

}
