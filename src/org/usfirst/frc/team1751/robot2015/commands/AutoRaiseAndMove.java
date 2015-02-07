package org.usfirst.frc.team1751.robot2015.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *    a)start lift to double tote position
 * 	  b)back up couple feet
 * >>>b)2.start moving right
 */
public class AutoRaiseAndMove extends CommandGroup {
    
    public  AutoRaiseAndMove() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addParallel(new ElevatorToDoubleTote());
    	addSequential(new Drive(Drive.REVERSE, .5, 1));
    	addSequential(new DriveToTarget());
    }
}
