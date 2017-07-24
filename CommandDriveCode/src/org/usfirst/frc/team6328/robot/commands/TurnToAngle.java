package org.usfirst.frc.team6328.robot.commands;

import org.usfirst.frc.team6328.robot.Robot;
import org.usfirst.frc.team6328.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turns the specified number of degrees, -180 to 180. Resets the gyro at the beginning by default
 * If not resetting the gyro, angle is a target, so passing 90 may not turn 90 degrees
 */
public class TurnToAngle extends Command implements PIDOutput {
	
	private double kP;
    private double kI;
    private double kD;
    private double kF;
    private double kToleranceDegrees;
    private int kToleranceBufSamples;
    private double updatePeriod;
    private PIDController turnController;
    private double targetAngle;
    private boolean absoluteAngle;
    
    private double rotateToAngleRate;
    
    public TurnToAngle(double angle) {
    		this(angle, false);
    }

	public TurnToAngle(double angle, boolean absoluteAngle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super("TurnToAngle");
    	requires(Robot.driveSubsystem);

        targetAngle = angle;
        if (RobotMap.practiceRobot) {
        	kP = 0.01;
        	kI = 0;
        	kD = 0.003;
        	kF = 0;
        	kToleranceDegrees = 1.0;
        	kToleranceBufSamples = 10;
        	updatePeriod = 0.05;
        } else {
        	kP = 0.0077; // 0.008
        	kI = 0;
        	kD = 0.0137; // 0.014
        	kF = 0;
        	kToleranceDegrees = 1.0;
        	kToleranceBufSamples = 10;
        	updatePeriod = 0.02;
        }
     this.absoluteAngle = absoluteAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	turnController = new PIDController(kP, kI, kD, kF, Robot.ahrs, this, updatePeriod);
    	turnController.setInputRange(-180.0f,  180.0f);
        turnController.setOutputRange(-1.0, 1.0);
        turnController.setAbsoluteTolerance(kToleranceDegrees);
        turnController.setToleranceBuffer(kToleranceBufSamples);
        turnController.setContinuous(true);
        LiveWindow.addActuator("DriveSystem", "RotateController", turnController);
        
        targetAngle = absoluteAngle ? targetAngle : Robot.ahrs.getYaw()+targetAngle;
        // limit input to -180 to 180
        targetAngle = (targetAngle>180) ? -180+(targetAngle-180) : targetAngle;
        targetAngle = (targetAngle<-180) ? 180+(targetAngle+180) : targetAngle;
        turnController.setSetpoint(targetAngle);
    	turnController.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double outputVelocity = rotateToAngleRate*RobotMap.maxVelocity;
    	if (RobotMap.tuningMode) {
    		SmartDashboard.putNumber("Angle", Robot.ahrs.getAngle());
    		SmartDashboard.putNumber("Rate", Robot.ahrs.getRate());
    		SmartDashboard.putNumber("Yaw", Robot.ahrs.getYaw());
    		SmartDashboard.putNumber("Turn to angle rate", rotateToAngleRate);
    		SmartDashboard.putNumber("Velocity", outputVelocity);
    	}
    	Robot.driveSubsystem.drive(outputVelocity*-1, outputVelocity);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Math.abs(Robot.ahrs.getYaw() - targetAngle) < kToleranceDegrees) && turnController.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveSubsystem.disable();
    	turnController.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
    @Override
    public void pidWrite(double output) {
    	rotateToAngleRate = output;
    }
}
