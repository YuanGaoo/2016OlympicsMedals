package OlympicsMedals;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class OlympicsMedals {
	WebDriver driver;


	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().fullscreen();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}


	@Test(priority = 0)
	public void SortTest() throws InterruptedException {
		String url = "https://en.wikipedia.org/wiki/2016_Summer_Olympics#Medal_table";
		driver.get(url);
		List<Integer> Rank = new ArrayList<>(addRank());

		System.out.println("actual--->" + Rank);

		assertSquenceNumber(Rank);
		Thread.sleep(500);

	}

	@Test(priority = 1)
	public void afterClick_NOC() throws InterruptedException {

		String NOCtaxXpath = "//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/thead/tr/th[2]";

		driver.findElement(By.xpath(NOCtaxXpath)).click();

		List<String> NOC = new ArrayList<>(addNOC());
		System.out.println("actual--->" + NOC);
		assertSquenceString(NOC);
		List<Integer> Rank = new ArrayList<>(addRank());
		SortedSet<Integer> sort = new TreeSet<Integer>(Rank);
		Iterator<Integer> it = sort.iterator();
		for (Integer each : Rank) {
			Integer num = it.next();
			if (each == num)
				continue;
			assertNotEquals(each, num);
		}
		Thread.sleep(500);

	}

	// 1. Go to website https://en.wikipedia.org/wiki/2016_Summer_Olympics.
	// 2. Write a method that returns the name of the country with the most number
	// of gold medals.
	@Test(priority = 2)
	public void theMostGold() throws InterruptedException {
		String url = "https://en.wikipedia.org/wiki/2016_Summer_Olympics";
		driver.navigate().to(url);
		
		String mostNumberOfGold=most_Number_of_Medals(addNOC(),addGold());
		String expected=" United States (USA)";
		System.out.println("actual--->"+mostNumberOfGold+",  expected---->"+expected);
		assertEquals(mostNumberOfGold.trim(), expected.trim());
		Thread.sleep(500);

	}
	@Test(priority = 3)
	public void theMostSilver() throws InterruptedException {

		String mostNumberOfSilver=most_Number_of_Medals(addNOC(),addSilver());
		assertEquals(mostNumberOfSilver.trim(), "United States (USA)");
		System.out.println("actual--->"+mostNumberOfSilver+",  expected----> United States (USA)");
		Thread.sleep(500);
	}
	@Test(priority = 4)
	public void theMostBronze() throws InterruptedException {
		String mostNumberOfBronze=most_Number_of_Medals(addNOC(),addBronze());
		assertEquals(mostNumberOfBronze.trim(), "United States (USA)");
		System.out.println("actual--->"+mostNumberOfBronze+",  expected----> United States (USA)");
		Thread.sleep(500);
	}
	@Test(priority = 5)
	public void theMostTotal() throws InterruptedException {
		String mostNumberOfTotal=most_Number_of_Medals(addNOC(),addTotal());
		assertEquals(mostNumberOfTotal.trim(), "United States (USA)");
		System.out.println("actual--->"+mostNumberOfTotal+", expected----> United States (USA)");
		Thread.sleep(500);
	}
	
	@Test(priority = 6)
	public void  silver_Medal_Count_IsEqual_to$18() throws InterruptedException{
			String url=" https://en.wikipedia.org/wiki/2016_Summer_Olympics";
			driver.navigate().to(url);
		 List<String> Actualname = countEqualToNum(addNOC(), addSilver(), 18);
		 List<String> expectname=new ArrayList<>();
		 expectname.add("China (CHN)");
		 expectname.add("France (FRA)");
		 assertEquals(Actualname, expectname);
		 System.out.println(Actualname);
		 Thread.sleep(500);
	}
	@Test(priority = 7)
	public void getIndex() throws InterruptedException {
		driver.navigate().to(" https://en.wikipedia.org/wiki/2016_Summer_Olympics");
		System.out.println("the rows and column of Japan is -->"+returnContryNameOfIndex());
		Thread.sleep(500);
		
		
	}
	//countries whose sum of bronze medals is 18.
	@Test(priority = 8)
	public void sumOfBronzeis18() throws InterruptedException {
		System.out.println("----------test 7----------");
		String url=" https://en.wikipedia.org/wiki/2016_Summer_Olympics";
		driver.navigate().to(url);
		
		List<String> actual = whoSumOfBronzeisNum(addBronze(),18);
		List<String> expact = new ArrayList<>();
		expact.add(" Australia (AUS)");
		expact.add(" Italy (ITA)");
		assertEquals(actual, expact);
		System.out.println("actual---->"+actual);
		System.out.println("expact---->"+expact);
		Thread.sleep(500);
	}
	// ------------------------------up testNG-----------------below up test functionality method------------------------------------------------
	
	
	
	
	public List<String> whoSumOfBronzeisNum(List<Integer> medals, int sum){
		int valuesOfTwoCountry1 = 0;
		int valuesOfTwoCountry2 = 0;
		System.out.println(medals);
		
		for (int i = 0; i < medals.size(); i++) { //0+1,0+2,0+3,0+4,0+5;  1+2,1+3,1+4,1+5;  2+3,2+4,2+5  ,3+4,3+5 
			for (int j = i+1 ; j < medals.size(); j++) {
				if(((medals.get(i)+medals.get(j))==18)) {
					
					valuesOfTwoCountry1=medals.get(i);// find values
					valuesOfTwoCountry2=medals.get(j);//find values
				}
			}
		}
		
		Map<String, Integer> map = add_info_ToMap(addNOC(), medals);
		Set<Entry<String, Integer>> set = map.entrySet();
		
		Iterator<Entry<String, Integer>> it = set.iterator();
		// turn type
		List<String>name= new ArrayList<>();
		while(it.hasNext()) {
			Map.Entry<String, Integer> me= it.next();
			if(me.getValue()==valuesOfTwoCountry1||me.getValue()==valuesOfTwoCountry2) {
				name.add(me.getKey());
			}
		}
		return name;
			
	}
	
	public Map<String,Integer> returnContryNameOfIndex() {
		Map<String, Integer> map = add_info_ToMap(addNOC(), addRank()); // expected
		Set<Entry<String, Integer>> set = map.entrySet();
		Map<String, Integer> index= new HashMap<>();
		Iterator<Entry<String, Integer>> it = set.iterator();
		while(it.hasNext()) {
			Map.Entry<String, Integer> me = it.next();
			if(me.getKey().contains("Japan")) {
				index.put(me.getKey(), me.getValue());
				assertEquals(me.getValue(),Integer.valueOf(6));
			}
		}	
	
		
			return index;
		
	}
	public void assertSquenceNumber(List<Integer> integerArray) {
		SortedSet<Integer> sort = new TreeSet<Integer>(integerArray);
		Iterator<Integer> it = sort.iterator();
		for (Integer each : integerArray) {
			assertEquals(each, it.next());
		}
		System.out.println("expect--->" + integerArray);
	}

	public void assertSquenceString(List<String> Stringarray) {
		SortedSet<String> sort = new TreeSet<String>(Stringarray);
		Iterator<String> it = sort.iterator();
		for (String each : Stringarray) {
			assertTrue(each.equals(it.next()));

		}
		System.out.println("expect--->" + sort);
	}

	// Write a method that returns the name of the country with the most number of gold medals.
	public Map<String,Integer> add_info_ToMap(List<String> key,List<Integer> values){
		// put key value to map
		Iterator<String> noc = key.iterator();
		Iterator<Integer> NumberOfMedals = values.iterator();
		Map<String, Integer> map = new TreeMap<>();
		while (noc.hasNext()) {
			map.put(noc.next(), NumberOfMedals.next());
		}
		return map;
	}
	
	//silver medal count is equal to 18.
	public List<String> countEqualToNum(List<String> key,List<Integer> values,Integer number) {
		
		List<String> countEqualToNum = new ArrayList<>();
		 Map<String, Integer> map = add_info_ToMap(key, values);
		 Set<Entry<String, Integer>> entry = map.entrySet();
		 Iterator<Entry<String, Integer>> it = entry.iterator();
		 while(it.hasNext()) {
			Map.Entry<String, Integer> each = it.next();
			if(each.getValue()==number) {
				countEqualToNum.add(each.getKey().trim());
			}
		 }
		 return countEqualToNum;
		
	}
	
	public String most_Number_of_Medals(List<String> key,List<Integer> values) {
		
		 Map<String,Integer>sort=add_info_ToMap(key, values);
		//iterat map , get maxnum, 
		Set<Entry<String, Integer>> set = sort.entrySet();
		Iterator<Entry<String, Integer>> iter = set.iterator();
		List<Integer> medals= new ArrayList<>(values);
		int maxNum = medals.get(0);
		for (int i = 1; i < medals.size(); i++) {
			if (medals.get(i) > maxNum) {
				maxNum = medals.get(i);
			}
		}
		String bigNumberCountry="";
		while (iter.hasNext()) {
			Map.Entry<String, Integer> me = iter.next();
			if(me.getValue()==Integer.valueOf(maxNum)) {
				bigNumberCountry=me.getKey();
			}

		}
		return bigNumberCountry;
	}

