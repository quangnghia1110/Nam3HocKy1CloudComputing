package hcmute.service;

import java.util.List;

import hcmute.model.user.Address;
import hcmute.model.user.User;

public interface IAddressService {

	List<Address> getAddressByUser(User user);

	Address getAddressById(Long addressId);

	void updateAddress(Address address, User user);

}
