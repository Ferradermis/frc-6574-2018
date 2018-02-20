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
	 * Whether or not to use single button pneumatics toggle.
	 */
	public static final boolean USE_TOGGLE = true;
	
	/**
	 * Contains constants relating to the configuration of joystick inputs.
	 */
	public static class joystick {
		
		/**
		 * The deadzone amount used for joysticks, expressed as a percent from 0 to 1.
		 */
		public static final double DEAD_PERCENT = 0.1;
		
		/**
		 * Joystick button used to the spin the shooter at a slow speed in reverse.
		 */
		public static final int SHOOTER_SLOW_REVERSE = 9;

		/**
		 * Joystick button used to the spin the shooter at a fast speed in reverse.
		 */
		public static final int SHOOTER_FAST_REVERSE = 7;

		/**
		 * Joystick button used to the spin the shooter at a slow speed forward.
		 */
		public static final int SHOOTER_SLOW_FORWARD = 10;

		/**
		 * Joystick button used to the spin the shooter at a fast speed forward.
		 */
		public static final int SHOOTER_FAST_FORWARD = 8;
		
		/**
		 * Joystick button number used to clear gyro value.
		 */
		public static final int RESET_GYRO = 11;
		
		/**
		 * Joystick button number used to clear encoder readings.
		 */
		public static final int CLEAR_ENCODERS = 12;
		
		/**
		 * Joystick button number used to engage the drive train's shifting mechanism (used only when not in toggle control mode).
		 */
		public static final int ENGAGE_SHIFTER = -1;
		
		/**
		 * Joystick button number used to disengage the drive train's shifting mechanism (used only when not in toggle control mode).
		 */
		public static final int DISENGAGE_SHIFTER = -1;
		 
		/**
		 * Joystick button number used to toggle the drive train's shifting mechanism.
		 */
		public static final int SHIFT = 5;
		
		/**
		 * Joystick button number used to toggle deployment of the intake mechanism (used only when in toggle control mode).
		 */
		public static final int TOGGLE_INTAKE = 6;
		
		/**
		 * Joystick button number used to spin the intake's arm rollers forward.
		 */
		public static final int ARM_FORWARD = 3;
		
		/**
		 * Joystick button number used to spin the intake's arm rollers backward.
		 */
		public static final int ARM_BACKWARD = 4;
		
		/**
		 * Joystick button number used to toggle lifted state of the shooter mechanism (used only not in toggle control mode).
		 */
		public static final int TOGGLE_SHOOTER = 1;
		
		/**
		 * Joystick button number used to raise the shooter mechanism (used only when not in toggle control mode).
		 */
		public static final int RAISE_SHOOTER = -1;
		
		/**
		 * Joystick button number used to lower the shooter mechanism (used only when not in toggle control mode).
		 */
		public static final int LOWER_SHOOTER = -1;
		
		/**
		 * Joystick button number used to deploy the intake's arms (used only when not in toggle control mode).
		 */
		public static final int DEPLOY_ARM = -1;
		
		/**
		 * Joystick button number used to retract the intake's arms (used only when not in toggle control mode).
		 */
		public static final int RETRACT_ARM = -1;
		
	}
	
	/**
	 * Contains constants relating to the configuration of controller inputs.
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
		
		/**
		 * Controller button number used to toggle the drive train's shifting mechanism.
		 */
		public static final int SHIFT = 5;
		
		/**
		 * Controller button number used to toggle deployment of the intake mechanism.
		 */
		public static final int TOGGLE_INTAKE = -1;
		
		/**
		 * Controller button number used to spin the intake's arm rollers forward.
		 */
		public static final int ARM_FORWARD = -1;
		
		/**
		 * Controller button number used to spin the intake's arm rollers backward.
		 */
		public static final int ARM_BACKWARD = -1;
		
	}
	
}
