
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class StockWindow extends JFrame implements ActionListener {

	JLabel labelId = new JLabel("ID");
	JLabel labelName = new JLabel("��ǰ��");
	JLabel labelStock = new JLabel("���");
	JLabel labelPrice = new JLabel("�ܰ�");

	JTextField textFieldId = new JTextField(10);
	JTextField textFieldName = new JTextField(10);
	JTextField textFieldStock = new JTextField(10);
	JTextField textFieldPrice = new JTextField(10);

	JButton buttonAccept = new JButton("");
	Item item;
	String menu;

	public StockWindow(String menu) {
		this.menu = menu;
		display();
		setSize(300, 300);
		setVisible(true);
	}

	public StockWindow(String menu, Item item) {
		this.menu = menu;
		this.item = item;
		display();
		setSize(300, 300);
		setVisible(true);
	}

	public void display() {
		Container ct = getContentPane();
		JPanel jp = new JPanel(new GridLayout(4, 2));
		buttonAccept.setText(menu);
		textFieldId.setEditable(false);
		
		if(item != null) {
			textFieldId.setText(String.valueOf(item.getId()));
			textFieldName.setText(item.getItem_name());
			textFieldStock.setText(String.valueOf(item.getItem_stock()));
			textFieldPrice.setText(String.valueOf(item.getItem_price()));
		}
		
		jp.add(labelId);
		jp.add(textFieldId);

		jp.add(labelName);
		jp.add(textFieldName);

		jp.add(labelStock);
		jp.add(textFieldStock);

		jp.add(labelPrice);
		jp.add(textFieldPrice);

		ct.add(jp, BorderLayout.CENTER);
		ct.add(buttonAccept, BorderLayout.SOUTH);

		buttonAccept.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		String state = e.getActionCommand(); // buttonAccept.getText();
		String id, name = null, stock, price;
		boolean result = false;
		switch (state) {
		case "���":
			name = textFieldName.getText();
			stock = textFieldStock.getText();
			price = textFieldPrice.getText();

			Item register = new Item();
			register.setItem_name(name);
			register.setItem_stock(Integer.parseInt(stock));
			register.setItem_price(Integer.parseInt(price));

			try {
				result = ItemDAO.getInstance().insertItem(register);
				// jtableStock.getModel() ������Ʈ
				DefaultTableModel model = (DefaultTableModel) POS_StockManagement.jtableStock.getModel();
				// loadDB(model)
				POS_StockManagement.loadDB(model);
				this.dispose();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "����":
			name = textFieldName.getText();
			stock = textFieldStock.getText();
			price = textFieldPrice.getText();
			
			item.setItem_name(name);
			item.setItem_stock(Integer.parseInt(stock));
			item.setItem_price(Integer.parseInt(price));
			
			try {
				result = ItemDAO.getInstance().updateItem(item);
				// jtableStock.getModel() ������Ʈ
				DefaultTableModel model = (DefaultTableModel) POS_StockManagement.jtableStock.getModel();
				// loadDB(model)
				POS_StockManagement.loadDB(model);
				this.dispose();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case "����":
			id = textFieldId.getText();
			
			
			item.setId(Integer.parseInt(id));
			
			
			try {
				result = ItemDAO.getInstance().deleteItem(Integer.parseInt(id));
				
				int res;
				res = JOptionPane.showConfirmDialog(null, "������ ��ǰ " + name + "�� �����Ͻðڽ��ϱ�?");
				
				if(res == 0) {
					ItemDAO.getInstance().deleteItem(Integer.parseInt(id));
					DefaultTableModel model = (DefaultTableModel) POS_StockManagement.jtableStock.getModel();
					POS_StockManagement.loadDB(model);
					this.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "������ ����߽��ϴ�.");
				}
				// jtableStock.getModel() ������Ʈ
				// loadDB(model)
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			result = boolean deleteItem(int id);
			break;
		}
//		boolean insertItem(Item item)

	}

}
