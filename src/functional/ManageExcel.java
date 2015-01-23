package functional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ManageExcel {

	private int xRows, xCols;

	private String xldata[][];
	private String xlFileInput;
	private String xlFileOutput;
	private XSSFWorkbook workbook;

	public ManageExcel(String xlFileInput, String xlFileOutput) {
		this.xlFileInput = xlFileInput;
		this.xlFileOutput = xlFileOutput;
		try {
			this.workbook = new XSSFWorkbook(new FileInputStream(
					this.getXlFileInput()));
		} catch (IOException e) {
			System.err
					.println("File not found !!! Or There are something wrong.");
		}
	}

	public String getXlFileInput() {
		return xlFileInput;
	}

	public void setXlFileInput(String xlFileInput) {
		this.xlFileInput = xlFileInput;
	}

	public String getXlFileOutput() {
		return xlFileOutput;
	}

	public void setXlFileOutput(String xlFileOutput) {
		this.xlFileOutput = xlFileOutput;
	}

	public XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public String[][] getTestCase() {
		String[][] tc = null;
		for (int index = 1; index < workbook.getNumberOfSheets(); index++) {
			String sheetName = workbook.getSheetName(index);
			if (sheetName.equalsIgnoreCase("testcases")) {
				try {
					tc = this.xlRead(index);
				} catch (Exception x) {
					System.err.println("Can't not get test cases.");
				}
			}
		}
		return tc;
	}

	public String[][] getTestStep() {
		String[][] ts = null;
		for (int index = 1; index < workbook.getNumberOfSheets(); index++) {
			String sheetName = workbook.getSheetName(index);
			if (sheetName.equalsIgnoreCase("teststeps")) {
				try {
					ts = this.xlRead(index);
				} catch (Exception x) {
					System.err.println("Can't not get test step.");
				}
			}
		}
		return ts;
	}

	public String[][] getTestData(String tcName) {
		String[][] td = null;
		for (int index = 1; index < workbook.getNumberOfSheets(); index++) {
			String sheetName = workbook.getSheetName(index);
			if (sheetName.equalsIgnoreCase(tcName)) {
				try {
					if (tcName != null) {
						td = this.xlRead(index);
					} else {
						System.err.println("Test case name is null !!! .");
					}
				} catch (Exception x) {
					System.err.println("Can't not get test Data.");
				}
			}
		}
		return td;
	}

	public String[][] xlRead(int sheetNo) throws Exception {
		xldata = null;
		XSSFSheet mySheet = this.workbook.getSheetAt(sheetNo); // Referring to
																// 1st sheet
		xRows = mySheet.getLastRowNum() + 1;
		xCols = mySheet.getRow(0).getLastCellNum();
		System.out.println("Rows " + sheetNo + " are " + xRows);
		System.out.println("Cols " + sheetNo + " are " + xCols);
		xldata = new String[xRows][xCols];
		for (int i = 0; i < xRows; i++) {
			XSSFRow row = mySheet.getRow(i);
			for (int j = 0; j < xCols; j++) {
				XSSFCell cell = row.getCell(j); // To read value from each col
												// in each row
				String value = cellToString(cell);
				xldata[i][j] = value;
			}
		}
		return xldata;
	}

	public String cellToString(XSSFCell cell) {

		int type = cell.getCellType();
		Object result;
		switch (type) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 0
			result = cell.getNumericCellValue();
			break;
		case HSSFCell.CELL_TYPE_STRING: // 1
			result = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_FORMULA: // 2
			throw new RuntimeException("We can't evaluate formulas in Java");
		case HSSFCell.CELL_TYPE_BLANK: // 3
			result = "-";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN: // 4
			result = cell.getBooleanCellValue();
			break;
		case HSSFCell.CELL_TYPE_ERROR: // 5
			throw new RuntimeException("This cell has an error");
		default:
			throw new RuntimeException("We don't support this cell type: "
					+ type);
		}
		return result.toString();
	}

	public void xlWrite(String xlFileOutput) throws Exception {
		
		
		for (int n = 1; n < workbook.getNumberOfSheets(); n++) {
			 System.out.println("Sheet name: " + workbook.getSheetName(n));
		}
		
		
		
		
		FileOutputStream fileExcelOutput = new FileOutputStream(new File(
				xlFileOutput));
		workbook.write(fileExcelOutput);		
		fileExcelOutput.flush();
		fileExcelOutput.close();

	}
}
