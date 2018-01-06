package org.usfirst.frc.team1619.robot.framework.util.stabilizers;

import java.util.ArrayList;
import java.util.List;

public abstract class Stabilizer<T> {

	protected List<T> values = new ArrayList<>();
	private int size;
	private int count = 0;

	public Stabilizer(int size) {
		this.size = size;
	}

	public T push(T value) {
		this.count++;

		this.values.add(value);
		if (this.values.size() > this.size) {
			return this.values.remove(0);
		}

		return null;
	}

	public void reset() {
		this.values = new ArrayList<>();
	}

	public abstract T get();

	public int getReferencePoint() {
		return this.count;
	}

	public boolean isExpired(int referencePoint) {
		return this.count - referencePoint >= this.size;
	}

}
