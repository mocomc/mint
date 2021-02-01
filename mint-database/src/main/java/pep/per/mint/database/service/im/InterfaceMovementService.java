package pep.per.mint.database.service.im;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.PageInfo;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.an.RequirementService;

@Service
public class InterfaceMovementService {

	static Logger logger = LoggerFactory.getLogger(InterfaceMovementService.class);

	@Autowired
	RequirementService requirementService;

	public Map exportInterfaces(int countPerPage, String exportPath, String compressYn, String exportZipPath, String zipFileName) throws Exception {

		Map params = new HashMap();
		params.put("perCount", countPerPage);
		int page = 1;
		params.put("page", page);
		PageInfo pageInfo = requirementService.getRequirementsPageInfo(params);

		int totoalCount = pageInfo.getTotalCount();
		int tailCount = pageInfo.getTailCount();
		int pageCount = pageInfo.getPageCount();

		List<String> requirementIdList = requirementService.getRequirementIdList();
		if(requirementIdList.size() > 0){
			File exportFiles = new File(exportPath);
			if(exportFiles.exists()){
				//new File(exportPath).renameTo(new File(Util.join(exportPath,"-",Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI))));
				exportFiles.deleteOnExit();
			}
		}
		for( ; page <= pageCount ; page ++){

			int startIndex = (page - 1) * countPerPage + 1;
			int endIndex = startIndex + countPerPage - 1;

			List<String> requirements = new ArrayList<String>();
			for(int i = startIndex ; i <= endIndex ; i ++){
				String requiremnetId = requirementIdList.get(i);
				requirements.add(requiremnetId);
			}
			Map map = new HashMap();
			map.put("requirementIds", requirements);
			List<Requirement> requirementDetailList = requirementService.getRequirementDetailList(map);

			exportFile(requirementDetailList, exportPath, Util.join("export-",page,".json"));

		}

		if(tailCount > 0 ){

			int startIndex = (page - 1) * countPerPage + 1;



			List<String> requirements = new ArrayList<String>();
			for(int i = startIndex ; i < requirementIdList.size() ; i ++){
				String requiremnetId = requirementIdList.get(i);
				requirements.add(requiremnetId);
			}
			Map map = new HashMap();
			map.put("requirementIds", requirements);
			List<Requirement> requirementDetailList = requirementService.getRequirementDetailList(map);


			System.out.println("startIndex:" + startIndex);
			System.out.println("page:" + page);
			logger.debug("startIndex:" + startIndex);
			logger.debug("page:" + page);


			exportFile(requirementDetailList, exportPath, Util.join("export-",page,".json"));
		}

		if("Y".equals(compressYn) && requirementIdList.size() > 0){
			compress(exportPath, exportZipPath , zipFileName);
			File exportFiles = new File(exportPath);
			if(exportFiles.exists()){
				//new File(exportPath).renameTo(new File(Util.join(exportPath,"-",Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI))));
				exportFiles.deleteOnExit();
			}
		}

		Map res = params;
		return res;
	}

	private void compress(String srcPath, String exportZipPath, String zipFileName) throws IOException {
		// TODO Auto-generated method stub
		ZipOutputStream zos = null;
		OutputStream out = null;
		try{
			byte[] buffer = new byte[1024];

			File zipPath = new File(exportZipPath);
			if(!zipPath.exists()) zipPath.mkdirs();
			File zipFile = new File(zipPath, zipFileName);
			out = new FileOutputStream(zipFile);
			zos = new ZipOutputStream(out);
			File srcDir = new File(srcPath);
			File[] files = srcDir.listFiles();
			for(int i = 0 ; i < files.length ; i ++){
				FileInputStream fis = null;
				try{
					fis = new FileInputStream(files[i]);
					zos.putNextEntry(new ZipEntry(files[i].getName()));
					int length = 0;
					while((length = fis.read(buffer)) > 0){
						zos.write(buffer, 0, length);
					}
				}finally{
					zos.closeEntry();
					if(fis != null) fis.close();
				}
			}

		}finally{
			if(zos != null) zos.close();
		}
	}

	private void exportFile(List<Requirement> requirementDetailList, String exportPath, String fileName) throws Exception  {
		ByteArrayOutputStream baos = null;
		FileOutputStream fos = null;
		try{

			File path = new File(exportPath);
			if(!path.exists()){
				path.mkdirs();
			}

			File file = new File(path,fileName);
			baos = new ByteArrayOutputStream();

			String json = Util.toJSONString(requirementDetailList);
			baos.write(json.getBytes());

			fos = new FileOutputStream(file);
			byte b [] = baos.toByteArray();
			fos.write(b);
			fos.flush();
		}finally{
			if(baos != null) baos.close();
			if(fos != null) fos.close();
		}
	}

}
