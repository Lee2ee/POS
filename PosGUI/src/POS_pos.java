
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

	// ItemDAO 객체 생성(dao) 및 로드
	ItemDAO dao = ItemDAO.getInstance();

	// "제품 불러오기" 버튼 객체 선언(btnDB)
	JButton btnDB = new JButton("제품 불러오기");

	// "상품" 라벨 객체 선언(lblItem)
	JLabel lblItem = new JLabel("상품");

	// "상품리스트" 콤보박스 객체 선언(cmbBox)
	JComboBox<String> cmbBox = new JComboBox<String>();

	// "수량" 라벨 객체 선언(lblStock)
	JLabel lblStock = new JLabel("수량");

	// "수량입력박스" 텍스트필드 객체 선언(txtStock)
	JTextField txtStock = new JTextField();

	// "총가격" 라벨 객체 선언(lblTotal)
	JLabel lblTotal = new JLabel("총가격");

	// "총가격 출력박스" 텍스트필드 객체 선언(txtTotal)
	JTextField txtTotal = new JTextField();

	// "추가" 버튼 객체 선언(btnAdd)
	JButton btnAdd = new JButton("추가");

	// "결재" 버튼 객체 선언(btnPay)
	JButton btnPay = new JButton("결제");

	// "취소" 버튼 객체 선언(btnCancel)
	JButton btnCancel = new JButton("취소");

	// "테이블출력" JTable 객체 선언(jTableItem)
	JTable jTableItem = new JTable();

	// JTable에 출력할 Model 객체 선언(tableModel)
	DefaultTableModel tableModel = new DefaultTableModel();

	// JComboBox에 출력할 Model 객체 선언(comboModel)
	DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>();

	// 총가격 저장할 정수형 멤버변수 선언(total)
	int total = 0;

	String orPrice = null;

	public POS_pos() {

		// 자동 배치 레이아웃 비활성화
		setLayout(null);

		// 각 컴포넌트 객체 생성 및 화면 배치/크기 조정
		tableModel.addColumn("상품명");
		tableModel.addColumn("구매수량");
		tableModel.addColumn("단가");
		tableModel.addColumn("구매가격");
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

		// JPanel에 추가
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

		// 이벤트 처리를 위한 리스너 등록
		btnDB.addActionListener(this);
		btnAdd.addActionListener(this);
		btnPay.addActionListener(this);
		btnCancel.addActionListener(this);

		// 비활성화
		txtTotal.setEditable(false);

	}

//   @Override
	public void actionPerformed(ActionEvent e) {

		// 이벤트 객체로부터 텍스트 가져오기
		String buttonText = e.getActionCommand();
		// 제품명, 재고량, 가격 저장할 지역변수 선언 및 초기화
		String name = null;
		String stock = null;
		String price = null;

		// [제품 불러오기] 버튼 클릭 시
		if (buttonText == "제품 불러오기") {
			// comboBox의 모든 데이터 요소 삭제(removeAllItems());
			cmbBox.removeAllItems();
			// DB로부터 상품명 전체 검색 및 Vector에 저장
			comboModel.addAll(dao.getItem());
			// Vector에 저장한 상품명을 comboBox에 추가
			cmbBox.setModel(comboModel);
		}

		// [추가] 버튼 클릭 시
		else if (buttonText == "추가") {
			// comboBox에서 선택한 상품명과 텍스트필드에 입력한 수량 저장
			name = cmbBox.getSelectedItem().toString();
			stock = txtStock.getText();

			// 재고 가져오기
			try {
				orPrice = dao.getStock(name);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			// 재고보다 많이 입력할 시
			try {
				if (Integer.parseInt(orPrice) < Integer.parseInt(stock)) {
					JOptionPane.showMessageDialog(null, "남아있는 재고보다 많이 입력하셨습니다.");
					clean();
				} else {
					// DB로부터 사용자가 선택한 상품명의 단가 불러오기
					try {
						price = dao.getPrice(name);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// 사용자가 선택한 상품의 구매가격(단가*수량)과 누적 총액 연산하기
					int buyPrice = Integer.parseInt(stock) * Integer.parseInt(price);
					total = buyPrice + total;
					txtTotal.setText("" + total);

					// 상품명, 구매수량, 구매가격, 누적총액을 Vector에 저장
					Vector<Object> items = new Vector<Object>();
					items.add(name);
					items.add(stock);
					items.add(price);
					items.add(buyPrice);

					// Vector 객체를 tableModel에 추가
					tableModel.addRow(items);
				}
			} catch (NumberFormatException ae) {
				JOptionPane.showMessageDialog(null, "수량을 입력해 주세요.");
			}
		}

		// [결재] 버튼 클릭 시
		else if (buttonText == "결제") {
			int res;
			int input;
			// "결재하시겠습니까?"라는 다이얼로그 창 출력(JOptionPane.showConfirmDialog())
			res = JOptionPane.showConfirmDialog(null, "결제하시겠습니까?");
			// "YES"를 누르면 "총금액은 ~입니다"를 출력한 후 사용자로부터 숫자 입력받기(JOptionPane.showInputDialog())
			if (res == 0) {
				try {
					input = Integer
							.parseInt(JOptionPane.showInputDialog("총 금액은 " + total + "원 입니다.\n 지불하실 금액을 입력해 주세요."));

					// 사용자 입력금액이 총금액보다 크면 "지불금액,거스름돈"을 출력한 후 DB 업데이트(stockUpdate), 모든 컴포넌트 내의 데이터
					if (input >= total) {
						JOptionPane.showMessageDialog(null,
								"지불금액 : " + input + "원\n거스름돈 : " + (input - total) + "원 입니다.");
						stockUpdate(tableModel);
						// 초기화(clean())
						clean();
					} else {
						// 그렇지 않으면 "금액이 적습니다" Dialog 창 출력
						JOptionPane.showMessageDialog(null, "지불할 금액이 부족합니다.");
					}
				} catch (NumberFormatException | SQLException ae) {
					JOptionPane.showMessageDialog(null, "지불할 금액이 부족합니다.");
				}
			} else {
				JOptionPane.showInputDialog(null, "결제 취소하셨습니다.");
			}

		} // [취소] 버튼 클릭 시
		else {
			// "주문을 취소하시겠습니까?" Dialog 창 출력
			int res;
			res = JOptionPane.showConfirmDialog(null, "주문을 취소하시겠습니까?");
			if (res == 0) {
				JOptionPane.showMessageDialog(null, "취소가 완료되었습니다.");
			}
			// 모든 컴포넌트의 데이터 초기화
			clean();
		}
	}

	// JTable, 수량과 총가격의 JTextField 내 데이터 초기화
	public void clean() {
		int rows = tableModel.getRowCount();
		for (int i = rows - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		txtStock.setText(null);
		txtTotal.setText(null);
	}

	// JTable에 출력된 모든 데이터의 상품명, 재고량, 가격을 이용하여 DB 데이터 업데이트
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