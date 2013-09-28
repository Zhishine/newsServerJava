package util;

import java.util.Date;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;



public class ShareDataManager {
	private static ShareDataManager instance;
	public static  String CATEGORYNEWSKEY="news:category:new:%d";
	 public static  String CATEGORYPRODUCTKEY="news:category:product:%d";
    public static  String CATEGORYIMAGEKEY="news:category:image:%d";
    public static  String CATEGORYKEY="news:category:%d";
    public static  String RYPRODUCTKEY="news:product:%d";
    public static  String APPKEY="news:app:%d";
    public static  String NUMIDKEY="news:numId";
    public static  String APPIDKEY="news:app:id";
    public static  String APPLISTKEY="news:app:list";
    public static String NEWSKEY="news:%d";
    public static String IMAGEKEY="image:%d";
    public Boolean mCacheIsRealTime=true;
    private String IP="http://zhong.hontek.com.cn/";
    //private String IP="http://192.168.1.115:8080/news/";
    private static JedisPool pool;
    
     public Jedis getJedis()
     {
    	 return pool.getResource();
     }
     
     public void returnJedis(Jedis obj)
     {
    	 pool.returnResource(obj);
     }
     
     public String getIP()
     {
    	return IP;
     }
	 public static ShareDataManager getInstance()
	 {
		 if(instance==null)
		 {
			 instance=new ShareDataManager();
		 }
		 return instance;
	 }
	  ShareDataManager()
	 {
		 JedisPoolConfig config = new JedisPoolConfig();
	        config.setMaxActive(5000);
	        config.setMaxIdle(5000);
	        config.setMaxWait(2000);
	        config.setTestOnBorrow(true);
	        pool = new JedisPool(config, "127.0.0.1");
	       
	 }
	public String getCategoryProductKey(int categoryId)
	{
			 return String.format(CATEGORYPRODUCTKEY, categoryId);
    }
	 public String getCategoryKey(int categoryId)
	 {
		 return String.format(CATEGORYKEY, categoryId);
	 }
	 public String getCategoryNewsKey(int categoryId)
	 {
			 return String.format(CATEGORYNEWSKEY, categoryId);
	 }
	 public String getCategoryImageKey(int categoryId)
	 {
		 return String.format(CATEGORYIMAGEKEY, categoryId);
	 }
	 public String getNewsKey(int newsId)
	 {
		 return String.format(NEWSKEY, newsId);
	 }
	 public String getImageKey(int imageId)
	 {
		 return String.format(IMAGEKEY, imageId);
	 }
	 public String getAppKey(int id)
	 {
		 return String.format(APPKEY, id);
	 }
	 public String getProductKey(long numId)
	 {
		 return String.format(RYPRODUCTKEY, numId);
	 }
}
