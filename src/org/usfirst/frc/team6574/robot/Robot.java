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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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
	public XboxController driveController = new XboxController(RobotMap.input.DRIVE_CONTROLLER_USB_NUM);
	
	SendableChooser<String> autoPos = new SendableChooser<String>();
	
	boolean pressingShifter = false;
	boolean pressingIntake = false;
	boolean pressingShooter = false;
	boolean pressingSpin = false;
	boolean pressingJoystick = false;
	boolean pressingCamera = false;
	boolean pressingShooterSpin = false;
	boolean pressingBooper = false;
	
	boolean oneJoystick = false;
	boolean controllerDrive = false;
	boolean frontCamera = true;

	String robotPos;
	String ownership;
	
	Timer reloadTimer = new Timer();
	Timer spinUpTimer = new Timer();
	Timer intakeMoveTimer = new Timer();
	Timer booperTimer = new Timer();
	
	double getLeftY() {
		if (controllerDrive) {
			return -driveController.getRawAxis(1);
		}
		return -leftJoystick.getY();
	}
	
	double getRightY() {
		if (controllerDrive) {
			return -driveController.getRawAxis(3);
		}
		return -rightJoystick.getY();
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		autoPos.addDefault("LINE", "LINE");
		autoPos.addObject("LEFT", "LEFT");
		autoPos.addObject("MID", "MID");
		autoPos.addObject("RIGHT", "RIGHT");
		SmartDashboard.putData("Autonomous", autoPos);
		
		drive.calibrateGyro();
		
		new Thread(() -> {
			UsbCamera forward = CameraServer.getInstance().startAutomaticCapture(RobotMap.camera.FORWARD_USB_NUM);
			UsbCamera backward = CameraServer.getInstance().startAutomaticCapture(RobotMap.camera.BACKWARD_USB_NUM);
			//forward.setFPS(fps);
			//forward.setResolution(width, height);
			forward.setResolution(640, 480);
			backward.setResolution(640, 480);
			//forward.setFPS(15);
			//backward.setFPS(15);
			CvSink forwardSink = CameraServer.getInstance().getVideo(forward);
			CvSink backwardSink = CameraServer.getInstance().getVideo(backward);
			CvSource outputStream = CameraServer.getInstance().putVideo("Working Camera Feed", 640, 480);
			Mat image = new Mat();
			while (!Thread.interrupted()) {
				if (frontCamera) {
					forwardSink.grabFrame(image);
					outputStream.putFrame(image);
				} else {
					backwardSink.grabFrame(image);
					outputStream.putFrame(image);
				}
			}
		}).start();
	}

	/** 
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		Autonomous.autoStage = 0;
		drive.clearEncoders();
		compressor.stop();
	}

	@Override
	public void disabledPeriodic() {
		SmartDashboard.putString("Chosen Auto", autoPos.getSelected());
	}
	
	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		//position = autoPos.getSelected().equals("LEFT") ? 1 : (autoPos.getSelected().equals("MID") ? 2 : (autoPos.getSelected().equals("RIGHT") ? 3 : 0));
		
		//position = 3;
		
		//position = DriverStation.getInstance().getLocation();

		//position = DriverStation.getInstance().get
		
		//robotPos = autoPos.getSelected();
		
		robotPos = autoPos.getSelected();
		ownership = DriverStation.getInstance().getGameSpecificMessage();
		while (ownership.equals("")) {
			ownership = DriverStation.getInstance().getGameSpecificMessage();
		}
		
		//switchOwnership = (positions.charAt(0) == 'L' && position == 1) || (positions.charAt(0) == 'R' && position == 3);
		
		drive.clearEncoders();
		drive.resetGyro();
		
		Autonomous.autoStage = 0;
	
		compressor.start();
		compressor.setClosedLoopControl(true);
		
		//shooter.lower();
		//intake.retract();
		drive.shiftLow();
		
		//shooter.unload();
		//shooter.lower();
		
		shooter.raise();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		SmartDashboard.putString("GYRO VALUE", "" + drive.getGyroAngle());
		if (robotPos.equals("LINE")) {
			Autonomous.autoLine();
		} else if (robotPos.equals("LEFT") && ownership.charAt(0) == 'L') {
			Autonomous.SWITCH.leftFromLeft();
		} else if (robotPos.equals("MID") && ownership.charAt(0) == 'L') {
			Autonomous.SWITCH.leftFromCenter();
		} if (robotPos.equals("MID") && ownership.charAt(0) == 'R') {
			Autonomous.SWITCH.rightFromCenter();
		} else if (robotPos.equals("RIGHT") && ownership.charAt(0) == 'R') {
			Autonomous.SWITCH.rightFromRight();
		} else {
			Autonomous.autoLine();
		}
		//Autonomous.autoLine();
		
		/*if (robotPos.equals("LEFT")) {
			if (ownership.charAt(1) == 'L') {
				Autonomous.SCALE.leftFromLeft();
			} else if (ownership.charAt(0) == 'L') {
				Autonomous.SWITCH.leftFromLeft();
			} else {
				Autonomous.autoLine();
			}
		} else if (robotPos.equals("MID")) {
			
		} else if (robotPos.equals("RIGHT")) {
			if (ownership.charAt(1) == 'R') {
				Autonomous.SCALE.rightFromRight();
			} else if (ownership.charAt(0) == 'R') {
				Autonomous.SWITCH.rightFromRight();
			} else {
				Autonomous.autoLine();
			}
		}*/
		/*if (!switchOwnership) {
			if (position == 2) {
				//Mid turn and auto line
				Autonomous.midAutoLine();
			} else {
				//Side forward auto line
				Autonomous.sideAutoLine();
			}
		} else {
			if (position == 1) {
				//Left wall owned switch
				Autonomous.switchLeftLeft();
			} else if (position == 3) {
				//Right wall owned switch
				Autonomous.switchRightRight();
			}
		}*/
		//Autonomous.autoLine();
	}
	
	@Override
	public void teleopInit() {
		Autonomous.autoTimer.stop();
		Autonomous.autoTimer.reset();
		
		reloadTimer.stop();
		reloadTimer.reset();
		
		spinUpTimer.stop();
		spinUpTimer.reset();
		
		intakeMoveTimer.stop();
		intakeMoveTimer.reset();
		
		booperTimer.stop();
		booperTimer.reset();
		
		drive.clearEncoders();
		drive.resetGyro();
		
		pressingShifter = false;
		pressingIntake = false;
		pressingShooter = false;
		pressingSpin = false;
		pressingJoystick = false;
		pressingShooterSpin = false;
		pressingBooper = false;
		oneJoystick = false;
		
		compressor.start();
		compressor.setClosedLoopControl(true);
		
		shooter.stop();
		shooter.unload();
		
		drive.shiftHigh();
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
				if ((!controllerDrive && rightJoystick.getRawButton(Controls.joystick.TURBO)) || (controllerDrive && driveController.getRawButton(7))) {
					driveValue = 1;
				}
				drive.left(driveValue);
			} else if (getLeftY() < -Controls.joystick.DEAD_PERCENT) {
				driveValue = getLeftY() + Controls.joystick.DEAD_PERCENT;
				if ((!controllerDrive && rightJoystick.getRawButton(Controls.joystick.TURBO)) || (controllerDrive && driveController.getRawButton(7))) {
					driveValue = -1;
				}
				drive.left(driveValue);
			} else {
				drive.stopLeft();
			}
			driveValue = 0;
			if (getRightY() > Controls.joystick.DEAD_PERCENT) {
				driveValue = getRightY() - Controls.joystick.DEAD_PERCENT;
				if ((!controllerDrive && rightJoystick.getRawButton(Controls.joystick.TURBO)) || (controllerDrive && driveController.getRawButton(7))) {
					driveValue = 1;
				}
				drive.right(driveValue);
			} else if (getRightY() < -Controls.joystick.DEAD_PERCENT) {
				driveValue = getRightY() + Controls.joystick.DEAD_PERCENT;
				if ((!controllerDrive && rightJoystick.getRawButton(Controls.joystick.TURBO)) || (controllerDrive && driveController.getRawButton(7))) {
					driveValue = -1;
				}
				drive.right(driveValue);
			} else {
				drive.stopRight();
			}
		}
		
		if (!controllerDrive) {
			/*
			// Gear shift toggle
			if (leftJoystick.getRawButton(Controls.joystick.TOGGLE_SHIFT)) {
				if (!pressingShifter) {
					drive.toggleShifter();
					pressingShifter = true;
				}
			} else {
				pressingShifter = false;
			}*/
			
			if (leftJoystick.getRawButton(Controls.joystick.TOGGLE_SHIFT)) {
				drive.shiftLow();
			} else {
				drive.shiftHigh();
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
			if (leftJoystick.getRawButton(Controls.joystick.TOGGLE_CAMERA)) {
				if (!pressingCamera) {
					frontCamera = !frontCamera;
					pressingCamera = true;
				}
			} else {
				pressingCamera = false;
			}
		} else {
			//Drive gear shift
			if (driveController.getRawButton(8)) {
				drive.shiftHigh();
			} else {
				drive.shiftLow();
			}
			
		}
		
		// Shooter toggle
		if (controller.getRawButton(5)) {
			shooter.lower();
		}
		
		if (controller.getRawButton(6)) {
			shooter.raise();
		}
		
		/*if (controller.getRawButton(Controls.controller.TOGGLE_SHOOTER_DEPLOY) && intakeMoveTimer.get() == 0.0) {
			if (!pressingShooter) {
				if (shooter.getRaised()) {
					// [! DELAY FOR INTAKE RETRACT BEFORE SHOOTING]
					if (shooter.getSpinning()) {
						shooter.spinShooter(Constants.SHOOTER_SPEED_SWITCH);
					}
					intakeMoveTimer.reset();
					intakeMoveTimer.start();
					shooter.lower();
				} else {
					if (shooter.getSpinning()) {
						shooter.spinShooter(Constants.SHOOTER_SPEED_SCALE);
					}
					intakeMoveTimer.reset();
					intakeMoveTimer.start();
					intake.deploy();
				}
				pressingShooter = true;
			}
		} else {
			pressingShooter = false;
		}
		
		// Secondary actions after delay from toggle (intake, shooter)
		if (intakeMoveTimer.get() > Constants.INTAKE_MOVE_TIME) {
			intakeMoveTimer.stop();
			intakeMoveTimer.reset();
		}*/
		
		// Shooter spin toggle
		/*if (controller.getRawButton(Controls.controller.TOGGLE_SHOOTER_SPIN)) {
			if (!pressingShooterSpin) {
				if (shooter.getSpinning()) {
					shooter.stop();
				} else {
					if (shooter.getRaised()) {
						//shooter.spinShooter(Constants.SHOOTER_SPEED_SCALE);
						shooter.spinUpper(Constants.SHOOTER_SPEED_SCALE);
					} else {
						shooter.spinShooter(Constants.SHOOTER_SPEED_SWITCH);
					}
				}
				pressingShooterSpin = true;
			}
		} else {
			pressingShooterSpin = false;
		}*/
		
		// Shoot
		if (controller.getRawButton(Controls.controller.SHOOT) && reloadTimer.get() == 0) {
			if (shooter.getRaised()) {
				shooter.spinUpper(Constants.SHOOTER_SPEED_SCALE);
				spinUpTimer.reset();
				spinUpTimer.start();
			} else {
				shooter.spinShooter(Constants.SHOOTER_SPEED_SWITCH);
				shooter.load();
				reloadTimer.reset();
				reloadTimer.start();
			}
		}
		if (reloadTimer.get() > Constants.UNLOAD_TIME) {
			shooter.unload();
			shooter.stop();
			reloadTimer.stop();
			reloadTimer.reset();
		}
		
		if (spinUpTimer.get() > 0.6) {
			shooter.spinLower(Constants.SHOOTER_SPEED_SCALE);
			shooter.load();
			reloadTimer.reset();
			reloadTimer.start();
			spinUpTimer.stop();
			spinUpTimer.reset();
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
		
		// Intake spinning
		if (controller.getRawButton(Controls.controller.INTAKE_IN)) {
			intake.spinIntake(Constants.INTAKE_SPEED);
			//conveyor.spin(Constants.CONVEYOR_SPEED);
		} else if (controller.getRawButton(Controls.controller.INTAKE_OUT)) {
			intake.spinIntake(-Constants.INTAKE_SPEED);
			//conveyor.spin(-Constants.CONVEYOR_SPEED);
		} else {
			intake.stop();
			//conveyor.stop();
		}
		
		/*if (controller.getRawButton(1)) {
			conveyor.boop();
		} else {
			conveyor.stopBoop();
		}*/
		
		if (controller.getRawButton(1)) {
			if (!pressingBooper && booperTimer.get() == 0.0) {
				conveyor.boop();
				booperTimer.stop();
				booperTimer.reset();
				booperTimer.start();
				pressingBooper = true;
			}
		} else {
			pressingBooper = false;
		}
		
		if (booperTimer.get() > 0.5) {
			conveyor.stopBoop();
			booperTimer.stop();
			booperTimer.reset();
		}
		
		/*
		if (controller.getXButton() && conveyor.isArmStopped()) {
			conveyor.closeArm();
		}
		if (conveyor.isArmClosing()) {
			if (conveyor.closedLimit.get()) {
				conveyor.stopArm();
				//conveyor.openArm();
			}
		}*/
		/*
		if (conveyor.isArmOpening()) {
			if (conveyor.openLimit.get()) {
				conveyor.stopArm();
			}
		}*/
		
		//SmartDashboard teleop readings
		//SmartDashboard.putNumber("Encoder distance", drive.getEncoderDist());
		//SmartDashboard.putNumber("Left encoder distance", drive.getLeftDistance());
		//SmartDashboard.putNumber("Right encoder distance", drive.getRightDistance());
		//SmartDashboard.putNumber("Left encoder velocity", drive.getLeftVelocity());
		//SmartDashboard.putNumber("Right encoder velocity", drive.getRightVelocity());
		//SmartDashboard.putNumber("Gyro angle", drive.getGyroAngle());
		
		SmartDashboard.putString("INTAKE: ", intake.getDeployed() ? "IN" : "OUT");
		//SmartDashboard.putString("INTAKE SPIN: ", intake.get ? "SPIN IN" : "SPIN OUT");
		SmartDashboard.putString("DRIVE TRAIN: ", drive.getShifted() ? "HIGH SPEED" : "HIGH TORQUE");
		SmartDashboard.putString("SHOOTER: ", shooter.getRaised() ? "RAISED" : "LOWERED");
		SmartDashboard.putString("CAMERA: ", frontCamera ? "FRONT VIEW" : "BACK VIEW");	
		SmartDashboard.putString("GYRO", "" + drive.getGyroAngle());
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		compressor.start();
		compressor.setClosedLoopControl(true);
		if (leftJoystick.getTrigger()) {
			shooter.load();
		}
		if (rightJoystick.getTrigger()) {
			shooter.unload();
		}
		//Autonomous.switchLeftLeft();
		/*compressor.start();
		compressor.setClosedLoopControl(true);
		if (leftJoystick.getRawButton(1)) {
			intake.deploy();
		} else if (rightJoystick.getRawButton(1)) {
			intake.retract();
		}
		
		if (leftJoystick.getRawButton(2)) {
			shooter.raise();
		} else if (rightJoystick.getRawButton(2)) {
			shooter.lower();
		}
		
		if (leftJoystick.getRawButton(3)) {
			shooter.load();
		} else if (rightJoystick.getRawButton(3)) {
			shooter.unload();
		}
		
		if (leftJoystick.getRawButton(4)) {
			drive.engageShifter();
		} else if (rightJoystick.getRawButton(4)) {
			drive.disengageShifter();
		}*/
	}
	
}
