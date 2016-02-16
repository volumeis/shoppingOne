package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("UpdateProductAction - execute Start...");
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service=new ProductServiceImpl();
		
		Product prod = service.getProduct(prodNo); 
				
	//	System.out.println("updateVO : " + prodVO + " : " + prodNo);
		System.out.println("변경후프로도 : " + prod);
		
		prod.setProdName(request.getParameter("prodName"));
		prod.setProdDetail(request.getParameter("prodDetail"));
		prod.setManuDate(request.getParameter("manuDate"));
		prod.setPrice(Integer.parseInt(request.getParameter("price")));
		
		System.out.println("변경전프로도 : " + prod);
		service.updateProduct(prod);
		
		String menu = "ok";
		request.setAttribute("menu", menu);
		
		System.out.println("UpdateProductAction - execute End...");
		
		//return "forward:/product/confirmDetailProductView.jsp";
		return "redirect:/getProduct.do?prodNo="+prodNo+"&menu="+menu;
	}

}

/* 	
		if(sessionId.equals(userId)){
			session.setAttribute("user", userVO);
		}
		
		return "redirect:/getUser.do?userId="+userId;
	*/
