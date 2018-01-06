package org.usfirst.frc.team1619.robot.framework.IO;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import org.usfirst.frc.team1619.robot.framework.util.Timer;

import java.util.ArrayList;
import java.util.List;

public class Solenoids {

	private interface SolenoidWrapper {

		void set(boolean output);

		void flushOutput();

		void reset();

	}

	private class SingleSolenoidWrapper implements SolenoidWrapper {

		public Solenoid solenoid;
		private boolean output = false;

		public SingleSolenoidWrapper(int id) {
			this.solenoid = new Solenoid(id);
		}

		public void set(boolean output) {
			this.output = output;
		}

		public void flushOutput() {
			this.solenoid.set(this.output);
		}

		public void reset() {
			this.output = false;
		}

	}

	private class DualSolenoidWrapper implements SolenoidWrapper {

		public Solenoid onSolenoid, offSolenoid;
		private boolean first = true;
		private boolean output = false;
		private boolean delta = false;
		private Timer energizingTimer = new Timer();

		public DualSolenoidWrapper(int onID, int offID) {
			this.onSolenoid = new Solenoid(onID);
			this.offSolenoid = new Solenoid(offID);
		}

		public void set(boolean output) {
			if (!this.delta && output != this.output || this.first) {
				this.first = false;
				this.delta = true;
				this.output = output;
				this.energizingTimer.start(1000);
			}
		}

		public void flushOutput() {
			if (this.delta) {
				(this.output ? this.offSolenoid : this.onSolenoid).set(false);
				this.delta = false;
			}

			if (this.energizingTimer.isStarted()) {
				Solenoid solenoid = this.output ? this.onSolenoid : this.offSolenoid;
				if (this.energizingTimer.isDone()) {
					solenoid.set(false);
					this.energizingTimer.reset();
				}
				else {
					solenoid.set(true);
				}
			}
		}

		public void reset() {
			this.first = true;
			this.output = false;
			this.delta = false;
			this.energizingTimer.reset();
		}

	}

	private List<SolenoidWrapper> solenoids = new ArrayList<>();
	private Compressor compressor;

	public Solenoids(int compressorID) {
		this.compressor = new Compressor(compressorID);
		this.compressor.setClosedLoopControl(true);
		this.compressor.clearAllPCMStickyFaults();
	}

	public int addSingle(int id) {
		this.solenoids.add(new SingleSolenoidWrapper(id));
		return this.solenoids.size() - 1;
	}

	public int addDual(int onID, int offID) {
		this.solenoids.add(new DualSolenoidWrapper(onID, offID));
		return this.solenoids.size() - 1;
	}

	public void set(int id, boolean output) {
		this.solenoids.get(id).set(output);
	}

	public void update() {
		for (SolenoidWrapper solenoid : this.solenoids) {
			solenoid.flushOutput();
		}
	}

	public void disable() {
		for (SolenoidWrapper solenoid : this.solenoids) {
			solenoid.reset();
		}
	}

	public void disableCompressor() {
		this.compressor.setClosedLoopControl(false);
		this.compressor.stop();
	}

	public void enableCompressor() {
		this.compressor.setClosedLoopControl(true);
	}

}
