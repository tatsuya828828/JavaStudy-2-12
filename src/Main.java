import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {
		try {
			Class.forName("org.h2.Driver");
		// JDBCドライバJARが見つからない場合の処理
		} catch(ClassNotFoundException e) {
			System.out.println("ドライバが見つかりません");
			e.printStackTrace();
		}

		// DBへの接続
		// getConnectionの引数にはJDBC URLと接続IDとパスワードを与える
		try(Connection con = DriverManager.getConnection("jdbc:h2:~/mydb", "sa", "")){
			System.out.println("H2データベースに接続しました");
			//メインのDB操作処理
		// 接続やSQL処理の失敗時の処理
		} catch(SQLException e) {
			System.out.println("接続もしくは処理に失敗しました");
			e.printStackTrace();
		}
	}
}
