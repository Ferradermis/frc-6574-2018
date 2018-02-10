package org.usfirst.frc.team6574.robot.commands;

import org.usfirst.frc.team6574.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public abstract class AutoCommand extends Command {

	double encoderDist;
	int position;
	
	public AutoCommand() {
		requires(Robot.drive);
	}
	
	@Override
	protected void initialize() {
		encoderDist = 0;
		position = DriverStation.getInstance().getLocation();
	}
	
	@Override
	protected void execute() {
		encoderDist = Robot.drive.getEncoderDist();
		specificImplementation();
	}
	
	protected abstract void specificImplementation();

	@Override
	protected abstract boolean isFinished();
	

	@Override
	protected void end() {
		
	}

	@Override
	protected void interrupted() {
			
	}

}
