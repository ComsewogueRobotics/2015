
package org.usfirst.frc.team1751.robot2015.commands;

import org.usfirst.frc.team1751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 1. Close arms around tote
 * 2. Lift off of ground
 * 3: a)start lift to double tote position
 * 	  b)back up couple feet
 * >>>b)2.start moving right
 * 4: When tote is found and centered AND lift is at double tote pos, move forward
 * 5: Drop tote
 * 6: move lift down
 * 7: close arms
 * 8: lift off of ground
 * 9: move to auto zone 
 * 10. lift to bottom
 * 11. open arms
 */
public class ThreeToteAuto extends CommandGroup {

    public ThreeToteAuto() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        requires(Robot.arms);
        requires(Robot.elevator);
        addSequential(new LedRingOn());
        //1)
    	addSequential(new CloseAndHoldArms());
    	//2)
    	addSequential(new RaiseElevator(.5,.75));
    	//3)
    	addSequential(new AutoRaiseAndMove());
    	//4)
    	addSequential(new Drive(Drive.FORWARD, .65, .9));
    	//5)
    	addSequential(new OpenArms());
    	//6)
    	addSequential(new LowerElevator(3, .75));
    	//7)
    	addSequential(new CloseAndHoldArms());
    	//8)
    	addSequential(new RaiseElevator(.5, .75));
    	
    	//AND AGAIN
    	addSequential(new AutoRaiseAndMove());
    	//4)
    	addSequential(new Drive(Drive.FORWARD, .65, .9));
    	//5)
    	addSequential(new OpenArms());
    	//6)
    	addSequential(new LowerElevator(3, .75));
    	//7)
    	addSequential(new CloseAndHoldArms());
    	//8)
    	addSequential(new RaiseElevator(.5, .75));
    	
    	//and back
    	
    	//9)
    	addSequential(new Drive(Drive.REVERSE, 1, 2));
    	//10)
    	addSequential(new LowerElevator(1, .75));
    	//11)
    	addSequential(new OpenArms());
    	addSequential(new LedRingOff());
    }
}
