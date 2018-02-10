package org.usfirst.frc.team6574.robot.subsystems;

import org.usfirst.frc.team6574.robot.Constants;
import org.usfirst.frc.team6574.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * Drive train subsystem for control/PID
 * 
 * @author brantmeierz
 *
 */
public class DriveTrain extends PIDSubsystem {
	
	TalonSRX frontLeft;
	TalonSRX frontRight;
	TalonSRX backLeft;
	TalonSRX backRight;
	
	Compressor compressor;
	
	DoubleSolenoid shifter;
	
	Gyro gyro;
	/*
	Encoder leftEncoder;
	Encoder rightEncoder;
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
		
		//frontLeft.config
		//frontLeft.getSensorCollection().getQuadrature();
		//frontRight.getSensorCollection().getQuadraturePosition();
		
		//setInputRange(0, 0);
		setOutputRange(-1, 1);
		setPercentTolerance(1);
		
		compressor = new Compressor();
		compressor.start();
		compressor.setClosedLoopControl(true);
		
		getPIDController().setContinuous(false);
		
		gyro = new ADXRS450_Gyro();
		gyro.reset();
		
		shifter = new DoubleSolenoid(0, 1);
		
		/*
		leftEncoder = new Encoder(RobotMap.encoder.LEFT_A_CHANNEL, RobotMap.encoder.LEFT_B_CHANNEL);
		rightEncoder = new Encoder(RobotMap.encoder.RIGHT_A_CHANNEL, RobotMap.encoder.RIGHT_B_CHANNEL);
		leftEncoder.setDistancePerPulse(Constants.ENCODER_PULSE_DISTANCE);
		rightEncoder.setDistancePerPulse(Constants.ENCODER_PULSE_DISTANCE);
		
		leftEncoder.reset();
		rightEncoder.reset();
		*/
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
	
	public void frontLeft(double speed) {
		frontLeft.set(ControlMode.PercentOutput, speed);
		//MASTER
	}
	
	public void frontRight(double speed) {
		frontRight.set(ControlMode.PercentOutput, -speed);
		//MASTER
	}
	
	public void backLeft(double speed) {
		backLeft.set(ControlMode.PercentOutput, speed);
		//FOLLOWER MODE/SLAVE
	}
	
	public void backRight(double speed) {
		backRight.set(ControlMode.PercentOutput, -speed);
		//backRight.set(ControlMode.Follower, speed);
		//FOLLOWER MODE/SLAVE
	}
	
	public void set(double speed) {
		frontLeft(speed);
		frontRight(speed);
		backLeft(speed);
		backRight(speed);
	}
	
	/**
	 * Stops all drive train motors.
	 * 
	 * @author brantmeierz
	 * 
	 */
	public void stop() {
		frontLeft.set(ControlMode.PercentOutput, 0);
		frontRight.set(ControlMode.PercentOutput, 0);
		backLeft.set(ControlMode.PercentOutput, 0);
		backRight.set(ControlMode.PercentOutput, 0);
	}
	
	public void stopLeft() {
		frontLeft.set(ControlMode.PercentOutput, 0);
		backLeft.set(ControlMode.PercentOutput, 0);
	}
	
	public void stopRight() {
		frontRight.set(ControlMode.PercentOutput, 0);
		backRight.set(ControlMode.PercentOutput, 0);
	}
	
	public void engageShifter() {
		shifter.set(Value.kForward);
	}

	public void disengageShifter() {
		shifter.set(Value.kReverse);
	}
	
	/**
	 * Gets the average distance of the the left and right drive train encoders since last reset.
	 * 
	 * @return	double containing the average distance in inches
	 */
	public double getEncoderDist() {
		return ((6 * Math.PI / 256) * (frontLeft.getSensorCollection().getQuadraturePosition() + frontRight.getSensorCollection().getQuadraturePosition())) / 2;
	}
	
	public void clearEncoders() {
		frontLeft.getSensorCollection().setQuadraturePosition(0, 0);
		frontRight.getSensorCollection().setQuadraturePosition(0, 0);
		/*
		leftEncoder.reset();
		rightEncoder.reset();
		*/
	}
	
	public double getLeftVelocity() {
		return frontLeft.getSensorCollection().getQuadratureVelocity();
	}
	
	public double getRightVelocity() {
		return frontRight.getSensorCollection().getQuadratureVelocity();
	}
	
	public double getGyroAngle() {
		return gyro.getAngle();
	}
	
	public void resetGyro() {
		gyro.reset();
	}
}
