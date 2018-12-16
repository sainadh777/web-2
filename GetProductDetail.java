import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Vector;
import java.util.*;
import sdsu.*;


public class GetProductDetail extends HttpServlet {
        private ServletContext context=null;
        private RequestDispatcher dispatcher = null;
        private String toDo = "";  
          
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
        
         String toDo = "/WEB-INF/jsp/product.jsp";
         String sku = request.getParameter("sku");
         String query="select sku, category.name,vendor.name,vendorModel, description, features, retail ,quantity from product, category,vendor where sku='"+sku+"' and vendor.id = product.venID and category.id = product.catID order by sku;";
         
         Vector <String[]> result= DBHelper.doQuery(query);
        
         if(result.isEmpty())
         {
           return;
           //response.sendRedirect("http://jadran.sdsu.edu/jadrn052/proj2.html");
         }
         
            String[] array = result.get(0);
          List<String> list= new ArrayList<String>(Arrays.asList(array));

         query="select on_hand_quantity from on_hand where sku='"+sku+"';";
          result = DBHelper.doQuery(query);
           
            if(!result.isEmpty())
            {
               int qt= Integer.parseInt(result.get(0)[0]);
                if(qt>0)
                {
                    list.add("In Stock");
                }
                 else
                {
                     list.add("More on the way");
                }            
            }
            else
            {
                list.add("Coming Soon");
            } 
        


        request.setAttribute("product", list); 
        context = getServletContext();      
        dispatcher  = request.getRequestDispatcher(toDo); 
        dispatcher.forward(request, response);              
    }    
}