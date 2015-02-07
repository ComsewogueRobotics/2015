package org.usfirst.frc.team1751.robot2015.commands;

import org.usfirst.frc.team1751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RaiseElevator extends Command {
	private boolean isTimed = false;
	private double speed = 1;
    public RaiseElevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.elevator);
    }
    public RaiseElevator(double timeout, double speed){
    	System.out.println("RaiseElevator("+timeout+", "+speed+")");
    	isTimed = true;
    	this.speed=speed;
    	setTimeout(timeout);
    	requires(Robot.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elevator.setMotors(-1*speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(isTimed){
        	return isTimedOut()||Robot.elevator.isTop();
        }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.setMotors(0);
    	System.out.println("RaiseElevator ended.");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