// ------------------------------up test functionality method-----------------below get each info method------------------------------------------------

	public List<Integer> addRank() {
		List<Integer> Rank = new ArrayList<>();
		for (int cowsCount = 1; cowsCount <= 10; cowsCount++) {
			String RankXpath = "//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["
					+ cowsCount + "]/td[1]";
			Integer RankInt = Integer.parseInt((driver.findElement(By.xpath(RankXpath)).getText()));
			Rank.add(RankInt);
		}
		return Rank;
	}

	public List<Integer> addGold() {
		List<Integer> Gold = new ArrayList<>();
		for (int cowsCount = 1; cowsCount <= 10; cowsCount++) {
			String GoldXpath = "//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["
					+ cowsCount + "]/td[2]";
			Integer GoldInt = Integer.parseInt((driver.findElement(By.xpath(GoldXpath)).getText()));
			Gold.add(GoldInt);

		}
		return Gold;

	}

	public List<Integer> addSilver() {
		List<Integer> Silver = new ArrayList<>();
		for (int cowsCount = 1; cowsCount <= 10; cowsCount++) {
			String SilverXpath = "//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["
					+ cowsCount + "]/td[3]";
			Integer SilverInt = Integer.parseInt((driver.findElement(By.xpath(SilverXpath)).getText()));
			Silver.add(SilverInt);

		}
		return Silver;

	}

	public List<Integer> addBronze() {
		List<Integer> Bronze = new ArrayList<>();
		for (int cowsCount = 1; cowsCount <= 10; cowsCount++) {
			String BronzeXpath = "//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["
					+ cowsCount + "]/td[4]";
			Integer BronzeInt = Integer.parseInt((driver.findElement(By.xpath(BronzeXpath)).getText()));
			Bronze.add(BronzeInt);

		}
		return Bronze;

	}

	public List<Integer> addTotal() {
		List<Integer> Total = new ArrayList<>();
		for (int cowsCount = 1; cowsCount <= 10; cowsCount++) {
			String TotalXpath = "//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["
					+ cowsCount + "]/td[5]";
			Integer TotalInt = Integer.parseInt((driver.findElement(By.xpath(TotalXpath)).getText()));
			Total.add(TotalInt);

		}
		return Total;
	}

	public List<String> addNOC() {
		List<String> NOC = new ArrayList<>();
		for (int cowsCount = 1; cowsCount <= 10; cowsCount++) {
			// country name
			String NOCXpath = "//table[@class='wikitable sortable plainrowheaders jquery-tablesorter']/tbody/tr["
					+ cowsCount + "]/th[1]";
			NOC.add(driver.findElement(By.xpath(NOCXpath)).getText());

		}
		return NOC;

	}


	@AfterClass
	public void close() {
		 driver.close();
	}

}
