package org.usfirst.frc.team1619.robot.framework.IO;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public abstract class Network implements ITableListener {

	private NetworkTable table, preferences;

	protected Network(String name) {
		this.table = NetworkTable.getTable(name);
		this.table.addTableListener(this);
	}

	@Override
	public void valueChanged(ITable source, String key, Object value, boolean isNew) {
		this.handleValueChange(key, value, isNew);
	}

	protected abstract void handleValueChange(String key, Object value, boolean isNew);

}
