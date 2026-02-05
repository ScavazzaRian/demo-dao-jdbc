package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDAO;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		System.out.println("=== Test 1: Department Create ===");
		Department dp = new Department(null, "Teste");
		DepartmentDAO dpao = DaoFactory.createDepartmentDao();
		dpao.insert(dp);
		
		System.out.println("=== Test 2: Department Delete ===");
		dpao.deleteById(5);
		
		System.out.println("=== Test 3: Department Find All ===");
		List<Department> list = dpao.findAll();
		for (Department d : list) {
			System.out.println(d);
		}
		
		System.out.println("=== Test 4: Department Find By ID ===");
		Department dp2 = dpao.findById(1);
		System.out.println(dp2);
		
		System.out.println("=== Test 5: Department Update ===");
		dp2.setName("NOVO NOME");
		dpao.update(dp2);
	}

}
