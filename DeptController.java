package egovframework.example.sample.web;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonObject;

import egovframework.example.sample.service.DeptService;
import egovframework.example.sample.service.DeptVO;
@Controller
public class DeptController {
	
	@Resource(name="deptService")
	private DeptService deptService;
	

	@RequestMapping(value="deptWrite.do")
	public String deptWrite() {
		
		return "deptWrite";
	}
	
	@RequestMapping(value="deptWriteSave.do")
	public String InsertDept(DeptVO vo) throws Exception{
		
		String con = vo.getLoc();
		con = con.replace("&lt;", "<");
		con = con.replace("&gt", ">");
		con = con.replace("&amp;lt", "<");
		con = con.replace("&amp;gt;", ">");
		con = con.replace("&amp;nbsp;", " ");
		con = con.replace("&amp;amp;", "&");
		con = con.replace("&quot;&quot;", "\"\"");
		con = con.replace("&quot;", "\"");
		con = con.replace(";","\n");
		vo.setLoc(con);
	     deptService.InsertDept(vo);

	     
//	        String result = deptService.InsertDept(vo);
//			if( result == null ) {   // ok
//				System.out.println("저장완료");
//			} else {
//				System.out.println("저장실패");
//			}
//			
			return "redirect:/deptList.do";

 
      
	}

	
	@RequestMapping(value="deptList.do")
	public String selectDeptList(DeptVO vo,ModelMap model) throws Exception {
		
		List<?> list = deptService.SelectDeptList(vo);
		//System.out.println(list);
		model.addAttribute("resultList",list);

		return "deptList";
	}
	
	@RequestMapping(value="deptDetail.do")
	public String selectDeptDetail(int deptno,ModelMap model) throws Exception {
		
		DeptVO vo = deptService.selectDeptDetail(deptno);
		System.out.println("부서번호:" + vo.getDeptno());
		model.addAttribute("deptVO",vo);

		return "deptDetail";
	}
	
	@RequestMapping(value="deptDelete.do")
	public String deleteDept(int deptno) throws Exception {
		
		int result = deptService.deleteDept(deptno);
		
		if( result == 1) {   // ok
			System.out.println("삭제완료");
		} else {
			System.out.println("삭제실패");
		}
		

		return "";
	}
	
	@RequestMapping(value="deptModifyWrite.do")
	public String selectDeptModify(int deptno,ModelMap model) throws Exception {
		
		DeptVO vo = deptService.selectDeptDetail(deptno);
		
		model.addAttribute("vo",vo);

		return "deptModifyWrite";
	}
	
