
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class POS_pos extends JPanel implements ActionListener {

	// ItemDAO ��ü ����(dao) �� �ε�
	ItemDAO dao = ItemDAO.getInstance();

	// "��ǰ �ҷ�����" ��ư ��ü ����(btnDB)
	JButton btnDB = new JButton("��ǰ �ҷ�����");

	// "��ǰ" �� ��ü ����(lblItem)
	JLabel lblItem = new JLabel("��ǰ");

	// "��ǰ����Ʈ" �޺��ڽ� ��ü ����(cmbBox)
	JComboBox<String> cmbBox = new JComboBox<String>();

	// "����" �� ��ü ����(lblStock)
	JLabel lblStock = new JLabel("����");

	// "�����Է¹ڽ�" �ؽ�Ʈ�ʵ� ��ü ����(txtStock)
	JTextField txtStock = new JTextField();

	// "�Ѱ���" �� ��ü ����(lblTotal)
	JLabel lblTotal = new JLabel("�Ѱ���");

	// "�Ѱ��� ��¹ڽ�" �ؽ�Ʈ�ʵ� ��ü ����(txtTotal)
	JTextField txtTotal = new JTextField();

	// "�߰�" ��ư ��ü ����(btnAdd)
	JButton btnAdd = new JButton("�߰�");

	// "����" ��ư ��ü ����(btnPay)
	JButton btnPay = new JButton("����");

	// "���" ��ư ��ü ����(btnCancel)
	JButton btnCancel = new JButton("���");

	// "���̺����" JTable ��ü ����(jTableItem)
	JTable jTableItem = new JTable();

	// JTable�� ����� Model ��ü ����(tableModel)
	DefaultTableModel tableModel = new DefaultTableModel();

	// JComboBox�� ����� Model ��ü ����(comboModel)
	DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>();

	// �Ѱ��� ������ ������ ������� ����(total)
	int total = 0;

	String orPrice = null;

	public POS_pos() {

		// �ڵ� ��ġ ���̾ƿ� ��Ȱ��ȭ
		setLayout(null);

		// �� ������Ʈ ��ü ���� �� ȭ�� ��ġ/ũ�� ����
		tableModel.addColumn("��ǰ��");
		tableModel.addColumn("���ż���");
		tableModel.addColumn("�ܰ�");
		tableModel.addColumn("���Ű���");
		jTableItem = new JTable(tableModel);
		JScrollPane jscroll = new JScrollPane(jTableItem);

		btnDB.setBounds(20, 20, 140, 40);
		lblItem.setBounds(20, 90, 100, 30);
		cmbBox.setBounds(70, 90, 200, 30);
		lblStock.setBounds(20, 140, 100, 30);
		txtStock.setBounds(70, 140, 200, 30);
		lblTotal.setBounds(20, 250, 100, 40);
		txtTotal.setBounds(70, 250, 200, 40);
		btnAdd.setBounds(170, 190, 100, 40);
		btnPay.setBounds(300, 250, 100, 40);
		btnCancel.setBounds(410, 250, 100, 40);
		jscroll.setBounds(300, 20, 210, 210);

		// JPanel�� �߰�
		add(btnDB);
		add(lblItem);
		add(cmbBox);
		add(lblStock);
		add(txtStock);
		add(lblTotal);
		add(txtTotal);
		add(btnAdd);
		add(btnPay);
		add(btnCancel);
		add(jscroll);

		// �̺�Ʈ ó���� ���� ������ ���
		btnDB.addActionListener(this);
		btnAdd.addActionListener(this);
		btnPay.addActionListener(this);
		btnCancel.addActionListener(this);

		// ��Ȱ��ȭ
		txtTotal.setEditable(false);

	}

//   @Override
	public void actionPerformed(ActionEvent e) {

		// �̺�Ʈ ��ü�κ��� �ؽ�Ʈ ��������
		String buttonText = e.getActionCommand();
		// ��ǰ��, ���, ���� ������ �������� ���� �� �ʱ�ȭ
		String name = null;
		String stock = null;
		String price = null;

		// [��ǰ �ҷ�����] ��ư Ŭ�� ��
		if (buttonText == "��ǰ �ҷ�����") {
			// comboBox�� ��� ������ ��� ����(removeAllItems());
			cmbBox.removeAllItems();
			// DB�κ��� ��ǰ�� ��ü �˻� �� Vector�� ����
			comboModel.addAll(dao.getItem());
			// Vector�� ������ ��ǰ���� comboBox�� �߰�
			cmbBox.setModel(comboModel);
		}

		// [�߰�] ��ư Ŭ�� ��
		else if (buttonText == "�߰�") {
			// comboBox���� ������ ��ǰ��� �ؽ�Ʈ�ʵ忡 �Է��� ���� ����
			name = cmbBox.getSelectedItem().toString();
			stock = txtStock.getText();

			// ��� ��������
			try {
				orPrice = dao.getStock(name);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			// ����� ���� �Է��� ��
			try {
				if (Integer.parseInt(orPrice) < Integer.parseInt(stock)) {
					JOptionPane.showMessageDialog(null, "�����ִ� ����� ���� �Է��ϼ̽��ϴ�.");
					clean();
				} else {
					// DB�κ��� ����ڰ� ������ ��ǰ���� �ܰ� �ҷ�����
					try {
						price = dao.getPrice(name);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// ����ڰ� ������ ��ǰ�� ���Ű���(�ܰ�*����)�� ���� �Ѿ� �����ϱ�
					int buyPrice = Integer.parseInt(stock) * Integer.parseInt(price);
					total = buyPrice + total;
					txtTotal.setText("" + total);

					// ��ǰ��, ���ż���, ���Ű���, �����Ѿ��� Vector�� ����
					Vector<Object> items = new Vector<Object>();
					items.add(name);
					items.add(stock);
					items.add(price);
					items.add(buyPrice);

					// Vector ��ü�� tableModel�� �߰�
					tableModel.addRow(items);
				}
			} catch (NumberFormatException ae) {
				JOptionPane.showMessageDialog(null, "������ �Է��� �ּ���.");
			}
		}

		// [����] ��ư Ŭ�� ��
		else if (buttonText == "����") {
			int res;
			int input;
			// "�����Ͻðڽ��ϱ�?"��� ���̾�α� â ���(JOptionPane.showConfirmDialog())
			res = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?");
			// "YES"�� ������ "�ѱݾ��� ~�Դϴ�"�� ����� �� ����ڷκ��� ���� �Է¹ޱ�(JOptionPane.showInputDialog())
			if (res == 0) {
				try {
					input = Integer
							.parseInt(JOptionPane.showInputDialog("�� �ݾ��� " + total + "�� �Դϴ�.\n �����Ͻ� �ݾ��� �Է��� �ּ���."));

					// ����� �Է±ݾ��� �ѱݾ׺��� ũ�� "���ұݾ�,�Ž�����"�� ����� �� DB ������Ʈ(stockUpdate), ��� ������Ʈ ���� ������
					if (input >= total) {
						JOptionPane.showMessageDialog(null,
								"���ұݾ� : " + input + "��\n�Ž����� : " + (input - total) + "�� �Դϴ�.");
						stockUpdate(tableModel);
						// �ʱ�ȭ(clean())
						clean();
					} else {
						// �׷��� ������ "�ݾ��� �����ϴ�" Dialog â ���
						JOptionPane.showMessageDialog(null, "������ �ݾ��� �����մϴ�.");
					}
				} catch (NumberFormatException | SQLException ae) {
					JOptionPane.showMessageDialog(null, "������ �ݾ��� �����մϴ�.");
				}
			} else {
				JOptionPane.showInputDialog(null, "���� ����ϼ̽��ϴ�.");
			}

		} // [���] ��ư Ŭ�� ��
		else {
			// "�ֹ��� ����Ͻðڽ��ϱ�?" Dialog â ���
			int res;
			res = JOptionPane.showConfirmDialog(null, "�ֹ��� ����Ͻðڽ��ϱ�?");
			if (res == 0) {
				JOptionPane.showMessageDialog(null, "��Ұ� �Ϸ�Ǿ����ϴ�.");
			}
			// ��� ������Ʈ�� ������ �ʱ�ȭ
			clean();
		}
	}

	// JTable, ������ �Ѱ����� JTextField �� ������ �ʱ�ȭ
	public void clean() {
		int rows = tableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		txtStock.setText(null);
		txtTotal.setText(null);
	}

	// JTable�� ��µ� ��� �������� ��ǰ��, ���, ������ �̿��Ͽ� DB ������ ������Ʈ
	public void stockUpdate(DefaultTableModel model) throws SQLException {
		Item item = null;
		boolean result = false;
		
		for (int i = 0; i < model.getRowCount(); i++) {
			item.setItem_name(String.valueOf(model.getValueAt(i, 0)));
		}
		for (int i = 0; i < model.getRowCount(); i++) {
			item.setItem_stock(Integer.parseInt((String) model.getValueAt(i, 1)));
		}
		
		result = ItemDAO.getInstance().updateItem(item);
	}
}