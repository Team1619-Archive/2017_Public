package org.usfirst.frc.team1619.robot.states.Shooter2;

import org.usfirst.frc.team1619.robot.Constants;
import org.usfirst.frc.team1619.robot.framework.state.MultiSubsystemStateWrapper;

public class MultiShootSequenceTeleop extends MultiShootSequence {

	public static final MultiSubsystemStateWrapper<MultiShootSequenceTeleop> WRAPPER = new MultiSubsystemStateWrapper<MultiShootSequenceTeleop>(MultiShootSequenceTeleop.class) {

		@Override
		public boolean isReady() {
			return in.getBoolean(Constants.OPERATOR_BUTTON_SHOOT);
		}

	};

	@Override
	protected boolean isDone() {
		return !in.getBoolean(Constants.OPERATOR_BUTTON_SHOOT);
	}

}
