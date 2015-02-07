package org.usfirst.frc.team1751.robot2015.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.usfirst.frc.team1751.robot2015.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToTarget extends Command {
	private static final int codesPerRev = 360;
	private static final double p = .2;
	private static final double i = 0;
	private static final double d = 0;
	private static final double maxRPM = 550; 
	private static final double pi = Math.PI;
	
	public static final double FORWARD = pi;
	public static final double RIGHT = -pi/2;
	public static final double REVERSE = 0;
	public static final double LEFT = pi/2;
	
	private static final double lowerX = 155;
	private static final double upperX = 165;
	private static final double minArea = 50;
	
	private static final boolean visualize = true;
	private VideoCapture vcap;
	
	private double angle;
	private double speed;
	
    public DriveToTarget() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	this.angle = RIGHT;
    	this.speed = .45;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.setSpeedControl(codesPerRev, p, i, d);
    	//Robot.drivetrain.setVoltageControl();
    	Robot.drivetrain.enable();
    	vcap = new VideoCapture();
    	vcap.open(0, 320, 240, 7.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//calculate magnitude of vector
    	double speed = this.speed;
    	//calculate desired angle
    	double theta = angle;
    	double z = 0;
    	//calculate voltage multipliers
    	double lf = speed*Math.sin(theta+(pi/4.0))+z;
    	double rf = speed*Math.cos(theta+(pi/4.0))-z;
    	double lr = speed*Math.cos(theta+(pi/4.0))+z;
    	double rr = speed*Math.sin(theta+(pi/4.0))-z;
    	
    	//make sure all values are within [-1,1]
    	double largest = 1;
    	if(Math.abs(lf)>largest)
    		largest = Math.abs(lf);
    	if(Math.abs(lr)>largest)
    		largest = Math.abs(lr);
    	if(Math.abs(rf)>largest)
    		largest = Math.abs(rf);
    	if(Math.abs(rr)>largest)
    		largest = Math.abs(rr);
    	lf/=largest;
    	lr/=largest;
    	rf/=largest;
    	rr/=largest;
    	//set the values
    	Robot.drivetrain.drive(lf*maxRPM, lr*maxRPM, rf*maxRPM, rr*maxRPM);
    	
    	
    }
    
    private boolean isCentered(){
    	Mat raw = new Mat();
    	Mat gray = raw.clone();
    	Mat inproc = raw.clone();
    	List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    	Mat hierarchy = raw.clone();
    	//read frame
    	vcap.read(raw);
    	//Convert to grayscale
    	Imgproc.cvtColor(raw, gray, Imgproc.COLOR_RGB2GRAY);
    	//Threshold to filter out anything that isn't bright enough to be target
    	Imgproc.threshold(gray, inproc, 0, 1, Imgproc.THRESH_BINARY);
    	//Find the possible targets    	
    	Imgproc.findContours(inproc, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);    	
    	//Select the two largest contours, these should be our targets
    	List<MatOfPoint> tmp = new ArrayList<MatOfPoint>();
    	System.out.println("Number of contours found: "+contours.size());
    	
    	
		tmp.addAll(contours);
    	contours = new ArrayList<MatOfPoint>();
    	if(tmp.size()==0){
    		System.out.println("No contours found");
    		return false;
    	}
    	contours.add(findLargestContour(tmp));
    	if(tmp.size()==0){
    		System.out.println("Only one contour found");
    		return false;
    	}
    	contours.add(findLargestContour(tmp));
    	//remove noise
    	Imgproc.drawContours(inproc, tmp, -1, new Scalar(0,0,0));
    	if(visualize){
    		/*File output = new File("/home/lvuser/output.png");
    		try{
    			output.createNewFile();
    		}catch(Exception e){
    			e.printStackTrace();
    		}*/
    		Highgui.imwrite("/home/lvuser/output.png", inproc);
    	}
    	//visualize
    	
    		
    	
    	//Gather data
    	double areaOne = Imgproc.contourArea(contours.get(0));
    	double areaTwo = Imgproc.contourArea(contours.get(1));
    	Moments moments = Imgproc.moments(inproc, true);
    	Point center = new Point();
    	center.x = moments.get_m10()/moments.get_m00();
    	center.y = moments.get_m01()/moments.get_m00();
    	System.out.println("Largest contour area: "+areaOne);
    	System.out.println("2nd largest contour area: "+areaTwo);
    	System.out.println("Centroid point: "+center.toString());
    	raw.release();
    	gray.release();
    	inproc.release();
    	//large enough?
    	if(areaOne+areaTwo<minArea){
    		System.out.println("Targets not big enough.");
    		return false;
    	}
    	//centered?
    	if(center.x>lowerX&&center.x<upperX){
    		System.out.println("Lined up");
    		return true;
    	}
    	
    	
    	return false;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isCentered();
        //return false;
    }
    private MatOfPoint findLargestContour(List<MatOfPoint> contours){
    	int largest = 0;
    	for(int i = 1; i < contours.size(); i++){
    		if(Imgproc.contourArea(contours.get(i))>Imgproc.contourArea(contours.get(largest)))
    			largest = i;
    	}
    	return contours.remove(largest);
    }
    /*public static void showResult(Mat img) {
        Imgproc.resize(img, img, new Size(320, 240));
        MatOfByte matOfByte = new MatOfByte();
        Highgui.imencode(".jpg", img, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            JFrame frame = new JFrame();
            frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.disable();
    	vcap.release();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}