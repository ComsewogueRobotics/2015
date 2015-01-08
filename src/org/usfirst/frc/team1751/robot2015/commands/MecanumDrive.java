package org.usfirst.frc.team1751.robot2015.commands;

import org.usfirst.frc.team1751.robot2015.Robot;
import java.lang.Math;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MecanumDrive extends Command {
	private static final int codesPerRev = 360;
	private static final double p = .2;
	private static final double i = .01;
	private static final double d = 0;
	private static final double maxRPM = 5;
	private static final double pi = Math.PI;
    public MecanumDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);		
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.drivetrain.setSpeedControl(codesPerRev, p, i, d);
    	Robot.drivetrain.setVoltageControl();
    	Robot.drivetrain.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//some debug
    	Robot.drivetrain.sendSpeeds();
    	//get joystick values
    	double y = Robot.oi.getDriveStick().getY();
    	double x = Robot.oi.getDriveStick().getX();
    	double z = Robot.oi.getDriveStick().getTwist();
    	//calculate magnitude of vector
    	double speed = Math.sqrt(Math.pow(y, 2)+Math.pow(x, 2));
    	//calculate desired angle
    	double theta = Math.atan2(y, x)/*+Robot.drivetrain.getAngle()*/;   //Uncomment for field-centric
    	//calculate voltage multipliers
    	double lf = speed*Math.sin(theta+(pi/4.0))+z;
    	double lr = speed*Math.cos(theta+(pi/4.0))-z;
    	double rf = speed*Math.cos(theta+(pi/4.0))+z;
    	double rr = speed*Math.sin(theta+(pi/4.0))-z;
    	
    	//make sure all values are within [-1,1]
    	double largest = 1;
    	if(Math.abs(lf)>largest)
    		largest = Math.abs(lf);
    	if(Math.abs(lr)>largest)
    		largest = Math.abs(lr);
    	if(Math.abs(rf)>largest)
    		largest = Math.abs(rf);
    	if(Math.abs(rr)>largest)
    		largest = Math.abs(rr);
    	lf/=largest;
    	lr/=largest;
    	rf/=largest;
    	rr/=largest;
    	//set the values
    	Robot.drivetrain.drive(lf, lr, rf, rr);
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.disable();
    }
}
