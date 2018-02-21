package org.usfirst.frc.team6574.robot.subsystems;

import org.usfirst.frc.team6574.robot.Robot;
import org.usfirst.frc.team6574.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {

	Spark left1;
	Spark left2;
	Spark right1;
	Spark right2;
	
	DoubleSolenoid lifter;
	DoubleSolenoid loader;
	
	public Shooter() {
		left1 = new Spark(RobotMap.shooter.LEFT_1_PWM_NUM);
		left2 = new Spark(RobotMap.shooter.LEFT_2_PWM_NUM);
		right1 = new Spark(RobotMap.shooter.RIGHT_1_PWM_NUM);
		right2 = new Spark(RobotMap.shooter.RIGHT_2_PWM_NUM);
		
		lifter = new DoubleSolenoid(RobotMap.shooter.RAISE_PCN_ID, RobotMap.shooter.LOWER_PCN_ID);
		loader = new DoubleSolenoid(RobotMap.shooter.LOAD_PCN_ID, RobotMap.shooter.UNLOAD_PCN_ID);
		
		lower();
		unload();
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
	
	public void spin(double val) {
		left1.set(-val);
		left2.set(-val);
		right1.set(val);
		right2.set(val);
	}
	
	public void stop() {
		left1.stopMotor();
		left2.stopMotor();
		right1.stopMotor();
		right2.stopMotor();
	}
	
	public void load() {
		loader.set(Value.kForward);
	}
	
	public void unload() {
		loader.set(Value.kReverse);
	}
	
	public void raise() {
		lifter.set(Value.kForward);
		Robot.intake.deploy();
	}
	
	public void lower() {
		lifter.set(Value.kForward);
	}
	
	public boolean getRaised() {
		return lifter.get() == Value.kForward;
	}
	
	public boolean getLoaded() {
		return loader.get() == Value.kForward;
	}
	
}
