package com.team1241.frc2017;

/**
 * This class is used to save constant values that will be changed every once in awhile. 
 * This can include PID tuned values or autonomous distances, etc...
 *
 *@author Kaveesha Siribaddana
 * @since 11/01/17
 */


public class NumberConstants {
	
	//**************************************************************************
    //*************************** PID VALUES (DRIVE) ***************************
    //**************************************************************************
	
	public static final double pDrive 									 = 0.02;
	public static final double iDrive 									 = 0.00;
	public static final double dDrive 									 = 0.1;
	
	public static final double Drive_Scale 								 = 0.00;
	
	//**************************************************************************
    //**************************** PID VALUES (GYRO) ***************************
    //**************************************************************************
	
	public static final double pGyro 									 = 0.008;
	public static final double iGyro 									 = 0.00;
	public static final double dGyro 									 = 0.00;
	
	//**************************************************************************
    //************************** PID VALUES (SHOOTER) **************************
    //**************************************************************************
	
	/*public static final double pShooter 								= 0.00005;
	public static final double iShooter 								= 0.000001;
	public static final double dShooter 								= 0.00;*/
	
	public static final double pShooter 								= 0.00015;
	public static final double iShooter 								= 0.00;
	public static final double dShooter 								= 0.00;

	//**************************************************************************
    //************************** PID VALUES (CONVEYOR) *************************
    //**************************************************************************
	
	public static final double pConveyor 								= 0.00;
	public static final double iConveyor 								= 0.00;
	public static final double dConveyor 								= 0.00;
	
	//**************************************************************************
    //**************************** LINE REGRESSION *****************************
    //**************************************************************************
	
	public static final int[] RPMS_CONVEYOR = new int[] {1300, 4100, 1350, 4500, 5000};
	public static final double[] POWERS_CONVEYOR = new double[] { 0.5, 0.7, 0.4, 0.9, 1.0};
	
	/*public static final int[] RPMS_SHOOTER = new int[] { 2800, 3100, 3400, 3800, 4000 };
	public static final double[] POWERS_SHOOTER = new double[] { 0.48, 0.53, 0.59, 0.68, 0.72 };*/
	
	public static final int[] RPMS_SHOOTER = new int[] { 2430, 2750, 3030, 3300, 3570 };
	public static final double[] POWERS_SHOOTER = new double[] { 0.4, 0.45, 0.5, 0.55, 0.6};
	
	//**************************************************************************
    //******************************* RPM VALUES *******************************
    //**************************************************************************
	
	public static final double ShotRPM									= 2000;
}
