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
		
	/**
	 * The robot's gyroscope ID.
	 */
	public static final int GYRO_ID = 0;
	
	/**
	 * Map values associated with the robot's input devices.
	 */
	public static class input {
		/**
		 * The USB ID value assigned to the left or primary joystick.
		 */
		public static final int LEFT_JOYSTICK_USB_NUM = 0;
		/**
		 * The USB ID value assigned to the right or secondary joystick.
		 */
		public static final int RIGHT_JOYSTICK_USB_NUM = 1;
		/**
		 * The USB ID value assigned to the game controller.
		 */
		public static final int CONTROLLER_USB_NUM = 2;
		public static final int DRIVE_CONTROLLER_USB_NUM = 3;
	}
	
	public static class camera {
		/**
		 * The USB ID value assigned to the forward-facing camera.
		 */
		public static final int FORWARD_USB_NUM = 1;
		/**
		 * The USB ID value assigned to the backward-facing camera.
		 */
		public static final int BACKWARD_USB_NUM = 0;
	}
	
	/**
	 * Map values associated with the robot's shooter mechanism.
	 */
	public static class shooter {
		/**
		 * The PWM output number associated with the shooter's left Spark motor controllers.
		 */
		public static final int LEFT_TOP_PWM_NUM = 6; // or 7
		public static final int LEFT_BOTTOM_PWM_NUM = 7; // or 6
		/**
		 * The PWM output number associated with the shooter's right Spark motor controllers.
		 */
		public static final int RIGHT_TOP_PWM_NUM = 4; // or 5
		public static final int RIGHT_BOTTOM_PWM_NUM = 5; // or 4
		
		public static final int LOAD_PCM_ID = 6; // or 7
		public static final int UNLOAD_PCM_ID = 7; // or 6
		
		public static final int RAISE_PCM_ID = 4; // or 5
		public static final int LOWER_PCM_ID = 5; // or 4
	}
	
	/**
	 * Map values associated with the robot's drive train.
	 */
	public static class driveTrain {
		/**
		 * The CAN ID assigned to the front left Talon SRX motor controller.
		 */
		public static final int FRONT_LEFT_CAN_ID = 2;
		/**
		 * The CAN ID assigned to the front right Talon SRX motor controller.
		 */
		public static final int FRONT_RIGHT_CAN_ID = 1;
		/**
		 * The CAN ID assigned to the back left Talon SRX motor controller.
		 */
		public static final int BACK_LEFT_CAN_ID = 3;
		/**
		 * The CAN ID assigned to the back right Talon SRX motor controller.
		 */
		public static final int BACK_RIGHT_CAN_ID = 0;
		/**
		 * The PCN port for the solenoid to disengage the drive shifting mechanism.
		 */
		public static final int SHIFT_OFF_PCM_ID = 0;
		/**
		 * The PCN port for the solenoid to engage the drive shifting mechanism.
		 */
		public static final int SHIFT_ON_PCM_ID = 1;
	}
	
	/**
	 * Map values associated with the robot's intake.
	 */
	public static class intake {
		/**
		 * The PWM output number associated with the intake's left Spark motor controller.
		 */
		public static final int LEFT_PWM_NUM = 0;
		/**
		 * The PWM output number associated with the intake's right Spark motor controller.
		 */
		public static final int RIGHT_PWM_NUM = 1;
		/**
		 * The PCM port for the solenoid to deploy the intake mechanism.
		 */
		public static final int DEPLOY_PCM_ID = 2;
		/**
		 * The PCM port for the solenoid to retract mechanism.
		 */
		public static final int RETRACT_PCM_ID = 3;
	}
	
	/**
	 * Map values associated with the robot's conveyor.
	 */
	public static class conveyor {
		/**
		 * The PWM output number associated with the conveyor's left Spark motor controller.
		 */
		public static final int LEFT_PWM_NUM = 2;
		/**
		 * The PWM output number associated with the conveyor's right Spark motor controller.
		 */
		public static final int RIGHT_PWM_NUM = 3;
	}
	
}