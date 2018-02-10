package org.usfirst.frc.team6574.robot;

public class Constants {
	
	/**
	 * The deadzone used for joysticks
	 */
	public static final double JOYSTICK_MOVE_THRESHOLD = 0.3;
	
	/**
	 * The default straight driving speed used for autonomous movement
	 */
	public static final double AUTO_SPEED = 0.7;
	
	public static final double DRIVE_PID_P = 0.0;
	public static final double DRIVE_PID_I = 0.0;
	public static final double DRIVE_PID_D = 0.0;
	
	public static final boolean USE_DUAL_JOYSTICK = false;
	public static final boolean USE_CONTROLLER = true;
	
	/**
	 * The fast speed of the shooter
	 */
	public static final double SHOOTER_SPEED_FAST = 1.0;
	/**
	 * The slow speed of the shooter
	 */
	public static final double SHOOTER_SPEED_SLOW = 0.6;
	
	/**
	 * The IP address of the radio on the robot's network
	 */
	public static final String RADIO_IP = "10.65.74.1";
	/**
	 * The IP address of the RoboRio on the robot's network
	 */
	public static final String RIO_IP = "10.65.74.2";
	/**
	 * The IP address of the Raspberry Pi on the robot's network
	 */
	public static final String PI_IP = "10.65.74.5";
	
	/**
	 * Distances (in inches) of important auto movements
	 */
	public static class dist {
		public static final double WALL_TO_SWITCH_SIDE = 0.0;
		public static final double WALL_TO_SCALE_SIDE = 0.0;
	}
	
}
