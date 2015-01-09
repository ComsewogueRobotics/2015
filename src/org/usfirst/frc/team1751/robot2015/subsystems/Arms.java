package org.usfirst.frc.team1751.robot2015.subsystems;

import org.usfirst.frc.team1751.robot2015.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class Arms extends PIDSubsystem {
	private static final double p = 0;
	private static final double i = 0;
	private static final double d = 0;
	private static AnalogInput pot = new AnalogInput(RobotMap.pot);
	private static Talon armMotor = new Talon(RobotMap.armMotor);
    // Initialize your subsystem here
    public Arms() {
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	super(p,i,d);
    	
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	return pot.pidGet();
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	armMotor.set(output);
    }
    public double getPotVal(){
    	return pot.pidGet(); 
    }
    public void set(double val){
    	setSetpoint(val);
    }
    
}
