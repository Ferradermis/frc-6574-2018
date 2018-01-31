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
	
	public static final int GYRO_ID = 0;
	
	public static class input {
		public static final int LEFT_JOYSTICK_USB_NUM = 0;
		public static final int RIGHT_JOYSTICK_USB_NUM = 1;
		
		public static final int CONTROLLER_USB_NUM = 0;
	}
	
	public static class shooter {
		public static final int LEFT_PWM_NUM = 0;
		public static final int RIGHT_PWM_NUM = 1;
	}
	
	public static class driveTrain {
		public static final int FRONT_LEFT_CAN_ID = 2;
		public static final int FRONT_RIGHT_CAN_ID = 1;
		public static final int BACK_LEFT_CAN_ID = 3;
		public static final int BACK_RIGHT_CAN_ID = 0;
		
		public static final int LEFT_SHIFT_ON_PCN_ID = 0;
		public static final int LEFT_SHIFT_OFF_PCN_ID = 1;
		public static final int RIGHT_SHIFT_ON_PCN_ID = 2;
		public static final int RIGHT_SHIFT_OFF_PNC_ID = 3;

		public static final int LEFT_ENCODER_ID = 1;
		public static final int RIGHT_ENCODER_ID = 0;
	}
	
}