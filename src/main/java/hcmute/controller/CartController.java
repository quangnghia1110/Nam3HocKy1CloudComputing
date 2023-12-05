package hcmute.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import hcmute.dto.LaptopForm;
import hcmute.dto.CheckoutForm;
import hcmute.model.Laptop;
import hcmute.model.Cart;
import hcmute.model.CartItem;
import hcmute.model.user.User;
import hcmute.security.UserPrincipal;
import hcmute.service.ILaptopService;
import hcmute.service.ICartItemService;
import hcmute.service.ICartService;
import hcmute.service.IUserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member/cart")
@Slf4j
public class CartController {

	@Autowired
	private ICartService cartService;

	@Autowired
	private ICartItemService cartItemService;

	@Autowired
	private ILaptopService laptopService;

	@Autowired
	IUserService userService;

	@GetMapping
	public ModelAndView viewCartPage(ModelAndView mav, HttpServletRequest request, HttpSession session) {

		checkError(request, session, mav);
		log.info("in cart page");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		User user = userService.getUserById(userPrincipal.getId());
		userPrincipal.setCartItemNum(String.valueOf(user.getCart().getCartItems().size()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		renderObject(mav, userPrincipal.getId());
		mav.setViewName("client/cart");
		return mav;
	}

	private void checkError(HttpServletRequest request, HttpSession session, ModelAndView mav) {
		session = request.getSession();
		String error = "";
		String laptopId = "";
		if (Objects.nonNull(session.getAttribute("error"))) {
			error = session.getAttribute("error").toString();
			session.removeAttribute("error");
		}
		if (Objects.nonNull(session.getAttribute("error-laptopId"))) {
			laptopId = session.getAttribute("error-laptopId").toString();
			session.removeAttribute("error-laptopId");
		}
		if (Objects.nonNull(error) && error != "" && error.equals("true")) {
			mav.addObject("notChecked", true);
		} else if (Objects.nonNull(laptopId) && laptopId != "") {
			Laptop laptop = cartItemService.getItemById(Long.parseLong(laptopId)).getLaptop();
			if (laptop.getAvailable() == false) {
				mav.addObject("notAvailable", laptop.getTitle());
			} else {
				mav.addObject("insufficient", laptop.getTitle());
				mav.addObject("inventory", laptop.getInventory().getQuantiy());
			}
		}

	}

	@DeleteMapping("/delete")
	@ResponseBody
	public String deleteCartItem(@RequestParam("itemId") String itemId) {
		cartItemService.deleteById(Long.parseLong(itemId));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		// set cartNum on authenticated user
		Long newCartItemNum = Long.parseLong(userPrincipal.getCartItemNum()) - 1;
		userPrincipal.setCartItemNum(String.valueOf(newCartItemNum));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return "success";

	}

	@PostMapping("/update")
	@ResponseBody
	public String updateCartItem(@RequestParam("itemId") String itemId,
			@RequestParam("currItemNum") String currItemNum) {
		cartItemService.updateItem(Long.parseLong(itemId), Integer.parseInt(currItemNum));
		return "success";
	}

	private void renderObject(ModelAndView mav, Long userId) {
		Cart cart = cartService.getCartByUser(userId);
		List<CartItem> cartItems = cart.getCartItems();
		mav.addObject("checkoutForm", new CheckoutForm());
		mav.addObject("cart", cart);
		mav.addObject("cartItems", cartItems);
	}

	@GetMapping("/add")
	public ModelAndView addToCart(ModelAndView mav, @RequestParam("laptopId") String laptopId,
			@RequestParam(value = "quantity", required = false) String quantity) {

		// Get authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		User user = userService.getUserById(userPrincipal.getId());

		if (Objects.isNull(quantity)) {
			quantity = "1";
		}

		cartService.addToCart(user, Long.parseLong(laptopId), Integer.parseInt(quantity));

		mav.setViewName("redirect:/member/cart");
		return mav;
	}

	@PostMapping("/add")
	public ModelAndView addToCartWithQuantity(ModelAndView mav, @ModelAttribute("laptopId") String laptopId,
			@ModelAttribute("quantity") String quantity) {

		LaptopForm laptop = laptopService.getById(Long.parseLong(laptopId));
		if (Long.parseLong(laptop.getQuantity()) <= Long.parseLong(quantity)) {
			mav.addObject("insufficient", true);
			int sold = laptopService.getSoldNumberById(Long.parseLong(laptopId));
			List<Laptop> topFeatured = laptopService.getTopFeatured();
			mav.addObject("topFeatured", topFeatured);
			mav.addObject("sold", sold);
			mav.addObject("laptop", laptop);
			mav.setViewName("client/shop-detail");
			return mav;
		}
		// Get authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		User user = userService.getUserById(userPrincipal.getId());

		cartService.addToCart(user, Long.parseLong(laptopId), Integer.parseInt(quantity));
		mav.setViewName("redirect:/member/cart");
		return mav;
	}
}
