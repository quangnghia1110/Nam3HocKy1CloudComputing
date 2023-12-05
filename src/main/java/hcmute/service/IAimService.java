package hcmute.service;

import hcmute.dto.AimForm;

public interface IAimService {

    String getSalesData(int year);

    String getProductData(int year);

    String getCustomerData(int year);

    String getAimData(int year);

    String getAimInYear(int year);

    void save(AimForm aimForm);

}