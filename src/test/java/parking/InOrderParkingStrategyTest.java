package parking;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class InOrderParkingStrategyTest {

	@Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
	    * With using Mockito to mock the input parameter */
        Receipt receipt = new Receipt();
        receipt.setCarName("car1");
        receipt.setParkingLotName("parkingLot1");

        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
        ParkingLot parkingLot = mock(ParkingLot.class);
        Car car = mock(Car.class);

        when(parkingLot.getName()).thenReturn("parkingLot1");
        when(car.getName()).thenReturn("car1");

        Assert.assertEquals(receipt.getCarName(),inOrderParkingStrategy.createReceipt(parkingLot,car).getCarName());
        Assert.assertEquals(receipt.getParkingLotName(),inOrderParkingStrategy.createReceipt(parkingLot,car).getParkingLotName());
    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */


    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt(){

	    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */
        List<ParkingLot> parkingLots =Arrays.asList(new ParkingLot("pk1",1));
        Car car = new Car("car1");
        InOrderParkingStrategy inOrderParkingStrategy =Mockito.spy(new InOrderParkingStrategy());

        inOrderParkingStrategy.park(parkingLots,car);

        Mockito.verify(inOrderParkingStrategy,times(1)).createReceipt(parkingLots.get(0),car);
        Mockito.verify(inOrderParkingStrategy,times(0)).createNoSpaceReceipt(car);

    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */

    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */

    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot(){

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */
        List<ParkingLot> parkingLots =Arrays.asList(new ParkingLot("pk1",0),new ParkingLot("pk2",1));
        Car car = new Car("car1");
        InOrderParkingStrategy inOrderParkingStrategy =Mockito.spy(new InOrderParkingStrategy());

        Receipt receipt = inOrderParkingStrategy.park(parkingLots,car);
        Mockito.verify(inOrderParkingStrategy,times(1)).createReceipt(parkingLots.get(1),car);
    }


}
