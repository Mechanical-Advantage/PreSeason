package org.usfirst.frc.team6328.robot.commands;

import org.usfirst.frc.team6328.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the shooter wheel
 */
public class RunShooter extends Command {
	
	private final int targetSpeed = 5000;

    public RunShooter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super("RunShooter");
    	requires(Robot.shooterSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.shooterSubsystem.getSpeed()>targetSpeed) {
    		Robot.shooterSubsystem.stop();
    	} else {
    		Robot.shooterSubsystem.run();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterSubsystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooterSubsystem.stop();
    }
}
