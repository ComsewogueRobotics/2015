
package org.usfirst.frc.team1751.robot2015.subsystems;

import org.usfirst.frc.team1751.robot2015.RobotMap;
//import org.usfirst.frc.team1751.robot2015.commands.CalibrateMode;
import org.usfirst.frc.team1751.robot2015.commands.MecanumDrive;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Drivetrain extends Subsystem {
	//motor controllers,
	private static CANJaguar leftFront;
	private static CANJaguar leftRear;
	private static CANJaguar rightFront;
	private static CANJaguar rightRear;
	//sensor to get relative direction,
	private static Gyro gyro;
	//LED ring for the camera
	private static Relay ledRing;
	//sensor on front of the robot to detect when it was at the game piece
	private static DigitalInput atTote;
	
	public Drivetrain(){
		super();
		//initialize aboe using constants from the RobotMap class.
		leftFront = new CANJaguar(RobotMap.leftFront);
		leftRear  = new CANJaguar(RobotMap.leftRear);
		rightFront = new CANJaguar(RobotMap.rightFront);
		rightRear = new CANJaguar(RobotMap.rightRear);
		gyro = new Gyro(RobotMap.gyro);
		ledRing = new Relay(RobotMap.ledRing);
		atTote = new DigitalInput(RobotMap.atTote);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
		
		//We always want the drive command running so it is default for this subsystem 
		setDefaultCommand(new MecanumDrive());
		//dont know what this used to be, i'll cross that bridge when I get to it
    	//setDefaultCommand(new CalibrateMode());
	}
	//Controls for the LED ring
    public void ledsOn(){
    	ledRing.set(Relay.Value.kForward); //Value.kOn sets both outputs to +12v ! So we want kForward
    }
    public void ledsOff(){
    	ledRing.set(Relay.Value.kOff);
	}
	/**
	 * @return the angle relative to the last calibrated 0 (forward)
	 */
    public double getAngle(){
    	double angle = gyro.getAngle()%360;
    	if(angle<0)
    		return angle+360;
    	return angle;
	}
	/**
	 * @return the raw angle from the gyroscope for use in PID commands
	 */
    public double getAnglePID(){
    	return gyro.getAngle();
	}
	/**
	 * Sets the current direction as 0 (north)
	 */
    public void resetGyro(){
    	gyro.reset();
	}
	/**
	 * sets the sensitivity of the gyroscope in some units involving volts and degrees?
	 * @param n sensitivity
	 */
    public void setGyro(double n){
    	gyro.setSensitivity(n);
	}
	/**
	 * @return whether or not the front switch is touching a tote
	 */
    public boolean isAtTote(){
    	return !atTote.get();
	}
	/**
	 * Updates the speeds of each motor on SmartDashboard
	 */
    public void sendSpeeds(){
    	SmartDashboard.putNumber("Left Front Speed", leftFront.getSpeed());
    	SmartDashboard.putNumber("Left Rear Speeed", leftRear.getSpeed());
    	SmartDashboard.putNumber("Right Front Speed", rightFront.getSpeed());
    	SmartDashboard.putNumber("Right Rear Speed", rightRear.getSpeed());
	}
	/**
	 * Sets the Jags into closed loop speed control
	 * @param codesPerRev calibration value from encoder
	 * @param p
	 * @param i
	 * @param d
	 */
    public void setSpeedControl(int codesPerRev, double p, double i, double d){
    	leftFront.setSpeedMode(CANJaguar.kQuadEncoder, codesPerRev, p, i, d);
    	leftRear.setSpeedMode(CANJaguar.kQuadEncoder, codesPerRev, p, i, d);
    	rightFront.setSpeedMode(CANJaguar.kQuadEncoder, codesPerRev, p, i, d);
    	rightRear.setSpeedMode(CANJaguar.kQuadEncoder, codesPerRev, p, i, d);
	}
	/**
	 * 
	 * @param id CAN id of the jaguar
	 * @return speed of given jaguar's corresponding wheel, or the average of the four
	 */
    public double getSpeed(int id){
    	switch(id){
    	case(1):
    		return leftFront.getSpeed();
    	case(2):
    		return rightFront.getSpeed();
    	case(3):
    		return leftRear.getSpeed();
    	case(4):
    		return rightRear.getSpeed();
		default:
			return (leftFront.getSpeed()+leftRear.getSpeed()+rightFront.getSpeed()+rightRear.getSpeed())/4.0;
    	}
	}
	/**
	 * Ironically sets the jags to percent control mode while still collecting speed information
	 * @param codesPerRev calibration value of encoder
	 */
    public void setVoltageControl(int codesPerRev){
    	
    	leftFront.setPercentMode(CANJaguar.kQuadEncoder, codesPerRev);
    	leftRear.setPercentMode(CANJaguar.kQuadEncoder, codesPerRev);
    	rightFront.setPercentMode(CANJaguar.kQuadEncoder, codesPerRev);
    	rightRear.setPercentMode(CANJaguar.kQuadEncoder, codesPerRev);
	}
	/**
	 * enable jags
	 */
    public void enable(){
    	leftFront.enableControl();
    	leftRear.enableControl();
    	rightFront.enableControl();
    	rightRear.enableControl();
	}
	/**
	 * read
	 */
    public void disable(){
    	/*leftFront.disableControl();
    	leftRear.disableControl();
    	rightFront.disableControl();
    	rightRear.disableControl();*/
    	drive(0,0,0,0);
	}
	/**
	 * 
	 * @param lf left front speed|percent
	 * @param lr left rear speed|percent
	 * @param rf right front speed|percent
	 * @param rr right rear speed|percent
	 */
    public void drive(double lf, double lr, double rf, double rr){
    	leftFront.set(-lf);
    	leftRear.set(-lr);
    	rightFront.set(rf);
    	rightRear.set(rr);
    }
}

