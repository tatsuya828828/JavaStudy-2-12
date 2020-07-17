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
			// 自動コミットをfalseにすることで、手動コミットにすることができる
			// con.setAutoCommit(false);
			// 手動コミットの際は、コミットするときはcommitメソッド、途中で処理をキャンセルしたい場合はrollbackメソッドを使う
			System.out.println("H2データベースに接続しました");
			// 雛形を用意する
			try(PreparedStatement pstmt = con.prepareStatement("SELECT*FROM MONSTERS WHERE HP >= ?")){
				// 雛形に値を流しこんで送信
				pstmt.setInt(1, 10);
				// 検索系SQL文の送信
				// 戻り値を使い検索結果のデータを取り出すことができる
				ResultSet rs = pstmt.executeQuery();
				// 結果表を処理する
				// 複数の行の結果表の処理
				// nextメソッドは次の行進むことが可能であればtrueを返すのでrs.next()で結果行の最後まで処理を順次進めることができる
				while(rs.next()) {
					System.out.println(rs.getString("NAME"));
				}
				// 0もしくは1行の結果表が返される可能性があるSELECT文の場合、結果処理は
				// 目的の行が見つかったか見つからなかったかの二択になるため以下のようなコードパターンが多い
				if(rs.next()) {
					System.out.println("ゴブリンのHPは"+ rs.getInt("HP"));
				} else {
					System.out.println("ゴブリンはDBに存在しません");
				}
			} catch(Exception e) {
				System.out.println("処理時にエラーが発生しました");
				e.printStackTrace();
			}
		// 接続やSQL処理の失敗時の処理
		} catch(SQLException e) {
			System.out.println("接続もしくは処理に失敗しました");
			e.printStackTrace();
		}
	}
}
