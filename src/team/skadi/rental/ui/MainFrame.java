/**
 * 
 */
package team.skadi.rental.ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedList;

import javax.swing.JFrame;

/**
 * @author mizudp
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	public static final int FRAME_WIDTH = 1000, FRAME_HEIGHT = 610;
	public static final int FRAME_MIN_WIDTH, FRAME_MIN_HEIGHT;

	CardLayout cardLayout;
	UserPanel userPanel;
	ManagerPanel managerPanel;
	SignUpPanel signUpPanel;
	ManagerLoginPanel managerLoginPanel;
	LoginPanel loginPanel;
	HelpPanel helpPanel;

	private LinkedList<PanelName> panelStack;

	static {
		FRAME_MIN_HEIGHT = (int) (FRAME_HEIGHT * 0.85);
		FRAME_MIN_WIDTH = (int) (FRAME_WIDTH * 0.85);
	}

	enum PanelName {
		LOGIN("login"), MANAGER_LOGIN("manager login"), SIGN_UP("signUp"), USER("user"), MANAGER("manager"),
		HELP("help");

		String value;

		PanelName(String value) {
			this.value = value;
		}

	}

	public MainFrame() {
		super("德莱联盟电源租凭系统");
		panelStack = new LinkedList<>();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int x = (screenSize.width >> 1) - (FRAME_WIDTH >> 1);
		int y = (screenSize.height >> 1) - (FRAME_HEIGHT >> 1);
		setBounds(x, y, FRAME_WIDTH, FRAME_HEIGHT);
		setMinimumSize(new Dimension(FRAME_MIN_WIDTH, FRAME_MIN_HEIGHT));
		bulidLayout();
		setVisible(true);
	}

	private void bulidLayout() {
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		loginPanel = new LoginPanel(this);
		add(PanelName.LOGIN.value, loginPanel);
		managerLoginPanel = new ManagerLoginPanel(this);
		add(PanelName.MANAGER_LOGIN.value, managerLoginPanel);
		signUpPanel = new SignUpPanel(this);
		add(PanelName.SIGN_UP.value, signUpPanel);
		helpPanel = new HelpPanel(this);
		add(PanelName.HELP.value, helpPanel);
		userPanel = new UserPanel(this);
		add(PanelName.USER.value, userPanel);
		managerPanel = new ManagerPanel(this);
		add(PanelName.MANAGER.value, managerPanel);
	}

	public void showPanel(PanelName previous, PanelName panelName) {
		cardLayout.show(getContentPane(), panelName.value);
		panelStack.push(previous);
	}

	public void showPreviousPanel() {
		cardLayout.show(getContentPane(), panelStack.pop().value);
	}
}
