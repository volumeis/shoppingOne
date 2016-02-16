package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDAO {
	
	//Field
	
	//Constructor
	public ProductDAO() {
		
	}
	
	//Method
	public void insertProduct(Product prod) throws Exception{
		
		System.out.println("ProductDAO - insmertProduct Start...");
		
		Connection con = DBUtil.getConnection();

		String sql = "insert "
				+ "into PRODUCT "
				+ "values (seq_product_prod_no.NEXTVAL,?,?,?,?,?,SYSDATE)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, prod.getProdName());
		pStmt.setString(2, prod.getProdDetail());
		pStmt.setString(3, prod.getManuDate().replace("-", ""));
		pStmt.setInt(4, prod.getPrice());
		pStmt.setString(5, "파일없음");
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
		System.out.println("ProductDAO - insertProduct End...");
	}
	
	public Product findProduct(int prodNo) throws Exception{
		
		System.out.println("ProductDAO - findProduct Start...");
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT "
				+ "prod_no, prod_name, prod_detail, manufacture_day, price, image_file, reg_date"
				+ " FROM product WHERE prod_no=?";
		
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1,prodNo);

		ResultSet rs = pStmt.executeQuery();
		
		Product prod = null;
		while(rs.next()){
			prod = new Product();
			prod.setProdNo(rs.getInt("prod_no"));
			prod.setProdName(rs.getString("prod_name"));
			prod.setProdDetail(rs.getString("prod_detail"));
			prod.setManuDate(rs.getString("manufacture_day"));
			prod.setPrice(rs.getInt("price"));
			prod.setFileName(rs.getString("image_file"));
			prod.setRegDate(rs.getDate("reg_date"));
		}
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("ProductDAO - findProduct End...");
		
		return prod;
	}
	
	public Map<String,Object> getProductList(Search search) throws Exception {
		
		System.out.println("ProductDAO - getProductList Start...");
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT prod_no,  prod_name, prod_detail, manufacture_day, price, image_file, reg_date"
				+ " FROM product ";
		
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("") ) {
				sql += " WHERE prod_no LIKE '" + search.getSearchKeyword() + "'";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("") ) {
				sql += " WHERE prod_name LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("") ) {
				sql += " WHERE price='" + search.getSearchKeyword()	+ "'";
			}
		}
		sql += " ORDER BY prod_no";

		System.out.println("ProductDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);
		
		List<Product> list = new ArrayList<Product>();
		
		if (rs.next()) {
			Product product = new Product();
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));

			list.add(product);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);
		
		rs.close();
		pStmt.close();
		con.close();


		System.out.println("ProductDAO - getProductList End...");
	
		return map;
	}

	public void updateProduct(Product prod) throws Exception{
		
		System.out.println("ProductDAO - updateProduct Start...");
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE product "
				+ "SET PROD_NAME=?, PROD_DETAIL=?, PRICE=? "//, MANUFACTURE_DAY=?
				+ "where PROD_NO=?";
		
		System.out.println("--updateProduct : " + prod);
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		//상품명 		
		pStmt.setString(1, prod.getProdName());
		//상품상세정보
		pStmt.setString(2, prod.getProdDetail());
		//제조일자 		
		//pStmt.setString(3, prod.getManuDate());
		//가격 
		pStmt.setInt(3, prod.getPrice());
		//제품번호
		pStmt.setInt(4, prod.getProdNo());
		
		pStmt.executeUpdate();
		pStmt.close();
		con.close();
		
		System.out.println("ProductDAO - updateProduct End...");
		
	}
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
	
}
