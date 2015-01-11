package org.usfirst.frc.team1751.robot2015.subsystems;

import org.usfirst.frc.team1751.robot2015.Robot;
import org.usfirst.frc.team1751.robot2015.RobotMap;
import org.usfirst.frc.team1751.robot2015.commands.ElevatorControl;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
    private DigitalInput top = new DigitalInput(RobotMap.elevTop);
	private DigitalInput bot = new DigitalInput(RobotMap.elevBot);
    private Talon motor = new Talon(RobotMap.elevatorMotor);
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ElevatorControl());
    }
    public void setMotor(double val){
    	if(isTop()){
    		if(val>0)
    			return;
    	} else if(isBot()){
    		if(val<0)
    			return;
    	}
    	motor.set(val);
    }
    public boolean isTop(){
    	return top.get();
    }
    public boolean isBot(){
    	return bot.get();
    }
}

