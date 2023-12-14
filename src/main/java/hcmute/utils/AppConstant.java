package hcmute.utils;

public class AppConstant {
	
	public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";
	public static final String UPLOAD_USER_DIRECTORY = System.getProperty("user.dir") + "/uploads/users";
	public static final String UPLOAD_LAPTOP_DIRECTORY = System.getProperty("user.dir") + "/uploads/laptops";
	public static final String UPLOAD_CATEGORY_DIRECTORY = System.getProperty("user.dir") + "/uploads/categories";;
	public static final String ROLE_NOT_FOUND = "Not found role with name: ";
	public static final String CATEGORY_NOT_FOUND = "Not found category with name: ";
	public static final String USER_NOT_FOUND = "Not found user with username: ";
	public static final String CART_NOT_FOUND = "Not found cart with user: ";
	public static final String CART_ITEM_NOT_FOUND = "Not found cart item with id: ";
	public static final String LAPTOP_NOT_FOUND = "Not found laptop with title: ";
	public static final String CONTACT_NOT_FOUND = "Not found contact with id: ";
	public static final Long STANDARD = 20000L;
	public static final Long FAST = 40000L;
	public static final Long VERY_FAST = 60000L;
}
