package org.usfirst.frc.team1619.robot.framework.util.stabilizers;

public class NumericStabilizer extends Stabilizer<Double> {

	private double sum;

	public NumericStabilizer(int size) {
		super(size);
	}

	@Override
	public Double push(Double value) {
		this.sum += value;

		Double removedValue = super.push(value);
		if (removedValue != null) {
			this.sum -= removedValue;
		}

		return removedValue;
	}

	@Override
	public void reset() {
		super.reset();

		this.sum = 0.0;
	}

	@Override
	public Double get() {
		int size = this.values.size();
		return size == 0 ? 0.0 : this.sum / size;
	}

}
