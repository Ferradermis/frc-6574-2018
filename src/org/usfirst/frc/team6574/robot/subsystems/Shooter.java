package org.usfirst.frc.team6574.robot.subsystems;

import org.usfirst.frc.team6574.robot.Robot;
import org.usfirst.frc.team6574.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Shooter extends Subsystem {

	Spark left;
	Spark right;
	
	DoubleSolenoid lifter;
	DoubleSolenoid loader;
	
	public Shooter() {
		left = new Spark(RobotMap.shooter.LEFT_PWM_NUM);
		right = new Spark(RobotMap.shooter.RIGHT_PWM_NUM);
		
		lifter = new DoubleSolenoid(RobotMap.shooter.RAISE_PCN_ID, RobotMap.shooter.LOWER_PCN_ID);
		loader = new DoubleSolenoid(RobotMap.shooter.LOAD_PCN_ID, RobotMap.shooter.UNLOAD_PCN_ID);
	}
	
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
}
