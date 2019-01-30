package org.usfirst.frc.team1751.robot2015.commands;

import org.usfirst.frc.team1751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class CalibrateMode extends Command {
	double maxlf;
	double maxrf;
	double maxlr;
	double maxrr;
    public CalibrateMode() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	requires(Robot.arms);
    	requires(Robot.elevator);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
		//Sets some doubles to 0 and the drivetrain to percent control mode
    	Robot.drivetrain.setVoltageControl(360);
    	maxlf = 0;
    	maxrf = 0;
    	maxlr = 0;
    	maxrr = 0;
    	Robot.drivetrain.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double y = Robot.oi.getDriveStick().getY();
    	double x = Robot.oi.getShootStick().getY();
    	double z = Robot.oi.getShootStick().getX();
    	Robot.drivetrain.drive(y, y, y, y);
    	Robot.elevator.setMotors(x);
    	Robot.arms.debugSet(z);
    	SmartDashboard.putNumber("Potentiometer Reading", Robot.arms.getPotVal());
		Robot.drivetrain.sendSpeeds();
		//Um I guess this was to find out the max speeds of things
    	if(Math.abs(Robot.drivetrain.getSpeed(1))>Math.abs(maxlf))
    		maxlf = Math.abs(Robot.drivetrain.getSpeed(1));
    	if(Math.abs(Robot.drivetrain.getSpeed(2))>Math.abs(maxrf))
    		maxrf = Math.abs(Robot.drivetrain.getSpeed(2));
    	if(Math.abs(Robot.drivetrain.getSpeed(3))>Math.abs(maxlr))
    		maxlr = Math.abs(Robot.drivetrain.getSpeed(3));
    	if(Math.abs(Robot.drivetrain.getSpeed(4))>Math.abs(maxrr))
    		maxrr = Math.abs(Robot.drivetrain.getSpeed(4));
    	/*SmartDashboard.putNumber("Left Front Max RPM", maxlf);
    	SmartDashboard.putNumber("Right Front Max RPM", maxrf);
    	SmartDashboard.putNumber("Left Rear Max RPM", maxlr);
    	SmartDashboard.putNumber("Right Rear Max RPM", maxrr);*/
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
    }
}
