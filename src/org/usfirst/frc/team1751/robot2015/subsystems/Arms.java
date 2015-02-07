package org.usfirst.frc.team1751.robot2015.subsystems;

import org.usfirst.frc.team1751.robot2015.RobotMap;
import org.usfirst.frc.team1751.robot2015.commands.ArmsControl;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class Arms extends PIDSubsystem {
	private static final double p = 40;
	private static final double i = 0.003;
	private static final double d = 0;
	private static AnalogInput pot;
	private static DigitalInput closed;
	private static DigitalInput open;
	private static Talon armMotor;
	private static Relay swagLights;
    // Initialize your subsystem here
    public Arms() {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	super(p,i,d);
    	pot = new AnalogInput(RobotMap.pot);
    	closed = new DigitalInput(RobotMap.armsClosed);
    	open = new DigitalInput(RobotMap.armsOpened);
    	armMotor = new Talon(RobotMap.armMotor);
    	swagLights = new Relay(RobotMap.swagLights);
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ArmsControl());
    }
    public void swagOn(){
    	swagLights.set(Relay.Value.kForward);
    }
    //WHY WOULD YOU EVER USE THIS
    public void noSwag(){
    	swagLights.set(Relay.Value.kOff);
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	return getPotVal();
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	System.out.println("Output: "+output);
    	armMotor.set(-output);
    }
    public double getPotVal(){
    	return pot.pidGet(); 
    }
    public void set(double val){
    	setSetpoint(val);
    	enable();
    }
    public void debugSet(double val){
    	armMotor.set(val);
    }
    public boolean isClosed(){
    	return !closed.get();
    }
    public boolean isOpen(){
    	return !open.get();
    }
    
}
