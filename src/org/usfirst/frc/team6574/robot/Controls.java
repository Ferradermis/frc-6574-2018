package org.usfirst.frc.team6574.robot;

/**
 * Contains various constants associated with input device button mapping
 * and configuration. Constants associated with hardware mapping for the 
 * robot should be contained within the RobotMap class, and other general
 * constants should be contained within the Constants class.
 * 
 * @author Zach Brantmeier
 */
public class Controls {
	
	/**
	 * Contains constants relating to the configuration of joystick inputs.
	 */
	public static class joystick {
		
		/**
		 * The deadzone amount used for joysticks, expressed as a percent from 0 to 1.
		 */
		public static final double DEAD_PERCENT = 0.1;
 
		/**
		 * Joystick button number used to toggle the drive train's shifting mechanism.
		 */
		public static final int TOGGLE_SHIFT = 2;
		
		/**
		 * Joystick button number used to engage the drive train's turbo feature.
		 */
		public static final int TURBO = 1;
		
		/**
		 * Joystick button number used to toggle between single and dual joystick control modes.
		 */
		public static final int TOGGLE_DUAL = 7;
	}
	
	/**
	 * Contains constants relating to the configuration of controller inputs.
	 */
	public static class controller {
		
		/**
		 * Controller button number used to toggle shooter wheel spinning.
		 */
		public static final int TOGGLE_SHOOTER_SPIN = 4;
		
		/**
		 * Controller button number used to toggle deployment of the intake mechanism.
		 */
		public static final int TOGGLE_INTAKE = 2;
		
		/**
		 * Controller button number used to spin the intake's arm rollers forward.
		 */
		public static final int INTAKE_IN = 7;
		
		/**
		 * Controller button number used to spin the intake's arm rollers backward.
		 */
		public static final int INTAKE_OUT = 8;
		
		/**
		 * Controller button number used to toggle camera feeds.
		 */
		public static final int TOGGLE_CAMERA = 9;
		
		/**
		 * Controller button number used to toggle shooter deployment.
		 */
		public static final int TOGGLE_SHOOTER_DEPLOY = 1;
		
		/**
		 * Controller button number used to shoot a cube.
		 */
		public static final int SHOOT = 3;
		
	}
	
}
