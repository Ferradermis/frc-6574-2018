/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6574.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static final int PDP_CAN_ID = -1;
	public static final int RIO_CAN_ID = -1;
	public static final int PCM_CAN_ID = -1;
	
	/**
	 * The robot's gyroscope ID 
	 */
	public static final int GYRO_ID = 0;
	
	/**
	 * Map values associated with the robot's input devices
	 */
	public static class input {
		public static final int LEFT_JOYSTICK_USB_NUM = 0;
		public static final int RIGHT_JOYSTICK_USB_NUM = 1;
		
		public static final int CONTROLLER_USB_NUM = 0;
	}
	
	/**
	 * Map values associated with the robot's shooter mechanism
	 */
	public static class shooter {
		/**
		 * The PWM output number associated with the shooter's left Spark motor controller
		 */
		public static final int LEFT_PWM_NUM = 0;
		/**
		 * The PWM output number associated with the shooter's right Spark motor controller
		 */
		public static final int RIGHT_PWM_NUM = 1;
	}
	
	/**
	 * Map values associated with the robot's drive train
	 */
	public static class driveTrain {
		/**
		 * The CAN ID assigned to the front left Talon SRX motor controller
		 */
		public static final int FRONT_LEFT_CAN_ID = 2;
		/**
		 * The CAN ID assigned to the front right Talon SRX motor controller
		 */
		public static final int FRONT_RIGHT_CAN_ID = 1;
		/**
		 * The CAN ID assigned to the back left Talon SRX motor controller
		 */
		public static final int BACK_LEFT_CAN_ID = 3;
		/**
		 * The CAN ID assigned to the back right Talon SRX motor controller
		 */
		public static final int BACK_RIGHT_CAN_ID = 0;
		
		public static final int LEFT_SHIFT_ON_PCN_ID = 0;
		public static final int LEFT_SHIFT_OFF_PCN_ID = 1;
		public static final int RIGHT_SHIFT_ON_PCN_ID = 2;
		public static final int RIGHT_SHIFT_OFF_PNC_ID = 3;
	}
	
	/**
	 * Map values associated with the robot's encoders
	 */
	public static class encoder {
		/**
		 * The digital input channel associated with the left drive train encoder's A channel
		 */
		public static final int LEFT_A_CHANNEL = 0;
		/**
		 * The digital input channel associated with the left drive train encoder's B channel
		 */
		public static final int LEFT_B_CHANNEL = 0;
		/**
		 * The digital input channel associated with the right drive train encoder's A channel
		 */
		public static final int RIGHT_A_CHANNEL = 0;
		/**
		 * The digital input channel associated with the right drive train encoder's B channel
		 */
		public static final int RIGHT_B_CHANNEL = 0;
	}
	
}