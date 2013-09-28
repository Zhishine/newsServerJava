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

import news.modal.News;

import util.DatabaseManager;

import com.google.gson.Gson;

public class SelectNews extends HttpServlet {
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
			 sql="select * from news n,categoryNewsFK f where f.newsId=n.id and f.categoryId="+categoryId+" limit "+start+","+pageSize;
			 break;
		 case 1:
			 int id=Integer.valueOf(request.getParameter("id"));
		     sql="select * from news where id="+id;	
			 break;
		 case 2:
			  String key=new String(request.getParameter("key").getBytes("ISO-8859-1"), "UTF-8");
		     sql="select * from news where title like '%"+key+"%'";	
			 break;
		 }
	
		 try {
		 ResultSet result=DatabaseManager.getInstance().getStatement().executeQuery(sql);
		 ArrayList<News> list=new ArrayList<News>();
		 while(result.next())
		 {
			 News news=new News();
			 news.setId(result.getInt("id"));
			 news.setTitle(URLEncoder.encode(result.getString("title"),"utf-8"));
			 news.setTitleImageUrl(result.getString("titleImageUrl"));
			 news.setRedirectUrl(result.getString("redirectUrl"));
			 if(result.getInt("isWebPage")==0)
			 news.setIsWebPage(true);
			 else
			 news.setIsWebPage(false);
			 news.setDescription(URLEncoder.encode(result.getString("description"),"utf-8"));
			 news.setExt1(result.getString("ext1"));
			 news.setExt2(result.getString("ext2"));
			 news.setExt3(result.getString("ext3"));
			 
			 list.add(news);
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
