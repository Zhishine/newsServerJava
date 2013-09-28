package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

import util.DatabaseManager;
import util.ShareDataManager;
import news.modal.Product;

public class DeleteProduct extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
	  long numId=Long.valueOf(request.getParameter("numId")) ;
	  //long numId=Long.valueOf(request.getParameter("numId")) ;
	  if(numId>0)
	  {
		 String sql="delete from product where numId="+numId;
		 String delCategoryProductSql="delete from categoryProductFK where numId="+numId;
		 try {
		 DatabaseManager.getInstance().getStatement().execute(sql);
		 DatabaseManager.getInstance().getStatement().execute(delCategoryProductSql);
		 PrintWriter out=response.getWriter();
		 out.write("true");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

		  String numIdKey=ShareDataManager.getInstance().getProductKey(Long.valueOf(numId));
	      Jedis jedis=ShareDataManager.getInstance().getJedis();
	      jedis.del(numIdKey+":name");
	      jedis.del(numIdKey+":pcClickUrl");
	      jedis.del(numIdKey+":mobileClickUrl");
	      jedis.del(numIdKey+":imageUrl");
	      jedis.del(numIdKey+":oldPrice");
	      jedis.del(numIdKey+":newPrice");
	      jedis.del(numIdKey+":imageRate");
	      ShareDataManager.getInstance().returnJedis(jedis);
	  
	 }
}
