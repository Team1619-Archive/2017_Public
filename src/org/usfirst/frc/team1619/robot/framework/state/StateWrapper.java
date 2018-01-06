package org.usfirst.frc.team1619.robot.framework.state;

public abstract class StateWrapper<T extends State> {

	private Class<T> clazz;

	public StateWrapper(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T createInstance() throws IllegalAccessException, InstantiationException {
		return this.clazz.newInstance();
	}

	public abstract boolean isReady();

}
