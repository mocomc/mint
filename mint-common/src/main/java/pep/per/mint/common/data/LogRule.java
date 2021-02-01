package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class LogRule extends Rule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4756848287258163771L;
	
	/**
	 * SAVE_TYPE_DB 	- DB로 저장
	 * SAVE_TYPE_FILE	- FILE로 저장
	 */
	public final static int SAVE_TYPE_DB = 0;
	public final static int SAVE_TYPE_FILE = 1;
	
	/**
	 * 로그파일 변경 형식 
	 * FILE_SWITCH_TYPE_CIRCULAR 	- 순환 로그 생성 옵션
	 * FILE_SWITCH_TYPE_LINEAR_DAY	- 일별로그 생성 옵션
	 * FILE_SWITCH_TYPE_LINEAR_HOUR	- 시간별 로그 생성
	 */
	public final static int FILE_SWITCH_TYPE_CIRCULAR = 0;
	public final static int FILE_SWITCH_TYPE_LINEAR_DAY = 1;
	public final static int FILE_SWITCH_TYPE_LINEAR_HOUR = 2;
	
	/**
	 * DIVISION_TYPE_NONE		- 로그파일 분류기준 미분류(모든로그를 한나의 파일에 남김)
	 * DIVISION_TYPE_INTERFACE	- 로그파일 분류기준 - 인터페이스별 로그파일
	 * DIVISION_TYPE_BUSINESS	- 로그파일 분류기준 - 업무별 로그파일
	 */
	public final static int DIVISION_TYPE_NONE = 0;
	public final static int DIVISION_TYPE_INTERFACE = 1;
	public final static int DIVISION_TYPE_BUSINESS = 2;
	
	@JsonProperty
	int saveType;
	
	@JsonProperty
	boolean usable;
	
	@JsonProperty
	String prefix;
	
	@JsonProperty
	String savePath;
	
	@JsonProperty
	String ext = "log";
	
	@JsonProperty
	int fileSwitchType;
	
	@JsonProperty
	int fileSwitchTime;
	
	@JsonProperty
	int fileSwitchNum;
	
	@JsonProperty
	int fileSwitchSize;
	
	@JsonProperty
	int divisionType;
	
	@JsonProperty
	String backupPath;

	public LogRule(){
		super();
	}
	
	public int getSaveType() {
		return saveType;
	}

	public void setSaveType(int saveType) {
		this.saveType = saveType;
	}

	public boolean isUsable() {
		return usable;
	}

	public void setUsable(boolean usable) {
		this.usable = usable;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public int getFileSwitchType() {
		return fileSwitchType;
	}

	public void setFileSwitchType(int fileSwitchType) {
		this.fileSwitchType = fileSwitchType;
	}

	public int getFileSwitchTime() {
		return fileSwitchTime;
	}

	public void setFileSwitchTime(int fileSwitchTime) {
		this.fileSwitchTime = fileSwitchTime;
	}

	public int getFileSwitchNum() {
		return fileSwitchNum;
	}

	public void setFileSwitchNum(int fileSwitchNum) {
		this.fileSwitchNum = fileSwitchNum;
	}

	public int getFileSwitchSize() {
		return fileSwitchSize;
	}

	public void setFileSwitchSize(int fileSwitchSize) {
		this.fileSwitchSize = fileSwitchSize;
	}

	public int getDivisionType() {
		return divisionType;
	}

	public void setDivisionType(int divisionType) {
		this.divisionType = divisionType;
	}

	public String getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}
	
}
