package org.usfirst.frc.team1751.robot2015.commands;

import org.usfirst.frc.team1751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ArmsControl extends Command {

    public ArmsControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arms);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putBoolean("isClosed()", Robot.arms.isClosed());
    	SmartDashboard.putBoolean("isOpen()", Robot.arms.isOpen());
    	/*double in = Robot.oi.getShootStick().getX();
    	if(in<-.05&&Robot.arms.isClosed()){
    		Robot.arms.debugSet(-.15);
    		return;
    	}else if(in>0&&Robot.arms.isOpen()){
    		Robot.arms.debugSet(0);
    		return;
    	}
    	Robot.arms.debugSet(in);*/
    	if(Robot.oi.getDriveStick().getPOV()==90){
    		Robot.arms.debugSet(.5);
    		return;
    	}else if(Robot.oi.getDriveStick().getPOV()==270){
    		Robot.arms.debugSet(-.5);
    		return;
    	}else{
    		Robot.arms.debugSet(0);
    	}
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
