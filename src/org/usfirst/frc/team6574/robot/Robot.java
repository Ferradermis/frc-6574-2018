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
import org.usfirst.frc.team6574.robot.subsystems.Conveyor;
import org.usfirst.frc.team6574.robot.subsystems.DriveTrain;
import org.usfirst.frc.team6574.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team6574.robot.subsystems.Intake;
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
	
	//public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	
	public static final DriveTrain drive = new DriveTrain(0, 0, 0);
	public static final Intake intake = new Intake();
	public static final Conveyor conveyor = new Conveyor();
	public static final Shooter shooter = new Shooter();
	
	public static OI m_oi;
	
	boolean leftOwnSwitch;
	boolean leftScale;
	boolean leftOppositeSwitch;
	
	//Command m_autonomousCommand;
	//SendableChooser<Command> m_chooser = new SendableChooser<Command>();
	//SendableChooser<Boolean> control_chooser;
	
	boolean usingJoystick = true;
	
	boolean pressingShifter = false;
	boolean pressingIntake = false;
	
	double intakeSpin = 0.0;
	
	double getLeftY() {
		if (usingJoystick)
			return m_oi.leftJoystick.getY();
		if (!usingJoystick)
			return m_oi.controller.getRawAxis(1);
		return 0;
	}
	
	double getRightY() {
		if (usingJoystick)
			return m_oi.rightJoystick.getY();
		if (!usingJoystick)
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
		//m_chooser.addDefault("Default Auto", new AutoDefault());
		//m_chooser.addObject("Switch Auto", new AutoSwitch());
		//m_chooser.addObject("Scale Auto", new AutoScale());
		
		//control_chooser = new SendableChooser<Boolean>();
		//control_chooser.addObject("Use Controller", new Boolean(true));
		//control_chooser.addObject("Use Joystick", new Boolean(false));
		//SmartDashboard.putData("Control chooser", control_chooser);
		
		drive.calibrateGyro();
		
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
		//m_autonomousCommand = m_chooser.getSelected();
		
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
		//if (m_autonomousCommand != null) {
		//	m_autonomousCommand.start();
		//}
		
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

	
	String controlSelected = "";
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		//if (m_autonomousCommand != null) {
		//	m_autonomousCommand.cancel();
		//}
		
		distanceMoved = 0;
		drive.clearEncoders();
		pressingShifter = false;
		pressingIntake = false;
		intakeSpin = 0.0;
		
		//controlSelected = SmartDashboard.getString("Controls", "Joystick");
		//usingJoystick = (Boolean)control_chooser.getSelected();
	}
   
	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		//Scheduler.getInstance().run();
		

		//SmartDashboard.putString("Value", controlSelected);
		
		
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
		
		intake.spin(intakeSpin);
		conveyor.spin(intakeSpin);
		
		if (usingJoystick) {
			if (m_oi.leftJoystick.getRawButton(Controls.joystick.SHOOTER_SLOW_REVERSE)) {
				shooter.spin(-Constants.SHOOTER_SPEED_SLOW);
			} else if (m_oi.leftJoystick.getRawButton(Controls.joystick.SHOOTER_FAST_REVERSE)) {
				shooter.spin(-Constants.SHOOTER_SPEED_FAST);
			} else if (m_oi.leftJoystick.getRawButton(Controls.joystick.SHOOTER_SLOW_FORWARD)) {
				shooter.spin(Constants.SHOOTER_SPEED_SLOW);
			} else if (m_oi.leftJoystick.getRawButton(Controls.joystick.SHOOTER_FAST_FORWARD)) {
				shooter.spin(Constants.SHOOTER_SPEED_FAST);
			} else {
				shooter.stop();
			}
			
			if (m_oi.leftJoystick.getRawButton(Controls.joystick.CLEAR_ENCODERS)) {
				drive.clearEncoders();
			}
			if (m_oi.leftJoystick.getRawButton(Controls.joystick.RESET_GYRO)) {
				drive.resetGyro();
			}
			if (m_oi.leftJoystick.getRawButton(Controls.joystick.SHIFT)) {
				if (!pressingShifter) {
					drive.toggleShifter();
					pressingShifter = true;
				}
			} else {
				pressingShifter = false;
			}
			
			if (Controls.USE_TOGGLE) {
				if (m_oi.leftJoystick.getRawButton(Controls.joystick.TOGGLE_INTAKE)) {
					if (!pressingIntake) {
						intake.toggleDeploy();
						pressingIntake = true;
					}
				} else {
					pressingIntake = false;
				}
			} else {
				if (m_oi.leftJoystick.getRawButton(Controls.joystick.ENGAGE_SHIFTER)) {
					drive.engageShifter();
				} else if (m_oi.leftJoystick.getRawButton(Controls.joystick.DISENGAGE_SHIFTER)) {
					drive.disengageShifter();
				}
			}
			
			if (m_oi.leftJoystick.getRawButton(Controls.joystick.ARM_FORWARD)) {
				if (intakeSpin == 0 || intakeSpin == -Constants.INTAKE_SPEED) {
					intakeSpin = Constants.INTAKE_SPEED;
				} else if (intakeSpin == Constants.INTAKE_SPEED) {
					intakeSpin = 0;
				}
			} else if (m_oi.leftJoystick.getRawButton(Controls.joystick.ARM_BACKWARD)) {
				if (intakeSpin == 0 || intakeSpin == Constants.INTAKE_SPEED) {
					intakeSpin = -Constants.INTAKE_SPEED;
				} else if (intakeSpin == -Constants.INTAKE_SPEED) {
					intakeSpin = 0;
				}
			}
		} else {
			if (m_oi.controller.getRawButton(Controls.controller.SHOOTER_SLOW_REVERSE)) {
				shooter.spin(-Constants.SHOOTER_SPEED_SLOW);
			} else if (m_oi.controller.getRawButton(Controls.controller.SHOOTER_FAST_REVERSE)) {
				shooter.spin(-Constants.SHOOTER_SPEED_FAST);
			} else if (m_oi.controller.getRawButton(Controls.controller.SHOOTER_SLOW_FORWARD)) {
				shooter.spin(Constants.SHOOTER_SPEED_SLOW);
			} else if (m_oi.controller.getRawButton(Controls.controller.SHOOTER_FAST_FORWARD)) {
				shooter.spin(Constants.SHOOTER_SPEED_FAST);
			} else {
				shooter.stop();
			}
			
			if (m_oi.controller.getRawButton(Controls.controller.RESET_GYRO)) {
				drive.resetGyro();
			}
			
			if (m_oi.controller.getRawButton(Controls.controller.CLEAR_ENCODERS)) {
				drive.clearEncoders();
			}
			if (Controls.USE_TOGGLE) {
				if (m_oi.controller.getRawButton(Controls.controller.SHIFT)) {
					if (!pressingShifter) {
						drive.toggleShifter();
						pressingShifter = true;
					}
				} else {
					pressingShifter = false;
				}
			} else {
				if (m_oi.controller.getRawButton(Controls.controller.ENGAGE_SHIFTER)) {
					drive.engageShifter();
				} else if (m_oi.controller.getRawButton(Controls.controller.DISENGAGE_SHIFTER)) {
					drive.disengageShifter();
				}
			}
		}
		
		SmartDashboard.putNumber("Encoder distance", drive.getEncoderDist());
		SmartDashboard.putNumber("Left encoder distance", drive.getLeftDistance());
		SmartDashboard.putNumber("Right encoder distance", drive.getRightDistance());
		SmartDashboard.putNumber("Left encoder velocity", drive.getLeftVelocity());
		SmartDashboard.putNumber("Right encoder velocity", drive.getRightVelocity());
		
		SmartDashboard.putNumber("PID error", drive.getPIDController().getError());
		SmartDashboard.putNumber("PID Setpoint", drive.getPIDController().getSetpoint());
		
		SmartDashboard.putNumber("Gyro angle", drive.getGyroAngle());
		
		//
		// Tank Drive
		//
		if (getLeftY() > Controls.joystick.DEAD_PERCENT) {
			drive.frontLeft(getLeftY() - Controls.joystick.DEAD_PERCENT);
			drive.backLeft(getLeftY() - Controls.joystick.DEAD_PERCENT);
		} else if (getLeftY() < -Controls.joystick.DEAD_PERCENT) {
			drive.frontLeft(getLeftY() + Controls.joystick.DEAD_PERCENT);
			drive.backLeft(getLeftY() + Controls.joystick.DEAD_PERCENT);
		} else if (usingJoystick && m_oi.leftJoystick.getTrigger() || !usingJoystick && m_oi.controller.getRawButton(10)) {
			drive.set(0.6);
		} else {
			drive.stopLeft();
		}
		if (getRightY() > Controls.joystick.DEAD_PERCENT) {
			drive.frontRight(getRightY() - Controls.joystick.DEAD_PERCENT);
			drive.backRight(getRightY() - Controls.joystick.DEAD_PERCENT);
		} else if (getRightY() < -Controls.joystick.DEAD_PERCENT) {
			drive.frontRight(getRightY() + Controls.joystick.DEAD_PERCENT);
			drive.backRight(getRightY() + Controls.joystick.DEAD_PERCENT);
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
