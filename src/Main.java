import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
			// メインのDB操作処理
			// INSERT、UPDATE、DELETEのような更新系のSQLには、処理後データベースから何行分の処理に成功したかを表す単純な数字だけが返される
			// 送信すべきSQLの雛形を準備
			// ?の部分はパラメータと呼ばれる
			// プログラムからデータベースへSQL文を送信する場合、似ているけれど一部だけが違うSQL文をいくつも送ることがよくある
			// そのためPreparedStatementクラスを使い、SQL文の雛形を準備することから始める
			// Statementという比較的手軽にSQL文を送れるクラスもあるが性能やセキュリティ面で懸念があるため、
			// 基本的にはPreparedStatementクラスを使う
			try(PreparedStatement pstmt = con.prepareStatement("DELETE FROM MONSTERS WHERE HP <= ? OR NAME =?")){
				// 第一引数のパラメータ番号は、雛形の前から数えて何番目の?マーク部分に値を流し込むかを指定する
				// 雛形に値を流し込みSQL文を組み立て送信する
				pstmt.setInt(1, 10); // 1番目の?に10を流し込み
				pstmt.setString(2, "ゾンビ"); // 2番目の?に"ゾンビ"を流し込み
				// 送信には、PreparedStatementクラスのexecuteUpdateメソッドを使う
				// このメソッドはUPDATEだけでなくINSERTやDELETEにも使える、int型の戻り値は処理の結果変更された行数
				// SQL文を送信する前までに、全ての?マークの部分に適切な値を流し込んでおかなければSQLExceptionという例外が発生する
				int r = pstmt.executeUpdate();
				// 処理結果を判定
				// excuteUpdateメソッドの戻り値が、想定された数値であれば成功とみなす
				if(r != 0) {
					System.out.println(r +"件のモンスター削除しました");
				} else {
					System.out.println("該当するモンスターはいませんでした");
				}
				// 雛形を用意しておけば1回目の送信を終えた後にパラメータを変えて2回目の送信を行うこともできる
				pstmt.setInt(1, 50);
				pstmt.setString(2, "ゴブリン");
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
