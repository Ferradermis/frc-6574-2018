/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6574.robot;

import org.usfirst.frc.team6574.robot.commands.ExampleCommand;
import org.usfirst.frc.team6574.robot.subsystems.DriveTrain;
import org.usfirst.frc.team6574.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team6574.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

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

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	SendableChooser<Boolean> control_chooser = new SendableChooser<>();
	
	Gyro gyro = new AnalogGyro(RobotMap.GYRO_ID);
	
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
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		m_chooser.addObject("Left Scale Auto", new ExampleCommand());
		m_chooser.addObject("Right Scale Auto", new ExampleCommand());
		m_chooser.addObject("Left Switch Auto", new ExampleCommand());
		m_chooser.addObject("Left Scale Auto", new ExampleCommand());
		
		control_chooser.addObject("Use Controller", new Boolean(false));
		control_chooser.addObject("Use Joystick", new Boolean(true));
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
		
		//DriverStation.Alliance.values()
		
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
	}
	
	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
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
		usingJoystick = (Boolean)control_chooser.getSelected();
	}
   
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		//
		// Tank Drive
		//
		if (getLeftY() > Constants.CONTROLLER_DEADZONE) {
			drive.frontLeft(getLeftY() - Constants.CONTROLLER_DEADZONE);
			drive.backLeft(getLeftY() - Constants.CONTROLLER_DEADZONE);
		} else if (getLeftY() < -Constants.CONTROLLER_DEADZONE) {
			drive.frontLeft(getLeftY() + Constants.CONTROLLER_DEADZONE);
			drive.backLeft(getLeftY() + Constants.CONTROLLER_DEADZONE);
		} else {
			drive.stopLeft();
		}
		if (getRightY() > Constants.CONTROLLER_DEADZONE) {
			drive.frontRight(getRightY() - Constants.CONTROLLER_DEADZONE);
			drive.backRight(getRightY() - Constants.CONTROLLER_DEADZONE);
		} else if (getRightY() < -Constants.CONTROLLER_DEADZONE) {
			drive.frontRight(getRightY() + Constants.CONTROLLER_DEADZONE);
			drive.backRight(getRightY() + Constants.CONTROLLER_DEADZONE);
		} else {
			drive.stopRight();
		}
		if (!usingJoystick /*Constants.USE_CONTROLLER*/) {
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
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
	}
	
}
