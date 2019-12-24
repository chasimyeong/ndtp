package ndtp.domain;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 파일 기본 정보 관리 클래스
 * @author Cheon JeongDae
 *
 */
public class FileInfo {
	
	public FileInfo() {
	}
	
	// 사용자 ID
	private String userId;
	// 파일명 : 실제파일명 + date
	private String fileName;
	// 실제 파일명
	private String fileRealName;
	// 파일 경로
	private String filePath;
	// 파일 용량
	private String fileSize;
	// 파일 확장자
	private String fileExt;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp insertDate;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileRealName() {
		return fileRealName;
	}
	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public Timestamp getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}
	@Override
	public String toString() {
		return "FileInfo [userId=" + userId + ", fileName=" + fileName + ", fileRealName=" + fileRealName
				+ ", filePath=" + filePath + ", fileSize=" + fileSize + ", fileExt=" + fileExt + ", updateDate="
				+ updateDate + ", insertDate=" + insertDate + "]";
	}
}
