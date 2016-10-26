package cn.com.sims.common;

import java.util.Random;

public class RandomId { 
    private Random random;  
    private String table; 
    public RandomId() {  
        random = new Random();  
        table = "0123456789";  
    }  
    public  String randomId(String id) {  
	 		String ret = null;
	        String num = id;
	        	if(id.length()<=5){
	        	    num = String.format("%05d", Integer.parseInt(id));
	        		}
	        
	        int key = random.nextInt(10),   
	            seed = random.nextInt(100);  
	        Caesar caesar = new Caesar(table, seed);  
	        num = caesar.encode(key, num);  
	        ret = num   
	            + String.format("%01d", key)   
	            + String.format("%02d", seed);   
	        return ret;  
    }  
//    public static void main(String[] args) {  
//        RandomId r = new RandomId();  
//        for (int i = 0; i < 30; i += 1) {  
//            System.out.println(r.randomId(i));  
//        }  
//    }  
}
