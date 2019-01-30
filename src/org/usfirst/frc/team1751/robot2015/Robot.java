
package org.usfirst.frc.team1751.robot2015;

import org.usfirst.frc.team1751.robot2015.commands.LedRingOn;
import org.usfirst.frc.team1751.robot2015.commands.MoveOnlyAuto;
import org.usfirst.frc.team1751.robot2015.commands.SepToteAndCanAuto;
import org.usfirst.frc.team1751.robot2015.commands.SingleToteAuto;
import org.usfirst.frc.team1751.robot2015.commands.ThreeToteAuto;
import org.usfirst.frc.team1751.robot2015.commands.ToteCanSetAuto;
import org.usfirst.frc.team1751.robot2015.commands.TwoToteAuto;
import org.usfirst.frc.team1751.robot2015.subsystems.Arms;
import org.usfirst.frc.team1751.robot2015.subsystems.Drivetrain;
import org.usfirst.frc.team1751.robot2015.subsystems.Elevator;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {
    //This static block instructed the JVM to load the OpenCV library for robot vision
	static{
		System.load("/usr/local/lib/lib_OpenCV/java/libopencv_java2410.so");
    }
    //Robot subsystems
	public static Drivetrain drivetrain;
	public static Arms arms;
	public static Elevator elevator;
    public static OI oi;

    //SmartDashboard widget that drivers use to specify the desired autonomous mode
	SendableChooser autoChooser;
    Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        //initialize subsystems
		drivetrain = new Drivetrain();
		arms = new Arms();
		elevator = new Elevator();
        oi = new OI();
        
        //Put command to turn on the LED ring (for camera)
        SmartDashboard.putData("", new LedRingOn());
        //initialize autonomous mode chooser widget
        autoChooser = new SendableChooser();
        autoChooser.addObject("Three Tote", new ThreeToteAuto());
        autoChooser.addObject("Two Tote", new TwoToteAuto());
        autoChooser.addObject("Tote and Can Set", new ToteCanSetAuto());
        autoChooser.addObject("Separate Tote and Can", new SepToteAndCanAuto());
        autoChooser.addDefault("Single Tote", new SingleToteAuto());
        autoChooser.addObject("Move Only", new MoveOnlyAuto());
        autoChooser.addObject("None", null);
        SmartDashboard.putData("Autonomous:", autoChooser);
        //Put data about the running commands from the Scheduler onto the dashboard
        SmartDashboard.putData(Scheduler.getInstance());
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        // schedule the autonomous command (example)
    	autonomousCommand = (Command)autoChooser.getSelected();
    	if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
