package org.usfirst.frc.team1751.robot2015.subsystems;

import org.usfirst.frc.team1751.robot2015.RobotMap;
import org.usfirst.frc.team1751.robot2015.commands.ElevatorControl;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Elevator extends Subsystem {
    private DigitalInput top;
	private DigitalInput bot;
	private DigitalInput mid;
	private DigitalInput nearBot;
	private DigitalInput safe;
    private Talon motorL;
    private Talon motorR;
	
	public Elevator(){
		top = new DigitalInput(RobotMap.elevTop);
		bot = new DigitalInput(RobotMap.elevBot);
		mid = new DigitalInput(RobotMap.elevMid);
		nearBot = new DigitalInput(RobotMap.elevNearBot);
		safe = new DigitalInput(RobotMap.elevSafety);
		motorL = new Talon(RobotMap.elevatorMotorL);
		motorR = new Talon(RobotMap.elevatorMotorR);
	}
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ElevatorControl());
    }
    public void setMotors(double val){
    	
    	if(isTop()&&val<0){
    			motorL.set(0);
    			motorR.set(0);
    			return;
    	} else if((isBot()||isSafety())&&val>0){
    			motorL.set(0);
    			motorR.set(0);
    			return;
    	} else if(val>.75){
    		motorL.set(.75);
    		motorR.set(.75);
    	}
    	motorL.set(-val);
    	motorR.set(val);
    	//SmartDashboard.putNumber("val", val);
    }
    public boolean isTop(){
    	return !top.get();
    }
    public boolean isBot(){
    	return !bot.get();
    }
    public boolean isSafety(){
    	return !safe.get();
    }
    public boolean isDoubleTote(){
    	return !mid.get();
    }
    public boolean isNearBot(){
    	return !nearBot.get();
    }
}

