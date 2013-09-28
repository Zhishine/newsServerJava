package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

import util.DatabaseManager;
import util.ShareDataManager;

public class AddProduct extends HttpServlet {
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		PrintWriter out=response.getWriter();
	  //URLDecoder.decode(request.getParameter("参数名"),"UTF-8");
	  boolean isEdit=Boolean.valueOf(request.getParameter("isEdit"));
	  String numIdStr=request.getParameter("numId");
	  Long numId=Long.valueOf(numIdStr) ;
	  String title=new String(request.getParameter("title").getBytes("ISO-8859-1"), "UTF-8");
	  String pcClickUrl=request.getParameter("pcClickUrl");
	  String mobileClickUrl=request.getParameter("mobileClickUrl");
	  String imageUrl=request.getParameter("imageUrl");
	  float imageRate=Float.valueOf(request.getParameter("imageRate"));
	  float newPrice=Float.valueOf(request.getParameter("newPrice")); 
	  float oldPrice=Float.valueOf(request.getParameter("oldPrice")); 
	  int categoryId=Integer.valueOf(request.getParameter("categoryId"));
	  if(numId>=0)
	  {
		 // if(!isEdit)
		 
		  Date date=new Date();
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		  String dd =format.format(date);
		 try {
		 if(!isEdit)
		 {
			 String existSql="select count(numId) from product where numId="+numId;
			 ResultSet existResult=DatabaseManager.getInstance().getStatement().executeQuery(existSql);
			 if(existResult.next())
			 {
				if(existResult.getInt(1)>0)
				{
					 out.write("2");
					return;
				}
			 }
	     String insertSql="insert into product(numId,title,pcClickUrl,mobileClickUrl,imageUrl,imageRate,newPrice,oldPrice,time)" +
				 		" values("+numId+",'"+title+"','"+pcClickUrl+"','"+mobileClickUrl+"','"+imageUrl+"',"+imageRate+","+newPrice+","+oldPrice+",'"+dd+"')";
		 DatabaseManager.getInstance().getStatement().execute(insertSql);
		 String insertCategorySql="insert into categoryProductFK(categoryId,numId) values("+categoryId+","+numId+")";
		 DatabaseManager.getInstance().getStatement().execute(insertCategorySql);
		 
			 Jedis jedis=ShareDataManager.getInstance().getJedis();
			 String numIdKey=ShareDataManager.getInstance().getProductKey(numId);
			 jedis.set(numIdKey+":title",title);
			 jedis.set(numIdKey+":pcClickUrl",pcClickUrl);
			 jedis.set(numIdKey+":mobileClickUrl",mobileClickUrl);
			 jedis.set(numIdKey+":imageUrl",imageUrl);
			 jedis.set(numIdKey+":imageRate",String.valueOf(imageRate));
			 jedis.set(numIdKey+":newPrice",String.valueOf(newPrice));
			 jedis.set(numIdKey+":oldPrice",String.valueOf(oldPrice));
			 jedis.lpush(ShareDataManager.NUMIDKEY,String.valueOf(numId));
			 String key=ShareDataManager.getInstance().getCategoryProductKey(categoryId);
			 jedis.lpush(key, String.valueOf(numId));
			 ShareDataManager.getInstance().returnJedis(jedis);
		 
		 out.write("1");
		 return;
		 }
		 else
		 {
	     String updateSql="update product set title='"+title+"', pcClickUrl='"+pcClickUrl+"'," +
	     		" mobileClickUrl='"+mobileClickUrl+"', imageUrl='"+imageUrl+"', imageRate="+imageRate+", newPrice="+newPrice+", " +
	     				"oldPrice="+oldPrice+", time='"+dd+"' where numId="+numId;
		 DatabaseManager.getInstance().getStatement().execute(updateSql);
		 out.write("1");
		 return;
		 }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			out.write("3");
			e.printStackTrace();
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			out.write("3");
			e.printStackTrace();
			return;
		}
	  }
	 }
}
