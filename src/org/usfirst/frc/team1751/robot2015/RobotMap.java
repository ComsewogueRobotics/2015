package org.usfirst.frc.team1751.robot2015;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//CAN Bus:
    public static int leftFront = 2;
    public static int leftRear = 4;
    public static int rightFront = 3;
    public static int rightRear = 5;
    
    //Sensors
    //Analog:
    public static int gyro = 0;
    public static int pot = 1;
    //Digital:
    public static int elevTop = 0;
    public static int elevBot = 1;
    //Other motors
    public static int armMotor = 0;
    public static int elevatorMotor = 1;
    
}
