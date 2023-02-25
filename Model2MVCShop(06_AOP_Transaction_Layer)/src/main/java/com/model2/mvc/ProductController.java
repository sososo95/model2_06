package com.model2.mvc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.ProductService;
import com.model2.mvc.service.domain.Product;


//==> 회원관리 Controller
@Controller
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
		System.out.println(this.getClass());
	}
	

	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}") 
	int pageSize;
	
	
	
	@RequestMapping("/addProduct.do") // 상품 추가
	public String addProduct( @ModelAttribute("product") Product product, Model model ) throws Exception {

		System.out.println("/addProduct.do");

		productService.insertProduct(product);
		
		model.addAttribute("pro", product);
		
		return "forward:/product/productView.jsp";
	}
	
	
	@RequestMapping("/getProduct.do") // 상품 정보 확인
	public String getProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
		
		System.out.println("/getProduct.do");
		
		Product product = productService.findProduct(prodNo);
		
		model.addAttribute("pro", product);
		
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping("/listProduct.do") // 상품 리스트
	public String listProduct( @ModelAttribute("search") Search search , Model model) throws Exception{
		
		
		System.out.println("/listProduct.do");
		
		if(search.getPage() ==0 ){
			search.setPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getPage(), ((Integer)map.get("totalCount")).intValue(), 
									pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		model.addAttribute("window", "product");
		
		return "forward:/product/listProduct.jsp";
	}
	
	
	@RequestMapping("/updateProductView.do") // 업데이트 뷰 확인
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/updateProductView.do");
		
		Product product = productService.findProduct(prodNo);
		
		model.addAttribute("pro", product);
		
		return "forward:/product/updateProductView.jsp"; 
	}

	@RequestMapping("/updateProduct.do")
	public String updateProduct(@ModelAttribute("product") Product product , Model model) throws Exception{

		System.out.println("/updateProduct.do");

		productService.updateProduct(product);
		
		model.addAttribute("pro", product);
		
		return "forward:/product/getProduct.jsp"; 
	}

}