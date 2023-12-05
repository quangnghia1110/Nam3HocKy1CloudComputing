package hcmute.service;



import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import hcmute.dto.LaptopForm;
import hcmute.dto.FilterForm;
import hcmute.model.Laptop;

public interface ILaptopService {

	void insert(LaptopForm laptopForm) throws ParseException, IOException;

	List<Laptop> getTopFeatured();

	List<Laptop> getBestSeller();

	List<Laptop> getAllLaptops();

	Long getMaxPrice();

	Long getNumAllLaptops();

	List<Laptop> getLaptopsByCategory(String category);

	List<LaptopForm> getLaptopShows();

	void deleteById(Long laptopId);

	LaptopForm getById(Long parseLong);

	void updateLaptop(LaptopForm laptopForm) throws ParseException, IOException;

	List<Laptop> filter(List<Laptop> laptops, FilterForm filterForm);

	int getSoldNumberById(Long laptopId);
}
