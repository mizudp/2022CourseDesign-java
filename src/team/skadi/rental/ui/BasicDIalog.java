package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class BasicDIalog extends JDialog implements ActionListener {

	public static final int NULL = 0, CENCEL = 1, CONFIRM = 2;

	protected int option;

	MainFrame mainFrame;
	private JButton confirmBtn;
	private JButton cencelBtn;

	public BasicDIalog(MainFrame mainFrame, String title) {
		super(mainFrame, title);
		this.mainFrame = mainFrame;
		bulidLayout();
		addListener();
		buildSouthBtnPanel();
		init();
	}

	protected void init() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		Rectangle bounds = mainFrame.getBounds();
		Point point = new Point();
		point.x = bounds.x + (bounds.width - getWidth()) / 2;
		point.y = bounds.y + (bounds.height - getHeight()) / 2;
		setLocation(point);
	}

	protected void buildSouthBtnPanel() {
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
		confirmBtn = new JButton("确定");
		confirmBtn.addActionListener(this);
		btnPanel.add(confirmBtn);
		cencelBtn = new JButton("取消");
		cencelBtn.addActionListener(this);
		btnPanel.add(cencelBtn);
		add(btnPanel, BorderLayout.SOUTH);
	}

	public int getResult() {
		setVisible(true);
		return option;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean close = true;
		if (e.getSource() == confirmBtn) {
			close = onConfirmBtnClick();
		} else if (e.getSource() == cencelBtn) {
			close = onCencelBtnClick();
		}
		if (close)
			dispose();
	}

	protected boolean onConfirmBtnClick() {
		option = CONFIRM;
		return true;
	}

	protected boolean onCencelBtnClick() {
		option = CENCEL;
		return true;
	}

	abstract protected void bulidLayout();

	abstract protected void addListener();
}
