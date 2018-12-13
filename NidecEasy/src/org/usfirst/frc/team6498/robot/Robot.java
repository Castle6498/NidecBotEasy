/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6498.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
	
	DifferentialDrive b;
	NidecBrushlessThree l, r,sh;
	NidecBrushless ac,tl,tr;
	LinearActuator acLinear, hood;	
	Joystick j;
	
	@Override
	public void robotInit() {
	
		l=new NidecBrushlessThree(Constants.kDriveEnablepwmPort,
				Constants.kLeftDriveDIOpwmPort, Constants.kLeftDriveDirectionpwmPort,
				Constants.kLeftDriveEncoderAPort, Constants.kLeftDriveEncoderBPort);
		r=new NidecBrushlessThree(18,
				Constants.kRightDriveDIOpwmPort, Constants.kRightDriveDirectionpwmPort,
				Constants.kRightDriveEncoderAPort, Constants.kRightDriveEncoderBPort);
		
		b = new DifferentialDrive(l,r);
		
		sh=new NidecBrushlessThree(Constants.kShooterEnablepwmPort,
				Constants.kShooterDIOpwmPort, Constants.kShooterDirectionpwmPort,
				Constants.kShooterEncoderAPort, Constants.kShooterEncoderBPort);
	
		ac = new NidecBrushless(Constants.kIntakeRollerEnable,Constants.kIntakeRollerMXPPort);
		
		tl = new NidecBrushless(Constants.kFeederEnablepwmPort,Constants.kFeederTriggerOneMXPPort);
		
		tr = new NidecBrushless(22,Constants.kFeederTriggerTwoMXPPort);
		
		acLinear = new LinearActuator();
		
		hood = new LinearActuator();
		
		j = new Joystick(0);
	}

	

	@Override
	public void teleopPeriodic() {
		
		//BASE ----------------------------
		b.arcadeDrive(-j.getY(), j.getX());
		//TODO later change to curvature drive :D ^^^
		
		//TRIGGER -------------------------
		if(j.getRawButton(6)) {
			tr.set(1);
			tl.set(-1);
		}else {
			tr.set(0);
			tl.set(0);
		}
				
		//RAISE/LOWER ACCUMULATOR----------
		if(j.getRawButton(10)) {
			acLinear.set(1);
		}else if(j.getRawButton(7)) {
			acLinear.set(0);
		}
		
		
		//ACCUMULATOR ----------------------
		if(j.getRawButton(11)) {
			ac.set(1);
		}else if(j.getRawButton(8)){
			ac.set(0);
		}
		
		
		//HOOD-------------------------------
		hood.set(j.getRawAxis(4));
		
		//SHOOTER----------------------------
		if(j.getRawButton(4)) {
			sh.set(-.8);
		}else if(j.getRawButton(2)){
			sh.set(0);
		}
		
		
		
		
	}

	
	@Override
	public void testPeriodic() {
	}
}
