package org.usfirst.frc.team1619.robot.framework.util;

import java.util.HashSet;
import java.util.Set;

public class MultiPromise<T> {

	private Set<Promise<T>> promises = new HashSet<>();

	public MultiPromise(Promise<T>... promises) {
		for (Promise<T> promise : promises) {
			this.promises.add(promise);
		}
	}

}
