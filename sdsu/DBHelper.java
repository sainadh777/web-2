package sdsu;

import java.util.*;
import java.sql.*;


    





public class DBHelper implements java.io.Serializable {
    private static String connectionURL = "jdbc:mysql://opatija:3306/jadrn000?user=jadrn052&password=stopper";               
    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    public DBHelper() {}    
    
    public static Vector runQuery(String s) {
        String answer = "";
        Vector<String []> answerVector = null;




		

	try {
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    connection = DriverManager.getConnection(connectionURL);
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery(s);
        
            ResultSetMetaData rsmd = resultSet.getMetaData();
			answerVector = new Vector<String []>();
            
           
            while(resultSet.next()) {
                String [] row = new String[rsmd.getColumnCount()];
                for(int i=0; i < rsmd.getColumnCount(); i++)
                row[i] = resultSet.getString(i+1);
            answerVector.add(row);       
		}
	}
	catch(Exception e) {
	    e.printStackTrace();
	}
//////////////////////////////////////////////////////////////////////////////////            
   // The finally clause always runs, and closes resources if open.
   // DO NOT OMIT THIS!!!!!!
   finally {
            try {
                if(resultSet != null)
                    resultSet.close();
                if(statement != null)
                    statement.close();
                if(connection != null)                   
            	    connection.close();
                }
            catch(SQLException e) {
                answer += e;
                }
        //////////////////////////////////////////////////////////////////////////
        }
        return answerVector;
    }   


public static Vector<String []> doQuery(String s) {
        String user = "jadrn052";
        String password = "stopper";
        String database = "jadrn052";
	String connectionURL = "jdbc:mysql://opatija:3306/" + database +
            "?user=" + user + "&password=" + password;		
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
        Vector<String[]> v = new Vector<String[]>();        

	try {
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    connection = DriverManager.getConnection(connectionURL);
	    statement = connection.createStatement();
	    resultSet = statement.executeQuery(s);
        
            ResultSetMetaData md = resultSet.getMetaData();
            int numCols = md.getColumnCount();
           
            while(resultSet.next()) {
                String [] tmp = new String[numCols];
                for(int i=0; i < numCols; i++)
                    tmp[i] = resultSet.getString(i+1);  // resultSet getString is 1 based
                v.add(tmp);                
                    }
		}
		catch(Exception e) {
			e.printStackTrace();
			}
// IMPORTANT, you must make sure that the resultSet, statement and connection
// are closed, or a memory leak occurs in Tomcat.            
        finally {
            try {
                resultSet.close();
                statement.close();                
        		connection.close();
                }
            catch(SQLException e) {}  // don't do anything if the connection is not open.
        }
        return v;
    }
	
}


/*
  
// This method is appropriate for DB operations that do not return a result 
// set, but rather the number of affected rows.  This includes INSERT and UPDATE    
    public static String doUpdate(String s) {
		String user = "jadrn052";
        String password = "stopper";
        String database = "jadrn052";
		String connectionURL = "jdbc:mysql://opatija:3306/" + database +
            "?user=" + user + "&password=" + password;	
		
		
		Connection connection = null;
		Statement statement = null;
        int result = -1;   

	try {
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    connection = DriverManager.getConnection(connectionURL);
	    statement = connection.createStatement();  
        result = statement.executeUpdate(s);
            }
	catch(Exception e) {
	    e.printStackTrace();
	    }
// IMPORTANT, you must make sure that the statement and connection
// are closed, or a memory leak occurs in Tomcat.            
        finally {
            try {
 
                    statement.close();
                            
            	    connection.close();
                }
            catch(SQLException e) { }
				
			
        }
        return result;
    }
   

/*
    public static String getQueryResultTable(Vector<String []> v) {
        StringBuffer toReturn = new StringBuffer();
    toReturn.append("<table>");
    for(int i=0; i < v.size(); i++) {
        String [] tmp = v.elementAt(i);
        toReturn.append("<tr>");        
        for(int j=0; j < tmp.length; j++)
            toReturn.append("<td>" + tmp[j] + "</td>");
        toReturn.append("</tr>");
        }
    toReturn.append("</table>"); 
    return toReturn.toString();
    } 
    
  */