package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("Action - AddProductAction Start...");

		Product product = new Product();
		
		//상품번호(service에서 가져옴)
		//productVO.setProdNo(1);
		//상품명
		product.setProdName(request.getParameter("prodName"));
		//상품상세정보
		product.setProdDetail(request.getParameter("prodDetail"));
		//제조일자
		product.setManuDate(request.getParameter("manuDate"));
		//가격
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		
		//productVO.setProTranCode(request.getParameter(""));
		//등록일자 (service에서 가져옴)
		//productVO.setRegDate(request);
		ProductService service=new ProductServiceImpl();
		service.addProduct(product);
		
		request.setAttribute("product", product);
		System.out.println("--AddProduct" + product);
		
		System.out.println("Action - AddProductAction Start...");

		return "forward:/product/addProduct.jsp";
	}                       
}
