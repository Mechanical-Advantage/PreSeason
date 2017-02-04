package org.usfirst.frc.team6328.robot.commands;

import org.usfirst.frc.team6328.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Sets the current camera
 */
public class SetCamera extends InstantCommand {

	private boolean frontCamera;
	
    public SetCamera(boolean useFrontCamera) {
        super("SetCamera");
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        frontCamera = useFrontCamera;
        requires(Robot.cameraSubsystem);
    }

    // Called once when the command executes
    protected void initialize() {
    	if (frontCamera) {
    		Robot.cameraSubsystem.useFrontCamera();
    	}
    	else {
    		Robot.cameraSubsystem.useRearCamera();
    	}
    }

}
