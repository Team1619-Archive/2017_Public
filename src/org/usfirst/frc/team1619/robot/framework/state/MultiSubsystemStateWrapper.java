package org.usfirst.frc.team1619.robot.framework.state;

public abstract class MultiSubsystemStateWrapper<T extends MultiSubsystemState> extends StateWrapper<T> {

	private T instance = null;

	public MultiSubsystemStateWrapper(Class<T> clazz) {
		super(clazz);
	}

	@Override
	public T createInstance() throws IllegalAccessException, InstantiationException {
		if (this.instance == null || this.instance.isDoneState()) {
			this.instance = super.createInstance();
		}

		return this.instance;
	}

}
