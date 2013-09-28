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
import news.modal.Category;

import com.google.gson.Gson;

public class SelectCategory extends HttpServlet {
	 public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		 String sql="select * from category order by id desc";
		 try {
		 ResultSet result=DatabaseManager.getInstance().getStatement().executeQuery(sql);
		 ArrayList list=new ArrayList();
		 while(result.next())
		 {
			 Category category=new Category();
			 category.setId(result.getInt("id"));
			 String name=URLEncoder.encode(result.getString("name"),"utf-8");
			 category.setName(name);
			 list.add(category);
		 }
		 if(list.size()>0)
		 {
			 Gson gson=new Gson();
			 String gsonStr=gson.toJson(list);
			 PrintWriter out=response.getWriter();
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
