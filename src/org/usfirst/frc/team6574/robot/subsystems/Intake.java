package org.usfirst.frc.team6574.robot.subsystems;

import org.usfirst.frc.team6574.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Intake extends Subsystem {

	Spark leftArm;
	Spark rightArm;
	
	DoubleSolenoid deploy;
	
	/**
	 * Constructs an intake subsystem for the robot.
	 */
	public Intake() {
		leftArm = new Spark(RobotMap.intake.LEFT_PWM_NUM);
		rightArm = new Spark(RobotMap.intake.RIGHT_PWM_NUM);
		
		deploy = new DoubleSolenoid(RobotMap.intake.DEPLOY_PCN_ID, RobotMap.intake.RETRACT_PCN_ID);
	}

	@Override
	protected void initDefaultCommand() {
		
	}
	
	/**
	 * Extends the robot's intake mechanism using a pneumatic piston.
	 */
	public void deploy() {
		deploy.set(Value.kReverse);
	}
	
	/**
	 * Retracts the robot's intake mechanism using a pneumatic piston.
	 */
	public void retract() {
		deploy.set(Value.kForward);
	}
	
	/**
	 * Alternates deployed and retracted status of the robot's intake mechanism.
	 */
	public void toggleDeploy() {
		if (deploy.get() == Value.kReverse) {
			retract();
		} else {
			deploy();
		}
	}
	
	/**
	 * Spins the intake's arm rollers.
	 * 
	 * @param speed	 a double containing the percent output
	 */
	public void spin(double speed) {
		leftArm.set(speed);
		rightArm.set(-speed);
	}
	
	/**
	 * Stops all of the intake's motors.
	 */
	public void stop() {
		leftArm.stopMotor();
		rightArm.stopMotor();
	}
	
	/**
	 * Returns the speed of the intake's arm's rollers.
	 * 
	 * @return	a double containing the percent output
	 */
	public double getSpin() {
		return leftArm.get();
	}
	
	/**
	 * Gets the deployed status of the intake.
	 * 
	 * @return	a boolean containing the state, with true deployed and false retracted
	 */
	public boolean getDeployed() {
		return deploy.get() == Value.kForward;
	}
}
