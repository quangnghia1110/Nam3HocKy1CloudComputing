package hcmute.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import hcmute.dto.RegisterForm;
import hcmute.model.Cart;
import hcmute.model.Category;
import hcmute.model.Image;
import hcmute.model.Inventory;
import hcmute.model.Laptop;
import hcmute.model.order.OrderTrack;
import hcmute.model.user.Role;
import hcmute.model.user.RoleName;
import hcmute.model.user.User;
import hcmute.repository.AddressRepository;
import hcmute.repository.CartReposiroty;
import hcmute.repository.CategoryRepository;
import hcmute.repository.ImageRepository;
import hcmute.repository.InventoryRepository;
import hcmute.repository.LaptopRepository;
import hcmute.repository.OrderTrackRepository;
import hcmute.repository.RoleRepository;
import hcmute.repository.UserRepository;
import hcmute.service.IUserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DatabaseInitalizer {
	@Autowired
	private IUserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	CartReposiroty cartReposiroty;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	ImageRepository imageRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	LaptopRepository laptopRepository;
	@Autowired
	InventoryRepository inventoryRepository;
	@Autowired
	OrderTrackRepository orderTrackRepository;

	public void initDb() throws IOException {
		addRoles();
		addUsers();
		addCategories();
		addImages();
		createCategoryMap();
		addLaptops();
		addOrderTracks();
	}
	
	private void addOrderTracks() {
		addOrderTrack("Đang chuẩn bị");
		addOrderTrack("Chờ thanh toán");
	}
	
	private void addOrderTrack(String status) {
		OrderTrack orderTrack = orderTrackRepository.findByStatus(status);
		if (orderTrack == null) {
			OrderTrack newOrderTrack = new OrderTrack();
			newOrderTrack.setStatus(status);
			orderTrackRepository.save(newOrderTrack);
		}
	}
	
	private void addLaptops() {
	    // Ultrabooks
	    addLaptop("Dell XPS 13", "Dell XPS is a line of consumer-oriented high-end laptop and desktop computers manufactured by Dell since 1993.", 1200L, LocalDate.of(2022, 3, 28));
	    addLaptop("MacBook Air (M1)", "MacBook Air is a line of laptop computers developed and manufactured by Apple Inc. It features a thin design, lightweight construction, and a Retina display.", 1400L, LocalDate.of(2022, 4, 15));

	    // Gaming Laptops
	    addLaptop("ASUS ROG Zephyrus G14", "ASUS ROG Zephyrus G14 is a powerful gaming laptop with a compact design and excellent performance.", 1800L, LocalDate.of(2022, 5, 5));
	    addLaptop("MSI GE76 Raider", "MSI GE76 Raider is a high-performance gaming laptop equipped with powerful hardware for gaming enthusiasts.", 2000L, LocalDate.of(2022, 6, 10));

	    // 2-in-1 Convertible Laptops
	    addLaptop("Microsoft Surface Pro 7", "Microsoft Surface Pro 7 is a versatile 2-in-1 convertible laptop known for its portability and performance.", 1300L, LocalDate.of(2022, 7, 18));
	    addLaptop("Lenovo Yoga C940", "Lenovo Yoga C940 is a premium 2-in-1 convertible laptop offering flexibility and powerful performance.", 1500L, LocalDate.of(2022, 8, 22));

	    // Business Laptops
	    addLaptop("HP Elite Dragonfly", "HP Elite Dragonfly is a business-oriented laptop known for its lightweight and durable design.", 1600L, LocalDate.of(2022, 9, 30));
	    addLaptop("Lenovo ThinkPad X1 Carbon", "Lenovo ThinkPad X1 Carbon is a business-class laptop recognized for its reliability and performance.", 1700L, LocalDate.of(2022, 10, 25));

	    // Budget Laptops
	    addLaptop("Acer Aspire 5", "Acer Aspire 5 is a budget-friendly laptop offering decent performance and features for everyday use.", 800L, LocalDate.of(2022, 11, 5));
	    addLaptop("HP Pavilion 15", "HP Pavilion 15 is an affordable yet capable laptop suitable for various tasks and casual gaming.", 900L, LocalDate.of(2022, 12, 12));

	    // Workstation Laptops
	    addLaptop("Dell Precision 7550", "Dell Precision 7550 is a powerful workstation laptop designed for professional use.", 2500L, LocalDate.of(2023, 1, 7));
	    addLaptop("HP ZBook Fury 17 G8", "HP ZBook Fury 17 G8 is a high-performance mobile workstation with advanced features.", 2800L, LocalDate.of(2023, 2, 15));

	    // Chromebooks
	    addLaptop("Google Pixelbook Go", "Google Pixelbook Go is a lightweight and portable Chromebook offering excellent battery life.", 1000L, LocalDate.of(2023, 3, 20));
	    addLaptop("Acer Chromebook Spin 713", "Acer Chromebook Spin 713 is a versatile Chromebook with a convertible design and good performance.", 1100L, LocalDate.of(2023, 4, 25));

	    // Thin & Light Laptops
	    addLaptop("LG Gram 14", "LG Gram 14 is an ultralight laptop known for its portability and long battery life.", 1300L, LocalDate.of(2023, 5, 30));
	    addLaptop("Lenovo IdeaPad Slim 7", "Lenovo IdeaPad Slim 7 is a sleek and lightweight laptop offering a blend of style and performance.", 1200L, LocalDate.of(2023, 6, 10));
	}
	
	Map<String, String> categoryMap = new HashMap<>();

	private void createCategoryMap() {

	    // Ultrabooks
	    categoryMap.put("Dell XPS 13", "Ultrabooks");
	    categoryMap.put("MacBook Air (M1)", "Ultrabooks");

	    // Gaming Laptops
	    categoryMap.put("ASUS ROG Zephyrus G14", "Gaming Laptops");
	    categoryMap.put("MSI GE76 Raider", "Gaming Laptops");

	    // 2-in-1 Convertible Laptops
	    categoryMap.put("Microsoft Surface Pro 7", "2-in-1 Convertible Laptops");
	    categoryMap.put("Lenovo Yoga C940", "2-in-1 Convertible Laptops");

	    // Business Laptops
	    categoryMap.put("HP Elite Dragonfly", "Business Laptops");
	    categoryMap.put("Lenovo ThinkPad X1 Carbon", "Business Laptops");

	    // Budget Laptops
	    categoryMap.put("Acer Aspire 5", "Budget Laptops");
	    categoryMap.put("HP Pavilion 15", "Budget Laptops");

	    // Workstation Laptops
	    categoryMap.put("Dell Precision 7550", "Workstation Laptops");
	    categoryMap.put("HP ZBook Fury 17 G8", "Workstation Laptops");

	    // Chromebooks
	    categoryMap.put("Google Pixelbook Go", "Chromebooks");
	    categoryMap.put("Acer Chromebook Spin 713", "Chromebooks");

	    // Thin & Light Laptops
	    categoryMap.put("LG Gram 14", "Thin & Light Laptops");
	    categoryMap.put("Lenovo IdeaPad Slim 7", "Thin & Light Laptops");
	}
	
	private void addLaptop(String title, String description, Long price, LocalDate releasedDate) {
		Laptop existingLaptop = laptopRepository.findByTitle(title);
		if (existingLaptop != null) {
			System.out.println("Laptop with name: " + title + " already exist");
		} else {
			Laptop laptop = new Laptop();
			laptop.setDescription(description);
			laptop.setPrice(price);
			Date date = Date.from(releasedDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			laptop.setReleaseDate(date);
			laptop.setAvailable(true);
			Category category = categoryRepository.findByName(categoryMap.get(title));
			if (category == null) {
				System.out.println("Category with name " + categoryMap.get(title) + " not found");
				return;
			}
			laptop.setCategory(category);
			laptop.setTitle(title);
			Inventory inventory = new Inventory();
			inventory.setQuantiy(40);
			inventoryRepository.save(inventory);
			laptop.setInventory(inventory);
			
			Laptop laptopSaved = laptopRepository.save(laptop);
			
			Image imageSaved = imageRepository.findByTitle(title);
			laptopSaved.setImage(imageSaved);
			laptopRepository.save(laptopSaved);
		}
	}
	
	private void addImages() {
		// Add images using the service
		addImage("Dell XPS 13", "https://th.bing.com/th/id/OIP.ADjoUOy8GMryL7mgRCfvnwHaE3?rs=1&pid=ImgDetMain");
		addImage("MacBook Air (M1)", "https://s.yimg.com/os/creatr-uploaded-images/2020-11/c8aea820-28a0-11eb-9f89-5ddd62987703");
		addImage("ASUS ROG Zephyrus G14", "https://www.windowscentral.com/sites/wpcentral.com/files/styles/large/public/field/image/2020/03/asus-rog-zephyrus-g14-hero2.jpg?itok=eklOcl6F");
		addImage("MSI GE76 Raider", "https://th.bing.com/th/id/R.8710ef513afd74e59b76a5dc7460006b?rik=9xqVJVByaELfjQ&pid=ImgRaw&r=0");
		addImage("Microsoft Surface Pro 7", "https://th.bing.com/th/id/R.a3c5e2381867cf805358d0973c2d4cc0?rik=icotPEjMjEAgWg&pid=ImgRaw&r=0");
		addImage("Lenovo Yoga C940", "https://www.notebookcheck-tr.com/uploads/tx_nbc2/c94015.jpg");
		addImage("HP Elite Dragonfly", "https://th.bing.com/th/id/R.f643fac81c984a3752c19c7595218e1f?rik=d69%2fW0G3d0uRVg&pid=ImgRaw&r=0");
		addImage("Lenovo ThinkPad X1 Carbon", "https://www.windowscentral.com/sites/wpcentral.com/files/styles/larger_wm_blb/public/field/image/2017/04/thinkpad-x1-carbon-hero2.jpg?itok=f9d-zg94");
		addImage("Acer Aspire 5", "https://www.notebookcheck.net/typo3temp/_processed_/5/6/csm_acer_8_dcc64722d5.jpg");
		addImage("HP Pavilion 15", "https://www.bhphotovideo.com/images/images2500x2500/hp_6qx96ua_aba_pavilion_i7_9750h_8gb_256gb_ssd_gtx_1660ti_w10h_15_6_1501636.jpg");
		addImage("Dell Precision 7550", "https://th.bing.com/th/id/OIP.WMWJeKfZp6FJZwH4JC42SwHaHa?rs=1&pid=ImgDetMain");
		addImage("HP ZBook Fury 17 G8", "https://th.bing.com/th/id/OIP.rjxMmv6q2Mm57MX93u6d7QHaFj?rs=1&pid=ImgDetMain");
		addImage("Google Pixelbook Go", "https://th.bing.com/th/id/OIP.sJFxgQkO7V0VagPyVdKrNAHaEK?rs=1&pid=ImgDetMain");
		addImage("Acer Chromebook Spin 713", "https://th.bing.com/th/id/R.1dad9227e0abc1dc3abf71b3ee78a026?rik=fO8LMktFXryCTg&pid=ImgRaw&r=0");
		addImage("LG Gram 14", "https://i5.walmartimages.com/asr/838998b6-8df3-4d18-b5dc-1600d65f8fe6_1.bd4220b80e0cb0e79f22127770ee7106.jpeg?odnWidth=1000&odnHeight=1000&odnBg=ffffff");
		addImage("Lenovo Ideapad Slim 7", "https://images.idgesg.net/images/article/2020/12/lenovo-ideapad-slim-7-14iil05-main-100869367-large.3x2.jpg");

	}
	
	private void addImage(String title, String imageUrl) {
		Image existingImage = imageRepository.findByUrl(imageUrl);
		if (existingImage != null) {
			System.out.println("Image with url " + imageUrl + " already exist");
		}
		if (existingImage == null) existingImage = imageRepository.findByTitle(title);
		if (existingImage != null) {
			System.out.println("Image with title " + title + " already exist");
		} else {
			Image newImage = new Image();
			newImage.setTitle(title);
			newImage.setThumbnailName(title);
			newImage.setUrl(imageUrl);
			newImage.setThumbnailURL(imageUrl);
			imageRepository.save(newImage);
		}
	}
  	
	private void addCategories() {
		List<String> categoryNames = Arrays.asList("Ultrabooks", "Gaming Laptops", "2-in-1 Convertible Laptops",
                "Business Laptops", "Budget Laptops", "Workstation Laptops", "Chromebooks", "Thin & Light Laptops");
		for (String categoryName : categoryNames) {
            addCategory(categoryName);
        }
        
	}
	
	private void addCategory(String categoryName) {
		// Check if the category already exists
        if (categoryRepository.findByName(categoryName) == null) {
            // If the category doesn't exist, create a new category and save it
            Category newCategory = new Category();
            newCategory.setName(categoryName);
            
            Image imageThumbnail = new Image();
			imageThumbnail.setThumbnailName("BookThumbnail.png");
			imageThumbnail
					.setThumbnailURL("https://th.bing.com/th/id/R.ffbcec602340203f8802af87d536d8f4?rik=o6CTq94xWSI3Vw&riu=http%3a%2f%2fak5.picdn.net%2fshutterstock%2fvideos%2f3486275%2fthumb%2f11.jpg&ehk=ayMY%2bQdXdLmUtDuF1oD57R7jmZDGXhuQlhL3mPoKU%2fc%3d&risl=&pid=ImgRaw&r=0");
			imageRepository.save(imageThumbnail);
			newCategory.setImage(imageThumbnail);
			categoryRepository.save(newCategory);
            System.out.println("Added category: " + categoryName);

        } else {
            System.out.println("Category already exists: " + categoryName);
        }
	}

	private void addRoles() {
		Role userRole = roleRepository.findByName(RoleName.USER);
		if (userRole == null) {
			userRole = new Role();
			userRole.setName(RoleName.USER);
			userRole.setDescription("This is role for application user");
			roleRepository.save(userRole);
		}

		Role adminRole = roleRepository.findByName(RoleName.ADMIN);
		if (adminRole == null) {
			adminRole = new Role();
			adminRole.setName(RoleName.ADMIN);
			adminRole.setDescription("This is role for admin");
			roleRepository.save(adminRole);
		}
		
		Role staffRole = roleRepository.findByName(RoleName.STAFF);
		if (staffRole == null) {
			staffRole = new Role();
			staffRole.setName(RoleName.STAFF);
			staffRole.setDescription("This is role for admin");
			roleRepository.save(staffRole);
		}
	}

	private void addUsers() throws IOException {
		addUser("admin", "123456789", "ngoquangnghia111003@gmail.com", "0974117373", RoleName.ADMIN);
		addUser("user", "123456789", "khachhanglaongnoi01@gmail.com", "0974117373", RoleName.USER);
		addUser("staff", "123456789", "21110559@student.hcmute.edu.vn", "0974117373", RoleName.STAFF);
	}

	private void addUser(String username, String password, String email, String phoneNumber, RoleName roleName) {
		RegisterForm registerForm = new RegisterForm();

		registerForm.setUsername(username);

		User existingUser = userRepository.findByUsername(username);
		if (existingUser != null) {
			log.info("user " + username + " already exist");
			return;
		}

		registerForm.setPassword(password);
		registerForm.setConfirmPassword(password);
		registerForm.setEmail(email);
		registerForm.setFirstName("John");
		registerForm.setLastName("Doe");
		registerForm.setGender("1");
		registerForm.setPhoneNumber(phoneNumber);
		registerForm.setStreet("123 Example Street");
		registerForm.setDistrict("Example District");
		registerForm.setSubDistrict("Example Sub-District");
		registerForm.setProvince("Example Province");
		registerForm.setFullAddress("123 Example Street, Example District, Example Sub-District, Example Province");
		registerForm.setBirthday("1990-01-01"); // Assuming the format is YYYY-MM-DD

		// Create new Cart for user
		Cart cart = new Cart();
		cart.setUser(null);
		Cart cartSaved = cartReposiroty.save(cart);

		log.info("In create new User");
		// get Role user
		Role role = roleRepository.findByName(roleName);
		if (Objects.isNull(role)) {
			log.error(AppConstant.ROLE_NOT_FOUND + roleName);
		}

		// Create new Image
		Image imageThumbnail = new Image();
		imageThumbnail.setThumbnailName("avtThumbnail.jpg");
		imageThumbnail.setThumbnailURL("E:\\HCMUTE\\School_Project\\bookstore_MetisBook\\uploads\\avtThumbnail.jpg");
		imageRepository.save(imageThumbnail);

		// Create new User
		User user = User.builder().username(registerForm.getUsername())
				.password(passwordEncoder.encode(registerForm.getPassword())).email(registerForm.getEmail())
				.firstName(registerForm.getFirstName()).lastName(registerForm.getLastName())
				.phoneNumber(registerForm.getPhoneNumber()).image(imageThumbnail)
				.birthday(registerForm.getBirthday().isEmpty() ? null : LocalDate.parse(registerForm.getBirthday()))
				.enabled(true) // true when click on verification link
				.gender(Integer.parseInt(registerForm.getGender())).addresses(null).cart(cartSaved)
				.roles(Arrays.asList(role)).build();
		userRepository.save(user);

	}

}
