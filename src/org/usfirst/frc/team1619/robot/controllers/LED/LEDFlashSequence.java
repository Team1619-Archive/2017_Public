package org.usfirst.frc.team1619.robot.controllers.LED;

import org.usfirst.frc.team1619.robot.framework.util.Timer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class LEDFlashSequence extends LEDState {

	private List<Integer> flashSequence = new ArrayList<>();
	private Iterator<Integer> flashSequenceIterator;
	private boolean loop;
	private int index = 0;
	private Timer timer = new Timer();
	private boolean done = false;

	public LEDFlashSequence(boolean loop) {
		this.loop = loop;
	}

	protected abstract int[] getFlashSequence();

	private void start() {
		this.flashSequenceIterator = this.flashSequence.iterator();
		this.timer.start(this.flashSequenceIterator.next());
		this.index = 0;
	}

	@Override
	public void initialize() {
		for (int duration : this.getFlashSequence()) {
			this.flashSequence.add(duration);
		}
		this.start();
	}

	@Override
	public void update() {
		if (this.timer.isDone()) {
			if (this.flashSequenceIterator.hasNext()) {
				this.timer.start(this.flashSequenceIterator.next());
				this.index++;
			}
			else if (this.loop) {
				this.start();
			}
			else {
				this.done = true;
			}
		}

		this.set(this.index % 2 == 1);
	}

	@Override
	public boolean isDone() {
		return this.done;
	}

	@Override
	public void dispose() {

	}

}
