import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Score {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.OracleDriver");
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String user = "YQEDU";
		String password = "YQEDU";

		Map<String, Integer> scoreMap = new HashMap<>();

		Connection conn = DriverManager.getConnection(url, user, password);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select question_id,score from af_question_bank");
		while (rs.next()) {
			String question_id = rs.getString("question_id");
			String score = rs.getString("score");
			scoreMap.put(question_id, Integer.parseInt(score));
		}
		rs.close();
		List<Object[]> list = new ArrayList<Object[]>();
		rs = st.executeQuery("select h_id,answer_record from af_user_exam_history");
		while (rs.next()) {
			String h_id = rs.getString("h_id");
			String answer_record = rs.getString("answer_record");

			JSONObject json = JSONObject.fromObject(answer_record);
			JSONArray arr = json.optJSONArray("data");
			Integer total = 0;
			for (int i = 0; i < arr.size(); i++) {
				JSONObject item = arr.optJSONObject(i);
				String questionId = item.optString("questionId");
				int result = item.optInt("result");
				if (result == 1) {
					Integer score = 1;
					if (scoreMap.containsKey(questionId)) {
						score = scoreMap.get(questionId);
					}
					total += score;
				}
			}
			list.add(new Object[] { h_id, total });
		}
		rs.close();
		for (int i = 0; i < list.size(); i++) {
			Object[] item = list.get(i);
			String sql = "update af_user_exam_history set totalscore=" + item[1] + " where h_id='" + item[0] + "'";
			System.out.println((i + 1) + ": " + sql);
			st.execute(sql);
		}
		st.close();
		conn.close();
	}
}
