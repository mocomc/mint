package pep.per.mint.front.service.im;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.upload.UploadSummary;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.ut.CommonUploadService;

@Service
public class InterfaceImportService {

	@Autowired
	CommonUploadService cs;

	@Async
	public void importDataAsync(File filePath, String filePostFix, String userId, String comments, File outputFolder, MultipartFile multipartFile) throws Exception{
		importData(filePath, filePostFix, userId, comments, outputFolder, multipartFile);
	}

	public void importData(File filePath, String filePostFix, String userId, String comments, File outputFolder, MultipartFile multipartFile) throws Exception{
		String resultCd = "0";
		String resultMsg = "요청을 처리중입니다.";
		String detailMsg = "";
		String processTime = Util.getFormatedDate();
		try {

			makeResultPage( resultCd,  resultMsg, detailMsg,  filePath, 0, 0, processTime) ;

			int totalCount = 0 ;
			int resultCount = 0 ;
			cs.deleteAllBeforeUpload();
			File [] importFiles = outputFolder.listFiles();
			for (File file : importFiles) {
				List list = (List) Util.readObjectFromJson(file, List.class, "UTF-8");
				if(list == null || list.size() == 0){
					throw new Exception(Util.join("인터페이스이관파일[",multipartFile.getOriginalFilename(),"]에 이관할 인터페이스 정보가 존재하지 않습니다."));
				}
				List<Requirement> requirements = new ArrayList<Requirement>();
				for (Object obj : list) {
				//logger.debug(Util.join("obj:", Util.toJSONString(obj)));
				Requirement requirement = Util.jsonMapper.convertValue(obj, Requirement.class);
				requirements.add(requirement);
				//logger.debug(Util.join("requirement:", Util.toJSONString(requirement)));

				}
				UploadSummary summary = new UploadSummary();
				summary.setComments(multipartFile.getOriginalFilename());
				summary.setBatchCount(requirements.size());
				summary.setFileName(File.separator + filePostFix);
				summary.setFilePath(filePath.getPath());
				summary.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				summary.setRegId(userId);
				summary.setComments(comments == null || comments.length() == 0 ? "import all interface data" : comments);

				int res = cs.upload(requirements, summary, userId);

				totalCount = totalCount + summary.getBatchCount();
				resultCount = resultCount + summary.getResultCount();
				resultCd = "1";
				resultMsg = "요청을 처리완료 하였습니다.";

			}


		}catch(Exception e){


			PrintWriter pw = null;
			try{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				pw = new PrintWriter(baos);
				e.printStackTrace(pw);
				pw.flush();
				if(pw != null)  detailMsg = baos.toString();
			}finally{
				if(pw != null) pw.close();
			}

			resultCd = "9";
			resultMsg = "요청을 처리중 예외가 발생하였습니다.";
		}finally{
			processTime = Util.getFormatedDate();
			makeResultPage( resultCd,  resultMsg, detailMsg,  filePath, 0, 0, processTime) ;
		}
	}



	private void makeResultPage(String resultCd, String resultMsg, String detailMsg, File filePath, int totalCount, int resultCount, String processTime) throws Exception {
		FileOutputStream fos = null;
		try{
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("resultCd", resultCd);
			result.put("resultMsg", resultMsg);
			result.put("detailMsg", detailMsg);
			result.put("totalCount", totalCount);
			result.put("resultCount", resultCount);
			String resultString = Util.toJSONString(result);
			File resultFile = new File(filePath, "result.json");
			fos = new FileOutputStream(resultFile);
			byte[] b = new byte[resultString.getBytes().length];
			fos.write(b, 0, b.length);
			fos.flush();
		}finally{
			if(fos != null) fos.close();
		}
	}

}
