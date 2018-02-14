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
	 * The default straight driving speed used for autonomous movement
	 */
	public static final double AUTO_SPEED = 0.7;
	
	/**
	 * The proportional value used for the drive train's PID control
	 */
	public static final double DRIVE_PID_P = 0.0;
	/**
	 * The integral value used for the drive train's PID control
	 */
	public static final double DRIVE_PID_I = 0.0;
	/**
	 * The derivative value used for the drive train's PID control
	 */
	public static final double DRIVE_PID_D = 0.0;
	
	/**
	 * The fast speed of the shooter
	 */
	public static final double SHOOTER_SPEED_FAST = 1.0;
	/**
	 * The slow speed of the shooter
	 */
	public static final double SHOOTER_SPEED_SLOW = 0.6;
	
	public static final double ENCODER_PULSE_DISTANCE = 0.0736;
	
	/**
	 * Distances (in inches) of important auto movements
	 */
	public static class dist {
		public static final double WALL_TO_SWITCH_SIDE = 0.0;
		public static final double WALL_TO_SCALE_SIDE = 0.0;
		public static final double AUTO_TEST = 240.0;
	}
	
}
