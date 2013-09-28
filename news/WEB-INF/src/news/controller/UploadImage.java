package news.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import util.DatabaseManager;
import util.ShareDataManager;

public class UploadImage extends HttpServlet {
	private String ABLUMPATH="image/ablum/";
	private String ADPATH="image/ad/";
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	 { 
	  String type=request.getParameter("type");
	  String rootPath=null;
	  String filePath=null;
	  File file=null;
	  BufferedImage srcImage=null;
	  InputStream inputStream=null;
	  FileOutputStream output=null;
	  JPEGImageEncoder encoder=null;
	  PrintWriter outResponse=null;
	  String imgPath=null;
	  if(type.equalsIgnoreCase("ablum"))
	  {
	      
		  Date date=new Date();
		  long ticks=date.getTime();
		  rootPath=this.getServletContext().getRealPath("/");
		  filePath=rootPath+ABLUMPATH+ticks+".jpg";
		  file=new File(filePath);
		  file.createNewFile();
		  output = new FileOutputStream(file);
		    
		  //int length=0;
		  //byte[] buffer=new byte[4096];
		  inputStream=request.getInputStream();
		  srcImage= ImageIO.read(inputStream);
		 // ImageIO.write(srcImage, "JPEG", file);
	      encoder=JPEGCodec.createJPEGEncoder(output);
	 
	      encoder.encode(srcImage);
//		  while ((index=in.read(buffer))>0) {
//	          length+=index;
//		      out.write(buffer, 0, index);
//		     }
	      //inputStream.close();
	      output.flush();
	      output.close();
	      //out.close();
	      //in.close();
	      outResponse=response.getWriter();
	      imgPath=ShareDataManager.getInstance().getIP()+ABLUMPATH+ticks+".jpg";
	      outResponse.write(imgPath);
	  }
	  else
	  {
		  String orderStr=request.getParameter("order");
		  int order=Integer.valueOf(orderStr);
		  rootPath=this.getServletContext().getRealPath("/");
		  filePath=rootPath+ADPATH+order+".jpg";
		  file=new File(filePath);
		  if(file.exists())
			  file.delete();
		  file.createNewFile();
		  output = new FileOutputStream(file);
		    
		  //int length=0;
		  //byte[] buffer=new byte[4096];
		  inputStream=request.getInputStream();
		  srcImage= ImageIO.read(inputStream);
		 // ImageIO.write(srcImage, "JPEG", file);
	      encoder=JPEGCodec.createJPEGEncoder(output);
	      encoder.encode(srcImage);
//		  while ((index=in.read(buffer))>0) {
//	          length+=index;
//		      out.write(buffer, 0, index);
//		     }
	      //inputStream.close();
	      output.flush();
	      output.close();
	      //out.close();
	      //in.close();
	      outResponse=response.getWriter();
	      imgPath=ShareDataManager.getInstance().getIP()+ADPATH+order+".jpg";
	      outResponse.write(imgPath);
	  }
	 
	 }
}
