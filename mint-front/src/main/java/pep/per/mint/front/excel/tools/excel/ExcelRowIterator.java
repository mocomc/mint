package pep.per.mint.front.excel.tools.excel;

public interface ExcelRowIterator {

	public void init();

	public boolean nextRow();

	public String getCellValue(int col);

	public int getCellCount();

	public byte getSheetIndex();

	public int getRowPos();

	public void prevRow();
}
