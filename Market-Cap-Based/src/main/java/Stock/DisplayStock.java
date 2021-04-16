package Stock;

import java.math.BigDecimal;
import java.lang.*;

public class DisplayStock {
	 String Stock_Symbol;
	double StockPrice;
	double Perc_ChangeIn_Price
	;
	
	public DisplayStock(String stock_Symbol, double stockPrice, double perc_ChangeIn_Price) {
		super();
		Stock_Symbol = stock_Symbol;
		StockPrice = stockPrice;
		Perc_ChangeIn_Price = perc_ChangeIn_Price;
	}
	public String toString() {
		return "Display_Stocks [Stock_Symbol=" + Stock_Symbol + ", StockPrice=" + StockPrice + ", Perc_ChangeIn_Price="
				+ Perc_ChangeIn_Price + "]";
	}
	 public int compareTo(DisplayStock  o)
	    {
	        if (this.Perc_ChangeIn_Price != o.Perc_ChangeIn_Price) {
	            return (int) (this.Perc_ChangeIn_Price - o.Perc_ChangeIn_Price);
	        }
			return 0;

	    }

}
