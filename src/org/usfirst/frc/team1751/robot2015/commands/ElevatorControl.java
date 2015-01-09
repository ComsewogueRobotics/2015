package org.usfirst.frc.team1751.robot2015.commands;

import org.usfirst.frc.team1751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;

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
    	double val = Robot.oi.getShootStick().getY();
    	if(Robot.elevator.isTop()){
    		if(val>0)
    			return;
    	} else if(Robot.elevator.isBot()){
    		if(val<0)
    			return;
    	}
    	Robot.elevator.setMotor(val);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.setMotor(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.elevator.setMotor(0);
    }
}
