package emp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/InsertEmpSev")
public class InsertEmpServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public InsertEmpServ() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpDAO dao = new EmpDAO();
		String firstName =request.getParameter("firstName");
		String lastName =request.getParameter("lastName");
		String email =request.getParameter("email");
		String hireDate =request.getParameter("hireDate");
		String salary =request.getParameter("salary");
		String jobId =request.getParameter("jobId");
		Employee emp = new Employee();
		emp.setFirstName(firstName);
		emp.setLastName(lastName);
		emp.setEmail(email);
		emp.setHireDate(hireDate);
		emp.setJobId(jobId);
		emp.setSalary(Integer.parseInt(salary));
		System.out.println(emp);
		dao.insertEmp(emp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
