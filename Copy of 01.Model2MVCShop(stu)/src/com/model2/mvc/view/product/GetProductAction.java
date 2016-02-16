package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("Action - GetProductAction Start...");

		int prodNo= Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service=new ProductServiceImpl();
		Product vo=service.getProduct(prodNo);
		
		//parameter menu is condition
		String menu = request.getParameter("menu");
		//System.out.println(menu);
		
		request.setAttribute("vo", vo);
				
		System.out.println("Action - GetProductAction End...");
		if (menu.equals("manage")) {
			return "forward:/updateProductView.do?prodNo="+prodNo+"&menu=manage";
		} else if (menu.equals("ok")){
			return "forward:/product/confirmDetailProductView.jsp";
		} else {
			return "forward:/product/purchaseProductView.jsp";
		} 
	}
}