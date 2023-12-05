package hcmute.controller.admin;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hcmute.dto.LaptopForm;
import hcmute.dto.CategoryForm;
import hcmute.model.Category;
import hcmute.service.ILaptopService;
import hcmute.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin/laptop")
@Slf4j
public class AdminLaptopController {
	@Autowired
	ILaptopService laptopService;
	@Autowired
	ICategoryService categoryService;

	@GetMapping
	public ModelAndView laptopView() {
		ModelAndView mav = new ModelAndView();
		List<LaptopForm> laptopForms = laptopService.getLaptopShows();
		mav.addObject("laptops", laptopForms);
		mav.setViewName("admin/laptop/laptop");
		return mav;
	}

	@GetMapping("/insert")
	public ModelAndView viewInsertlaptop() {
		ModelAndView mav = new ModelAndView();
		LaptopForm laptopForm = new LaptopForm();
		List<Category> categories = categoryService.getAllCategories();
		mav.addObject("categories", categories);
		mav.addObject("laptopForm", laptopForm);
		mav.setViewName("admin/laptop/formAddLaptop.html");
		return mav;
	}
	@PostMapping("/add")
	public ModelAndView insert(@Valid @ModelAttribute("laptopForm") LaptopForm laptopForm, BindingResult result) throws ParseException, IOException {
		ModelAndView mav = new ModelAndView();
		
		if (result.hasErrors()) {
			log.info("lỗi");
			mav.setViewName("redirect:/admin/laptop/insert");
			return mav;
		}
		laptopService.insert(laptopForm);
		mav.setViewName("redirect:/admin/laptop/");
		
		return mav;
	}
	
	@DeleteMapping("/delete")
	public ModelAndView deletelaptop(
			ModelAndView mav,
			@RequestParam("laptopId") String laptopId) {	
		laptopService.deleteById(Long.parseLong(laptopId));
		mav.setViewName("admin/laptop/laptop.html");
		return mav;
	}
	
	@GetMapping("/edit")
	public ModelAndView viewUpdatelaptop(
			ModelAndView mav,
			@RequestParam("laptopId") String laptopId) {	
		
		LaptopForm laptopForm = laptopService.getById(Long.parseLong(laptopId));
		
		List<Category> categories = categoryService.getAllCategories();
		mav.addObject("categories", categories);
		mav.addObject("laptopForm", laptopForm);
		mav.setViewName("admin/laptop/formEditLaptop.html");
		return mav;
	}
	
	@PostMapping("/edit")
	public ModelAndView updatelaptop(
			ModelAndView mav, 
			@ModelAttribute("laptopForm") LaptopForm laptopForm, BindingResult result) throws ParseException, IOException {	
		laptopService.updateLaptop(laptopForm);
		mav.addObject("updateSucceed", true);
		mav.setViewName("redirect:/admin/laptop/");
		return mav;
	}
	@GetMapping("/category")
	public ModelAndView categoryView() {
		ModelAndView mav = new ModelAndView();
		List<CategoryForm> categoryForms = categoryService.getCategoryShows();
		mav.addObject("categoryForms", categoryForms);
		mav.setViewName("admin/laptop/category.html");
		return mav;
	}
	@GetMapping("/category/add")
	public ModelAndView viewInsertCategory() {
		ModelAndView mav = new ModelAndView();
		CategoryForm categoryForm = new CategoryForm();
		mav.addObject("category", categoryForm);
		mav.setViewName("admin/laptop/formAddCategory.html");
		return mav;
	}
	@PostMapping("/category/add")
	public ModelAndView insertCategory(@Valid @ModelAttribute("category") CategoryForm category, BindingResult result) throws IOException {
		ModelAndView mav = new ModelAndView();
		categoryService.insert(category);
		mav.setViewName("redirect:/admin/laptop/");
		return mav;
	}
	@GetMapping("/category/edit")
	public ModelAndView viewUpdateCategory(
			ModelAndView mav,
			@RequestParam("id") String id) {

		CategoryForm categoryForm = categoryService.getById(Long.parseLong(id));
		mav.addObject("category", categoryForm);
		mav.setViewName("admin/laptop/formEditCategory.html");
		return mav;
	}
	@PostMapping("/category/edit")
	public ModelAndView updateCategory(
			ModelAndView mav,
			@ModelAttribute("category") CategoryForm categoryForm, BindingResult result) throws ParseException, IOException {
		categoryService.updateCategory(categoryForm);
		mav.addObject("updateSucceed", true);
		mav.setViewName("redirect:/admin/laptop/category");
		return mav;
	}
}
