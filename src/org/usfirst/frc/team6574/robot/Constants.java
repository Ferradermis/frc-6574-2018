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
	public static final double AUTO_SPEED = 0.7;
	
	/**
	 * The speed of the intake arm rollers.
	 */
	public static final double INTAKE_SPEED = 0.5;
	
	/**
	 * The speed of the conveyor belt rollers.
	 */
	public static final double CONVEYOR_SPEED = 1;
	
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
