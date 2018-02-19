package org.usfirst.frc.team6574.robot.subsystems;

import org.usfirst.frc.team6574.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Conveyor extends Subsystem {

	Spark leftRoller;
	Spark rightRoller;
	
	/**
	 * Constructs a conveyor subsystem for the robot.
	 */
	public Conveyor() {
		leftRoller = new Spark(RobotMap.conveyor.LEFT_PWM_NUM);
		rightRoller = new Spark(RobotMap.conveyor.RIGHT_PWM_NUM);
	}

	@Override
	protected void initDefaultCommand() {
		
	}
	
	/**
	 * Spins the conveyor's rollers.
	 * 
	 * @param speed	 a double containing the percent output
	 */
	public void spin(double speed) {
		leftRoller.set(speed);
		rightRoller.set(-speed);
	}

	/**
	 * Stops all of the intake's motors.
	 */
	public void stop() {
		leftRoller.stopMotor();
		rightRoller.stopMotor();
	}
	
}
