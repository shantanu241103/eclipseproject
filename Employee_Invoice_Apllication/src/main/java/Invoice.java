

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Invoice
 */
@WebServlet("/Invoice")
public class Invoice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Invoice() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		try
		{
			int empid=Integer.parseInt(request.getParameter("uid"));
			String empName=request.getParameter("uname");
			float salary=Float.parseFloat(request.getParameter("usalary"));
			
			
			float ta=0.0f,da=0.0f,hra=0.0f,pf=0.0f;
			float gross_salary,net_salary;
			
			if(salary<30000)
			{
				ta=(salary*7)/100;
				da=(salary*9)/100;
				hra=(salary*11)/100;
				pf=(salary*17)/100;
				
			}
			else if(salary>=30000 && salary<50000)
			{
				ta=(salary*11)/100;
				da=(salary*13)/100;
				hra=(salary*17.25f)/100;
				pf=(salary*21.75f)/100;
			}
			else
			{
				ta=(salary*17)/100;
				da=(salary*18.25f)/100;
				hra=(salary*21.22f)/100;
				pf=(salary*32.25f)/100;
			}
			gross_salary=salary+ta+da+hra;
			net_salary=gross_salary-pf;
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","shdb","shdb");
			PreparedStatement ps=con.prepareStatement("insert into empInfo values(?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, empid);
			ps.setString(2, empName);
			ps.setFloat(3, salary);
			ps.setFloat(4, ta);
			ps.setFloat(5,da);
			ps.setFloat(6, hra);
			ps.setFloat(7,pf);
			ps.setFloat(8, gross_salary);
			ps.setFloat(9, net_salary);
			
			int i=ps.executeUpdate();
			out.println(i+" New User Registered Successfully");
			con.close();
			
		}
		
			
		
		catch(Exception ex)
		{
			out.println(ex);
		}
	}

}
