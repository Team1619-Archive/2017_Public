package org.usfirst.frc.team1619.robot.framework.IO;

import edu.wpi.first.wpilibj.Relay;

import java.util.HashMap;
import java.util.Map;

public class Relays {

	private class RelayWrapper {

		public Relay relay;
		private boolean output = false;
		private boolean delta = true;

		public RelayWrapper(int id) {
			this.relay = new Relay(id);
		}

		public void set(boolean output) {
			if (!delta && output != this.output) {
				this.output = output;
				this.delta = true;
			}
		}

		public void flushValue() {
			if (this.delta) {
				this.relay.set(this.output ? Relay.Value.kForward : Relay.Value.kOff);
				this.delta = false;
			}
		}

	}

	private Map<Integer, RelayWrapper> relays = new HashMap<>();

	public void add(int id) {
		this.relays.put(id, new RelayWrapper(id));
	}

	public Relay get(int id) {
		return this.relays.get(id).relay;
	}

	public void set(int id, boolean output) {
		this.relays.get(id).set(output);
	}

	public void update() {
		for (RelayWrapper relay : this.relays.values()) {
			relay.flushValue();
		}
	}

	public void disable() {
		for (RelayWrapper relay : this.relays.values()) {
			relay.set(false);
		}
	}

}
