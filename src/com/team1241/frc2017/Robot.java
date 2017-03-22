
package com.team1241.frc2017;

import java.io.IOException;

import com.team1241.frc2017.auto.BaseCameraTrack;
import com.team1241.frc2017.auto.CenterGearCommand;
import com.team1241.frc2017.auto.DriveCameraTrack;
import com.team1241.frc2017.auto.DriveCommand;
import com.team1241.frc2017.auto.IntakePistonCommand;
import com.team1241.frc2017.auto.LeftGearCommandBlue;
import com.team1241.frc2017.auto.NoAuto;
import com.team1241.frc2017.auto.RightGearCommandRed;
import com.team1241.frc2017.auto.RightGearShootCommandRed;
import com.team1241.frc2017.auto.ShootAuton;
import com.team1241.frc2017.auto.TurnCommand;
import com.team1241.frc2017.subsystems.Conveyor;
import com.team1241.frc2017.subsystems.Drivetrain;
import com.team1241.frc2017.subsystems.Hanger;
import com.team1241.frc2017.subsystems.Hopper;
import com.team1241.frc2017.subsystems.Intake;
import com.team1241.frc2017.subsystems.LEDstrips;
import com.team1241.frc2017.subsystems.Shooter;
import com.team1241.frc2017.utilities.DataOutput;
import com.team1241.frc2017.utilities.Target;
import com.team1241.frc2017.utilities.UDPClient;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
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
 * 
 * @author Team 1241: Theory6
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static Drivetrain drive;
	public static Intake intake;
	public static Shooter shooter;
	public static Conveyor conveyor;
	public static Hopper hopper;
	public static Hanger hanger;
	public static LEDstrips ledstrips;

	Preferences pref;
	public static double rpm;
	public static double conveyorRPM;
	public static double power;
	public static double powerC;
	public static double p;
	public static double i;
	public static double d;
	public static double pDrive;
	public static double iDrive;
	public static double dDrive;
	public static double pGyro;
	public static double iGyro;
	public static double dGyro;

	Command autonomousCommand;
	SendableChooser autoChooser;

	Target target = new Target();

	DataOutput data;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		pref = Preferences.getInstance();
		oi = new OI();
		drive = new Drivetrain();
		intake = new Intake();
		shooter = new Shooter();
		hopper = new Hopper();
		hanger = new Hanger();
		conveyor = new Conveyor();
		ledstrips = new LEDstrips();

		data = new DataOutput("data.txt");

		autoChooser = new SendableChooser();

		autoChooser.addDefault("No Auton", new NoAuto());
		// autoChooser.addObject("Drive Straight", new
		// ProfiledPath(DriveStraightProfile.Points,
		// DriveStraightProfile.kNumPoints));
		autoChooser.addObject("Drive Straight", new DriveCommand(93, 0.25, 2, 5));
		autoChooser.addObject("Turn Command", new TurnCommand(90, 0.8, 5, 1));
		autoChooser.addObject("Left Gear Command Blue", new LeftGearCommandBlue());
		autoChooser.addObject("Right Gear Command Red", new RightGearCommandRed());
		autoChooser.addObject("Center Gear Command", new CenterGearCommand());
		autoChooser.addObject("Shoot Auto", new ShootAuton());

		SmartDashboard.putData("Auto Mode(s)", autoChooser);

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		updateSmartDashboard();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	public void autonomousInit() {
		// autonomousCommand = (Command) autoChooser.getSelected();
		//autonomousCommand = new CenterGearCommand();
		 autonomousCommand = new ShootAuton();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		drive.resetEncoders();
		drive.resetGyro();

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		updateSmartDashboard();
	}

	public void teleopInit() {
		rpm = pref.getDouble("RPM", 0.0);
		conveyorRPM = pref.getDouble("Conveyor RPM", 0.0);
		power = pref.getDouble("Shooter Power", 0.0);
		powerC = pref.getDouble("Conveyor Power", 0.0);
		p = pref.getDouble("Shooter pGain", 0.0);
		drive.resetGyro();
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		/*
		 * try { new UDPClient().start(); SmartDashboard.putString("thread",
		 * "start"); } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); SmartDashboard.putString("thread",
		 * e.toString()); }
		 */
		pref = Preferences.getInstance();
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		updateSmartDashboard();
		// data.writeStatement(""+target.getCenterX(), ""+drive.getYaw());

		/*
		 * if(oi.getToolBackButton()){ //new BaseCameraTrack().start(); //new
		 * TurnCommand(-15, 0.8, 2, 1).start(); new DriveCameraTrack(36, 0.4,
		 * 3).start(); } if(oi.getToolRightAnalogButton()) data.close();
		 */
		// Robot.intake.retractIntake();
		// hopper.retractHopper();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	int counter = 0;

	public void updateSmartDashboard() {
		rpm = pref.getDouble("RPM", 0.0);
		power = pref.getDouble("Shooter Power", 0.0);
		powerC = pref.getDouble("Conveyor Power", 0.0);
		p = pref.getDouble("Shooter pGain", 0.0);
		i = pref.getDouble("Shooter iGain", 0.0);
		d = pref.getDouble("Shooter dGain", 0.0);

		pDrive = pref.getDouble("Drive pGain", 0.0);
		iDrive = pref.getDouble("Drive iGain", 0.0);
		dDrive = pref.getDouble("Drive dGain", 0.0);

		pGyro = pref.getDouble("Gyro pGain", 0.0);
		iGyro = pref.getDouble("Gyro iGain", 0.0);
		dGyro = pref.getDouble("Gyro dGain", 0.0);

		counter++;
		if (counter % 50 == 0) {
			SmartDashboard.putNumber("counter", counter / 50);
		}
		SmartDashboard.putBoolean("Can Shoot", shooter.shooterPID.isDone());
		SmartDashboard.putNumber("Shooter RPM", Math.round(shooter.getRPM(rpm)));
		SmartDashboard.putNumber("Set RPM", rpm);
		SmartDashboard.putNumber("Set Power", power);
		SmartDashboard.putNumber("Gyro Angle", drive.getYaw());
		SmartDashboard.putNumber("Right Encoder", drive.getRightDriveEncoder());
		SmartDashboard.putNumber("Left Encoder", drive.getLeftDriveEncoder());
		target.updateCoordinates();
		SmartDashboard.putNumber("Center X Value", target.getCenterX());
		SmartDashboard.putNumber("Pixel to Degree", drive.pixelToDegree(target.getCenterX()));
		SmartDashboard.putNumber("offset", drive.getOffset(target.getTopY()));
		SmartDashboard.putNumber("Height", target.getHeight());
		SmartDashboard.putBoolean("Limit Switch", hanger.limitEngaged());
		SmartDashboard.putNumber("Setpoint",
				Robot.drive.getYaw() + drive.pixelToDegree(target.getCenterX()) - drive.getOffset(target.getHeight()));

		SmartDashboard.putNumber("Conveyor Speed", conveyor.getConveyorSpeed());
	}
}
