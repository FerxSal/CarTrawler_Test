import com.cartrawler.assessment.app.AssessmentRunner;
import com.cartrawler.assessment.car.CarResult;

import java.util.*;
import java.util.stream.Collectors;


import com.cartrawler.assessment.carutils.CarUtils;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CarUtilsTest  {

    Set<CarResult> listCars;
    List<CarResult> list;
    CarUtils carUtil;

    @Before
    public void setUp() throws Exception {
        listCars = AssessmentRunner.CARS;
        list = listCars.stream().collect(Collectors.toList());
        carUtil = new CarUtils();
    }


    @Test
    public void whenCarList_thenRemoveDuplicates() {

        List<CarResult> result = carUtil.removeAnyDuplicate(list);

        assertTrue(!result.isEmpty());

        assertTrue(result.size() == 169);
    }


    @Test
    public void whenCarList_sortCorporateByLowPrice(){

        List<CarResult> result = carUtil.sortCorporateByLowPrice(list);

        assertTrue(!result.isEmpty());
        Iterator<CarResult> countriesIterator = result.iterator();

        CarResult current, previous = countriesIterator.next();

        while (countriesIterator.hasNext()) {

           current = countriesIterator.next();
           if ( countriesIterator != countriesIterator.next()) {
               assertTrue(current.getRentalCost() <= (countriesIterator.next()).getRentalCost());
           }
           previous = current;
        }

    }



}
