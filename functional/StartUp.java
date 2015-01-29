package functional;



public class StartUp {
	public static String vResult, vError, vflag;
	public static String xTD[][] = null;

	public static void main(String[] args) throws Exception {

		String xTS[][] = null; 
		String xTC[][] = null;
		String vKeyword, vIP1, vIP2;
		String xlFileInput = "D:\\selenium\\TRUEMOVE\\TrueData.xlsx";
		// read
		String xlFileOutput = "D:\\selenium\\TRUEMOVE\\TrueData_ResultTest.xlsx";

		Driver keywordDriver = new Driver();
		ManageExcel manageExcel = new ManageExcel(xlFileInput, xlFileOutput);

		xTC = manageExcel.getTestCase();
		xTS = manageExcel.getTestStep();

		for (int tc = 1; tc < xTC.length; tc++) { // Loop of Test case.
			if (xTC[tc][4].equals("Y")) {
				vflag = "Pass";
				String tcName = xTC[tc][2]; // Get TC Name.
				xTD = manageExcel.getTestData(tcName);
				for (int td = 1; td < xTD.length; td++) { // Loop of Test data.
					if (xTD[td][1].equals("Y")) {
						for (int ts = 1; ts < xTS.length; ts++) {// Loop of Test
																	// step.
							if (xTC[tc][1].equals(xTS[ts][0])) {
								vKeyword = xTS[ts][4];
								vIP1 = xTS[ts][5];
								vIP2 = xTS[ts][6];
								System.out.println("---" + vKeyword + "````"
										+ vIP1 + "````" + vIP2);
								vResult = "Pass";
								vError = "No Error";
								vResult = keywordDriver.keyword_executor(
										vKeyword, vIP1, vIP2, td);

								xTS[ts][7] = vResult;
								xTS[ts][8] = vError;
								if (!vError.equals("No Error")) {
									vflag = "Fail";
									// cap picture error
									keywordDriver.takeScreenShot(xTC[tc][1],
											xTD[td][0]);
									// File scrFile = ((TakesScreenshot)driver)
									// .getScreenshotAs(OutputType.FILE);
									// FileUtils.copyFile(scrFile, new File(
									// "D:\\selenium\\TRUEMOVE\\picture_cap\\picture_caption_truemaxx"
									// + tc + ".png"));
								}
							}
						}
					}
				}
				xTC[tc][5] = vflag;
			}
		}

		manageExcel.xlWrite(xlFileOutput);

	}
}
