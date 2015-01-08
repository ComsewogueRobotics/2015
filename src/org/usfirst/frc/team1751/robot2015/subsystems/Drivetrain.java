
package org.usfirst.frc.team1751.robot2015.subsystems;

import org.usfirst.frc.team1751.robot2015.RobotMap;
import org.usfirst.frc.team1751.robot2015.commands.MecanumDrive;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Drivetrain extends Subsystem {
    
	private static CANJaguar leftFront = new CANJaguar(RobotMap.leftFront);
	private static CANJaguar leftRear = new CANJaguar(RobotMap.leftRear);
	private static CANJaguar rightFront = new CANJaguar(RobotMap.rightFront);
	private static CANJaguar rightRear = new CANJaguar(RobotMap.rightRear);
	private static Gyro gyro = new Gyro(RobotMap.gyro);
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new MecanumDrive());
    }
    public double getAngle(){
    	double angle = gyro.getAngle()%360;
    	if(angle<0)
    		return angle+360;
    	return angle;
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
    public void setVoltageControl(){
    	leftFront.setPercentMode();
    	leftRear.setPercentMode();
    	rightFront.setPercentMode();
    	rightRear.setPercentMode();
    }
    public void enable(){
    	leftFront.enableControl();
    	leftRear.enableControl();
    	rightFront.enableControl();
    	rightRear.enableControl();
    }
    public void disable(){
    	leftFront.disableControl();
    	leftRear.disableControl();
    	rightFront.disableControl();
    	rightRear.disableControl();
    }
    public void drive(double lf, double lr, double rf, double rr){
    	leftFront.set(lf);
    	leftRear.set(lr);
    	rightFront.set(rf);
    	rightRear.set(rr);
    }
}

