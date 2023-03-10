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
//				System.out.println("????????????");
//			} else {
//				System.out.println("????????????");
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
		System.out.println("????????????:" + vo.getDeptno());
		model.addAttribute("deptVO",vo);

		return "deptDetail";
	}
	
	@RequestMapping(value="deptDelete.do")
	public String deleteDept(int deptno) throws Exception {
		
		int result = deptService.deleteDept(deptno);
		
		if( result == 1) {   // ok
			System.out.println("????????????");
		} else {
			System.out.println("????????????");
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
//			System.out.println("????????????");
//		} else {
//			System.out.println("????????????");
//		}	   


		return "deptModifyWrite";
	}
	
//	  @RequestMapping(value="/imageUpload.do", method = RequestMethod.POST)
//	    public void imageUpload(HttpServletRequest request,
//	    		HttpServletResponse response, MultipartHttpServletRequest multiFile
//	    		, @RequestParam MultipartFile upload) throws Exception{
//	    	// ?????? ?????? ??????
//	    	UUID uid = UUID.randomUUID();
//	    	
//	    	OutputStream out = null;
//	    	PrintWriter printWriter = null;
//	    	
//	    	//?????????
//	    	response.setCharacterEncoding("utf-8");
//	    	response.setContentType("text/html;charset=utf-8");
//	    	try{
//	    		//?????? ?????? ????????????
//	    		String fileName = upload.getOriginalFilename();
//	    		byte[] bytes = upload.getBytes();
//	    		
//	    		//????????? ?????? ??????
//	    		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/views/images");// ????????? ?????? ??????(?????? ?????? ??????)
//	    		String ckUploadPath = path + uid + "_" + fileName;
//	    		File folder = new File(path);
//	    		System.out.println("path:"+path);	// ????????? ???????????? console??? ??????
//	    		//?????? ???????????? ??????
//	    		if(!folder.exists()){
//	    			try{
//	    				folder.mkdirs(); // ?????? ??????
//	    		}catch(Exception e){
//	    			e.getStackTrace();
//	    		}
//	    	}
//	    	
//	    	out = new FileOutputStream(new File(ckUploadPath));
//	    	out.write(bytes);
//	    	out.flush(); // outputStram??? ????????? ???????????? ???????????? ?????????
//	    	
//	    	String callback = request.getParameter("CKEditorFuncNum");
//	    	printWriter = response.getWriter();
//	    	String fileUrl = request.getContextPath() + "/WEB-INF/views/images/" +fileName; //url??????
//	    	
//	    	// ???????????? ????????? ??????
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
//	// ????????? ????????? ????????? ????????????
//	    @RequestMapping(value="/ckImgSubmit.do")
//	    public void ckSubmit(@RequestParam(value="uid") String uid
//	    		, @RequestParam(value="fileName") String fileName
//	    		, HttpServletRequest request, HttpServletResponse response)
//	    throws ServletException, IOException{
//	    	
//	    	//????????? ????????? ????????? ??????
//	    	String path = request.getSession().getServletContext().getRealPath("/WEB-INF/views/images");// ????????? ????????? ??????
//	    	System.out.println("path:"+path);
//	    	String sDirPath = path + uid + "_" + fileName;
//	    	
//	    	File imgFile = new File(sDirPath);
//	    	
//	    	//?????? ????????? ?????? ????????? ?????? ??????????????? ??? ????????? ????????? ????????????.
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
//	    // ???????????? ????????????, ????????????, ????????????????????? ??????????????? ??????
//	    public void imageUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload)
//	//MultipartFile ????????? ckedit?????? upload??? ???????????? ???????????? ??????
//	            throws Exception {
//	 
//	        // ??????????????? ?????????????????? ????????? ??????
//	        response.setCharacterEncoding("utf-8");
//	 
//	        // ??????????????? ??????????????? ???????????? response ????????? ?????? ??????
//	        response.setContentType("text/html; charset=utf-8");
//	 
//	        // ???????????? ?????? ??????
//	        String fileName = upload.getOriginalFilename();
//	 
//	        // ????????? ????????? ????????? ??????
//	        byte[] bytes = upload.getBytes();
//	 
//	        // ???????????? ???????????? ????????????(?????? ??????????????? ??????)
//	        String uploadPath = "C:\\class\\egovernment\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\aa\\WEB-INF\\views\\images";
//
//	        
//	OutputStream out = new FileOutputStream(new File(uploadPath + fileName));
//	 
//	        // ????????? ?????????
//	        // write???????????? ??????????????? ????????? ??? ???????????? ??????????????? ??????.
//	        // ????????? ???????????? ?????? ???????????? ?????? (???????????? ?????????)
//	        out.write(bytes);
//	 
//	        // ?????????????????? ?????? ??????
//	        String callback = request.getParameter("CKEditorFuncNum");
//	 
//	        // ??????=>?????????????????? ????????? ??????(?????????????????? ??????)
//	        PrintWriter printWriter = response.getWriter();
//	        String fileUrl = request.getContextPath() + "/images/" + fileName;
//	        printWriter.println("<script>window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + fileUrl
//	                + "','???????????? ????????????????????????.')" + "</script>");
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
//			           // String uploadPath = req.getSession().getServletContext().getRealPath("/WEB-INF/views/images/"); //?????????
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
//			            String fileUrl = "C:\\Users\\c401\\Downloads\\ckeditor-main\\ckeditor-main\\aa (3)\\src\\main\\webapp\\WEB-INF\\views\\images/" +fileName2 +fileName; //url??????
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
//	        //???????????? ?????? ??????
//	        String fileName=upload.getOriginalFilename();
//	 
//	        //????????? ????????? ????????? ??????
//	        byte[] bytes=upload.getBytes();
//	 
//	        //???????????? ???????????? ????????????(?????? ??????????????? ??????)
//	       
//	        		String uploadPath = request.getSession().getServletContext().getRealPath("/images/");
//	        OutputStream out=new FileOutputStream(
//	                new File(uploadPath+fileName));
//	 
//	        //????????? ?????????
//	        out.write(bytes);
//	        //?????????????????? ?????? ??????
//	        String callback=request.getParameter("CKEditorFuncNum");
//	 
//	        //??????=>?????????????????? ????????? ??????(?????????????????? ??????)
//	        PrintWriter printWriter=response.getWriter();
//	 
//	        String fileUrl=
//	                    request.getContextPath()+"/images/"+fileName;
//	 
//	        printWriter.println(
//	"<script>window.parent.CKEDITOR.tools.callFunction("
//	+callback+",'"+fileUrl+"','???????????? ????????????????????????.')"
//	+"</script>");
//	        printWriter.flush();
//	    }
//	 
	//???????????????//ckeditor??????
	@RequestMapping(value="fileupload.do")
	public void fileupload(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multiFile
			,@RequestParam MultipartFile upload) throws Exception {
		
		OutputStream out = null;
		PrintWriter printWriter = null;
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		try {
			String fileName = upload.getOriginalFilename();
			System.out.println("????????????"+fileName);
			byte[] bytes = upload.getBytes();
			String path = "C:\\Users\\c401\\Downloads\\ckeditor-main\\ckeditor-main\\aa (3)\\src\\main\\webapp\\WEB-INF\\views\\images\\";
			String ckUploadPath = path+fileName;
			File foler = new File(path); //??????
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
	    	String fileUrl = "ckImgSubmit.do?fileName=" + fileName; // ????????????
	    	System.out.println("fileurl"+fileUrl);
	    	// ???????????? ????????? ??????
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
    	
    	//????????? ????????? ????????? ??????
    	String path = "C:\\Users\\c401\\Downloads\\ckeditor-main\\ckeditor-main\\aa (3)\\src\\main\\webapp\\WEB-INF\\views\\images\\";	// ????????? ????????? ??????
    	System.out.println("path"+path);
    	String sDirPath = path + fileName;
    	
    	File imgFile = new File(sDirPath);
    	
    	//?????? ????????? ?????? ????????? ?????? ??????????????? ??? ????????? ????????? ????????????.
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






