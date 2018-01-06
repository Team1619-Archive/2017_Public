package org.usfirst.frc.team1619.robot.framework.IO;

import org.usfirst.frc.team1619.robot.framework.IO.sensors.Sensor;

import java.util.ArrayList;
import java.util.List;

public class In {

	private abstract class SensorWrapper<T> {

		private boolean trackDeltas;
		public Sensor<T> sensor;
		public T value, delta;

		public SensorWrapper(Sensor<T> sensor, boolean trackDeltas) {
			this.sensor = sensor;
			this.trackDeltas = trackDeltas;
		}

		public void update() {
			T value = this.sensor.get();
			if (this.trackDeltas) {
				this.delta = this.computeDelta(value);
			}
			this.value = value;
		}

		protected abstract T computeDelta(T value);

	}

	private class GenericSensorWrapper<T> extends SensorWrapper<T> {

		public GenericSensorWrapper(Sensor<T> sensor) {
			super(sensor, false);
		}

		@Override
		protected T computeDelta(T value) {
			return null;
		}
	}

	private class BooleanSensorWrapper extends SensorWrapper<Boolean> {

		public BooleanSensorWrapper(Sensor<Boolean> sensor, boolean trackDeltas) {
			super(sensor, trackDeltas);
		}

		@Override
		protected Boolean computeDelta(Boolean value) {
			return value != this.value;
		}

	}

	private class NumericSensorWrapper extends SensorWrapper<Double> {

		public NumericSensorWrapper(Sensor<Double> sensor, boolean trackDeltas) {
			super(sensor, trackDeltas);
		}

		@Override
		protected Double computeDelta(Double value) {
			return value - this.value;
		}

	}

	private List<SensorWrapper<?>> genericSensors = new ArrayList<>();
	private List<SensorWrapper<Boolean>> booleanSensors = new ArrayList<>();
	private List<SensorWrapper<Double>> numericSensors = new ArrayList<>();

	public <T> int addGeneric(Sensor<T> sensor) {
		this.genericSensors.add(new GenericSensorWrapper<T>(sensor));
		return this.genericSensors.size() - 1;
	}

	public int addBoolean(Sensor<Boolean> sensor, boolean trackDeltas) {
		this.booleanSensors.add(new BooleanSensorWrapper(sensor, trackDeltas));
		return this.booleanSensors.size() - 1;
	}

	public int addNumeric(Sensor<Double> sensor, boolean trackDeltas) {
		this.numericSensors.add(new NumericSensorWrapper(sensor, trackDeltas));
		return this.numericSensors.size() - 1;
	}

	public <T> Sensor<T> getGenericSensor(int index) {
		return (Sensor<T>) this.genericSensors.get(index).sensor;
	}

	public <T> T getGeneric(int index) {
		return ((GenericSensorWrapper<T>) this.genericSensors.get(index)).value;
	}

	public Sensor<Boolean> getBooleanSensor(int index) {
		return this.booleanSensors.get(index).sensor;
	}

	public boolean getBoolean(int index) {
		return this.booleanSensors.get(index).value;
	}

	public boolean getBooleanDelta(int index) {
		return this.booleanSensors.get(index).delta;
	}

	public boolean getBooleanAndDelta(int index, boolean value) {
		return this.getBoolean(index) == value && this.getBooleanDelta(index);
	}

	public Sensor<Double> getNumericSensor(int index) {
		return this.numericSensors.get(index).sensor;
	}

	public double getNumeric(int index) {
		return this.numericSensors.get(index).value;
	}

	public double getNumericDelta(int index) {
		return this.numericSensors.get(index).delta;
	}

	public void update() {
		for (SensorWrapper<?> sensorWrapper : this.genericSensors) {
			sensorWrapper.update();
		}
		for (SensorWrapper<Boolean> sensorWrapper : this.booleanSensors) {
			sensorWrapper.update();
		}
		for (SensorWrapper<Double> sensorWrapper : this.numericSensors) {
			sensorWrapper.update();
		}
	}

}
