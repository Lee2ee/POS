import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class Test {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		ItemDAO dao = ItemDAO.getInstance();
		
		Vector<Item> list = new Vector<Item>();
		Vector<String> itemlist = new Vector<String>();
		
		/*
		try {
			list = dao.getAllItem();			
			Iterator it = list.iterator();
			
			System.out.println("*** 占쏙옙체 占쏙옙품 占쏙옙占� 占쏙옙占� *** ");
			while(it.hasNext()) {
				Item item = (Item)it.next();
				System.out.println(item.getId()+", "+item.getItem_name()+", " +item.getItem_stock()+", "+item.getItem_price());
			}
			
			System.out.println("*** 占쏙옙품占쏙옙 占쏙옙占� *** ");
			Vector<String> itemlist = new Vector<String>();
			
			itemlist = dao.getItem();
			
			//占쏙옙회占싹면서 vector占쏙옙 占쏙옙 占쏙옙占� 占쏙옙占�
			it = itemlist.iterator();
			while(it.hasNext()) {
				String item = (String)it.next();
				System.out.println(item);
			}
			
			System.out.println("*** 占쏙옙품 占쏙옙占쏙옙 占쏙옙占� *** ");
			Scanner sc = new Scanner(System.in);
			System.out.println("占쏙옙품占쏙옙 占쌉뤄옙 : ");
			String input = sc.next();
			String price = dao.getPrice(input);
			System.out.println("占쏙옙품 占쏙옙占쏙옙 : " + price);
			
			System.out.println("*** 占쏙옙품 占쏙옙占� 占쏙옙占쏙옙占쏙옙트 *** ");
			String total = dao.getStock("aaa");
			System.out.println("占실몌옙 占쏙옙占쏙옙 : ");
			String count = sc.next();
			dao.updateStock(total, count, "aaa");
			
			list = dao.getAllItem();			
			it = list.iterator();
			
			System.out.println("*** 占쏙옙체 占쏙옙품 占쏙옙占� 占쏙옙占� *** ");
			while(it.hasNext()) {
				Item item = (Item)it.next();
				System.out.println(item.getId()+", "+item.getItem_name()+", " +item.getItem_stock()+", "+item.getItem_price());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
		// 6. 占쏙옙占쏙옙占쏙옙화占쏙옙 - 占쏙옙품 占신깍옙 占쏙옙占�
		Scanner sc = new Scanner(System.in);
		Item itemNew = new Item();
		
		System.out.println("***** 占쏙옙품 占신깍옙 占쏙옙占� *****");
		System.out.print("占쏙옙품占쏙옙 占쌉뤄옙 : ");
		String name = sc.next();
		System.out.print("占쌉곤옙 占쌉뤄옙 : ");
		int stock = sc.nextInt();
		System.out.print("占쌤곤옙 占쌉뤄옙 : ");
		int price = sc.nextInt();
		
		itemNew.setItem_name(name);
		itemNew.setItem_stock(stock);
		itemNew.setItem_price(price);
		
		boolean result = dao.insertItem(itemNew);
		
		list = dao.getAllItem();
		Iterator it = list.iterator();
		
		System.out.println("***** 占쏙옙체 占쏙옙품 占쏙옙占� *****");
		while(it.hasNext()) {
			Item item = (Item)it.next();
			System.out.println(item.getId() + ", " + item.getItem_name() + ", " +  item.getItem_stock() + ", " +  item.getItem_price());
		}
		
		// 7. 占쏙옙占쏙옙占쏙옙화占쏙옙 - 占쏙옙품 占쏙옙占쏙옙 占쏙옙占쏙옙
		
		// 8. 占쏙옙占쏙옙占쏙옙화占쏙옙 - 占쏙옙占� 占쏙옙품 占쏙옙占쏙옙

	}

}
