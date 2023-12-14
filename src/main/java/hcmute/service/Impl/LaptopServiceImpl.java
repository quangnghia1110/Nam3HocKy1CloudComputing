package hcmute.service.Impl;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.dto.LaptopForm;
import hcmute.dto.FilterForm;
import hcmute.model.Laptop;
import hcmute.model.CartItem;
import hcmute.model.Image;
import hcmute.model.Inventory;
import hcmute.model.order.Order;
import hcmute.model.order.OrderItem;
import hcmute.model.order.OrderTrack;
import hcmute.repository.*;
import hcmute.service.ILaptopService;
import hcmute.utils.AppConstant;
import hcmute.utils.FileUploadUtils;
import hcmute.utils.Swap;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LaptopServiceImpl implements ILaptopService {
	@Autowired
	LaptopRepository laptopRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	InventoryRepository inventoryRepository;
	@Autowired
	ImageRepository imageRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderItemRepository orderItemRepository;
	@Autowired
	UserRepository userRepository;

	@Override
	public void insert(LaptopForm laptopForm) throws ParseException, IOException {
		Laptop laptop = new Laptop();
		laptop.setDescription(laptopForm.getDescription());
		laptop.setPrice(Long.parseLong(laptopForm.getPrice()));
		laptop.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(laptopForm.getReleaseDate()));
		laptop.setAvailable(true);
		laptop.setCategory(categoryRepository.findById(laptopForm.getCategory().getId()).get());
		laptop.setTitle(laptopForm.getTitle());
		Inventory inventory = new Inventory();
		inventory.setQuantiy(Integer.parseInt(laptopForm.getQuantity()));
		inventoryRepository.save(inventory);
		laptop.setInventory(inventory);
		// log.info(laptop.toString());

		Laptop laptopSaved = laptopRepository.save(laptop);
		if (!laptopForm.getFile().isEmpty()) {
			Path fileNameAndPath = FileUploadUtils.saveLaptopImage(laptopForm.getFile(), laptopSaved.getId());
			Image image = new Image();
			image.setTitle(laptopSaved.getId().toString() + ".png");
			image.setUrl(fileNameAndPath.toString());
			Image imageSaved = imageRepository.save(image);
			laptopSaved.setImage(imageSaved);
			laptopRepository.save(laptopSaved);
		} else {
			// Create thumbnail image 1
			Image imageThumbnail = new Image();
			imageThumbnail.setThumbnailName("laptopThumbnail.png");
			imageThumbnail
					.setThumbnailURL("E:\\HCMUTE\\School_Project\\laptopstore_Metislaptop\\uploads\\laptopThumbnail.png");
			imageRepository.save(imageThumbnail);
			laptopSaved.setImage(imageThumbnail);
			laptopRepository.save(laptopSaved);
		}

	}

	@Override
	public List<Laptop> getTopFeatured() {
		List<Laptop> topFeatured = new ArrayList<>();
		List<Laptop> laptops = laptopRepository.findAll();
		if (!laptops.isEmpty()) {
			topFeatured.add(laptops.get(0));
			topFeatured.add(laptops.get(1));
		}
		return topFeatured;
	}

	@Override
	public List<Laptop> getBestSeller() {
		List<Laptop> bestSeller = new ArrayList<>();
		List<Laptop> laptops = laptopRepository.findAll();
		if (!laptops.isEmpty()) {
			bestSeller.add(laptops.get(2));
			bestSeller.add(laptops.get(3));
		}
		return bestSeller;
	}

	@Override
	public List<Laptop> getAllLaptops() {
		List<Laptop> laptops = laptopRepository.findAll();
		return laptops;
	}

	@Override
	public Long getMaxPrice() {
		List<Laptop> laptops = laptopRepository.findAll();
		Long max = 0L;
		for (Laptop laptop : laptops) {
			if (laptop.getPrice() > max) {
				max = laptop.getPrice();
			}
		}

		double tempPrice = (double) max;
		while (tempPrice > 10) {
			tempPrice = tempPrice / 10;
		}
		tempPrice = Math.ceil(tempPrice);

		while (tempPrice < max) {
			tempPrice = tempPrice * 10;
		}
		return (long) tempPrice;
	}

	@Override
	public Long getNumAllLaptops() {
		List<Laptop> laptops = laptopRepository.findAll();
		return (long) laptops.size();
	}

	@Override
	public List<Laptop> getLaptopsByCategory(String category) {
		List<Laptop> laptops = new ArrayList<>();
		for (Laptop laptop : laptopRepository.findAll()) {
			if (laptop.getCategory().getDomain().compareTo(category) == 0) {
				laptops.add(laptop);
			}
		}
		return laptops;
	}

	public List<LaptopForm> getLaptopShows() {
		List<Laptop> laptops = laptopRepository.findAll();
		List<LaptopForm> laptopForms = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		for (Laptop laptop : laptops) {
			LaptopForm laptopForm = new LaptopForm();
			laptopForm.setCategory(laptop.getCategory());
			laptopForm.setDescription(laptop.getDescription());
			// laptopForm.setFile(laptop.getImage());
			laptopForm.setPrice(laptop.getPrice().toString());
			laptopForm.setReleaseDate(formatter.format(laptop.getReleaseDate()));
			laptopForm.setQuantity(laptop.getInventory().getQuantiy().toString());
			laptopForm.setTitle(laptop.getTitle());
			laptopForm.setId(laptop.getId().toString());
			laptopForm.setAvailable(laptop.getAvailable() == true ? "Còn bán" : "Ngưng bán");
			if (laptop.getCreateBy()!=null){
				laptopForm.setCreateBy(userRepository.findById(laptop.getCreateBy()).get().getUsername());
			} else {
				laptopForm.setCreateBy("");
			}
			if (laptop.getUpdateBy()!=null){
				laptopForm.setLastUpdateBy(userRepository.findById(laptop.getUpdateBy()).get().getUsername());
			} else {
				laptopForm.setLastUpdateDate("");
			}
			laptopForm.setCreateDate(formatter.format(laptop.getCreatedAt()));
			laptopForm.setLastUpdateDate(formatter.format(laptop.getUpdatedAt()));
			laptopForms.add(laptopForm);
		}
		return laptopForms;
	}

	@Override
	public void deleteById(Long laptopId) {
		laptopRepository.deleteById(laptopId);

	}

	@Override
	public LaptopForm getById(Long laptopId) {
		Laptop laptop = laptopRepository.findById(laptopId).get();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		LaptopForm laptopForm = new LaptopForm();
		laptopForm.setCategory(laptop.getCategory());
		laptopForm.setDescription(laptop.getDescription());
		// laptopForm.setFile(laptop.getImage());
		if (!Objects.isNull(laptop.getImage())) {
			laptopForm.setImageName(laptop.getImage().getTitle());
			laptopForm.setThumbnailName(laptop.getImage().getThumbnailName());
		}
		laptopForm.setId(laptopId.toString());
		laptopForm.setPrice(laptop.getPrice().toString());
		laptopForm.setReleaseDate(formatter.format(laptop.getReleaseDate()));
		log.info(laptopForm.getReleaseDate());
		laptopForm.setQuantity(laptop.getInventory().getQuantiy().toString());
		laptopForm.setTitle(laptop.getTitle());
		laptopForm.setAvailable(laptop.getAvailable() == true ? "Còn bán" : "Ngưng bán");
		return laptopForm;
	}

	@Override
	public void updateLaptop(LaptopForm laptopForm) throws ParseException, IOException {
		Laptop laptop = laptopRepository.findById(Long.parseLong(laptopForm.getId())).get();
		if (Objects.isNull(laptop)) {
		} else {
			laptop.setDescription(laptopForm.getDescription());
			laptop.setPrice(Long.parseLong(laptopForm.getPrice()));
			laptop.setTitle(laptopForm.getTitle());
			laptop.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(laptopForm.getReleaseDate()));
			laptop.setAvailable(laptopForm.getAvailable().equals("Còn bán") ? true : false);
			laptop.setCategory(categoryRepository.findById(laptopForm.getCategory().getId()).get());
			Inventory inventory = new Inventory();
			inventory.setQuantiy(Integer.parseInt(laptopForm.getQuantity()));
			inventoryRepository.save(inventory);
			laptop.setInventory(inventory);
			// log.info(laptop.toString());

			Laptop laptopSaved = laptopRepository.save(laptop);
			if (!laptopForm.getFile().isEmpty()) {
				Path fileNameAndPath = FileUploadUtils.saveLaptopImage(laptopForm.getFile(), laptopSaved.getId());
				Image image = new Image();
				image.setTitle(laptopSaved.getId().toString() + ".png");
				image.setUrl(fileNameAndPath.toString());
				Image imageSaved = imageRepository.save(image);
				laptopSaved.setImage(imageSaved);
				laptopRepository.save(laptopSaved);
			}
		}
		

	}

	@Override
	public List<Laptop> filter(List<Laptop> laptops, FilterForm filterForm) {
		List<Laptop> filterlaptops = new ArrayList<>();
		for (Laptop laptop : laptops) {
			if (laptop.getPrice() >= filterForm.getMinPrice() && laptop.getPrice() <= filterForm.getMaxPrice()) {
				filterlaptops.add(laptop);
			}
		}
		if (filterlaptops.size() < 2) {
			return filterlaptops;
		} else if (filterForm.getSort() != "none") {
			int min;
			int n = filterlaptops.size();
			for (int i = 0; i < n - 1; i++) {
				min = i;
				for (int j = i + 1; j < n; j++) {
					if (filterlaptops.get(j).getPrice() < filterlaptops.get(min).getPrice())
						min = j;
				}
				swap(i, min, filterlaptops);
			}

			if (filterForm.getSort() == "decre") {
				Collections.reverse(filterlaptops);
			}
		}
		
		if(filterForm.getTextSearch() != "") {
			laptops.clear();
			laptops.addAll(filterlaptops);
			filterlaptops.clear();
			String keyword = filterForm.getTextSearch();
			for (Laptop laptop : laptops) {
				if(laptop.keywordInTitle(keyword)) filterlaptops.add(laptop);
			}
		}
		return filterlaptops;
	}

	private List<Laptop> swap(int index1, int index2, List<Laptop> list) {
		Laptop temp = list.get(index1);
		list.set(index1, list.get(index2));
		list.set(index2, temp);
		return list;
	}

	@Override
	public int getSoldNumberById(Long laptopId) {
		int sold = 0;
		Laptop laptop = laptopRepository.findById(laptopId).get();
		if (Objects.isNull(laptop)) {
			return -1;
		}
		List<Order> orders = orderRepository.findAll();
		for (Order order : orders) {
			if (!order.getOrderTrack().getStatus().equals("Chờ thanh toán")) {
				for (OrderItem orderItem : order.getOrderItems()) {
					if (orderItem.getLaptop().getId() == laptopId) {
						sold = sold + orderItem.getQuantity();
					}

				}
			}
		}

		return sold;
	}

}
