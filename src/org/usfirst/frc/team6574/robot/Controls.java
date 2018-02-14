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
	
	public static final boolean USE_DUAL_JOYSTICK = false;
	public static final boolean USE_CONTROLLER = true;
	
	/**
	 * Contains constants relating to the configuration of joystick inputs.
	 */
	public static class joystick {
		
		/**
		 * The deadzone amount used for joysticks, expressed as a percent from 0 to 1.
		 */
		public static final double DEAD_PERCENT = 0.3;
		
		/**
		 * Joystick button number used to clear gyro value.
		 */
		public static final int RESET_GYRO = 11;
		
		/**
		 * Joystick button number used to clear encoder readings.
		 */
		public static final int CLEAR_ENCODERS = 12;
		
		/**
		 * Joystick button number used to engage the drive train's shifting mechanism.
		 */
		public static final int ENGAGE_SHIFTER = 3;
		
		/**
		 * Joystick button number used to disengage the drive train's shifting mechanism.
		 */
		public static final int DISENGAGE_SHIFTER = 4;
		 
	}
	
	/**
	 * Contains constants relating to the configuratino of controller inputs.
	 */
	public static class controller {
		
		/**
		 * Controller button used to the spin the shooter at a slow speed in reverse.
		 */
		public static final int SHOOTER_SLOW_REVERSE = 2;

		/**
		 * Controller button used to the spin the shooter at a fast speed in reverse.
		 */
		public static final int SHOOTER_FAST_REVERSE = 1;

		/**
		 * Controller button used to the spin the shooter at a slow speed forward.
		 */
		public static final int SHOOTER_SLOW_FORWARD = 3;

		/**
		 * Controller button used to the spin the shooter at a fast speed forward.
		 */
		public static final int SHOOTER_FAST_FORWARD = 4;
		
		/**
		 * Controller button number used to clear gyro value.
		 */
		public static final int RESET_GYRO = 9;
		
		/**
		 * Controller button number used to clear encoder readings.
		 */
		public static final int CLEAR_ENCODERS = 9;
		
		/**
		 * Controller button number used to engage the drive train's shifting mechanism.
		 */
		public static final int ENGAGE_SHIFTER = 5;
		
		/**
		 * Controller button number used to disengage the drive train's shifting mechanism.
		 */
		public static final int DISENGAGE_SHIFTER = 6;
		
	}
	
}