import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;
import sdsu.*;


public class SearchProducts extends HttpServlet {
        private ServletContext context=null;
    private RequestDispatcher dispatcher = null;
        private String toDo = "";  
          
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        
       
        String keyword=request.getParameter("keyword");
        HttpSession session = request.getSession(false);
        String responseString="", colseparator="|", rowseparator="||", sku;

        String query="select sku,category.name,vendor.name,vendorModel, retail from product, vendor, category where vendor.name like '%"+keyword+"%' and product.venID=vendor.id and product.catID=category.id order by sku;";
        Vector<String []> answer = DBHelper.doQuery(query);
        
        if(answer.isEmpty())
        {
        query="select sku, category.name,vendor.name,vendorModel, retail from product, vendor, category where vendorModel like '%"+keyword+"%' and product.venID=vendor.id and product.catID=category.id order by sku;";
        answer = DBHelper.doQuery(query);            
        }   

        if(answer.isEmpty()) 
        {
          query="select sku, category.name,vendor.name,vendorModel,retail from product, vendor, category where category like '%"+keyword+"%' and product.venID=vendor.id and product.catID=category.id order by sku;";
         answer = DBHelper.doQuery(query);
        }        
           
          if(!answer.isEmpty())
          {
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
               int qt= Integer.parseInt(answer.get(0)[0]);
                if(qt>0)
                {
                    responseString+="in stock";
                }
                 else
                {
                     responseString+="more on the way";
                }            
         }
         else
         {
            responseString+="coming soon";
         }
         responseString+=rowseparator;
     }
        responseString = responseString.substring(0, responseString.length()-2);
                     
    }

      response.setContentType("text/plain");  
         response.setCharacterEncoding("UTF-8"); 
         response.getWriter().write(responseString); 

    }   
}