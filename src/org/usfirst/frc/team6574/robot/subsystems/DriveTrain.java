package org.usfirst.frc.team6574.robot.subsystems;

import org.usfirst.frc.team6574.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Drive train subsystem for control/PID
 * 
 * @author brantmeierz
 */
public class DriveTrain extends PIDSubsystem {
	
	TalonSRX frontLeft;
	TalonSRX frontRight;
	TalonSRX backLeft;
	TalonSRX backRight;
	
	Compressor compressor;
	
	DoubleSolenoid shifter;
	
	Gyro gyro;

	/**
	 * Constructs a drive train object for the robot.
	 * 
	 * @param p	a double containing the proportional value for the PIDSubsystem superclass
	 * @param i a double containing the integral value for the PIDSubsystem superclass
	 * @param d a double containing the derivative value for the PIDSubsystem superclass
	 */
	public DriveTrain(double p, double i, double d) {
		super(p, i, d);
		
		frontLeft = new TalonSRX(RobotMap.driveTrain.FRONT_LEFT_CAN_ID);
		frontRight = new TalonSRX(RobotMap.driveTrain.FRONT_RIGHT_CAN_ID);
		backLeft = new TalonSRX(RobotMap.driveTrain.BACK_LEFT_CAN_ID);
		backRight = new TalonSRX(RobotMap.driveTrain.BACK_RIGHT_CAN_ID);
		//backLeft.follow(frontLeft);
		//backRight.follow(frontRight);
		
		frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		//setInputRange(0, 0);
		setOutputRange(-1, 1);
		setPercentTolerance(1);
		
		compressor = new Compressor();
		compressor.start();
		compressor.setClosedLoopControl(true);
		
		getPIDController().setContinuous(false);
		
		gyro = new ADXRS450_Gyro();
		gyro.reset();
		
		shifter = new DoubleSolenoid(RobotMap.driveTrain.SHIFT_OFF_PCN_ID, RobotMap.driveTrain.SHIFT_ON_PCN_ID);

		getPIDController().onTarget();
	}

	@Override
	protected double returnPIDInput() {
		return frontLeft.getSensorCollection().getQuadraturePosition();
	}

	@Override
	protected void usePIDOutput(double output) {
		//DRIVE TRAIN DRIVE OUTPUT
	}

	@Override
	protected void initDefaultCommand() {
		
	}
	
	/**
	 * Sets the speed of the front left motor.
	 * 
	 * @param speed	a double in the range -1 to 1 indicating the percent of max speed, negative for reverse
	 */
	public void frontLeft(double speed) {
		frontLeft.set(ControlMode.PercentOutput, speed);
		//MASTER
	}
	
	/**
	 * Sets the speed of the front right motor.
	 * 
	 * @param speed	a double in the range -1 to 1 indicating the percent of max speed, negative for reverse
	 */
	public void frontRight(double speed) {
		frontRight.set(ControlMode.PercentOutput, -speed);
		//MASTER
	}
	
	/**
	 * Sets the speed of the back left motor.
	 * 
	 * @param speed	a double in the range -1 to 1 indicating the percent of max speed, negative for reverse
	 */
	public void backLeft(double speed) {
		backLeft.set(ControlMode.PercentOutput, speed);
		//FOLLOWER MODE/SLAVE
	}
	
	/**
	 * Sets the speed of the back right motor.
	 * 
	 * @param speed	a double in the range -1 to 1 indicating the percent of max speed, negative for reverse
	 */
	public void backRight(double speed) {
		backRight.set(ControlMode.PercentOutput, -speed);
		//backRight.set(ControlMode.Follower, speed);
		//FOLLOWER MODE/SLAVE
	}
	
	/**
	 * Sets all of the drive train's motors to the same value.
	 * 
	 * @param speed	a double in the range -1 to 1 indicating the percent of max speed, negative for reverse
	 */
	public void set(double speed) {
		frontLeft(speed);
		frontRight(speed);
		backLeft(speed);
		backRight(speed);
	}
	
	/**
	 * Stops all drive train motors.
	 */
	public void stop() {
		frontLeft.set(ControlMode.PercentOutput, 0);
		frontRight.set(ControlMode.PercentOutput, 0);
		backLeft.set(ControlMode.PercentOutput, 0);
		backRight.set(ControlMode.PercentOutput, 0);
	}
	
	/**
	 * Stops the drive train's left motors.
	 */
	public void stopLeft() {
		frontLeft.set(ControlMode.PercentOutput, 0);
		backLeft.set(ControlMode.PercentOutput, 0);
	}
	
	/**
	 * Stops the drive train's right motors.
	 */
	public void stopRight() {
		frontRight.set(ControlMode.PercentOutput, 0);
		backRight.set(ControlMode.PercentOutput, 0);
	}
	
	/**
	 * Engages the drive train's gear shifting mechanism.
	 */
	public void engageShifter() {
		shifter.set(Value.kForward);
	}

	/**
	 * Disengages the drive train's gear shifting mechanism.
	 */
	public void disengageShifter() {
		shifter.set(Value.kReverse);
	}
	
	/**
	 * Gets the average distance of the the left and right drive train encoders since last reset.
	 * 
	 * @return	a double containing the average distance in inches
	 */
	public double getEncoderDist() {
		return ((6 * Math.PI / 256) * (frontLeft.getSensorCollection().getQuadraturePosition() + frontRight.getSensorCollection().getQuadraturePosition())) / 2;
	}
	
	/**
	 * Resets the drive train's encoder values to zero.
	 */
	public void clearEncoders() {
		frontLeft.getSensorCollection().setQuadraturePosition(0, 0);
		frontRight.getSensorCollection().setQuadraturePosition(0, 0);
	}
	
	/**
	 * Gets the velocity reading from the drive train's left encoder.
	 * 
	 * @return	a double containing the left's velocity value
	 */
	public double getLeftVelocity() {
		return frontLeft.getSensorCollection().getQuadratureVelocity();
	}

	/**
	 * Gets the velocity reading from the drive train's right encoder.
	 * 
	 * @return	a double containing the right's velocity value
	 */
	public double getRightVelocity() {
		return frontRight.getSensorCollection().getQuadratureVelocity();
	}
	
	/**
	 * Gets the angle of drive train from its initial position.
	 * 
	 * @return	a double containing the drive train's current orientation
	 */
	public double getGyroAngle() {
		return gyro.getAngle();
	}
	
	/**
	 * Resets the drive train's gyroscope position to the zero value.
	 */
	public void resetGyro() {
		gyro.reset();
	}
	
}
