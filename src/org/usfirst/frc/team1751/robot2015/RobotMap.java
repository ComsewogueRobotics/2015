package org.usfirst.frc.team1751.robot2015;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//CAN Bus:
    public static final int leftFront = 2;
    public static final int leftRear = 4;
    public static final int rightFront = 3;
    public static final int rightRear = 5;
    
    //Sensors
    //Analog:
    public static final int gyro = 0;
    public static final int pot = 1;
    //Digital:
    public static final int elevTop = 0;
    public static final int elevBot = 1;
    public static final int elevMid = 2;
    //Other motors
    public static final int armMotor = 0;
    public static final int elevatorMotor = 1;
    
}
