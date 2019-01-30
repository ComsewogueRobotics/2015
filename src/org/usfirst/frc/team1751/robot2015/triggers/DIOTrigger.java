package org.usfirst.frc.team1751.robot2015.triggers;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This was probably unnecessary, but it essentially wraps a Trigger object around a DIO
 */
public class DIOTrigger extends Trigger {
    
	private DigitalInput dIN;
	
	public DIOTrigger(DigitalInput in){
		dIN = in;
	}
	public DIOTrigger(int port){
		this(new DigitalInput(port));
	}
	
    public boolean get() {
        return !dIN.get();
    }
}
