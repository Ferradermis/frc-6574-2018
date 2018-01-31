package org.usfirst.frc.team6574.robot.subsystems;

import org.usfirst.frc.team6574.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {

	Spark left = new Spark(RobotMap.shooter.LEFT_PWM_NUM);
	Spark right = new Spark(RobotMap.shooter.RIGHT_PWM_NUM);
	
	@Override
	protected void initDefaultCommand() {
		
	}
	
	public void spin(double val) {
		left.set(-val);
		right.set(val);
	}
	
	public void spinForward(double val) {
		left.set(-val);
		right.set(val);
	}
	
	public void spinBackward(double val) {
		left.set(val);
		right.set(-val);
	}
	
	public void stop() {
		left.stopMotor();
		right.stopMotor();
	}
	
}
