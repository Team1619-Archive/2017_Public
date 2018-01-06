package org.usfirst.frc.team1619.robot.framework.util.stabilizers;

import org.usfirst.frc.team1619.robot.framework.util.Promise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Stabilizer2 {

	private List<Double> values = new ArrayList<>();
	private double sum = 0.0;
	private Set<Promise<Double>> notFilledPromises = new HashSet<>();
	private int size;

	public Stabilizer2(int size) {
		this.size = size;
	}

	public void push(double value) {
		this.values.add(value);
		this.sum += value;
		if (this.values.size() > this.size) {
			this.sum -= this.values.remove(0);
		}

		if (this.notFilledPromises.size() > 0 && this.isFilled()) {
			for (Promise<Double> promise : this.notFilledPromises) {
				promise.resolve(this.getValue());
			}
			this.notFilledPromises.clear();
		}
	}

	public void reset() {
		if (this.values.size() > 0) {
			this.values = new ArrayList<>();
			this.sum = 0.0;
			this.notFilledPromises = new HashSet<>();
		}
	}

	public boolean isFilled() {
		return this.values.size() == this.size;
	}

	private double getValue() {
		assert this.isFilled();

		return this.sum / this.size;
	}

	public Promise<Double> get() {
		if (this.isFilled()) {
			return Promise.getResolved(this.getValue());
		}
		else {
			Promise<Double> promise = new Promise<>();
			this.notFilledPromises.add(promise);
			return promise;
		}
	}

}