	@RequestMapping(value="deptModifySave.do")
	public String updateDept(DeptVO vo) throws Exception {
		
		String con = vo.getLoc();
		con = con.replace("&lt;", "<");
		con = con.replace("&gt", ">");
		con = con.replace("&amp;lt", "<");
		con = con.replace("&amp;gt;", ">");
		con = con.replace("&amp;nbsp;", " ");
		con = con.replace("&amp;amp;", "&");
		con = con.replace("&quot;&quot;", "\"\"");
		con = con.replace("&quot;", "\"");
		con = con.replace(";","\n");
		vo.setLoc(con);
	     deptService.updateDept(vo);
		
	
	   
//		if( result == 1) {   // ok
//			System.out.println("수정완료");
//		} else {
//			System.out.println("수정실패");
//		}	   


		return "deptModifyWrite";
	}
	
//	  @RequestMapping(value="/imageUpload.do", method = RequestMethod.POST)
//	    public void imageUpload(HttpServletRequest request,
//	    		HttpServletResponse response, MultipartHttpServletRequest multiFile
//	    		, @RequestParam MultipartFile upload) throws Exception{
//	    	// 랜덤 문자 생성
//	    	UUID uid = UUID.randomUUID();
//	    	
//	    	OutputStream out = null;
//	    	PrintWriter printWriter = null;
//	    	
//	    	//인코딩
//	    	response.setCharacterEncoding("utf-8");
//	    	response.setContentType("text/html;charset=utf-8");
//	    	try{
//	    		//파일 이름 가져오기
//	    		String fileName = upload.getOriginalFilename();
//	    		byte[] bytes = upload.getBytes();
//	    		
//	    		//이미지 경로 생성
//	    		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/views/images");// 이미지 경로 설정(폴더 자동 생성)
//	    		String ckUploadPath = path + uid + "_" + fileName;
//	    		File folder = new File(path);
//	    		System.out.println("path:"+path);	// 이미지 저장경로 console에 확인
//	    		//해당 디렉토리 확인
//	    		if(!folder.exists()){
//	    			try{
//	    				folder.mkdirs(); // 폴더 생성
//	    		}catch(Exception e){
//	    			e.getStackTrace();
//	    		}
//	    	}
//	    	
//	    	out = new FileOutputStream(new File(ckUploadPath));
//	    	out.write(bytes);
//	    	out.flush(); // outputStram에 저장된 데이터를 전송하고 초기화
//	    	
//	    	String callback = request.getParameter("CKEditorFuncNum");
//	    	printWriter = response.getWriter();
//	    	String fileUrl = request.getContextPath() + "/WEB-INF/views/images/" +fileName; //url경로
//	    	
//	    	// 업로드시 메시지 출력
//	    	printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
//	    	printWriter.flush();
//	    	
//	    	}catch(IOException e){
//	    		e.printStackTrace();
//	    	} finally {
//	    		try {
//	    		if(out != null) { out.close(); }
//	    		if(printWriter != null) { printWriter.close(); }
//	    	} catch(IOException e) { e.printStackTrace(); }
//	    	}
//	    	return;
//	    }
//	  
//	// 서버로 전송된 이미지 뿌려주기
//	    @RequestMapping(value="/ckImgSubmit.do")
//	    public void ckSubmit(@RequestParam(value="uid") String uid
//	    		, @RequestParam(value="fileName") String fileName
//	    		, HttpServletRequest request, HttpServletResponse response)
//	    throws ServletException, IOException{
//	    	
//	    	//서버에 저장된 이미지 경로
//	    	String path = request.getSession().getServletContext().getRealPath("/WEB-INF/views/images");// 저장된 이미지 경로
//	    	System.out.println("path:"+path);
//	    	String sDirPath = path + uid + "_" + fileName;
//	    	
//	    	File imgFile = new File(sDirPath);
//	    	
//	    	//사진 이미지 찾지 못하는 경우 예외처리로 빈 이미지 파일을 설정한다.
//	    	if(imgFile.isFile()){
//	    		byte[] buf = new byte[1024];
//	    		int readByte = 0;
//	    		int length = 0;
//	    		byte[] imgBuf = null;
//	    		
//	    		FileInputStream fileInputStream = null;
//	    		ByteArrayOutputStream outputStream = null;
//	    		ServletOutputStream out = null;
//	    		
//	    		try{
//	    			fileInputStream = new FileInputStream(imgFile);
//	    			outputStream = new ByteArrayOutputStream();
//	    			out = response.getOutputStream();
//	    			
//	    			while((readByte = fileInputStream.read(buf)) != -1){
//	    				outputStream.write(buf, 0, readByte); 
//	    			}
//	    			
//	    			imgBuf = outputStream.toByteArray();
//	    			length = imgBuf.length;
//	    			out.write(imgBuf, 0, length);
//	    			out.flush();
//	    			
//	    		}catch(IOException e){
//	    			e.printStackTrace();
//	    		}finally {
//	    			outputStream.close();
//	    			fileInputStream.close();
//	    			out.close();
//	    			}
//	    		}
//	    }


//	   @RequestMapping("/imageUpload.do")
//	   
//	    // 이미지를 저장하고, 불러오고, 업로드하기위해 매개변수를 선언
//	    public void imageUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload)
//	//MultipartFile 타입은 ckedit에서 upload란 이름으로 저장하게 된다
//	            throws Exception {
//	 
//	        // 한글깨짐을 방지하기위해 문자셋 설정
//	        response.setCharacterEncoding("utf-8");
//	 
//	        // 마찬가지로 파라미터로 전달되는 response 객체의 한글 설정
//	        response.setContentType("text/html; charset=utf-8");
//	 
//	        // 업로드한 파일 이름
//	        String fileName = upload.getOriginalFilename();
//	 
//	        // 파일을 바이트 배열로 변환
//	        byte[] bytes = upload.getBytes();
//	 
//	        // 이미지를 업로드할 디렉토리(배포 디렉토리로 설정)
//	        String uploadPath = "C:\\class\\egovernment\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\aa\\WEB-INF\\views\\images";
//
//	        
//	OutputStream out = new FileOutputStream(new File(uploadPath + fileName));
//	 
//	        // 서버로 업로드
//	        // write메소드의 매개값으로 파일의 총 바이트를 매개값으로 준다.
//	        // 지정된 바이트를 출력 스트립에 쓴다 (출력하기 위해서)
//	        out.write(bytes);
//	 
//	        // 클라이언트에 결과 표시
//	        String callback = request.getParameter("CKEditorFuncNum");
//	 
//	        // 서버=>클라이언트로 텍스트 전송(자바스크립트 실행)
//	        PrintWriter printWriter = response.getWriter();
//	        String fileUrl = request.getContextPath() + "/images/" + fileName;
//	        printWriter.println("<script>window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + fileUrl
//	                + "','이미지가 업로드되었습니다.')" + "</script>");
//	        printWriter.flush();
//	    }
	
//	@ResponseBody
//	@RequestMapping(value = "fileupload.do")
//    public void communityImageUpload(HttpServletRequest req, HttpServletResponse resp, MultipartHttpServletRequest multiFile) throws Exception{
//		JsonObject jsonObject = new JsonObject();
//		PrintWriter printWriter = null;
//		OutputStream out = null;
//		MultipartFile file = multiFile.getFile("upload");
//		
//		if(file != null) {
//			if(file.getSize() >0 && StringUtils.isNotBlank(file.getName())) {
//				if(file.getContentType().toLowerCase().startsWith("image/")) {
//				    try{
//				    	 
//			            String fileName = file.getOriginalFilename();
//			            byte[] bytes = file.getBytes();
//			           
//			           // String uploadPath = req.getSession().getServletContext().getRealPath("/WEB-INF/views/images/"); //저장경
//			            String uploadPath = "C:\\Users\\c401\\Downloads\\ckeditor-main\\ckeditor-main\\aa (3)\\src\\main\\webapp\\WEB-INF\\views\\images";
//			            
//			            System.out.println("uploadPath:"+uploadPath);
//
//			            File uploadFile = new File(uploadPath);
//			            if(!uploadFile.exists()) {
//			            	uploadFile.mkdir();
//			            }
//			            String fileName2 = UUID.randomUUID().toString();
//			            uploadPath = uploadPath + "/" + fileName2 +fileName;
//			            
//			            out = new FileOutputStream(new File(uploadPath));
//			            out.write(bytes);
//			            
//			         
//			            String callback = req.getParameter("CKEditorFuncNum"); 
//			            printWriter = resp.getWriter();
//			            String fileUrl = "C:\\Users\\c401\\Downloads\\ckeditor-main\\ckeditor-main\\aa (3)\\src\\main\\webapp\\WEB-INF\\views\\images/" +fileName2 +fileName; //url경로
//			            System.out.println("fileUrl :" + fileUrl);
//			         
//			            JsonObject json = new JsonObject();
//			            json.addProperty("uploaded", 1);
//			            json.addProperty("fileName", fileName);
//			            json.addProperty("url", fileUrl);
//			            printWriter.print(json);
//			            System.out.println(json);
//			 
//			        }catch(IOException e){
//			            e.printStackTrace();
//			        } finally {
//			            if (out != null) {
//		                    out.close();
//		                }
//		                if (printWriter != null) {
//		                    printWriter.close();
//		                }
//			        }
//				}
//
//			
//		}
//		
//	}
//	}
//	
//	 @RequestMapping("imageUpload.do")
//	    public void imageUpload(HttpServletRequest request,
//	            HttpServletResponse response,
//	            MultipartFile upload) throws Exception {
//	        response.setCharacterEncoding("utf-8");
//	        response.setContentType("text/html; charset=utf-8");
//	 
//	        //업로드한 파일 이름
//	        String fileName=upload.getOriginalFilename();
//	 
//	        //파일을 바이트 배열로 변환
//	        byte[] bytes=upload.getBytes();
//	 
//	        //이미지를 업로드할 디렉토리(배포 디렉토리로 설정)
//	       
//	        		String uploadPath = request.getSession().getServletContext().getRealPath("/images/");
//	        OutputStream out=new FileOutputStream(
//	                new File(uploadPath+fileName));
//	 
//	        //서버로 업로드
//	        out.write(bytes);
//	        //클라이언트에 결과 표시
//	        String callback=request.getParameter("CKEditorFuncNum");
//	 
//	        //서버=>클라이언트로 텍스트 전송(자바스크립트 실행)
//	        PrintWriter printWriter=response.getWriter();
//	 
//	        String fileUrl=
//	                    request.getContextPath()+"/images/"+fileName;
//	 
//	        printWriter.println(
//	"<script>window.parent.CKEDITOR.tools.callFunction("
//	+callback+",'"+fileUrl+"','이미지가 업로드되었습니다.')"
//	+"</script>");
//	        printWriter.flush();
//	    }
//	 
	//원래했던거//ckeditor최종
	@RequestMapping(value="fileupload.do")
	public void fileupload(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multiFile
			,@RequestParam MultipartFile upload) throws Exception {
		
		OutputStream out = null;
		PrintWriter printWriter = null;
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		try {
			String fileName = upload.getOriginalFilename();
			System.out.println("파일이름"+fileName);
			byte[] bytes = upload.getBytes();
			String path = "C:\\Users\\c401\\Downloads\\ckeditor-main\\ckeditor-main\\aa (3)\\src\\main\\webapp\\WEB-INF\\views\\images\\";
			String ckUploadPath = path+fileName;
			File foler = new File(path); //생성
			System.out.println("path" + path);
			
			if(!foler.exists()) {
				try {
					foler.mkdir();
				}catch(Exception e) {
					e.getStackTrace();
				}
			}
			
			out = new FileOutputStream(new File(ckUploadPath));
			out.write(bytes);
			out.flush();
			
			String callback = request.getParameter("CKEditorFuncNum");
			printWriter = response.getWriter();
	    	String fileUrl = "ckImgSubmit.do?fileName=" + fileName; // 작성화면
	    	System.out.println("fileurl"+fileUrl);
	    	// 업로드시 메시지 출력
	    	printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
	    	printWriter.flush();
	    	
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
    		try {
    		if(out != null) { out.close(); }
    		if(printWriter != null) { printWriter.close(); }
    	} catch(Exception e) { e.printStackTrace(); }
    	}
    	return;
		
	}
	@RequestMapping(value="/ckImgSubmit.do")
    public void ckSubmit( @RequestParam(value="fileName") String fileName
    		, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
    	
    	//서버에 저장된 이미지 경로
    	String path = "C:\\Users\\c401\\Downloads\\ckeditor-main\\ckeditor-main\\aa (3)\\src\\main\\webapp\\WEB-INF\\views\\images\\";	// 저장된 이미지 경로
    	System.out.println("path"+path);
    	String sDirPath = path + fileName;
    	
    	File imgFile = new File(sDirPath);
    	
    	//사진 이미지 찾지 못하는 경우 예외처리로 빈 이미지 파일을 설정한다.
    	if(imgFile.isFile()){
    		byte[] buf = new byte[1024];
    		int readByte = 0;
    		int length = 0;
    		byte[] imgBuf = null;
    		
    		FileInputStream fileInputStream = null;
    		ByteArrayOutputStream outputStream = null;
    		ServletOutputStream out = null;
    		
    		try{
    			fileInputStream = new FileInputStream(imgFile);
    			outputStream = new ByteArrayOutputStream();
    			out = response.getOutputStream();
    			
    			while((readByte = fileInputStream.read(buf)) != -1){
    				outputStream.write(buf, 0, readByte); 
    			}
    			
    			imgBuf = outputStream.toByteArray();
    			length = imgBuf.length;
    			out.write(imgBuf, 0, length);
    			out.flush();
    			System.out.println(fileName);
    			
    		}catch(IOException e){
    			e.printStackTrace();
    		}finally {
    			outputStream.close();
    			fileInputStream.close();
    			out.close();
    			}
    		}
    } 

}






