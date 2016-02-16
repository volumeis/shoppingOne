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
		
		//��ǰ��ȣ(service���� ������)
		//productVO.setProdNo(1);
		//��ǰ��
		product.setProdName(request.getParameter("prodName"));
		//��ǰ������
		product.setProdDetail(request.getParameter("prodDetail"));
		//��������
		product.setManuDate(request.getParameter("manuDate"));
		//����
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		
		//productVO.setProTranCode(request.getParameter(""));
		//������� (service���� ������)
		//productVO.setRegDate(request);
		ProductService service=new ProductServiceImpl();
		service.addProduct(product);
		
		request.setAttribute("product", product);
		System.out.println("--AddProduct" + product);
		
		System.out.println("Action - AddProductAction Start...");

		return "forward:/product/addProduct.jsp";
	}                       
}
