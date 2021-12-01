package com.cartrawler.assessment.view;

import com.cartrawler.assessment.car.CarResult;

import java.util.List;
import java.util.Set;

public class Display {
    public void render(Set<CarResult> cars) {
        for (CarResult car : cars) {
            System.out.println (car);
        }
    }

    // Writing results with table format style
    public void formatDisplay(List<CarResult> listcars){

        System.out.println(String.format("%10s %16s %20s %15s %12s", "Corporate-Supplier", "Description", "SIPPCode","Rental-Cost","Fuel Policy"));
        for (CarResult car : listcars)
            System.out.println(String.format("%-25s%-25s%-10s%-10f %12s", car.getSupplierName() , car.getDescription(), car.getSippCode(), car.getRentalCost(),car.getFuelPolicy()));

    }


}
