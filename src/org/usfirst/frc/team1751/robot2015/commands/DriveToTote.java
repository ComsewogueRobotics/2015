package org.usfirst.frc.team1751.robot2015.commands;

import org.usfirst.frc.team1751.robot2015.Robot;

import java.lang.Math;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToTote extends Command {
	private static final int codesPerRev = 360;
	private static final double p = .2;
	private static final double i = 0;
	private static final double d = 0;
	private static final double kZ = .03;
	private static final double maxRPM = 550; 
	private static final double pi = Math.PI;
	
	public static final double FORWARD = pi;
	public static final double RIGHT = -pi/2;
	public static final double REVERSE = 0;
	public static final double LEFT = pi/2;
	
	private double angle;
	private double speed;
    public DriveToTote(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	this.angle = FORWARD;
    	this.speed = speed;
    	Robot.drivetrain.resetGyro();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.setSpeedControl(codesPerRev, p, i, d);
    	//Robot.drivetrain.setVoltageControl();
    	Robot.drivetrain.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//some debug
    	Robot.drivetrain.sendSpeeds();
    	//SmartDashboard.putNumber("Potentiometer Reading", Robot.arms.getPotVal());
    	//SmartDashboard.putNumber("Potentiometer setpoint", Robot.arms.getSetpoint());
    	//calculate magnitude of vector
    	double speed = this.speed;
    	//calculate desired angle
    	double theta = angle;
    	double z = Robot.drivetrain.getAnglePID()*kZ;
    	//calculate voltage multipliers
    	double lf = speed*Math.sin(theta+(pi/4.0))+z;
    	double rf = speed*Math.cos(theta+(pi/4.0))-z;
    	double lr = speed*Math.cos(theta+(pi/4.0))+z;
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
    	Robot.drivetrain.drive(lf*maxRPM, lr*maxRPM, rf*maxRPM, rr*maxRPM);
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.drivetrain.isAtTote();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.disable();
    }
}
