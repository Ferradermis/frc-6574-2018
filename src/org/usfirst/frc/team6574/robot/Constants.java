package org.usfirst.frc.team6574.robot;

/**
 * Contains various robot constants. Constants associated with hardware mapping
 * for the robot should be contained within the RobotMap class, and constants
 * associated with input device button mapping and configuration should be
 * contained within the Controls class.
 * 
 * @author Zach Brantmeier
 */
public class Constants {
	
	/**
	 * The default straight driving speed used for autonomous movement.
	 */
	public static final double AUTO_DRIVE_SPEED = 0.7;
	
	public static final double AUTO_ROTATE_SPEED = 0.4;
	
	public static final double UNLOAD_TIME = 1.0;
	public static final double INTAKE_MOVE_TIME = 1.0;
	
	/**
	 * The speed of the intake arm rollers.
	 */
	public static final double INTAKE_SPEED = 0.5;
	
	/**
	 * The speed of the conveyor belt rollers.
	 */
	public static final double CONVEYOR_SPEED = 1;
	
	public static final double ENCODER_PULSE_DISTANCE = 0.0736;
	
	public static final double SHOOTER_SPEED_SCALE = 0.8;
	public static final double SHOOTER_SPEED_SWITCH = 0.28;
	
	public static final double MULTIPLY_BY_THIS_TO_MAKE_INCHES = 424.7157;
	
}
