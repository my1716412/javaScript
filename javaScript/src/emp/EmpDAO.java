package emp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DAO;

//이 내용이 반복된다.
public class EmpDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	String login = "select id, passwd from user_login where id = ? and basswd =? ";
	
	String welcom = "select name from user_login where id = ? and passwd = ?";
	
	
	public Employee getEmployee(int empId) {
		conn = DAO.getConnect();
		String sql = "select * from emp_temp where employee_id = ?";
//		String sql1 = "{? = call get_dept_name(?)}";
		Employee emp = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empId);
			//1번째 파라미터 값을 empId로 대체하라
			rs = pstmt.executeQuery();
			           //select만
	//		CallableStatement cstmt = conn.prepareCall(sql1);
	//		cstmt.registerOutParameter(1, java.sql.Types.VARCHAR);
	//		cstmt.setInt(2, empId);
	//		cstmt.execute();
	//		String deptName = cstmt.getString(1);
			
			if(rs.next()) {
				emp =  new Employee();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setHireDate(rs.getString("hire_date"));
				emp.setSalary(rs.getInt("salary"));
	//			emp.setDeptName(deptName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        return emp;
	}
	
	public void insertEmpProc(Employee emp) {
		conn = DAO.getConnect();
		String sql = "{call add_new_member(?, ?, ?, ?, ?, ?)}";
		try {
			CallableStatement cstmt = conn.prepareCall(sql);
			cstmt.setString(1, emp.getFirstName());
			cstmt.setString(2, emp.getLastName());
			cstmt.setString(3, emp.getJobId());
			cstmt.setInt(4, emp.getSalary());
			cstmt.setString(5, emp.getHireDate());
			cstmt.setString(6, emp.getEmail());
			cstmt.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void insertEmp(Employee emp) {
		conn = DAO.getConnect();
		String sql = "insert into emp_temp(employee_id, first_name, last_name"
				     + ", email, job_id, hire_date, salary)" 
	                 + " values (employees_seq.nextval,?,?,?,?,?,?)";
		             //sql로 불러올때는 순서가 0부터 시작이 아닌 1부터 시작
		int rCnt = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++rCnt,  emp.getFirstName());
			pstmt.setString(++rCnt,  emp.getLastName());
			pstmt.setString(++rCnt, emp.getEmail());
			pstmt.setString(++rCnt, emp.getJobId());
			pstmt.setString(++rCnt,  emp.getHireDate());
			pstmt.setInt(++rCnt, emp.getSalary());
		             //숫자 직접 입력보다 단항연산자 쓰는 것이 더 안헷갈린다
			int r = pstmt.executeUpdate();
			System.out.println(r+"건이 입력되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Employee> getEmpList(){
		List<Employee> list = new ArrayList<>();
		conn = DAO.getConnect();
		String sql = "select * from emp_temp";
		Employee emp = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				emp = new Employee();
				emp.setEmployeeId(rs.getInt("employee_id"));
				                            //DB talbe colum name
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setHireDate(rs.getString("hire_date"));
				emp.setEmail(rs.getString("email"));
				emp.setSalary(rs.getInt("salary"));
				emp.setJobId(rs.getString("job_id"));
				list.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	public void updateEmp(Employee emp) {
		conn = DAO.getConnect();
		String sql = "update emp_temp set Salary = ? where Employee_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  emp.getSalary());
			pstmt.setInt(2,  emp.getEmployeeId());
			int r = pstmt.executeUpdate();
			System.out.println(r + "건이 입력되었습니다.");
		} catch (SQLException e) {		
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void deleteEmp(int empId) {
		conn = DAO.getConnect();
		String sql = "delete from emp_temp where employee_id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, empId);
			int r = pstmt.executeUpdate();
			System.out.println(r + "건이 삭제됨.");
			//update, insert, delete
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
