import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

public class Endtoend {

	WebDriver driver;
	int j;
	int activefiltercount;

	@BeforeClass
	public void setUp(){
		driver = new ChromeDriver();
		driver.get("https://todomvc.com/examples/react/dist/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@Test(priority = 1)
	public void toDoPage() {
		String actualTitle = driver.findElement(By.cssSelector("header[class='header'] h1")).getText();
		String expectedTitle = "todos";
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	@Test(priority = 2)
	public void enterTasks() {
		int i;
		for (i = 1; i <= 8; i++) {
			driver.findElement(By.id("todo-input")).sendKeys("Task" + i + Keys.ENTER);
			j = i;
		}

	}

	@Test(priority = 3)
	public void totalTasks() {
		List<WebElement> list = driver.findElements(By.xpath("//div[@class='view']"));
		System.out.println("Total entered tasks are " + list.size());
		int totallist = list.size();
		Assert.assertEquals(totallist, j);
		if (totallist == j) {
			System.out.println("All tasks are showing correctly");
		} else {
			System.out.println("All tasks are not showing correctly");
		}

	}

	@Test(priority = 4)
	public void selectUnselecttAllTasks() {
		driver.findElement(By.className("toggle-all")).click();
		WebElement list1 = driver.findElement(By.xpath("//input[@class='toggle']"));
		if (list1.isSelected()) {
			System.out.println("All tasks are selected");
		} else {
			System.out.println("All tasks are not selected");
		}
		driver.findElement(By.className("toggle-all")).click();

	}

	@Test(priority = 5)
	public void completedTasks() {
		driver.findElement(By.xpath("(//input[@type='checkbox'])[5]")).click();
		driver.findElement(By.xpath("(//input[@type='checkbox'])[6]")).click();
		List<WebElement> beforefilter = driver.findElements(By.xpath("//li[@class='completed']"));
		driver.findElement(By.linkText("Completed")).click();
		List<WebElement> afterfilter = driver.findElements(By.xpath("//li[@class='completed']"));
		if (beforefilter.equals(afterfilter)) {
			System.out.println("Completed tasks are showing correct after filtering");
		} else {
			System.out.println("Completed tasks are not showing correct after filtering");
		}

	}

	@Test(priority = 6)
	public void activeTasks() {
		driver.findElement(By.xpath("//a[@class='' and @href='#/']")).click();
		List<WebElement> allFilter = driver.findElements(By.xpath("//li[@class='' and @data-testid='todo-item']"));
		driver.findElement(By.linkText("Active")).click();
		List<WebElement> activefilter = driver.findElements(By.xpath("//li[@class='' and @data-testid='todo-item']"));
		activefiltercount = activefilter.size();
		if (allFilter.equals(activefilter)) {
			System.out.println("Active tasks are showing correct after filtering");
		} else {
			System.out.println("Active tasks are not showing correct after filtering");
		}

	}

	@Test(priority = 7)
	public void itemsLeft() {
		String left = driver.findElement(By.xpath("/html[1]/body[1]/section[1]/footer[1]/span[1]")).getText();
		String[] parts = left.split("\\s+");
		String num = parts[0];
		int count = Integer.parseInt(num);
		System.out.println("Number of items left: " + count);
		if (activefiltercount == count) {
			System.out.println("Items left is correct");
		} else {
			System.out.println("Items left is not correct");
		}

	}

	@Test(priority = 8)
	public void clearCompleted() {
		driver.findElement(By.className("clear-completed")).click();
		driver.findElement(By.linkText("Completed")).click();
		List<WebElement> clearList = driver.findElements(By.xpath("//li[@class='completed']"));
		int zeroList = clearList.size();
		Assert.assertEquals(zeroList, 0);
	}

	@AfterClass
	public void teatDown() {
		driver.quit();

	}

}
