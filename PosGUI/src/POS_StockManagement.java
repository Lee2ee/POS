
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class POS_StockManagement extends JPanel implements ActionListener{

	// �гο� ���Ե� ������Ʈ��
	JLabel labelName;
	JButton buttonDB;
	JButton buttonRegister;
	JButton buttonUpdate;
	JButton buttonDelete;
	static JTable jtableStock;
	
	// ����������� �ʱ�ȭ - �����ڸ޼ҵ�
	public POS_StockManagement() {
		
		setLayout(null);
		
		labelName = new JLabel("�����Ȳ");
		labelName.setSize(100, 40);
		labelName.setLocation(60, 20);
		
		buttonDB = new JButton("��ǰ ���ΰ�ħ");
		buttonDB.setBounds(10, 70, 150, 40);
		buttonDB.addActionListener(this);
		
		buttonRegister = new JButton("���");
		buttonRegister.setBounds(10, 130, 150, 40);
		buttonRegister.addActionListener(this);
		
		buttonUpdate = new JButton("����");
		buttonUpdate.setBounds(10, 190, 150, 40);
		buttonUpdate.addActionListener(this);
		
		buttonDelete = new JButton("����");
		buttonDelete.setBounds(10, 250, 150, 40);
		buttonDelete.addActionListener(this);
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("ID");
		model.addColumn("��ǰ��");
		model.addColumn("���");
		model.addColumn("�ܰ�");
		jtableStock = new JTable(model); // ���̺� ���·� ������ ��� ��(������ �ʿ�)
		JScrollPane jtable = new JScrollPane(jtableStock);
		jtable.setBounds(200, 20, 300, 280);
		
		
		add(labelName);
		add(buttonDB);
		add(buttonRegister);
		add(buttonUpdate);
		add(buttonDelete);
		add(jtable);
	
	}
	// �̺�Ʈ ó��
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		DefaultTableModel model = (DefaultTableModel)jtableStock.getModel();
		
		String buttonText = e.getActionCommand();
		if (buttonText == "��ǰ ���ΰ�ħ") {
			// DB����-select ��ü�˻�-vector ��Ƽ�-model�� ������ ���� --> loadDB(model)
			try {
				loadDB(model);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else if (buttonText == "���") {
			StockWindow window = new StockWindow(buttonText);
			
		}else if (buttonText == "����") {
			int row = jtableStock.getSelectedRow();
			if(row == -1) {
				
				JOptionPane.showMessageDialog(null, "������ ���� �����ϼ���!", "���!", JOptionPane.WARNING_MESSAGE);
				
			}else {
				String id = (String)jtableStock.getValueAt(row, 0);
				String name = (String)jtableStock.getValueAt(row, 1);
				String stock = (String)jtableStock.getValueAt(row, 2);
				String price = (String)jtableStock.getValueAt(row, 3);
				
				Item item = new Item();
				item.setId(Integer.parseInt(id));
				item.setItem_name(name);
				item.setItem_stock(Integer.parseInt(stock));
				item.setItem_price(Integer.parseInt(price));
				
				StockWindow window = new StockWindow(buttonText, item);				
			}
		}else {
			int row = jtableStock.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(null, "������ ���� �����ϼ���!", "���!", JOptionPane.WARNING_MESSAGE);				
			}else {
				String id = (String)jtableStock.getValueAt(row, 0);
				String name = (String)jtableStock.getValueAt(row, 1);
				String stock = (String)jtableStock.getValueAt(row, 2);
				String price = (String)jtableStock.getValueAt(row, 3);
				
				Item item = new Item();
				item.setId(Integer.parseInt(id));
				item.setItem_name(name);
				item.setItem_stock(Integer.parseInt(stock));
				item.setItem_price(Integer.parseInt(price));
				
				StockWindow window = new StockWindow(buttonText, item);
			}

			
		}
	}
	
	public static void loadDB(DefaultTableModel model) throws SQLException {
		
		Vector<Item> itemList = ItemDAO.getInstance().getAllItem();
		
		// JTable�� ��µ� ������(model) �ʱ�ȭ
		int rows = model.getRowCount();
		for (int i = rows-1; i >= 0; i--) {
			model.removeRow(i);
		}
		
		// itemList�� �����ϴ� DB�� ��� ���ڵ带 �� �� �� Vector�� ��Ƽ� model�� �߰�
		for (Item item : itemList) {
			String item_id = String.valueOf(item.getId()); //Integer.toString(), String.valueOf()
			String item_name = item.getItem_name();
			String item_stock = String.valueOf(item.getItem_stock());
			String item_price = String.valueOf(item.getItem_price());
			
			Vector<String> in = new Vector<String>();
			// in�� ��ҷ� id, ��ǰ��, ���, �ܰ��� �߰�
			in.add(item_id);
			in.add(item_name);
			in.add(item_stock);
			in.add(item_price);
			
			model.addRow(in);
		}
		
	}

}
