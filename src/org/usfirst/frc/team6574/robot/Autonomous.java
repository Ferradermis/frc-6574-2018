package org.usfirst.frc.team6574.robot;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

	public static int autoStage = 0;
	public static Timer autoTimer = new Timer();
	
	public static final class SWITCH {
		public static void leftFromLeft() {
			if (autoStage == 0) {
				if (Math.abs(Robot.drive.getEncoderDist()) < 120 * Constants.MULTIPLY_BY_THIS_TO_MAKE_INCHES) {
					Robot.drive.set(Constants.AUTO_DRIVE_SPEED);
				} else {
					Robot.drive.stop();
					Robot.drive.resetGyro();
					Robot.drive.clearEncoders();
					//Robot.shooter.lower();
					//autoTimer.reset();
					//autoTimer.start();
					autoStage = 1;
				}
			} else if (autoStage == 1) {
				/*if (autoTimer.get() < 0.2) {
					Robot.conveyor.spin(Constants.CONVEYOR_SPEED);
					Robot.conveyor.boop();
				} else {
					Robot.conveyor.stop();
					Robot.conveyor.stopBoop();
				}*/
				if (Robot.drive.getGyroAngle() > -90) {
					Robot.drive.rotate(-Constants.AUTO_ROTATE_SPEED);
				} else {
					Robot.drive.stop();
					Robot.drive.resetGyro();
					autoTimer.reset();
					autoTimer.start();
					autoStage = 2;
				}
			} else if (autoStage == 2) {
				if (autoTimer.get() < 2.5) {
					Robot.drive.set(-Constants.AUTO_DRIVE_SPEED);
				} else {
					Robot.drive.stop();
					autoTimer.stop();
					autoTimer.reset();
					autoTimer.start();
					autoStage = 3;
				}
			}  else if (autoStage == 3) {
				if (autoTimer.get() < 0.5) {
					Robot.shooter.spinShooter(Constants.SHOOTER_SPEED_AUTO);
				} else {
					autoTimer.stop();
					autoTimer.reset();
					autoTimer.start();
					autoStage = 4;
				}
			} else if (autoStage == 4) {
				if (autoTimer.get() < 0.5) {
					Robot.shooter.load();
				} else {
					Robot.shooter.unload();
					Robot.shooter.stop();
					autoTimer.stop();
					autoTimer.reset();
					autoStage = 5;
				}
			}
		}
		
		public static void leftFromRight() {
			
		}
		
		public static void rightFromLeft() {
			
		}
		
		public static void rightFromRight() {
			if (autoStage == 0) {
				if (Math.abs(Robot.drive.getEncoderDist()) < 120 * Constants.MULTIPLY_BY_THIS_TO_MAKE_INCHES) {
					Robot.drive.set(Constants.AUTO_DRIVE_SPEED);
				} else {
					Robot.drive.stop();
					Robot.drive.resetGyro();
					Robot.drive.clearEncoders();
					//Robot.shooter.lower();
					//autoTimer.reset();
					//autoTimer.start();
					autoStage = 1;
				}
			} else if (autoStage == 1) {
				/*if (autoTimer.get() < 0.2) {
					Robot.conveyor.spin(Constants.CONVEYOR_SPEED);
					Robot.conveyor.boop();
				} else {
					Robot.conveyor.stop();
					Robot.conveyor.stopBoop();
				}*/
				if (Robot.drive.getGyroAngle() < 90) {
					Robot.drive.rotate(Constants.AUTO_ROTATE_SPEED);
				} else {
					Robot.drive.stop();
					Robot.drive.resetGyro();
					autoTimer.reset();
					autoTimer.start();
					autoStage = 2;
				}
			} else if (autoStage == 2) {
				if (autoTimer.get() < 2.5) {
					Robot.drive.set(-Constants.AUTO_DRIVE_SPEED);
				} else {
					Robot.drive.stop();
					autoTimer.stop();
					autoTimer.reset();
					autoTimer.start();
					autoStage = 3;
				}
			} else if (autoStage == 3) {
				if (autoTimer.get() < 0.5) {
					Robot.shooter.spinShooter(Constants.SHOOTER_SPEED_AUTO);
				} else {
					autoTimer.stop();
					autoTimer.reset();
					autoTimer.start();
					autoStage = 4;
				}
			} else if (autoStage == 4) {
				if (autoTimer.get() < 0.5) {
					Robot.shooter.load();
				} else {
					Robot.shooter.unload();
					Robot.shooter.stop();
					autoTimer.stop();
					autoTimer.reset();
					autoStage = 5;
				}
			}
		}
		
		public static void leftFromCenter() {
			if (autoStage == 0) {
				if (Math.abs(Robot.drive.getEncoderDist()) < 10 * Constants.MULTIPLY_BY_THIS_TO_MAKE_INCHES) {
					Robot.drive.set(Constants.AUTO_DRIVE_SPEED);
				} else {
					Robot.drive.stop();
					Robot.drive.resetGyro();
					Robot.drive.clearEncoders();
					autoTimer.start();
					autoTimer.reset();
					autoStage = 1;
				}
			} else if (autoStage == 1) {
				//Robot.shooter.lower();
				if (Robot.drive.getGyroAngle() < 90) {
					Robot.drive.rotate(Constants.AUTO_ROTATE_SPEED);
				} else {
					//Robot.shooter.lower();
					Robot.drive.stop();
					Robot.drive.clearEncoders();
					autoStage = 2;
				}
			} else if (autoStage == 2) {
				if (Math.abs(Robot.drive.getEncoderDist()) < 102 * Constants.MULTIPLY_BY_THIS_TO_MAKE_INCHES) {
					Robot.drive.set(-Constants.AUTO_DRIVE_SPEED);
				} else {
					Robot.drive.stop();
					Robot.drive.clearEncoders();
					Robot.drive.resetGyro();
					autoStage = 3;
				}
			} else if (autoStage == 3) {
				if (Robot.drive.getGyroAngle() < 180) {
					Robot.drive.rotate(Constants.AUTO_ROTATE_SPEED);
				} else {
					Robot.drive.stop();
					Robot.drive.resetGyro();
					Robot.drive.clearEncoders();
					//autoTimer.start();
					//autoTimer.reset();
					//Robot.conveyor.spin(Constants.CONVEYOR_SPEED);
					//Robot.conveyor.boop();
					autoStage = 4;
				}
			} else if (autoStage == 4) {
				/*if (autoTimer.get() > 0.2) {
					Robot.conveyor.stop();
					Robot.conveyor.stopBoop();
					autoTimer.stop();
					autoTimer.reset();
				}*/
				if (Math.abs(Robot.drive.getEncoderDist()) < 70 * Constants.MULTIPLY_BY_THIS_TO_MAKE_INCHES) {
					Robot.drive.set(-Constants.AUTO_DRIVE_SPEED);
				} else {
					Robot.drive.stop();
					Robot.drive.clearEncoders();
					Robot.shooter.spinShooter(Constants.SHOOTER_SPEED_AUTO);
					Robot.shooter.load();
					autoTimer.start();
					autoTimer.reset();
					autoStage = 5;
				}
			} else if (autoStage == 5) {
				if (autoTimer.get() > 0.2) {
					Robot.drive.stop();
					Robot.shooter.stop();
					Robot.shooter.unload();
					autoStage = 6;
				}
			}
		}
		
		public static void rightFromCenter() {
			if (autoStage == 0) {
				if (Math.abs(Robot.drive.getEncoderDist()) < 40 * Constants.MULTIPLY_BY_THIS_TO_MAKE_INCHES) {
					Robot.drive.set(Constants.AUTO_DRIVE_SPEED);
				} else {
					//Robot.shooter.lower();
					Robot.drive.stop();
					Robot.drive.resetGyro();
					Robot.drive.clearEncoders();
					autoStage = 1;
				}
			} else if (autoStage == 1) {
				if (Robot.drive.getGyroAngle() < 180) {
					Robot.drive.rotate(Constants.AUTO_ROTATE_SPEED);
				} else {
					Robot.drive.stop();
					Robot.drive.resetGyro();
					Robot.drive.clearEncoders();
					//autoTimer.start();
					//autoTimer.reset();
					//Robot.conveyor.spin(Constants.CONVEYOR_SPEED);
					//Robot.conveyor.boop();
					autoStage = 2;
				}
			} else if (autoStage == 2) {
				/*if (autoTimer.get() > 0.2) {
					Robot.conveyor.stop();
					Robot.conveyor.stopBoop();
					autoTimer.stop();
					autoTimer.reset();
				}*/
				if (Math.abs(Robot.drive.getEncoderDist()) < 50 * Constants.MULTIPLY_BY_THIS_TO_MAKE_INCHES) {
					Robot.drive.set(-Constants.AUTO_DRIVE_SPEED);
				} else {
					Robot.drive.stop();
					Robot.shooter.spinShooter(Constants.SHOOTER_SPEED_AUTO);
					Robot.shooter.load();
					Robot.drive.resetGyro();
					Robot.drive.clearEncoders();
					autoTimer.reset();
					autoTimer.start();
					autoStage = 3;
				}
			} else if (autoStage == 3) {
				if (autoTimer.get() > 0.2) {
					Robot.shooter.unload();
					Robot.shooter.stop();
					autoTimer.stop();
					autoTimer.reset();
					autoStage = 4;
				}
			}
		}
	}
	
	public static final class SCALE {
		public static void leftFromLeft() {
			if (autoStage == 0) {
				if (Math.abs(Robot.drive.getEncoderDist()) < 270 * Constants.MULTIPLY_BY_THIS_TO_MAKE_INCHES) {
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
					Robot.shooter.spinShooter(Constants.SHOOTER_SPEED_SCALE);
					autoTimer.stop();
					autoTimer.reset();
					autoTimer.start();
					autoStage = 2;
				}
			} else if (autoStage == 2) {
				if (autoTimer.get() > 0.5) {
					Robot.shooter.load();
					autoTimer.stop();
					autoTimer.reset();
					autoTimer.start();
					autoStage = 3;
				}
			} else if (autoStage == 3) {
				if (autoTimer.get() > 0.5) {
					Robot.shooter.unload();
					Robot.shooter.stop();
					autoTimer.stop();
					autoTimer.reset();
					autoStage = 4;
				}
			}
		}
		
		public static void leftFromRight() {
			
		}
		
		public static void rightFromLeft() {
			
		}
		
		public static void rightFromRight() {
			if (autoStage == 0) {
				if (Math.abs(Robot.drive.getEncoderDist()) < 270 * Constants.MULTIPLY_BY_THIS_TO_MAKE_INCHES) {
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
					Robot.shooter.spinShooter(Constants.SHOOTER_SPEED_SCALE);
					autoTimer.stop();
					autoTimer.reset();
					autoTimer.start();
					autoStage = 2;
				}
			} else if (autoStage == 2) {
				if (autoTimer.get() > 0.5) {
					Robot.shooter.load();
					autoTimer.stop();
					autoTimer.reset();
					autoTimer.start();
					autoStage = 3;
				}
			} else if (autoStage == 3) {
				if (autoTimer.get() > 0.5) {
					Robot.shooter.unload();
					Robot.shooter.stop();
					autoTimer.stop();
					autoTimer.reset();
					autoStage = 4;
				}
			}
		}
	}
	
	public static void autoLine() {
		if (autoStage == 0) {
			if (Math.abs(Robot.drive.getEncoderDist()) < 120 * Constants.MULTIPLY_BY_THIS_TO_MAKE_INCHES) {
				Robot.drive.set(Constants.AUTO_DRIVE_SPEED);
			} else {
				Robot.drive.stop();
				Robot.drive.resetGyro();
				Robot.drive.clearEncoders();
				autoStage = 1;
			}
		}
	}
	
}
