package org.usfirst.frc.team6574.robot.subsystems;

import org.usfirst.frc.team6574.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * A drive train subsystem for control/PID for a CAN-based
 * tank driven robot.
 * 
 * @author Zach Brantmeier
 */
public class DriveTrain {
	
	TalonSRX frontLeft;
	TalonSRX frontRight;
	TalonSRX backLeft;
	TalonSRX backRight;
	
	DoubleSolenoid shifter;
	
	Gyro gyro;

	/**
	 * Constructs a drive train subsystem for the robot.
	 */
	public DriveTrain() {
		frontLeft = new TalonSRX(RobotMap.driveTrain.FRONT_LEFT_CAN_ID);
		frontRight = new TalonSRX(RobotMap.driveTrain.FRONT_RIGHT_CAN_ID);
		backLeft = new TalonSRX(RobotMap.driveTrain.BACK_LEFT_CAN_ID);
		backRight = new TalonSRX(RobotMap.driveTrain.BACK_RIGHT_CAN_ID);
		
		gyro = new ADXRS450_Gyro();
		gyro.reset();
		
		shifter = new DoubleSolenoid(RobotMap.driveTrain.SHIFT_OFF_PCM_ID, RobotMap.driveTrain.SHIFT_ON_PCM_ID);
		
		engageShifter();
	}

	/**
	 * Sets the speeds of the left motors.
	 * 
	 * @param speed	a double in the range -1 to 1 indicating the percent of max speed, negative for reverse
	 */
	public void left(double speed) {
		frontLeft.set(ControlMode.PercentOutput, -speed);
		backLeft.set(ControlMode.PercentOutput, -speed);
	}
	
	/**
	 * Sets the speeds of the right motors.
	 * 
	 * @param speed	a double in the range -1 to 1 indicating the percent of max speed, negative for reverse
	 */
	public void right(double speed) {
		frontRight.set(ControlMode.PercentOutput, speed);
		backRight.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * Sets all of the drive train's motors to the same value.
	 * 
	 * @param speed	a double in the range -1 to 1 indicating the percent of max speed, negative for reverse
	 */
	public void set(double speed) {
		left(speed);
		right(speed);
	}
	
	/**
	 * Stops all drive train motors.
	 */
	public void stop() {
		left(0);
		right(0);
	}
	
	/**
	 * Stops the drive train's left motors.
	 */
	public void stopLeft() {
		left(0);
	}
	
	/**
	 * Stops the drive train's right motors.
	 */
	public void stopRight() {
		right(0);
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
	 * Toggles the drive train's gear shifting mechanism between states.
	 */
	public void toggleShifter() {
		if (isShifted()) {
			disengageShifter();
		} else {
			engageShifter();
		}
	}
	
	/**
	 * Gets the status of the pneumatic gear shifter.
	 * 
	 * @return	a boolean containing the shifted state
	 */
	public boolean isShifted() {
		return shifter.get() == Value.kForward;
	}
	
	/**
	 * Gets the average distance of the the left and right drive train encoders since last reset.
	 * 
	 * @return	a double containing the average distance (in inches? not really)
	 */
	public double getEncoderDist() {
		return (frontLeft.getSensorCollection().getQuadraturePosition() + frontRight.getSensorCollection().getQuadraturePosition()) / 2;
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
	 * Gets the quadrature position reading from the drive train's left encoder.
	 * 
	 * @return	a double containing the encoder's position value (in inches? not really)
	 */
	public double getLeftDistance() {
		return frontLeft.getSensorCollection().getQuadraturePosition();
	}
	
	/**
	 * Gets the quadrature position reading from the drive train's right encoder.
	 * 
	 * @return	a double containing the encoder's position value (in inches? not really)
	 */
	public double getRightDistance() {
		return frontRight.getSensorCollection().getQuadraturePosition();
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
	
	/**
	 * Runs early gyroscope calibration.
	 */
	public void calibrateGyro() {
		gyro.calibrate();
	}

	/**
	 * Rotates the robot at a constant speed.
	 * 
	 * @param d	a double indicating the speed at which to rotate
	 */
	public void rotate(double d) {	
		left(d);
		right(-d);
	}
	
}
