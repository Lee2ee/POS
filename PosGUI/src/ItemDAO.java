
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ItemDAO {

	private ItemDAO() {}

	private static ItemDAO instance = new ItemDAO();

	public static ItemDAO getInstance() {
		return instance;
	}

	// 1. ��ü ���ڵ� �˻� ���(getAllItem())
	public Vector<Item> getAllItem() throws SQLException {

		Vector<Item> list = new Vector<Item>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from item";

		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			// rs ��ȸ�ϸ鼭 Item ��ü ����, Vector�� ������ item �߰�
			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setItem_name(rs.getString("item_name"));
				item.setItem_stock(rs.getInt("item_stock"));
				item.setItem_price(rs.getInt("item_price"));
				list.add(item);
			}

		} catch (Exception e) {
			System.out.println("DB ���� �Ǵ� SQL ����!");
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}

		return list;
	}

	// 2. ��ǰ�� ��� ó��
	public Vector<String> getItem() {
		Vector<Item> dblist = new Vector<Item>();
		Vector<String> itemlist = new Vector<String>();

		try {
			dblist = getAllItem();
			for (Item item : dblist) {
				itemlist.add(item.getItem_name());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return itemlist;
	}
	
	// 3. item_name�� �ش��ϴ� ������ �˻��ؼ� ��ȯ
	public String getPrice(String name) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String price = null;
		String sql = "select item_price from item where item_name=?";

		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, name);
			rs = pstmt.executeQuery();
			// rs ��ȸ�ϸ鼭 Item ��ü ����, Vector�� ������ item �߰�
			while (rs.next()) {
				price = Integer.toString(rs.getInt("item_price"));
			}

		} catch (Exception e) {
			System.out.println("DB ���� �Ǵ� SQL ����!");
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}

		return price;
	}
	
	// 4. item_name�� �ش��ϴ� ��� �˻��ؼ� ��ȯ
	public String getStock(String name) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String stock = null;
		String sql = "select item_stock from item where item_name=?";

		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, name);
			rs = pstmt.executeQuery();
			// rs ��ȸ�ϸ鼭 Item ��ü ����, Vector�� ������ item �߰�
			while (rs.next()) {
				stock = Integer.toString(rs.getInt("item_stock"));
			}

		} catch (Exception e) {
			System.out.println("DB ���� �Ǵ� SQL ����!");
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}

		return stock;
	}
	
	// 5. ��� ������Ʈ �ϱ�(item_stock==> total, stock, name)
	public void updateStock(String total, String stock, String name) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String update_stock = null;
		String sql = "update item set item_stock=?-? where item_name=?";

		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, total);
			pstmt.setNString(2, stock);
			pstmt.setNString(3, name);
			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("DB ���� �Ǵ� SQL ����!");
		} finally {
			pstmt.close();
			conn.close();
		}
	}
	
	// 6. ������ȭ�� - ��ǰ �ű� ���
	public boolean insertItem(Item item) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into item(item_name, item_stock, item_price) values(?, ?, ?)";
		boolean result = false;
		
		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item.getItem_name());
			pstmt.setInt(2, item.getItem_stock());
			pstmt.setInt(3, item.getItem_price());
			
			int r = pstmt.executeUpdate();
			
			if (r>0) {
				result = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			pstmt.close();
			conn.close();
		}
		
		return result;
	}
	
	// 7. ������ȭ�� - ��ǰ ���� ����
	public boolean updateItem(Item item) throws SQLException {
		
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = "update item set item_name=?, item_stock=?, item_price=? where (id=?)";
	boolean result = false;
	
	try {
		conn = DBConnect.connect();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, item.getItem_name());
		pstmt.setInt(2, item.getItem_stock());
		pstmt.setInt(3, item.getItem_price());
		pstmt.setInt(4, item.getId());
		
		int r = pstmt.executeUpdate();
		
		if (r>0) {
			result = true;
		}
		
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		pstmt.close();
		conn.close();
	}
	
	return result;
}
	
	// 8. ������ȭ�� - ��� ��ǰ ����
	public boolean deleteItem(int id) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from item where (id=?)";
		boolean result = false;
		
		try {
			conn = DBConnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			int r = pstmt.executeUpdate();
			
			if (r>0) {
				result = true;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			pstmt.close();
			conn.close();
		}
		
		return result;
	}
	
	
}
