package org.usfirst.frc.team6574.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {

	public static int autoStage = 0;
	public static double distanceMoved = 0;
	public static Timer autoTimer = new Timer();
	
	
	public static void switchLeftLeft() {
		if (autoStage == 0) {
			if (distanceMoved < 120 * Constants.MULTIPLY_BY_THIS_TO_MAKE_INCHES) {
				distanceMoved = Math.abs(Robot.drive.getEncoderDist());
				SmartDashboard.putNumber("Encoder dist", distanceMoved);
				Robot.drive.set(Constants.AUTO_DRIVE_SPEED);
			} else {
				Robot.drive.stop();
				Robot.drive.resetGyro();
				Robot.drive.clearEncoders();
				autoStage = 1;
			}
		} else if (autoStage == 1) {
			if (Robot.drive.getGyroAngle() > -90) {
				Robot.drive.rotate(-Constants.AUTO_ROTATE_SPEED);
			} else {
				Robot.drive.stop();
				Robot.drive.resetGyro();
				autoStage = 2;
				autoTimer.reset();
			}
		} else if (autoStage == 2) {
			if (autoTimer.get() < 2) {
				Robot.drive.set(-Constants.AUTO_DRIVE_SPEED);
			} else {
				Robot.drive.stop();
			}
		} else if (autoStage == 3) {
			autoTimer.stop();
			autoTimer.reset();
			Robot.drive.stop();
			Robot.drive.clearEncoders();
		}
	}
	
	public static void switchRightRight() {
		if (autoStage == 0) {
			if (distanceMoved < 120 * Constants.MULTIPLY_BY_THIS_TO_MAKE_INCHES) {
				distanceMoved = Math.abs(Robot.drive.getEncoderDist());
				SmartDashboard.putNumber("Encoder dist", distanceMoved);
				Robot.drive.set(Constants.AUTO_DRIVE_SPEED);
			} else {
				Robot.drive.stop();
				Robot.drive.resetGyro();
				Robot.drive.clearEncoders();
				autoStage = 1;
			}
		} else if (autoStage == 1) {
			if (Robot.drive.getGyroAngle() < 90) {
				Robot.drive.rotate(Constants.AUTO_ROTATE_SPEED);
			} else {
				Robot.drive.stop();
				Robot.drive.resetGyro();
				autoStage = 2;
				autoTimer.reset();
			}
		} else if (autoStage == 2) {
			if (autoTimer.get() < 2) {
				Robot.drive.set(-Constants.AUTO_DRIVE_SPEED);
			} else {
				Robot.drive.stop();
			}
		} else if (autoStage == 3) {
			autoTimer.stop();
			autoTimer.reset();
			Robot.drive.stop();
			Robot.drive.clearEncoders();
		}
	}
	
	public static void switchLeftCenter() {
		
	}
	
	public static void switchRightCenter() {
		
	}
	
	public static void sideAutoLine() {
		if (autoStage == 0) {
			if (distanceMoved < 120 * Constants.MULTIPLY_BY_THIS_TO_MAKE_INCHES) {
				distanceMoved = Math.abs(Robot.drive.getEncoderDist());
				SmartDashboard.putNumber("Encoder dist", distanceMoved);
				Robot.drive.set(Constants.AUTO_DRIVE_SPEED);
			} else {
				Robot.drive.stop();
				Robot.drive.resetGyro();
				Robot.drive.clearEncoders();
				autoStage = 1;
			}
		}
	}
	
	public static void scaleLeft() {
		
	}
	
	public static void scaleRight() {
		
	}
	
}
