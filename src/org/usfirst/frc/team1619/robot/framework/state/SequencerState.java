package org.usfirst.frc.team1619.robot.framework.state;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class SequencerState extends State {

	private List<State> sequence = new ArrayList<>();
	private Iterator<State> sequenceIterator;
	private boolean initialized;
	private State current;

	protected abstract void addSequence();

	protected void add(State state) {
		assert !(state instanceof MultiSubsystemState);
		this.sequence.add(state);
	}

	/**
	 * @return false if sequence has been completed
	 */
	private boolean advanceSequence() {
		if (this.current != null) {
			this.current.disposeState();
		}

		if (this.sequenceIterator.hasNext()) {
			this.current = this.sequenceIterator.next();
			this.current.initializeState(null);
			return true;
		}
		else {
			this.current = null;
			return false;
		}
	}

	@Override
	protected void initialize() {
		this.initialized = true;

		this.addSequence();
		this.sequenceIterator = this.sequence.iterator();
		this.advanceSequence();
	}

	@Override
	protected void update() {
		if (this.current != null) {
			if (this.current.isDoneState()) {
				if (!this.advanceSequence()) {
					return;
				}
			}

			this.current.updateState(null);
		}
	}

	@Override
	protected boolean isDone() {
		return this.current == null && this.initialized;
	}

	@Override
	protected void dispose() {
		if (this.current != null) {
			this.current.disposeState();
		}
	}

}
