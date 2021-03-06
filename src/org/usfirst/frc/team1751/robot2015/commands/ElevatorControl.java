package org.usfirst.frc.team1751.robot2015.commands;

import org.usfirst.frc.team1751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ElevatorControl extends Command {

    public ElevatorControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//debug
    	SmartDashboard.putNumber("Driverstick POV", Robot.oi.getDriveStick().getPOV());
    	SmartDashboard.putBoolean("isTop()", Robot.elevator.isTop());
    	SmartDashboard.putBoolean("isBot()", Robot.elevator.isBot());
    	SmartDashboard.putBoolean("isSafety()", Robot.elevator.isSafety());
    	SmartDashboard.putBoolean("isDoubleTote()", Robot.elevator.isDoubleTote());
    	SmartDashboard.putBoolean("isLooseSafe()", Robot.elevator.isLooseSafe());
    	if(Robot.oi.getDriveStick().getPOV()==180){
    		Robot.elevator.setMotors(.75);
    		return;
    	}else if(Robot.oi.getDriveStick().getPOV()==0){
    		Robot.elevator.setMotors(-.75);
    		return;
    	}
    		
    	double val = Robot.oi.getShootStick().getY();
    	Robot.elevator.setMotors(val);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.setMotors(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
