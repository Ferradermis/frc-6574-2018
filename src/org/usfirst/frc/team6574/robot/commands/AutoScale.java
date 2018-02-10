package org.usfirst.frc.team6574.robot.commands;

import org.usfirst.frc.team6574.robot.Constants;

public class AutoScale extends AutoCommand {
	
	@Override
	public void specificImplementation() {
		if (encoderDist < Constants.dist.WALL_TO_SCALE_SIDE) {
			
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
}
