package parking;

import mockit.Mocked;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VipParkingStrategyTestAnswer {

    public static final String PARKING_NAME_A = "ParkingA";
    public static final String PARKING_NAME_B = "ParkingB";
    public static final String PARKING_NAME_C = "ParkingC";
    public static final String CAR_NAME_AA111 = "AA111";
    public static final String CAR_NAME_BB111 = "BB111";

    @Mock
    CarDao carDao;
    @InjectMocks
    VipParkingStrategy injectedVipParkingStrategy = new VipParkingStrategy();

	@Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 4, Write a test case on VipParkingStrategy.park()
	    * With using Mockito spy, verify and doReturn */

        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        ParkingLot fullParkingLot = new ParkingLot(PARKING_NAME_A, 1);
        fullParkingLot.getParkedCars().add(createMockCar("Dummy"));

        List<ParkingLot> parkingLots = Arrays.asList(fullParkingLot);
        Car car = createMockCar(CAR_NAME_AA111);

        doReturn(true).when(spyVipParkingStrategy).isAllowOverPark(car);

        spyVipParkingStrategy.park(parkingLots, car);

        verify(spyVipParkingStrategy, times(1)).createReceipt(fullParkingLot, car);
        verify(spyVipParkingStrategy, times(0)).createNoSpaceReceipt(car);
    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */

        VipParkingStrategy spyVipParkingStrategy = spy(new VipParkingStrategy());

        ParkingLot fullParkingLot = new ParkingLot(PARKING_NAME_A, 1);
        fullParkingLot.getParkedCars().add(createMockCar("Dummy"));

        List<ParkingLot> parkingLots = Arrays.asList(fullParkingLot);
        Car car = createMockCar(CAR_NAME_AA111);

        doReturn(false).when(spyVipParkingStrategy).isAllowOverPark(car);

        spyVipParkingStrategy.park(parkingLots, car);

        verify(spyVipParkingStrategy, times(0)).createReceipt(fullParkingLot, car);
        verify(spyVipParkingStrategy, times(1)).createNoSpaceReceipt(car);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */

	    when(carDao.isVip(any())).thenReturn(true);
        boolean allowOverPark = injectedVipParkingStrategy.isAllowOverPark(createMockCar(CAR_NAME_AA111));
        Assert.assertTrue(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */

        when(carDao.isVip(any())).thenReturn(true);
        boolean allowOverPark = injectedVipParkingStrategy.isAllowOverPark(createMockCar(CAR_NAME_BB111));
        Assert.assertFalse(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse(){
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
