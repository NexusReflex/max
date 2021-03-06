package tests;


import javax.inject.Inject;

import com.kuka.generated.ioAccess.MediaFlangeIOGroup;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.uiModel.ApplicationDialogType;

/**
 * Implementation of a robot application.
 * <p>
 * The application provides a {@link RoboticsAPITask#initialize()} and a 
 * {@link RoboticsAPITask#run()} method, which will be called successively in 
 * the application lifecycle. The application will terminate automatically after 
 * the {@link RoboticsAPITask#run()} method has finished or after stopping the 
 * task. The {@link RoboticsAPITask#dispose()} method will be called, even if an 
 * exception is thrown during initialization or run. 
 * <p>
 * <b>It is imperative to call <code>super.dispose()</code> when overriding the 
 * {@link RoboticsAPITask#dispose()} method.</b> 
 * 
 * @see UseRoboticsAPIContext
 * @see #initialize()
 * @see #run()
 * @see #dispose()
 */
public class AkustischesGreifenDemo2 extends RoboticsAPIApplication {
	@Inject
	private LBR lbr;
	@Inject
	private MediaFlangeIOGroup mfTouch;

	@Override
	public void initialize() {
		// initialize your application here
	}

	@Override
	public void run() {
		// your application execution starts here
		while (true) {
		mfTouch.setSwitchOffX3Voltage(false);
		mfTouch.setOutputX3Pin1(true);
		lbr.move(ptp(getApplicationData().getFrame("/P21")));
		int grip = getApplicationUI().displayModalDialog(ApplicationDialogType.QUESTION, "Levitierendes Objekt greifen?", "Nein", "Ja");
		if(grip==0) break;
		lbr.move(lin(getApplicationData().getFrame("/P20")));
		mfTouch.setOutputX3Pin2(true);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
		mfTouch.setOutputX3Pin1(false);
		lbr.move(lin(getApplicationData().getFrame("/P21")));
		int back = getApplicationUI().displayModalDialog(ApplicationDialogType.QUESTION, "Objekt zurücklegen", "Nein", "Ja");
		if(back==0) break;
		lbr.move(lin(getApplicationData().getFrame("/P20")));
		mfTouch.setOutputX3Pin1(true);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
		mfTouch.setOutputX3Pin2(false);
		lbr.move(lin(getApplicationData().getFrame("/P21")));

		}
		
	}
}