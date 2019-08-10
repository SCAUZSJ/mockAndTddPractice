package parking;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class InOrderParkingStrategyTestAnswer {

    public static final String PARKING_NAME_A = "ParkingA";
    public static final String PARKING_NAME_B = "ParkingB";
    public static final String PARKING_NAME_C = "ParkingC";
    public static final String CAR_NAME_AA111 = "AA111";
    private InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();

	@Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
	    * With using Mockito to mock the input parameter */

        ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.getName()).thenReturn(PARKING_NAME_A);

        Car car = createMockCar(CAR_NAME_AA111);
        Receipt receipt = inOrderParkingStrategy.createReceipt(parkingLot, car);

        Assert.assertEquals(CAR_NAME_AA111, receipt.getCarName());
        Assert.assertEquals(PARKING_NAME_A, receipt.getParkingLotName());
    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */

        Car car = createMockCar(CAR_NAME_AA111);
        Receipt receipt = inOrderParkingStrategy.createNoSpaceReceipt(car);

        Assert.assertEquals(CAR_NAME_AA111, receipt.getCarName());
        Assert.assertEquals(ParkingStrategy.NO_PARKING_LOT, receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt(){

	    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */

        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());

        List<ParkingLot> parkingLots = new ArrayList<>();
        Car car = createMockCar(CAR_NAME_AA111);

        spyInOrderParkingStrategy.park(parkingLots, car);

        verify(spyInOrderParkingStrategy, times(1)).createNoSpaceReceipt(car);
    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */

        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());

        ParkingLot emptyParkingLog = new ParkingLot(PARKING_NAME_A, 1);
        List<ParkingLot> parkingLots = Arrays.asList(emptyParkingLog);
        Car car = createMockCar(CAR_NAME_AA111);

        spyInOrderParkingStrategy.park(parkingLots, car);

        verify(spyInOrderParkingStrategy, times(1)).createReceipt(emptyParkingLog, car);
        verify(spyInOrderParkingStrategy, times(0)).createNoSpaceReceipt(car);
    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */
        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());

        ParkingLot fullParkingLog = new ParkingLot(PARKING_NAME_A, 1);
        fullParkingLog.getParkedCars().add(createMockCar("Dummy"));
        List<ParkingLot> parkingLots = Arrays.asList(fullParkingLog);

        Car car = createMockCar(CAR_NAME_AA111);
        spyInOrderParkingStrategy.park(parkingLots, car);

        verify(spyInOrderParkingStrategy, times(0)).createReceipt(fullParkingLog, car);
        verify(spyInOrderParkingStrategy, times(1)).createNoSpaceReceipt(car);
    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot(){

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */
        InOrderParkingStrategy spyInOrderParkingStrategy = spy(new InOrderParkingStrategy());

        ParkingLot fullParkingLogA = new ParkingLot(PARKING_NAME_A, 1);
        fullParkingLogA.getParkedCars().add(createMockCar("Dummy"));

        ParkingLot emptyParkingLogB = new ParkingLot(PARKING_NAME_B, 1);
        ParkingLot emptyParkingLogC = new ParkingLot(PARKING_NAME_C, 1);

        List<ParkingLot> parkingLots = Arrays.asList(fullParkingLogA, emptyParkingLogB, emptyParkingLogC);

        Car car = createMockCar(CAR_NAME_AA111);
        spyInOrderParkingStrategy.park(parkingLots, car);

        verify(spyInOrderParkingStrategy, times(0)).createReceipt(fullParkingLogA, car);
        verify(spyInOrderParkingStrategy, times(1)).createReceipt(emptyParkingLogB, car);
        verify(spyInOrderParkingStrategy, times(0)).createReceipt(emptyParkingLogC, car);
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
