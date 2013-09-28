package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DatabaseManager;
import news.modal.Product;

import com.google.gson.Gson;

public class SelectProduct extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		 int type=Integer.valueOf(request.getParameter("type"));
		 String sql=null;
		 switch(type)
		 {
		 case 0:
			 int pageIndex=Integer.valueOf(request.getParameter("pageIndex"));
			 int pageSize=Integer.valueOf(request.getParameter("pageSize"));
			 int categoryId=Integer.valueOf(request.getParameter("categoryId"));
			 int start=pageIndex*pageSize;
			 sql="select * from product p,categoryProductFK f where f.numId=p.numId and f.categoryId="+categoryId+" order " +
			 		"by time desc limit "+start+","+pageSize;
			 break;
		 case 1:
			 long numId=Long.valueOf(request.getParameter("numId"));
		     sql="select * from product where numId="+numId;	
			 break;
		 case 2:
			  String key=new String(request.getParameter("key").getBytes("ISO-8859-1"), "UTF-8");
		     sql="select * from product where title like '%"+key+"%'";	
			 break;
		 }
	
		 try {
		 ResultSet result=DatabaseManager.getInstance().getStatement().executeQuery(sql);
		 ArrayList list=new ArrayList();
		 while(result.next())
		 {
			 Product product=new Product();
			 product.setNumId(result.getLong("numId"));
			 product.setTitle(URLEncoder.encode(result.getString("title"),"utf-8"));
			 product.setImageRate(result.getFloat("imageRate"));
			 product.setImageUrl(result.getString("imageUrl"));
			 product.setMobileUrl(result.getString("mobileClickUrl"));
			 product.setPCUrl(result.getString("pcClickUrl"));
			 product.setNewPrice(result.getFloat("newPrice"));
			 product.setOldPrice(result.getFloat("oldPrice"));
			 list.add(product);
		 }
		 if(list.size()>0)
		 {
			 Gson gson=new Gson();
			 String gsonStr=gson.toJson(list);
			 int length=gsonStr.length();
			 PrintWriter out=response.getWriter();
			 response.addHeader("content-length",String.valueOf(length));
			 out.write(gsonStr);
		 }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }	
}
