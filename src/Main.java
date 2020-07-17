import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
			// 雛形を用意する
			try(PreparedStatement pstmt = con.prepareStatement("SELECT*FROM MONSTERS WHERE HP >= ?")){
				// 雛形に値を流しこんで送信
				pstmt.setInt(1, 10);
				// 検索系SQL文の送信
				// 戻り値を使い検索結果のデータを取り出すことができる
				ResultSet rs = pstmt.executeQuery();
				// 結果表を処理する
			} catch(Exception e) {
				System.out.println("エラーが発生しました");
				e.printStackTrace();
			}
		// 接続やSQL処理の失敗時の処理
		} catch(SQLException e) {
			System.out.println("接続もしくは処理に失敗しました");
			e.printStackTrace();
		}
	}
}
