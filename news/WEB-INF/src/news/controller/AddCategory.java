package news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DatabaseManager;

public class AddCategory extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	 {
		String name=new String(request.getParameter("name").getBytes("ISO-8859-1"), "UTF-8");
		
	  if(name!=null)
	  {
		  String sql=null;
		
		  sql="insert into category(name) values('"+name+"')";
		
		 try {
		 DatabaseManager.getInstance().getStatement().execute(sql);
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
	 }
}
