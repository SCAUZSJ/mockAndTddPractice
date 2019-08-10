package sales;

import mockit.integration.junit4.JMockit;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.lang.reflect.Array;
import java.util.*;

@RunWith(JMockit.class)
public class SalesAppTest {

	@Test
	public void testGenerateReport() {
		//Given
		SalesApp salesApp = spy(new SalesApp());
		Sales sales = spy(new Sales());
		//When
		new mockit.MockUp<SalesDao>() {
			@mockit.Mock
			public Sales getSalesBySalesId(String salesId) {
				return sales;
			}
		};
		new mockit.MockUp<SalesReportDao>() {
			@mockit.Mock
			public List<SalesReportData> getReportData(Sales sales) {
				return null;
			}
		};
		new mockit.MockUp<SalesApp>() {
			@mockit.Mock
			public List<SalesReportData> getFilterReportData(boolean isSupervisor, SalesReportDao salesReportDao, List<SalesReportData> filteredReportDataList, Sales sales) {
				return null;
			}
		};

		new mockit.MockUp<SalesApp>() {
			@mockit.Mock
			public List<SalesReportData> getTempList(int maxRow, List<SalesReportData> reportDataList) {
				return null;
			}
		};
		new mockit.MockUp<SalesApp>() {
			@mockit.Mock
			public SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
				return new SalesActivityReport();
			}
		};
		new mockit.MockUp<SalesActivityReport>() {
			@mockit.Mock
			public String toXml() {
				return "xml";
			}
		};
		doReturn(false).when(salesApp).checkEffective(sales);
		salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);
		//when
		verify(salesApp,times(1)).generateReport(Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time"),null);
	}

	@Test
	public void testCheckEffective_givenEffectiveToAndEffectiveFrom_thenReturnFalse(){

		//Given
		Sales sales = spy(new Sales());
		SalesApp salesApp = spy(new SalesApp());
		//When
		doReturn(new Date(System.currentTimeMillis()+86400)).when(sales).getEffectiveTo();
		doReturn(new Date(System.currentTimeMillis()-86400)).when(sales).getEffectiveFrom();
		//Then
		Assert.assertEquals(false,salesApp.checkEffective(sales));
	}
	@Test
	public void testGetFilterReportData_givenTwoSalesReportData_thenFilterOne(){

		//Given
		Sales sales = spy(new Sales());
		SalesApp salesApp = spy(new SalesApp());
		SalesReportDao salesReportDao = spy(new SalesReportDao());
		SalesReportData salesReportData1 = spy(new SalesReportData());
		SalesReportData salesReportData2 = spy(new SalesReportData());
 		List<SalesReportData> salesReportDataList = spy(new ArrayList<SalesReportData>());

		//When
		doReturn(Arrays.asList(salesReportData1,salesReportData2)).when(salesReportDao).getReportData(sales);

		doReturn("SalesActivity").when(salesReportData1).getType();
		doReturn(true).when(salesReportData1).isConfidential();

		doReturn("no").when(salesReportData2).getType();

		salesApp.getFilterReportData(true,salesReportDao,salesReportDataList,sales);

		//Then
		verify(salesReportDataList,times(1)).add(any());
	}

	@Test
	public void testGetTempList_givenReportDataListAndMaxRow_thenReturnList(){
		//Given
		SalesApp salesApp = spy(new SalesApp());
		List<SalesReportData> result = salesApp.getTempList(1,Arrays.asList(new SalesReportData(),new SalesReportData()));
		Assert.assertEquals(2,result.size());
	}
	@Test
	public void generateSalesActivityReport() {

		//Given
		SalesApp salesApp = spy(new SalesApp());
		Sales sales = spy(new Sales());
		//When
		new mockit.MockUp<SalesDao>() {
			@mockit.Mock
			public Sales getSalesBySalesId(String salesId) {
				return sales;
			}
		};
		new mockit.MockUp<SalesReportDao>() {
			@mockit.Mock
			public List<SalesReportData> getReportData(Sales sales) {
				return null;
			}
		};
		new mockit.MockUp<SalesApp>() {
			@mockit.Mock
			public List<SalesReportData> getFilterReportData(boolean isSupervisor, SalesReportDao salesReportDao, List<SalesReportData> filteredReportDataList, Sales sales) {
				return null;
			}
		};

		new mockit.MockUp<SalesApp>() {
			@mockit.Mock
			public List<SalesReportData> getTempList(int maxRow, List<SalesReportData> reportDataList) {
				return null;
			}
		};
		new mockit.MockUp<SalesApp>() {
			@mockit.Mock
			public SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
				return new SalesActivityReport();
			}
		};
		new mockit.MockUp<SalesActivityReport>() {
			@mockit.Mock
			public String toXml() {
				return "xml";
			}
		};
		doReturn(false).when(salesApp).checkEffective(sales);

		//then
		try {
			salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);
		} catch (Exception e) {
			Assert.fail();
		}

	}


}
