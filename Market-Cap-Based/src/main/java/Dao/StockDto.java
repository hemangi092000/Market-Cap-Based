package Dao;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StockDto {
	public List getStocksForMarketCap(int Start_Amt, int Final_Amt) {
		String SELECT_STOCK_FOR_MARKETCAP_QUERY = "SELECT * FROM  Nifty50  WHERE MARKETCAP BETWEEN ? AND ?";
		String STOCK_SYMBOL = "Symbol";
		List stockList = new ArrayList();
		try {
			Connection con = DBConnection.CreateConnection();
			PreparedStatement pst = con.prepareStatement(SELECT_STOCK_FOR_MARKETCAP_QUERY);
			pst.setInt(1, Start_Amt);
			pst.setInt(2,Final_Amt);
			ResultSet rs = pst.executeQuery();

			if (null != rs) {
				while (rs.next()) {
					String stockSymbol = rs.getString(STOCK_SYMBOL);
					stockList.add(stockSymbol);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return stockList;

	}


}
