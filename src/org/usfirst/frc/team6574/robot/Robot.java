/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6574.robot;

import org.usfirst.frc.team6574.robot.commands.AutoDefault;
import org.usfirst.frc.team6574.robot.commands.AutoScale;
import org.usfirst.frc.team6574.robot.commands.AutoSwitch;
import org.usfirst.frc.team6574.robot.subsystems.DriveTrain;
import org.usfirst.frc.team6574.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team6574.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
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
	
	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	public static final DriveTrain drive = new DriveTrain(0, 0, 0);
	
	Shooter shooter = new Shooter();
	public static OI m_oi;
	
	boolean leftOwnSwitch;
	boolean leftScale;
	boolean leftOppositeSwitch;
	
	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<Command>();
	SendableChooser<Boolean> control_chooser;
	
	boolean usingJoystick = false;
	
	double getLeftY() {
		if (usingJoystick/*Constants.USE_DUAL_JOYSTICK*/)
			return m_oi.leftJoystick.getY();
		if (!usingJoystick/*Constants.USE_CONTROLLER*/)
			return m_oi.controller.getRawAxis(1);
		return 0;
	}
	
	double getRightY() {
		if (usingJoystick/*Constants.USE_DUAL_JOYSTICK*/)
			return m_oi.rightJoystick.getY();
		if (!usingJoystick/*Constants.USE_CONTROLLER*/)
			return m_oi.controller.getRawAxis(3);	
		return 0;
	}
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new AutoDefault());
		m_chooser.addObject("Switch Auto", new AutoSwitch());
		m_chooser.addObject("Scale Auto", new AutoScale());
		
		//control_chooser = new SendableChooser<Boolean>();
		//control_chooser.addObject("Use Controller", new Boolean(true));
		//control_chooser.addObject("Use Joystick", new Boolean(false));
		//SmartDashboard.putData("Control chooser", control_chooser);
		
		leftOwnSwitch = false;
		leftScale = false;
		leftOppositeSwitch = false;
		
	}

	/** 
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	double distanceMoved = 0;

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
		m_autonomousCommand = m_chooser.getSelected();
		
		// GET FMS POSITION
		
		distanceMoved = 0;
		
		String positions = DriverStation.getInstance().getGameSpecificMessage();
		leftOwnSwitch = positions.charAt(0) == 'L' ? true : false;
		leftScale = positions.charAt(1) == 'L' ? true : false;
		leftOppositeSwitch = positions.charAt(2) == 'L' ? true : false;
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		
		drive.clearEncoders();
		
	}
	
	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		SmartDashboard.putNumber("Encoder distance", drive.getEncoderDist());
		SmartDashboard.putNumber("Left encoder distance", drive.getLeftDistance());
		SmartDashboard.putNumber("Right encoder distance", drive.getRightDistance());
		SmartDashboard.putNumber("Left encoder velocity", drive.getLeftVelocity());
		SmartDashboard.putNumber("Right encoder velocity", drive.getRightVelocity());
		
		SmartDashboard.putNumber("Gyro angle", drive.getGyroAngle());
		
		/*if (drive.getEncoderDist() < Constants.dist.AUTO_TEST) {
			drive.set(Constants.AUTO_SPEED);
		}*/
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
		distanceMoved = 0;
		drive.clearEncoders();
		//usingJoystick = (Boolean)control_chooser.getSelected();
	}
   
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		//7900 "POSITION" per revolution
		//if (distanceMoved < )
		/*
		if (distanceMoved < 120.0 * (9.96/13.0)) {
			distanceMoved = 0.0736 * Math.abs(drive.getEncoderDist()) / 34;
			SmartDashboard.putNumber("Encoder dist", distanceMoved);
			drive.set(0.6);
		} else {
			drive.set(0);
		}*/
		
		
		if (usingJoystick) {			
			if (m_oi.leftJoystick.getRawButton(12)) {
				drive.clearEncoders();
			}
			if (m_oi.leftJoystick.getRawButton(11)) {
				drive.resetGyro();
			}
			if (m_oi.leftJoystick.getRawButton(3)) {
				drive.engageShifter();
			} else if (m_oi.leftJoystick.getRawButton(4)) {
				drive.disengageShifter();
			}
		} else {
			if (m_oi.controller.getRawButton(2)) {
				shooter.spin(-Constants.SHOOTER_SPEED_SLOW);
			} else if (m_oi.controller.getRawButton(1)) {
				shooter.spin(-Constants.SHOOTER_SPEED_FAST);
			} else if (m_oi.controller.getRawButton(3)) {
				shooter.spin(Constants.SHOOTER_SPEED_SLOW);
			} else if (m_oi.controller.getRawButton(4)) {
				shooter.spin(Constants.SHOOTER_SPEED_FAST);
			} else {
				shooter.stop();
			}
			
			if (m_oi.controller.getRawButton(9)) {
				drive.resetGyro();
				drive.clearEncoders();
			}
			
			if (m_oi.controller.getRawButton(5)) {
				drive.engageShifter();
			} else if (m_oi.controller.getRawButton(6)) {
				drive.disengageShifter();
			}
		}
		
		
		SmartDashboard.putNumber("Encoder distance", drive.getEncoderDist());
		SmartDashboard.putNumber("Left encoder distance", drive.getLeftDistance());
		SmartDashboard.putNumber("Right encoder distance", drive.getRightDistance());
		SmartDashboard.putNumber("Left encoder velocity", drive.getLeftVelocity());
		SmartDashboard.putNumber("Right encoder velocity", drive.getRightVelocity());
		
		SmartDashboard.putNumber("Gyro angle", drive.getGyroAngle());
		
		//
		// Tank Drive
		//
		if (getLeftY() > Constants.JOYSTICK_MOVE_THRESHOLD) {
			drive.frontLeft(getLeftY() - Constants.JOYSTICK_MOVE_THRESHOLD);
			drive.backLeft(getLeftY() - Constants.JOYSTICK_MOVE_THRESHOLD);
		} else if (getLeftY() < -Constants.JOYSTICK_MOVE_THRESHOLD) {
			drive.frontLeft(getLeftY() + Constants.JOYSTICK_MOVE_THRESHOLD);
			drive.backLeft(getLeftY() + Constants.JOYSTICK_MOVE_THRESHOLD);
		} else if (usingJoystick && m_oi.leftJoystick.getTrigger() || !usingJoystick && m_oi.controller.getRawButton(10)) {
			drive.set(0.6);
		} else {
			drive.stopLeft();
		}
		if (getRightY() > Constants.JOYSTICK_MOVE_THRESHOLD) {
			drive.frontRight(getRightY() - Constants.JOYSTICK_MOVE_THRESHOLD);
			drive.backRight(getRightY() - Constants.JOYSTICK_MOVE_THRESHOLD);
		} else if (getRightY() < -Constants.JOYSTICK_MOVE_THRESHOLD) {
			drive.frontRight(getRightY() + Constants.JOYSTICK_MOVE_THRESHOLD);
			drive.backRight(getRightY() + Constants.JOYSTICK_MOVE_THRESHOLD);
		} else if (usingJoystick && m_oi.leftJoystick.getTrigger() || !usingJoystick && m_oi.controller.getRawButton(10)) {
			drive.set(0.4);
		} else {
			drive.stopRight();
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
	}
	
}
