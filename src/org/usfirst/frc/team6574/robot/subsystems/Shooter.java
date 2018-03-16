package org.usfirst.frc.team6574.robot.subsystems;

import org.usfirst.frc.team6574.robot.Robot;
import org.usfirst.frc.team6574.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Spark;

public class Shooter {

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
		
		lifter = new DoubleSolenoid(RobotMap.shooter.RAISE_PCM_ID, RobotMap.shooter.LOWER_PCM_ID);
		loader = new DoubleSolenoid(RobotMap.shooter.LOAD_PCM_ID, RobotMap.shooter.UNLOAD_PCM_ID);
		
		lower();
		unload();
	}
	
	/**
	 * Spins the roller of the shooters, launching the cube will take place when the load() method is called.
	 * 
	 * @param val	a double containing the speed at which to spin, will functionally never need to be spun backwards but no hard restriction
	 */
	public void spinShooter(double val) {
		left1.set(-val);
		left2.set(-val);
		right1.set(val);
		right2.set(val);
	}
	
	/**
	 * Stops all motors on the shooter.
	 */
	public void stop() {
		left1.stopMotor();
		left2.stopMotor();
		right1.stopMotor();
		right2.stopMotor();
	}
	
	/**
	 * Pushes the intaken cube into the rollers with a piston to shoot it.
	 */
	public void load() {
		loader.set(Value.kForward);
	}
	
	/**
	 * Retracts the piston used to shoot the cube.
	 */
	public void unload() {
		loader.set(Value.kReverse);
	}
	
	/**
	 * Raises the shooter subsystem into shooting position, and forces the intake into deployed status to prevent overlap.
	 */
	public void raise() {
		lifter.set(Value.kForward);
		Robot.intake.deploy();
	}
	
	/**
	 * Lowers the shooter subsystem from raised position into its passive position.
	 */
	public void lower() {
		lifter.set(Value.kForward);
	}
	
	/**
	 * Returns the raised status of the shooter system.
	 * 
	 * @return	a boolean indicating whether or not the shooter is raised, with true meaning raised into shooting position and false meaning it is in its passive position
	 */
	public boolean getRaised() {
		return lifter.get() == Value.kForward;
	}
	
	/**
	 * Returns the loaded status of the shooter system.
	 * 
	 * @return	a boolean indicating whether or not the loading piston is engaged, with true meaning the piston is in shot position and false meaning it is in the unloaded position
	 */
	public boolean getLoaded() {
		return loader.get() == Value.kForward;
	}
	
}
