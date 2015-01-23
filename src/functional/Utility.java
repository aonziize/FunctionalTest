package functional;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class Utility {
	private WebDriver web = null;

	public boolean browser_open(String browserType, String URL) {
		switch (browserType) {
		case "firefox":
			web = new FirefoxDriver();
			break;
		case "chrome":
			System.setProperty("webdriver.chrome.driver",
					"driver/chromedriver.exe");
			web = new ChromeDriver();
			break;
		case "internetexplorer":
			web = new InternetExplorerDriver();
			break;
		case "htmlUnit":
			web = new HtmlUnitDriver(BrowserVersion.CHROME);
			break;
		}
		if (web != null) {
			web.get(URL);
			web.manage().timeouts()
			.implicitlyWait(10, TimeUnit.SECONDS);
			return true;
		} else {
			return false;
		}
		
		
	}

	public void radio_select(String xPath, String fText) {
		web.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		List<WebElement> radio = web.findElements(By.name(xPath));
		for (int i = 0; i < radio.size(); i++) {
			if (radio.get(i).getAttribute("value").equals(fText)) {
				radio.get(i).click();
				return;
			}
		}
		// exception err
		throw new NoSuchElementException("Invalid Radio button Selected");
	}

	public void edit_input(String xPath, String text) {
		web.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		web.findElement(By.xpath(xPath)).sendKeys(text);
	}

	public void list_select(String xPath, String fText) {
		web.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Select select = new Select(web.findElement(By.xpath(xPath)));
		List<WebElement> options = select.getOptions();

		for (int i = 0; i < options.size(); i++) {
			if (options.get(i).getText().equals(fText)) {
				if (!options.get(i).isSelected())
					options.get(i).click();
				return;
			}
		}
		throw new NoSuchElementException("Invalid list Selected");
	}

	public void checkbox_set(String xPath, String fText) {
		web.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String[] extract = fText.split(",");
		if (extract.length == 4) {
			List<WebElement> checkBox = web.findElements(By.xpath(xPath));
			for (int i = 0; i < extract.length; i++) {
				if (extract[i].equalsIgnoreCase("ON")) {
					checkBox.get(i).click();
				}
			}
		} else {
			throw new NoSuchElementException("Invalid CheckBox Selected");
		}
	}

	public void button_click(String xPath) {
		web.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		web.findElement(By.xpath(xPath)).click();
	}

	public boolean check_text(String xPath, String text) {
		String vText = web.findElement(By.xpath(xPath)).getText();
		if (text.equals(vText) || text.contains(vText)) {
			return true;
		} else {
			return false;
		}
	}

	public void click_link(String fText) {
		web.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		web.findElement(By.linkText(fText)).click();
	}

	public boolean dialog_click() {
		web.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String text = "";
		WebDriverWait wait = new WebDriverWait(web, 2);

		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = web.switchTo().alert();
		text = alert.getText();
		alert.accept();
		String[] temp = text.split(":");

		if (temp[0].equals("ข้อมูลผิดพลาด"))
			return false;
		else
			return true;
	}

	public boolean browser_close() {
		web.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		try {
			web.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
