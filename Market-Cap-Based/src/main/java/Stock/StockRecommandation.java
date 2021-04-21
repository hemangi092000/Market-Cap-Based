package Stock;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.lang.*;

import java.util.Map.Entry;
import Dao.StockDto;


public class StockRecommandation {
	public static TreeMap Get_Top_Stocks(Map map)
	{
		// TreeMap to store values of HashMap
		TreeMap<Double,JSONObject> Stock_map = new TreeMap<Double, JSONObject>(Collections.reverseOrder());

		// Copy all data from hashMap into TreeMap
		Stock_map.putAll(map);

		// Display the TreeMap which is naturally sorted
		return Stock_map;        
	}

	public static void main(String[] args) throws IOException, InterruptedException, ParseException 
	{
		String HEX_VALUE_FOR_DOUBLE_QUOTE = "%2C";
		String YAHOO_FINANCE_URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=IN&symbols=";
		
		
		StockDto stockDAO = new StockDto();
		int start_amt=200000;
		int final_Amt=400000;
		List stocksForSelectedCAP = stockDAO.getStocksForMarketCap(start_amt,final_Amt);
		String stockQueryStr = "";
		for (int i = 0; i < stocksForSelectedCAP.size(); i++) {
			String stockSymbol = (String) stocksForSelectedCAP.get(i);
			stockQueryStr = stockQueryStr + HEX_VALUE_FOR_DOUBLE_QUOTE + stockSymbol + HEX_VALUE_FOR_DOUBLE_QUOTE + ",";
		}
		stockQueryStr = stockQueryStr.substring(0, stockQueryStr.length() - 1);
		String final_url= YAHOO_FINANCE_URL+stockQueryStr;
        System.out.println(final_url);
        HttpRequest request = HttpRequest.newBuilder()
        		.uri(URI.create(final_url))
        		.header("x-rapidapi-key", "1ee55624d4mshcc9aa69bce55ab3p1e6d0ajsn0a809c836267")
        		.header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
        		.method("GET", HttpRequest.BodyPublishers.noBody())
        		.build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
		String json= response.body().toString();
		JSONObject jsonObject=null;
		jsonObject= (JSONObject) new JSONParser().parse(json);
		JSONObject res=(JSONObject) jsonObject.get("quoteResponse");
		JSONArray array=   (JSONArray) res.get("result");
		JSONObject[] values =new JSONObject[array.size()];
		HashMap<Double, JSONObject> tickerQuoteListMap = new HashMap<>();
        List<JSONObject> list = new ArrayList<JSONObject>();
        for(int i=0; i<array.size(); i++) {
            JSONObject jobject = (JSONObject) array.get(i);
            list.add(jobject);
        }
        for(int i=0;i<array.size();i++) {
        	values[i]=(JSONObject) array.get(i);
        	double percChangeInPrice= (double) values[i].get("fiftyDayAverageChangePercent");
        	tickerQuoteListMap.put(percChangeInPrice, list.get(i));
        }
        TreeMap<Double, List> Stock_map=Get_Top_Stocks(tickerQuoteListMap);
        int no_of_stocks=5;
		for (Entry<Double, List> entry : Stock_map.entrySet()) {
			if(no_of_stocks!=0) {
				values[no_of_stocks]=(JSONObject) entry.getValue();
				System.out.println( "Symbol : "+ values[no_of_stocks].get("symbol"));
				System.out.println("Market Price : "+ values[no_of_stocks].get("regularMarketPrice"));
				System.out.println("fiftyDayAverageChangePercent : "+values[no_of_stocks].get("fiftyDayAverageChangePercent"));
				System.out.println();
				System.out.println("==================================================================");
				System.out.println();
				no_of_stocks--;
			}
			else {
				break;
			}
		
	}

	/*	for(int i=0;i<array.size();i++) {
			values[i]=(JSONObject) array.get(i);
			System.out.println((i+1) + ": Symbol : "+ values[i].get("symbol"));
			System.out.println("Market Price : "+ values[i].get("regularMarketPrice"));
			System.out.println("Market Cap : "+values[i].get("marketCap"));
			System.out.println("==================================================================");
			System.out.println();
			//arrayOfJson[i]= new DisplayStock(values[i].get("symbol").toString() , (double) values[i].get("regularMarketPrice"),(double) values[i].get("fiftyDayAverage") );
	
		}*/

        
	}

}





