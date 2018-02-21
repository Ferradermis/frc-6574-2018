/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6574.robot;

import org.usfirst.frc.team6574.robot.subsystems.Conveyor;
import org.usfirst.frc.team6574.robot.subsystems.DriveTrain;
import org.usfirst.frc.team6574.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	public static final DriveTrain drive = new DriveTrain();
	public static final Intake intake = new Intake();
	public static final Conveyor conveyor = new Conveyor();
	//public static final Shooter shooter = new Shooter();
	
	public Joystick leftJoystick = new Joystick(RobotMap.input.LEFT_JOYSTICK_USB_NUM);
	public Joystick rightJoystick = new Joystick(RobotMap.input.RIGHT_JOYSTICK_USB_NUM);
	public XboxController controller = new XboxController(RobotMap.input.CONTROLLER_USB_NUM);
	
	boolean pressingShifter = false;
	boolean pressingIntake = false;
	boolean pressingShooter = false;
	boolean pressingSpin = false;
	boolean pressingJoystick = false;
	
	boolean oneJoystick = false;
	
	double distanceMoved = 0;

	int autoStage = 0;
	Timer t = new Timer();
	int pos = 0;
	boolean switchOwnership = false;
	
	
	double getLeftY() {
		return -leftJoystick.getY();
	}
	
	double getRightY() {
		return -rightJoystick.getY();
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		drive.calibrateGyro();
	}

	/** 
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		autoStage = 0;
		distanceMoved = 0;
		drive.clearEncoders();
	}

	@Override
	public void disabledPeriodic() {
		
	}
	
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		pos = DriverStation.getInstance().getLocation();
		
		String positions = DriverStation.getInstance().getGameSpecificMessage();
		switchOwnership = (positions.charAt(0) == 'L' && pos == 1) || (positions.charAt(0) == 'L' && pos == 3);
		
		drive.clearEncoders();
		distanceMoved = 0;
		drive.resetGyro();
		autoStage = 0;
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		if (!switchOwnership) {
			if (autoStage == 0) {
				if (distanceMoved < (120.0 * (9.96/13.0)) * 12/10) {
					distanceMoved = 0.0736 * Math.abs(drive.getEncoderDist()) / 34;
					SmartDashboard.putNumber("Encoder dist", distanceMoved);
					drive.set(0.5);
				} else {
					drive.stop();
					drive.resetGyro();
					drive.clearEncoders();
					autoStage = 1;
				}
			}
		} else {
			if (pos == 1) {
				if (autoStage == 0) {
					if (distanceMoved < (120.0 * (9.96/13.0)) * 12/10) {
						distanceMoved = 0.0736 * Math.abs(drive.getEncoderDist()) / 34;
						SmartDashboard.putNumber("Encoder dist", distanceMoved);
						drive.set(0.5);
					} else {
						drive.stop();
						drive.resetGyro();
						drive.clearEncoders();
						autoStage = 1;
					}
				} else if (autoStage == 1){
					if (drive.getGyroAngle() > -90) {
						drive.rotate(-0.3);
					} else {
						drive.stop();
						drive.resetGyro();
						autoStage = 2;
						t.reset();
					}
				} else if (autoStage == 2) {
					if (t.get() < 2) {
						drive.set(-0.4);
					} else {
						drive.stop();
					}
				} else if (autoStage == 3) {
					drive.stop();
					drive.clearEncoders();
				}
			} else if (pos == 3) {
				if (autoStage == 0) {
					if (distanceMoved < (120.0 * (9.96/13.0)) * 12/10) {
						distanceMoved = 0.0736 * Math.abs(drive.getEncoderDist()) / 34;
						SmartDashboard.putNumber("Encoder dist", distanceMoved);
						drive.set(0.5);
					} else {
						drive.stop();
						drive.resetGyro();
						drive.clearEncoders();
						autoStage = 1;
					}
				} else if (autoStage == 1){
					if (drive.getGyroAngle() < 90) {
						drive.rotate(0.3);
					} else {
						drive.stop();
						drive.resetGyro();
						autoStage = 2;
						t.reset();
					}
				} else if (autoStage == 2) {
					if (t.get() < 2) {
						drive.set(-0.4);
					} else {
						drive.stop();
					}
				} else if (autoStage == 3) {
					drive.stop();
					drive.clearEncoders();
				}
			}
		}
	}
	
	@Override
	public void teleopInit() {
		distanceMoved = 0;
		drive.clearEncoders();
		pressingShifter = false;
		pressingIntake = false;
		pressingShooter = false;
		pressingSpin = false;
		oneJoystick = false;
	}
   
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		
		//
		// Movement controls
		//
		if (oneJoystick) {
			if (getLeftY() > Controls.joystick.DEAD_PERCENT) {
				drive.set(getLeftY() + Controls.joystick.DEAD_PERCENT);
			} else if (getLeftY() < -Controls.joystick.DEAD_PERCENT) {
				drive.set(getLeftY() + Controls.joystick.DEAD_PERCENT);
			} else if (leftJoystick.getTwist() > Controls.joystick.DEAD_PERCENT) {
				drive.rotate(leftJoystick.getTwist() - Controls.joystick.DEAD_PERCENT);
			} else if (leftJoystick.getTwist() < -Controls.joystick.DEAD_PERCENT) {
				drive.rotate(leftJoystick.getTwist() + Controls.joystick.DEAD_PERCENT);
			} else {
				drive.stop();
			}
		} else {
			double driveValue = 0;
			if (getLeftY() > Controls.joystick.DEAD_PERCENT) {
				driveValue = getLeftY() - Controls.joystick.DEAD_PERCENT;
				if (leftJoystick.getRawButton(Controls.joystick.TURBO) || rightJoystick.getRawButton(Controls.joystick.TURBO)) {
					driveValue = 1;
				}
				drive.frontLeft(driveValue);
				drive.backLeft(driveValue);	
			} else if (getLeftY() < -Controls.joystick.DEAD_PERCENT) {
				driveValue = getLeftY() + Controls.joystick.DEAD_PERCENT;
				if (leftJoystick.getRawButton(Controls.joystick.TURBO) || rightJoystick.getRawButton(Controls.joystick.TURBO)) {
					driveValue = -1;
				}
				drive.frontLeft(driveValue);
				drive.backLeft(driveValue);
			} else {
				drive.stopLeft();
			}
			driveValue = 0;
			if (getRightY() > Controls.joystick.DEAD_PERCENT) {
				driveValue = getRightY() - Controls.joystick.DEAD_PERCENT;
				if (leftJoystick.getRawButton(Controls.joystick.TURBO) || rightJoystick.getRawButton(Controls.joystick.TURBO)) {
					driveValue = 1;
				}
				drive.frontRight(driveValue);
				drive.backRight(driveValue);
			} else if (getRightY() < -Controls.joystick.DEAD_PERCENT) {
				driveValue = getRightY() + Controls.joystick.DEAD_PERCENT;
				if (leftJoystick.getRawButton(Controls.joystick.TURBO) || rightJoystick.getRawButton(Controls.joystick.TURBO)) {
					driveValue = -1;
				}
				drive.frontRight(driveValue);
				drive.backRight(driveValue);
			} else {
				drive.stopRight();
			}
		}
		
		
		/*
			 * 
			 * SHOOTER
		if (m_oi.leftJoystick.getRawButton(Controls.joystick.TOGGLE_SHOOTER)) {
				if (!pressingShooter) {
					if (shooter.getRaised()) {
						shooter.lower();
						intake.retract();
					} else {
						intake.deploy();
						shooter.raise();
					}
					pressingShooter = true;
				}
			} else {
				pressingShooter = false;
			}
			 */
			
		//
		// Intake deploy toggle
		//
		//if (!shooter.getRaised()) {
		if (controller.getRawButton(Controls.controller.TOGGLE_INTAKE)) {
			if (!pressingIntake) {
				intake.toggleDeploy();
				pressingIntake = true;
			}
		} else {
			pressingIntake = false;
		}
		//}
		
		//
		// Gear shift toggle
		//
		if (leftJoystick.getRawButton(Controls.joystick.TOGGLE_SHIFT)) {
			if (!pressingShifter) {
				drive.toggleShifter();
				pressingShifter = true;
			}
		} else {
			pressingShifter = false;
		}
		
		//
		// Single/dual joystick toggle
		//
		if (leftJoystick.getRawButton(Controls.joystick.TOGGLE_DUAL)) {
			if (!pressingJoystick) {
				oneJoystick = !oneJoystick;
				pressingJoystick = true;
			}
		} else {
			pressingJoystick = false;
		}
		
		//
		// Intake spinning
		//
		if (controller.getRawButton(Controls.controller.INTAKE_IN)) {
			intake.spin(Constants.INTAKE_SPEED);
			conveyor.spin(Constants.CONVEYOR_SPEED);
		} else if (controller.getRawButton(Controls.controller.INTAKE_OUT)) {
			intake.spin(-Constants.INTAKE_SPEED);
			conveyor.spin(-Constants.CONVEYOR_SPEED);
		} else {
			intake.stop();
			conveyor.stop();
		}
		
		//
		// SmartDashboard teleop readings
		//
		SmartDashboard.putNumber("Encoder distance", drive.getEncoderDist());
		SmartDashboard.putNumber("Left encoder distance", drive.getLeftDistance());
		SmartDashboard.putNumber("Right encoder distance", drive.getRightDistance());
		SmartDashboard.putNumber("Left encoder velocity", drive.getLeftVelocity());
		SmartDashboard.putNumber("Right encoder velocity", drive.getRightVelocity());
		SmartDashboard.putNumber("Gyro angle", drive.getGyroAngle());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
	}
	
}
