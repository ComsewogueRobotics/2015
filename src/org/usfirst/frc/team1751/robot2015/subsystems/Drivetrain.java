
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
    
	private static CANJaguar leftFront;
	private static CANJaguar leftRear;
	private static CANJaguar rightFront;
	private static CANJaguar rightRear;
	private static Gyro gyro;
	private static Relay ledRing;
	private static DigitalInput atTote;
	
	public Drivetrain(){
		super();
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
    	setDefaultCommand(new MecanumDrive());
    	//setDefaultCommand(new CalibrateMode());
    }
    public void ledsOn(){
    	ledRing.set(Relay.Value.kForward);
    }
    public void ledsOff(){
    	ledRing.set(Relay.Value.kOff);
    }
    public double getAngle(){
    	double angle = gyro.getAngle()%360;
    	if(angle<0)
    		return angle+360;
    	return angle;
    }
    public double getAnglePID(){
    	return gyro.getAngle();
    }
    public void resetGyro(){
    	gyro.reset();
    }
    public void setGyro(double n){
    	gyro.setSensitivity(n);
    }
    public boolean isAtTote(){
    	return !atTote.get();
    }
    public void sendSpeeds(){
    	SmartDashboard.putNumber("Left Front Speed", leftFront.getSpeed());
    	SmartDashboard.putNumber("Left Rear Speeed", leftRear.getSpeed());
    	SmartDashboard.putNumber("Right Front Speed", rightFront.getSpeed());
    	SmartDashboard.putNumber("Right Rear Speed", rightRear.getSpeed());
    }
    public void setSpeedControl(int codesPerRev, double p, double i, double d){
    	leftFront.setSpeedMode(CANJaguar.kQuadEncoder, codesPerRev, p, i, d);
    	leftRear.setSpeedMode(CANJaguar.kQuadEncoder, codesPerRev, p, i, d);
    	rightFront.setSpeedMode(CANJaguar.kQuadEncoder, codesPerRev, p, i, d);
    	rightRear.setSpeedMode(CANJaguar.kQuadEncoder, codesPerRev, p, i, d);
    }
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
    public void setVoltageControl(int codesPerRev){
    	
    	leftFront.setPercentMode(CANJaguar.kQuadEncoder, codesPerRev);
    	leftRear.setPercentMode(CANJaguar.kQuadEncoder, codesPerRev);
    	rightFront.setPercentMode(CANJaguar.kQuadEncoder, codesPerRev);
    	rightRear.setPercentMode(CANJaguar.kQuadEncoder, codesPerRev);
    }
    public void enable(){
    	leftFront.enableControl();
    	leftRear.enableControl();
    	rightFront.enableControl();
    	rightRear.enableControl();
    }
    public void disable(){
    	/*leftFront.disableControl();
    	leftRear.disableControl();
    	rightFront.disableControl();
    	rightRear.disableControl();*/
    	drive(0,0,0,0);
    }
    public void drive(double lf, double lr, double rf, double rr){
    	leftFront.set(-lf);
    	leftRear.set(-lr);
    	rightFront.set(rf);
    	rightRear.set(rr);
    }
}

