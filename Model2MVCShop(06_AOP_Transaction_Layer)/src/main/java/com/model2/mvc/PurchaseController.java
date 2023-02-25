package com.model2.mvc;

import java.util.Map;

import javax.jws.WebParam.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.ProductService;
import com.model2.mvc.service.PurchaseService;
import com.model2.mvc.service.UserService;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;


//==> 회원관리 Controller
@Controller
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
		
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 3}") 
	int pageSize;
	
	
	
	@RequestMapping("/addPurchase.do") 
	public ModelAndView addPurchase( @ModelAttribute("purchase") Purchase purchase,
					@RequestParam("buyerId") String userId, @RequestParam("prodNo") int prodNo) throws Exception {

		System.out.println("/addPurchase.do");
	
		purchase.setBuyer(userService.getUser(userId)); // id값
		purchase.setPurchaseProd(productService.findProduct(prodNo)); // prodno값
		purchase.setTranCode("1"); // 트랜 코드는 강제로 삽입
		purchaseService.addPurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pur", purchase);
		
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
		
		return modelAndView;
	}
	
	
	
	@RequestMapping("/addPurchaseView.do") 
	public ModelAndView addPurchasevView( @RequestParam("prod_no") int prod_no) throws Exception {

		System.out.println("/addPurchaseView.do");
	
		Product product = productService.findProduct(prod_no);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pro", product);
		
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");
		
		return modelAndView;
	}
	
	
	@RequestMapping("/listPurchase.do") // 상품 리스트
	public ModelAndView listPurchase( @ModelAttribute("search") Search search) throws Exception{
		
		
		System.out.println("/listPurchase.do");
		
		if(search.getPage() ==0 ){
			search.setPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String , Object> map=purchaseService.getPurchaseList(search);
		Page resultPage = new Page( search.getPage(), ((Integer)map.get("totalCount")).intValue(), 
									pageUnit, pageSize);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		
		return modelAndView;
	}
	
	
	@RequestMapping("/getPurchase.do") 
	public ModelAndView getPurchase( @RequestParam("prodNo") int prodNo) throws Exception {
		
		System.out.println("/getPurchase.do");
		
		Purchase purchase = purchaseService.getPurchase(prodNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pur", purchase);
		
		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		
		return modelAndView;
	}

	
	@RequestMapping("/updatePurchaseView.do") // 업데이트 뷰 확인
	public ModelAndView updatePurchaseView( @RequestParam("prodNo") int prodNo) throws Exception{

		System.out.println("/updatePurchaseView.do");
		
		Purchase purchase = purchaseService.getPurchase(prodNo);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pur", purchase);
		
		modelAndView.setViewName("forward:/purchase/updatePurchaseView.jsp");
		
		return modelAndView;
	}
	

	@RequestMapping("/updatePurchase.do")
	public ModelAndView updatePurchase(@ModelAttribute("purchase") Purchase purchase ,
			@RequestParam("buyerId") String userId, @RequestParam("prodNo") int prodNo) throws Exception{

		System.out.println("/updatePurchase.do");
		
		purchase.setBuyer(userService.getUser(userId)); // id값
		purchase.setPurchaseProd(productService.findProduct(prodNo)); // prodno
		purchaseService.updatePurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("pur", purchase);
		
		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		
		return modelAndView;
	}
	
	
	@RequestMapping("/updateTranCode.do")
	public ModelAndView updateTranCode(@ModelAttribute("purchase") Purchase purchase,
			@RequestParam("tranCode") String tranCode) throws Exception{

		System.out.println("/updateTranCode.do");
		
		purchaseService.updateTranCode(purchase);
				
		ModelAndView modelAndView = new ModelAndView();	
		modelAndView.setViewName("forward:/listPurchase.do");
		
		return modelAndView;
	}
	
	
	@RequestMapping("/updateTranCodeByProd.do")
	public ModelAndView updateTranCodeByProd(@RequestParam("tranCode") String tranCode, @RequestParam("prodNo") int prodNo) throws Exception{

		System.out.println("/updateTranCodeByProd.do");
						
		Purchase purchase = purchaseService.getPurchase(prodNo);
		purchase.setTranCode(tranCode);
		purchaseService.updateTranCode(purchase);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/listProduct.do?menu=manage");
		
		return modelAndView;
	}

}