package application; 

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		Department dp = new Department(1, "Books");
		System.out.println(dp);

		SellerDAO sellerDAO = DaoFactory.createSellerDao();
		
		Seller seller = sellerDAO.findById(3);
		
		System.out.println(seller);
	}

}
