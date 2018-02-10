package org.usfirst.frc.team6574.robot.commands;

import org.usfirst.frc.team6574.robot.Constants;
import org.usfirst.frc.team6574.robot.Robot;

public class AutoScale extends AutoCommand {
	
	@Override
	public void specificImplementation() {
		if (encoderDist < Constants.dist.WALL_TO_SCALE_SIDE) {
			Robot.drive.set(Constants.AUTO_SPEED);
		} else {
			Robot.drive.stop();
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
}
