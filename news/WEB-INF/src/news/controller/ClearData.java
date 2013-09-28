package news.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import redis.clients.jedis.Jedis;
import util.DatabaseManager;
import util.ShareDataManager;

public class ClearData extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		 try {
		   int categoryId=Integer.valueOf(request.getParameter("categoryId"));
		   Jedis jedis=ShareDataManager.getInstance().getJedis();
		   String categoryProductKey=ShareDataManager.getInstance().getCategoryProductKey(categoryId);
		   jedis.del(categoryProductKey);
		   String categoryProductSql="select * from categoryProductFK where categoryId="+categoryId;
			ResultSet productResult=DatabaseManager.getInstance().getStatement().executeQuery(categoryProductSql);
			while(productResult.next()){
			jedis.lpush(categoryProductKey, productResult.getString("numId"));
			}
		   String categoryNewsKey=ShareDataManager.getInstance().getCategoryNewsKey(categoryId);
		   jedis.del(categoryNewsKey);
		   String categoryNewsSql="select * from categoryNewsFK where categoryId="+categoryId;
		   ResultSet newsResult=DatabaseManager.getInstance().getStatement().executeQuery(categoryNewsSql);
		   while(newsResult.next()){
			jedis.lpush(categoryNewsKey, newsResult.getString("newsId"));
			}
		   String categoryImageKey=ShareDataManager.getInstance().getCategoryImageKey(categoryId);
		   jedis.del(categoryImageKey);
		   String categoryImageSql="select * from categoryImageFK where categoryId="+categoryId;
		   ResultSet imageResult=DatabaseManager.getInstance().getStatement().executeQuery(categoryImageSql);
		  while(imageResult.next()){
			jedis.lpush(categoryImageKey, imageResult.getString("imageId"));
			}
			ShareDataManager.getInstance().returnJedis(jedis);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	 }
}
