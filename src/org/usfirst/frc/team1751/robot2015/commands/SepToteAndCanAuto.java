package org.usfirst.frc.team1751.robot2015.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SepToteAndCanAuto extends CommandGroup {
    
    public  SepToteAndCanAuto() {
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
    	//addSequential(new RaiseElevator(.75, .2));
    	addSequential(new DriveToTote(.4));
    	addSequential(new CloseAndHoldArms());
    	addSequential(new RaiseElevator(2, .7));
    	addSequential(new Turn(-90, .25));
    	addSequential(new Drive(Drive.FORWARD, .65, 3));
    	addSequential(new Turn(90, .25));
    	addSequential(new ToBottom());
    	addSequential(new OpenArms());
    }
}
