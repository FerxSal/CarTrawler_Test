package com.cartrawler.assessment.carutils;


import com.cartrawler.assessment.car.CarResult;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;



public class CarUtils {

    private HashSet<String> corporatecars = new HashSet<String>(Arrays.asList("AVIS", "BUDGET", "ENTERPRISE", "FIREFLY", "HERTZ", "SIXT", "THRIFTY"));

    //distinctByKeys() function
    private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors)
    {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t ->
        {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }


    //Remove any duplicates from the list (duplicates = same make, model, supplier, SIPP, FuelPolicy)
    public List<CarResult> removeAnyDuplicate(List<CarResult> cars) {

        if (cars.size() > 0) {

            List<CarResult> filteredCarList = cars
                    .stream()
                    .filter(distinctByKeys(CarResult::getSupplierName, CarResult::getDescription ,CarResult::getSippCode) )
                    .collect(Collectors.toList());

            return filteredCarList;
        }
        return cars;

    }

    // Sort the list so that all corporate cars appear at the top {AVIS, BUDGET, ENTERPRISE, FIREFLY, HERTZ, SIXT, THRIFTY}.
    public List<CarResult> sortCarsbyCorporate(List<CarResult> cars) {

        if (cars.size() > 0) {

            List<CarResult> result = cars.stream().filter(car -> corporatecars.contains(car.getSupplierName())).collect(Collectors.toList());
            result.sort(Comparator.comparing(CarResult::getSupplierName));
            return result;

        }
        return cars;
    }


    //Within both the corporate and non-corporate groups, sort the cars into “mini”, “economy”,
    //“compact” and “other” based on SIPP beginning with M, E, C respectively
    public Map<Object, List<CarResult>> sortSIPP(List<CarResult> cars) {

        if (cars.size() > 0) {

            Function<CarResult, List<Object>> compositeKey = carRecord ->
                    Arrays.<Object>asList(carRecord.getSippCode().startsWith("M"), carRecord.getSippCode().startsWith("E"), carRecord.getSippCode().startsWith("C"));

            Map<Object, List<CarResult>> map =
                    cars.stream().collect(Collectors.groupingBy(compositeKey, Collectors.toList()));

            return map;
        }

        return null;
    }

    public void displayMap( Map<Object, List<CarResult>> map){

        for (Map.Entry<Object, List<CarResult>> entry: map.entrySet())
        {
            List key = (List) entry.getKey();
            List<CarResult> value = entry.getValue();

            if ( String.valueOf(key.get(0)) == "true")
                System.out.println("MINI" + "=" + value);
            if ( String.valueOf(key.get(1)) == "true")
                System.out.println("ECONOMY" + "=" + value);
            if ( String.valueOf(key.get(2)) == "true")
                System.out.println("COMPACT"+ "=" + value);

        }

    }

    //Within Corporate cars sort low-to-high on price
    public List<CarResult> sortCorporateByLowPrice(List<CarResult> cars){

        List<CarResult> result = cars.stream().filter(car -> corporatecars.contains(car.getSupplierName())).collect(Collectors.toList());
        result.sort(Comparator.comparingDouble(CarResult::getRentalCost));

        return result;
    }


    //Within NonCorporate cars sort low-to-high on price
    public List<CarResult> sortNonCorporateByLowPrice(List<CarResult> cars){

        List<CarResult> result = cars.stream().filter(car -> !corporatecars.contains(car.getSupplierName())).collect(Collectors.toList());
        result.sort(Comparator.comparingDouble(CarResult::getRentalCost));

        return result;
    }


}