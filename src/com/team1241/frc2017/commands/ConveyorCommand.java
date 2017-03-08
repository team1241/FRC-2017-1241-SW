package com.team1241.frc2017.commands;

import com.team1241.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ConveyorCommand extends Command {

	public ConveyorCommand() {
		requires(Robot.conveyor);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		if (Robot.oi.getToolXButton()) {
			Robot.conveyor.agitatorFeeder(-0.5);
			Robot.conveyor.agitatorHopper(0.6);
			Robot.conveyor.conveyorMotor(-0.8);
		} else if (Robot.oi.getToolAButton()) {
			Robot.conveyor.agitatorFeeder(-0.9);
			Robot.conveyor.agitatorHopper(0.25);
			Robot.conveyor.conveyorMotor(0.65);
		} else {
			Robot.conveyor.agitatorFeeder(0);
			Robot.conveyor.agitatorHopper(0);
			Robot.conveyor.conveyorMotor(0);
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
