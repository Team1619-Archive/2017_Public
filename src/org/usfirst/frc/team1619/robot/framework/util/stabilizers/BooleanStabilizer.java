package org.usfirst.frc.team1619.robot.framework.util.stabilizers;

public class BooleanStabilizer extends Stabilizer<Boolean> {

	private int trueCount = 0;

	public BooleanStabilizer(int size) {
		super(size);
	}

	@Override
	public Boolean push(Boolean value) {
		if (value) {
			this.trueCount++;
		}

		Boolean removedValue = super.push(value);
		if (removedValue != null && removedValue) {
			this.trueCount--;
		}

		return super.push(value);
	}

	@Override
	public Boolean get() {
		int size = this.values.size();
		return size == 0 ? false : (double) this.trueCount / size >= 0.5;
	}

}
