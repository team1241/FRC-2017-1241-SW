package com.team1241.frc2017.commands;

import com.team1241.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ConveyorCommand extends Command {
	
	private SetConveyorRPM rpm;
	private SetConveyorRPM reverseRPM;

	public ConveyorCommand() {
		requires(Robot.conveyor);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		rpm = new SetConveyorRPM(Robot.conveyorRPM);
		reverseRPM = new SetConveyorRPM(-Robot.conveyorRPM);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		if (Robot.oi.getToolXButton()) {
			Robot.conveyor.agitatorFeeder(-0.5);
			Robot.conveyor.agitatorHopper(0.5);
			//reverseRPM.cancel();
			//rpm.start();
			Robot.conveyor.setConveyorPower(-0.4);
		} else if (Robot.oi.getToolAButton()) {
			Robot.conveyor.agitatorFeeder(-0.9);
			Robot.conveyor.agitatorHopper(0.25);
			//rpm.cancel();
			//reverseRPM.start();
			Robot.conveyor.setConveyorPower(0.65);
		} else {
			Robot.conveyor.agitatorFeeder(0);
			Robot.conveyor.agitatorHopper(0);
			//rpm.cancel();
			//reverseRPM.cancel();
			Robot.conveyor.setConveyorPower(0);
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
