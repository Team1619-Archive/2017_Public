package org.usfirst.frc.team1619.robot.framework.util;

import java.util.function.Consumer;

public class Promise<T> {

	private enum State {
		PENDING,
		FULFILLED,
		REJECTED,
		COMPLETED
	}

	private State state = State.PENDING;
	private T result;
	private String error;

	public void resolve(T result) {
		this.result = result;
		this.state = State.FULFILLED;
	}

	public void reject(String error) {
		this.error = error;
		this.state = State.REJECTED;
	}

	public void then(Consumer<T> thenConsumer, Consumer<String> catchConsumer) {
		this.then(thenConsumer, catchConsumer, null);
	}

	public void then(Consumer<T> thenConsumer, Consumer<String> catchConsumer, Consumer<Promise<T>> finallyConsumer) {
		if (this.state != State.PENDING && this.state != State.COMPLETED) {
			switch (this.state) {
				case FULFILLED:
					thenConsumer.accept(this.result);
					break;
				case REJECTED:
					if (catchConsumer != null) {
						catchConsumer.accept(this.error);
					}
					break;
			}

			if (finallyConsumer != null) {
				finallyConsumer.accept(this);
			}
			this.state = State.COMPLETED;
		}
	}

	public static <T> Promise<T> getResolved(T result) {
		Promise<T> promise = new Promise<>();
		promise.resolve(result);
		return promise;
	}

	public static <T> Promise<T> getRejected(String error) {
		Promise<T> promise = new Promise<>();
		promise.reject(error);
		return promise;
	}

}
