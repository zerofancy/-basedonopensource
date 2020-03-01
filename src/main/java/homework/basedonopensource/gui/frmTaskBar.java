package homework.basedonopensource.gui;

import javax.swing.JFrame;
import java.awt.event.WindowStateListener;
import java.awt.Frame;
import java.awt.event.WindowEvent;

public class frmTaskBar extends JFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = -872769333663420730L;
	private JFrame frmObj;
	private boolean mined=false;
	
	frmTaskBar(JFrame j){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent state) {
				 if(state.getNewState() == 1 || state.getNewState() == 7) {
//				     System.out.println("窗口最小化");
				    }else if(state.getNewState() == 0) {
//				     System.out.println("窗口恢复到初始状态");
				     frmTaskBar.this.setExtendedState(Frame.ICONIFIED);
				     if(mined) {
				    	 frmObj.setVisible(true);
				    	 frmObj.setAlwaysOnTop(true);
				    	 frmObj.setAlwaysOnTop(false);
				    	 
				     }else {
				    	 frmObj.setVisible(false);
				     }
				     mined=!mined;
				    }else if(state.getNewState() == 6) {
//				     System.out.println("窗口最大化");
				    }else {
//				    	System.out.println("other");
				    }
			}
		});
		frmObj=j;
		this.setTitle(j.getTitle());
		setVisible(true);
	     frmTaskBar.this.setExtendedState(Frame.ICONIFIED);
	     this.setIconImage(j.getIconImage());
	     j.setAlwaysOnTop(!j.isAlwaysOnTop());
	     j.setAlwaysOnTop(!j.isAlwaysOnTop());
	}
}
