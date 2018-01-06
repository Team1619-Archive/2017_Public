package org.usfirst.frc.team1619.robot.controllers;

import java.util.ArrayList;
import java.util.List;

public class Shots {

	private static final Shots instance;

	static {
		instance = new Shots();
	}

	public static Shots getInstance() {
		return instance;
	}

	private List<Shot> shots = new ArrayList<>();

	/**
	 * NOTE: shots must be added in ascending order by height
	 */
	public void addShot(Shot shot) {
		this.shots.add(shot);
	}

	public void reset() {
		this.shots = new ArrayList<>();
	}
	
	public Shot getShot(double height) {
		int index = 0;
		while (index < this.shots.size() && this.shots.get(index).height < height) {
			index++;
		}

		Shot shot;
		if (index == 0) {
			shot = this.shots.get(index);
		}
		else if (index == this.shots.size()) {
			shot = this.shots.get(this.shots.size() - 1);
		}
		else {
			Shot closer = this.shots.get(index);
			Shot farther = this.shots.get(index - 1);

			double slope = (farther.rpm - closer.rpm) / (farther.height - closer.height);
			double rpm = closer.rpm + slope * (height - closer.height);

			shot = new Shot(height, rpm, closer.hoodDeployed);
		}

		return shot;
	}

}
