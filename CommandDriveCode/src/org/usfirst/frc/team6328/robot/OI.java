package org.usfirst.frc.team6328.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team6328.robot.commands.AutoClimb;
import org.usfirst.frc.team6328.robot.commands.ClimberHold;
import org.usfirst.frc.team6328.robot.commands.CloseGearHandler;
import org.usfirst.frc.team6328.robot.commands.DriveWithJoystickOnHeading;
import org.usfirst.frc.team6328.robot.commands.EnableClosedLoop;
import org.usfirst.frc.team6328.robot.commands.OpenGearHandler;
import org.usfirst.frc.team6328.robot.commands.RunIntake;
import org.usfirst.frc.team6328.robot.commands.RunIntakeGroup;
import org.usfirst.frc.team6328.robot.commands.RunLoader;
import org.usfirst.frc.team6328.robot.commands.RunShooter;
import org.usfirst.frc.team6328.robot.commands.RunTrigger;
import org.usfirst.frc.team6328.robot.commands.SetCamera;
import org.usfirst.frc.team6328.robot.commands.ShakeGearHandler;
import org.usfirst.frc.team6328.robot.commands.TurnToAngle;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	// for gamepad f310
	/*private int controllerID = 0;
	private static int rightAxis = 5; // 5 for Xinput, 3 for Directinput (X/D switch on controller)
	private static int leftAxis = 1; // 1 for both Xinput and Directinput
	private Joystick controller = new Joystick(controllerID);
	private Button rightButton = new JoystickButton(controller, 1);
	private Button leftButton = new JoystickButton(controller, 2);*/
	
	// map left stick to ID 0 and right to ID 1 in driver station
	private Joystick leftController = new Joystick(0);
	private Joystick rightController = new Joystick(1);
	private Joystick oiController = new Joystick(2);
	
	private Button rightButton = new JoystickButton(rightController, 5);
	private Button leftButton = new JoystickButton(rightController, 4);
	private Button frontCameraButton = new JoystickButton(leftController, 3);
	private Button rearCameraButton = new JoystickButton(leftController, 2);
	private Button intakeOn = new JoystickButton(oiController, 1);
	private Button intakeOff = new JoystickButton(oiController, 1);
	private Button shoot = new JoystickButton(oiController, 1);
	private Button gearOpen = new JoystickButton(oiController, 1);
	private Button gearClose = new JoystickButton(oiController, 1);
	private Button gearShake = new JoystickButton(oiController, 1);
	private Button shooterSwitch = new JoystickButton(oiController, 1);
	private Button autoClimb = new JoystickButton(oiController, 1);
	//private Button autoClimb = new JoystickButton(leftController, 8);
	private Button climberHold = new JoystickButton(oiController, 1);
	//private Button openLoopDrive = new JoystickButton(oiController, 1);
	private Button openLoopDrive = new JoystickButton(rightController, 1); // for test robot
	private Button straightAssist = new JoystickButton(leftController, 1);
	
	private Button loaderForward = new JoystickButton(oiController, 1);
	private Button loaderBackward = new JoystickButton(oiController, 1);
	private Button intakeForward = new JoystickButton(oiController, 1);
	private Button intakeBackward = new JoystickButton(oiController, 1);
	private Button triggerForward = new JoystickButton(oiController, 1);
	private Button triggerBackward = new JoystickButton(oiController, 1);
	
	public OI() {
		rightButton.whenPressed(new TurnToAngle(90));
		leftButton.whenPressed(new TurnToAngle(-90));
		frontCameraButton.whenPressed(new SetCamera(true));
		rearCameraButton.whenPressed(new SetCamera(false));
		
		RunIntakeGroup intakeGroup = new RunIntakeGroup();
		RunTrigger backwardsTrigger = new RunTrigger(true);
		intakeOn.whenPressed(intakeGroup);
		intakeOff.cancelWhenPressed(intakeGroup);
		intakeOff.cancelWhenPressed(backwardsTrigger);
		shoot.whileHeld(new RunTrigger(false));
		shoot.whenReleased(backwardsTrigger);
		gearOpen.whenPressed(new OpenGearHandler());
		gearClose.whenPressed(new CloseGearHandler());
		gearShake.whileHeld(new ShakeGearHandler());
		shooterSwitch.whileHeld(new RunShooter());
		autoClimb.whenPressed(new AutoClimb());
		climberHold.whileHeld(new ClimberHold());
		openLoopDrive.whenPressed(new EnableClosedLoop(false));
		openLoopDrive.whenReleased(new EnableClosedLoop(true));
		straightAssist.whileHeld(new DriveWithJoystickOnHeading());
		
		loaderForward.whileHeld(new RunLoader(false));
		loaderBackward.whileHeld(new RunLoader(true));
		intakeForward.whileHeld(new RunIntake(false));
		intakeBackward.whileHeld(new RunIntake(true));
		triggerForward.whileHeld(new RunTrigger(false));
		triggerBackward.whileHeld(new RunTrigger(true));
	}
	
	public double getLeftAxis() {
		return leftController.getRawAxis(1);
	}
	public double getRightAxis() {
		return rightController.getRawAxis(1);
	}
	
	public double getClimbAxis() {
		return oiController.getRawAxis(0);
	}
	
	public boolean getOpenLoop() {
		return openLoopDrive.get();
	}
}

