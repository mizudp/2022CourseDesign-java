package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class BasicDialog extends JDialog implements ActionListener {

	public static final int NULL = -1, CENCEL = -3, CONFIRM = -2;

	protected int option;

	private JButton confirmBtn;
	private JButton cencelBtn;

	public BasicDialog(JFrame frame, String title) {
		super(frame, title);
	}

	protected void init() {
		bulidLayout();
		buildSouthBtnPanel();
		addListener();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		pack();
		Rectangle bounds = getOwner().getBounds();
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

	protected void addListener() {

	}
}
