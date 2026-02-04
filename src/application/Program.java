package application; 

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		System.out.println("=== TEST 1: SELLER FINDBYID ===");
		SellerDAO sellerDAO = DaoFactory.createSellerDao();
		Seller seller = sellerDAO.findById(3);
		System.out.println(seller);
		
		System.out.println("=== TEST 2: SELLER FINDBYDEPARTMENT ===");
		Department department = new Department(2, null);
		List<Seller> list = sellerDAO.findByDepartment(department);
		for (Seller obj : list) {
			System.out.println(obj);
		}
	}

}
