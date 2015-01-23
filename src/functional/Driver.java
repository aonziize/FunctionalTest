package functional;
import org.openqa.selenium.NoSuchElementException;

public class Driver { 
	private Utility util = new Utility();

	public String keyword_executor(String vKeyword, String vIP1, String vIP2,
			int row) throws Exception {

		String flag = "false";

		try {
			if (vKeyword.equals("browser_open")) {
				String typeBrowser = getIP(vIP1, row);
				String URL = getIP(vIP2, row);
				if (typeBrowser != null && URL != null) {
					util.browser_open(typeBrowser, URL);
					flag = "True";

					return "Pass";
				}
				// else {
				// return "Fail";
				// }
			}
			if (vKeyword.equals("radio_select")) {
				String xPath = vIP1;
				String text = getIP(vIP2, row);
				if (xPath != null && text != null) {
					util.radio_select(xPath, text);
					flag = "True";
					return "pass";
				}
				// else {
				// return "Fail";
				// }
			}
			if (vKeyword.equals("checkbox_set")) {
				util.checkbox_set(vIP1, getIP(vIP2, row));
				flag = "True";
				return "pass";
			}
			if (vKeyword.equals("list_select")) {
				util.list_select(vIP1, getIP(vIP2, row));
				flag = "True";
				return "pass";
			}
			if (vKeyword.equals("edit_input")) {
				String xPath = vIP1;
				String text = getIP(vIP2, row);
				util.edit_input(xPath, text);
				flag = "True";
				return "pass";
			}
			if (vKeyword.equals("button_click")) {
				util.button_click(vIP1);
				flag = "True";
				return "pass";
			}
			if (vKeyword.equals("click_link")) {
				util.click_link(vIP1);
				flag = "True";
				return "pass";
			}
			if (vKeyword.equals("dialog_click")) {
				if (util.dialog_click()) {
					flag = "True";
					return "Pass";
				}
				// else {
				// return "Fail";
				// }
			}
			if (vKeyword.equals("check_text")) {
				String text = getIP(vIP2, row);
				if (!text.equals("-")) {
					if (util.check_text(vIP1, text)) {
						flag = "True";
						return "Pass";
					}
					// else {
					// return "Fail";
					// }
				} else {
					flag = "True";
					return "Pass";
				}
			}
			if (vKeyword.equals("browser_close")) {
				if (util.browser_close()) {
					flag = "True";
					return "pass";
				} else {
					return "Fail";
				}
			}

			if (flag.equals("false")) {
				System.out.println("Keyword is missing" + vKeyword);
				// return "False-Keyword Missing";
				StartUp.vError = String.valueOf("False-Keyword Missing");
				return "Fail";
			}
		} catch (NoSuchElementException e) {
			System.out.println("Error is " + e);
			StartUp.vError = String.valueOf(e);

			return "Fail";
		}
		return "Unknown Keyword";
	}

	public String getIP(String vIP, int row) {
		String value = null;
		switch (vIP) {
		case "vUrl":
			value = StartUp.xTD[row][2];
			break;
		case "vBrowser":
			value = StartUp.xTD[row][3];
			break;
		case "vUsername":
			value = StartUp.xTD[row][4];
			break;
		case "vPassword":
			value = StartUp.xTD[row][5];
			break;
		case "vText":
			value = StartUp.xTD[row][6];
			break;
		default:
			value = null;
			break;
		}
		return value;
	}

	// capture picture
	public void takeScreenShot() {
	
		// cap picture error
		// vflag = "Fail";
		// File scrFile = ((TakesScreenshot)driver)
		// .getScreenshotAs(OutputType.FILE);
		// FileUtils.copyFile(scrFile, new File(
		// "D:\\selenium\\TRUEMOVE\\picture_cap\\picture_caption_truemaxx"
		// + tc + ".png"));
	}

}
