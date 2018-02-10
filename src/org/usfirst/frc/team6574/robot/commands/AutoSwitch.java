package org.usfirst.frc.team6574.robot.commands;

import org.usfirst.frc.team6574.robot.Constants;
import org.usfirst.frc.team6574.robot.Robot;

public class AutoSwitch extends AutoCommand {

	@Override
	protected void specificImplementation() {
		if (encoderDist < Constants.dist.WALL_TO_SWITCH_SIDE) {
			Robot.drive.set(Constants.AUTO_SPEED);
		}
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
	
}
