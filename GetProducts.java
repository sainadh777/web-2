

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;
import sdsu.*;



public class GetProducts extends HttpServlet {
        private ServletContext context=null;
    private RequestDispatcher dispatcher = null;
        private String toDo = "";  
          
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        
       
        HttpSession session = request.getSession(false);
        String responseString="";
        
        
            String sku, colseparator="|", rowseparator="||";

            String query="select sku,category.name,vendor.name,vendorModel,retail,quantity from product,category,vendor where product.venID=vendor.id and product.catID=category.id order by sku;";
            Vector<String []> answer = DBHelper.doQuery(query);            
           
             for(String[] row : answer)
             {

                sku=row[0];   
                for(String column: row)
                {
                    responseString+=column+colseparator;
                }

            query="select on_hand_quantity from on_hand where sku='"+sku+"';";
            answer= DBHelper.doQuery(query);
            if(answer.size()>0)
            {
               //int qt= Integer.parseInt(result.get(0)[0]);
                responseString+=answer.get(0)[0];
         }                  
         
         else
         {
            responseString+="null";
         }
         responseString+=rowseparator;
     }
         responseString = responseString.substring(0, responseString.length()-2);
         response.setContentType("text/plain");  
         response.setCharacterEncoding("UTF-8"); 
         response.getWriter().write(responseString);              
    }    
}