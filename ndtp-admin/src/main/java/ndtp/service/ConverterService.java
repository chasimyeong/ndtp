package ndtp.service;

import java.util.List;

import ndtp.domain.ConverterJob;
import ndtp.domain.ConverterJobFile;

/**
 * f4d converting manager
 * @author Cheon JeongDae
 *
 */
public interface ConverterService {
	
	/**
	 * f4d converter 변환 job 등록
	 * @param converterJob
	 * @return
	 */
	public int insertConverter(ConverterJob converterJob);
	
	/**
	 * 데이터 변환 작업 상태를 변경
	 * @param converterJob
	 * @return
	 */
	public int updateConverterJob(ConverterJob converterJob);
}
