import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemsDAO {
	public static ArrayList<Item> findByMinimumPrice(int i) {
		try {
			Class.forName("org.h2.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try(Connection con = DriverManager.getConnection("jdbc:h2:~/rpgdb")) {
			try(PreparedStatement pstmt = con.prepareStatement("SELECT*FROM ITEMS WHERE PRICE > ?")){
				pstmt.setInt(1, i);
				try(ResultSet rs = pstmt.executeQuery()){
					ArrayList<Item> items = new ArrayList<>();
					while(rs.next()) {
						Item item = new Item();
						// 1行分の情報を取得してインスタンスに変換
						item.setName(rs.getString("NAME"));
						item.setPrice(rs.getInt("PRICE"));
						item.setWeight(rs.getInt("WEIGHT"));
						// インスタンスをArrayListに追加
						items.add(item);
					}
					// 最後にArrayListを返す
					return items;
				} catch(SQLException e) {
					e.printStackTrace();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
