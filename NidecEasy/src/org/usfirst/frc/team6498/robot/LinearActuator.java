package org.usfirst.frc.team6498.robot;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;


public class LinearActuator extends PWM {
  private static double kMaxStroke = 140.0;
  private static double kMinStroke = 0.0;

  protected static final double kDefaultMaxServoPWM = 2.0;//2.4;
  protected static final double kDefaultMinServoPWM = 1.0;//.6;

  /**
   * Constructor.<br>
   *
   * <p>By default {@value #kDefaultMaxServoPWM} ms is used as the maxPWM value<br> By default
   * {@value #kDefaultMinServoPWM} ms is used as the minPWM value<br>
   *
   * @param channel The PWM channel to which the servo is attached. 0-9 are on-board, 10-19 are on
   *                the MXP port
   */
  public LinearActuator(final int channel) {
    super(channel);
    setBounds(kDefaultMaxServoPWM, 0, 0, 0, kDefaultMinServoPWM);
    setPeriodMultiplier(PeriodMultiplier.k4X);

    HAL.report(tResourceType.kResourceType_Servo, getChannel());
    setName("Servo", getChannel());
  }

  void setStrokeRange(double min, double max){
	  kMinStroke=min;
	  kMaxStroke=max;
  }
  
  double getMinStroke() {
	  return kMinStroke;
  }
  double getMaxStroke() {
	  return kMaxStroke;
  }

  /**
   * Set the linear actuator position.
   *
   * <p>linear actuator values range from 0.0 to 1.0 corresponding to the range of full left to full right.
   *
   * @param value Position from 0.0 to 1.0.
   */
  public void set(double value) {
    setPosition(value);
  }

  /**
   * Get the linear actuator position.
   *
   * <p>linear actuator values range from 0.0 to 1.0 corresponding to the range of full left to full right.
   *
   * @return Position from 0.0 to 1.0.
   */
  public double get() {
    return getPosition();
  }

  /**
   * Set the linear actuator angle.
   *
   * <p>Assume that the linear actuator angle is linear with respect to the PWM value (big assumption, need to
   * test).
   *
   * <p>linear actuator angles that are out of the supported range of the linear actuator simply "saturate" in that
   * direction In other words, if the linear actuator has a range of (X degrees to Y degrees) than angles of
   * less than X result in an angle of X being set and angles of more than Y degrees result in an
   * angle of Y being set.
   *
   * @param distance The distance in degrees to set the servo.
   */
  public void setStroke(double distance) {
    if (distance < kMinStroke) {
    	distance = kMinStroke;
    } else if (distance > kMaxStroke) {
    	distance = kMaxStroke;
    }

    setPosition(((distance - kMinStroke)) / getRange());
  }

  /**
   * Get the linear actuator distance.
   *
   * <p>Assume that the linear actuator distance is linear with respect to the PWM value (big assumption, need to
   * test).
   *
   * @return The angle in degrees to which the linear actuator is set.
   */
  public double getDistance() {
    return getPosition() * getRange() + kMinStroke;
  }

  private double getRange() {
    return kMaxStroke - kMinStroke;
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("LinearActuator");
    builder.addDoubleProperty("Value", this::get, this::set);
  }
}
