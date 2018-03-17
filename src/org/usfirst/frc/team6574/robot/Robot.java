/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6574.robot;

import org.opencv.core.Mat;
import org.usfirst.frc.team6574.robot.subsystems.Conveyor;
import org.usfirst.frc.team6574.robot.subsystems.DriveTrain;
import org.usfirst.frc.team6574.robot.subsystems.Intake;
import org.usfirst.frc.team6574.robot.subsystems.Shooter;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
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
	public static final Shooter shooter = new Shooter();
	
	public static final Compressor compressor = new Compressor();
	
	public Joystick leftJoystick = new Joystick(RobotMap.input.LEFT_JOYSTICK_USB_NUM);
	public Joystick rightJoystick = new Joystick(RobotMap.input.RIGHT_JOYSTICK_USB_NUM);
	public XboxController controller = new XboxController(RobotMap.input.CONTROLLER_USB_NUM);
	
	boolean pressingShifter = false;
	boolean pressingIntake = false;
	boolean pressingShooter = false;
	boolean pressingSpin = false;
	boolean pressingJoystick = false;
	boolean pressingCamera = false;
	boolean pressingShooterSpin = false;
	
	boolean oneJoystick = false;
	boolean frontCamera = true;

	int pos = 0;
	boolean switchOwnership = false;
	
	Timer reloadTimer = new Timer();
	Timer intakeMoveTimer = new Timer();
	
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
		Autonomous.autoStage = 0;
		Autonomous.distanceMoved = 0.0;
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
		drive.resetGyro();
		
		Autonomous.autoStage = 0;
		Autonomous.distanceMoved = 0;
	
		compressor.start();
		compressor.setClosedLoopControl(true);
		
		//shooter.unload();
		//shooter.lower();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		if (!switchOwnership) {
			//Auto line (if on wrong switch side or center)
			Autonomous.sideAutoLine();
		} else {
			if (pos == 1) {
				//Left wall owned switch
				Autonomous.switchLeftLeft();
			} else if (pos == 3) {
				//Right wall owned switch
				Autonomous.switchRightRight();
			}
		}
	}
	
	@Override
	public void teleopInit() {
		Autonomous.autoTimer.stop();
		Autonomous.autoTimer.reset();
		
		drive.clearEncoders();
		drive.resetGyro();
		
		pressingShifter = false;
		pressingIntake = false;
		pressingShooter = false;
		pressingSpin = false;
		pressingJoystick = false;
		pressingShooterSpin = false;
		oneJoystick = false;
		
		compressor.start();
		compressor.setClosedLoopControl(true);
	}
   
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		// Movement controls
		if (oneJoystick) {
			if (leftJoystick.getRawButton(Controls.joystick.TURBO)) {
				if (getLeftY() > Controls.joystick.DEAD_PERCENT) {
					drive.set(1);
				} else if (getLeftY() < -Controls.joystick.DEAD_PERCENT) {
					drive.set(-1);
				} else {
					drive.set(0);
				}
			} else {
				double driveAmount = 0;
				if (getLeftY() > Controls.joystick.DEAD_PERCENT) {
					driveAmount = getLeftY() - Controls.joystick.DEAD_PERCENT;
					if (leftJoystick.getTwist() > Controls.joystick.DEAD_PERCENT) {
						drive.left(driveAmount);
						drive.right(driveAmount - (leftJoystick.getTwist() * driveAmount) / 2);
					} else if (leftJoystick.getTwist() < -Controls.joystick.DEAD_PERCENT) {
						drive.left(driveAmount - (Math.abs(leftJoystick.getTwist()) * driveAmount) / 2);
						drive.right(driveAmount);
					} else {
						drive.set(driveAmount);
					}
				} else if (getLeftY() < -Controls.joystick.DEAD_PERCENT) {
					driveAmount = getLeftY() + Controls.joystick.DEAD_PERCENT;
					if (leftJoystick.getTwist() > Controls.joystick.DEAD_PERCENT) {
						drive.left(driveAmount + (leftJoystick.getTwist() * driveAmount) / 2);
						drive.right(driveAmount);
					} else if (leftJoystick.getTwist() < -Controls.joystick.DEAD_PERCENT) {
						drive.left(driveAmount);
						drive.right(driveAmount + (Math.abs(leftJoystick.getTwist()) * driveAmount) / 2);
					} else {
						drive.set(driveAmount);
					}
				} else if (leftJoystick.getTwist() > Controls.joystick.DEAD_PERCENT) {
					drive.rotate(leftJoystick.getTwist() - Controls.joystick.DEAD_PERCENT);
				} else if (leftJoystick.getTwist() < -Controls.joystick.DEAD_PERCENT) {
					drive.rotate(leftJoystick.getTwist() + Controls.joystick.DEAD_PERCENT);
				} else {
					drive.stop();
				}
			}
		} else {
			double driveValue = 0;
			if (getLeftY() > Controls.joystick.DEAD_PERCENT) {
				driveValue = getLeftY() - Controls.joystick.DEAD_PERCENT;
				if (leftJoystick.getRawButton(Controls.joystick.TURBO) || rightJoystick.getRawButton(Controls.joystick.TURBO)) {
					driveValue = 1;
				}
				drive.left(driveValue);
			} else if (getLeftY() < -Controls.joystick.DEAD_PERCENT) {
				driveValue = getLeftY() + Controls.joystick.DEAD_PERCENT;
				if (leftJoystick.getRawButton(Controls.joystick.TURBO) || rightJoystick.getRawButton(Controls.joystick.TURBO)) {
					driveValue = -1;
				}
				drive.left(driveValue);
			} else {
				drive.stopLeft();
			}
			driveValue = 0;
			if (getRightY() > Controls.joystick.DEAD_PERCENT) {
				driveValue = getRightY() - Controls.joystick.DEAD_PERCENT;
				if (leftJoystick.getRawButton(Controls.joystick.TURBO) || rightJoystick.getRawButton(Controls.joystick.TURBO)) {
					driveValue = 1;
				}
				drive.right(driveValue);
			} else if (getRightY() < -Controls.joystick.DEAD_PERCENT) {
				driveValue = getRightY() + Controls.joystick.DEAD_PERCENT;
				if (leftJoystick.getRawButton(Controls.joystick.TURBO) || rightJoystick.getRawButton(Controls.joystick.TURBO)) {
					driveValue = -1;
				}
				drive.right(driveValue);
			} else {
				drive.stopRight();
			}
		}
		
		// Shooter toggle
		if (controller.getRawButton(Controls.controller.TOGGLE_SHOOTER_DEPLOY)) {
			if (!pressingShooter) {
				if (shooter.getRaised()) {
					// [! DELAY FOR INTAKE RETRACT BEFORE SHOOTING]
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
		
		// Shooter spin toggle
		if (controller.getRawButton(Controls.controller.TOGGLE_SHOOTER_SPIN)) {
			if (!pressingShooterSpin) {
				shooter.spinShooter(Constants.SHOOTER_SPEED);
				pressingShooterSpin = true;
			} else {
				shooter.stop();
			}
		} else {
			pressingShooterSpin = false;
		}
		
		// Shoot
		if (controller.getRawButton(Controls.controller.SHOOT) && reloadTimer.get() == 0) {
			shooter.load();
			reloadTimer.reset();
			reloadTimer.start();
		}
		if (shooter.getLoaded() && reloadTimer.get() > Constants.UNLOAD_TIME) {
			shooter.unload();
			reloadTimer.stop();
			reloadTimer.reset();
		}
		
		// Intake deploy toggle
		if (!shooter.getRaised()) {
			if (controller.getRawButton(Controls.controller.TOGGLE_INTAKE)) {
				if (!pressingIntake) {
					intake.toggleDeploy();
					pressingIntake = true;
				}
			} else {
				pressingIntake = false;
			}
		}
		
		// Gear shift toggle
		if (leftJoystick.getRawButton(Controls.joystick.TOGGLE_SHIFT)) {
			if (!pressingShifter) {
				drive.toggleShifter();
				pressingShifter = true;
			}
		} else {
			pressingShifter = false;
		}
		
		// Single/dual joystick toggle
		if (leftJoystick.getRawButton(Controls.joystick.TOGGLE_DUAL)) {
			if (!pressingJoystick) {
				oneJoystick = !oneJoystick;
				pressingJoystick = true;
			}
		} else {
			pressingJoystick = false;
		}
		
		// Forward/backward camera toggle
		if (controller.getRawButton(Controls.controller.TOGGLE_CAMERA)) {
			if (!pressingCamera) {
				frontCamera = !frontCamera;
				pressingCamera = true;
			}
		} else {
			pressingCamera = false;
		}
		
		// Intake spinning
		if (controller.getRawButton(Controls.controller.INTAKE_IN)) {
			intake.spinIntake(Constants.INTAKE_SPEED);
			conveyor.spin(Constants.CONVEYOR_SPEED);
		} else if (controller.getRawButton(Controls.controller.INTAKE_OUT)) {
			intake.spinIntake(-Constants.INTAKE_SPEED);
			conveyor.spin(-Constants.CONVEYOR_SPEED);
		} else {
			intake.stop();
			conveyor.stop();
		}
		
		//SmartDashboard teleop readings
		SmartDashboard.putNumber("Encoder distance", drive.getEncoderDist());
		SmartDashboard.putNumber("Left encoder distance", drive.getLeftDistance());
		SmartDashboard.putNumber("Right encoder distance", drive.getRightDistance());
		SmartDashboard.putNumber("Left encoder velocity", drive.getLeftVelocity());
		SmartDashboard.putNumber("Right encoder velocity", drive.getRightVelocity());
		SmartDashboard.putNumber("Gyro angle", drive.getGyroAngle());
		SmartDashboard.putString("Shooter status", shooter.getRaised() ? "RAISED" : "LOWERED");
		SmartDashboard.putString("Intake status", intake.getDeployed() ? "DEPLOYED" : "RETRACTED");
		
		//Camera thread
		new Thread(() -> {
			UsbCamera forwardCamera = CameraServer.getInstance().startAutomaticCapture(RobotMap.camera.FORWARD_USB_NUM);
			UsbCamera backwardCamera = CameraServer.getInstance().startAutomaticCapture(RobotMap.camera.BACKWARD_USB_NUM);
			CvSink autonCvSink = CameraServer.getInstance().getVideo(forwardCamera);
			CvSink teleCvSink = CameraServer.getInstance().getVideo(backwardCamera);
			CvSource outputStream = CameraServer.getInstance().putVideo("Camera Feed", 1280, 720);
			Mat forwardImage = new Mat();
			Mat backwardImage = new Mat();
			while (!Thread.interrupted()) {
				if (frontCamera) {
					autonCvSink.grabFrame(forwardImage);
					outputStream.putFrame(forwardImage);
				} else {
					teleCvSink.grabFrame(backwardImage);
					outputStream.putFrame(backwardImage);
				}
			}
		}).start();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
	}
	
}
