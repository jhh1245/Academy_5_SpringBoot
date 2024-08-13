package com.githrd.demo_fileupload.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Controller
public class FileUploadController {
    
    @Autowired
    ServletContext application;


    // upload1.do?title=제목&photo=a.jpg
    // @RequestMapping(value="/upload1.do", method=RequestMethod.POST)
    @PostMapping("/upload1.do")
    public String upload1(String title, @RequestParam(name="photo") MultipartFile photo, Model model) throws IllegalStateException, IOException{ // @RequestParam(name="photo") 생략 가능. 변수명 = url 파라미터명 동일할 경우 

        // 저장할 위치 정보 구하기 (절대 경로)
        String absPath = application.getRealPath("/images/");
        // System.out.println(absPath);


        // 파일 업로드 처리 
        String filename = "no_file";
        if(!photo.isEmpty()){ // 업로드 파일이 존재하면 
            
            filename = photo.getOriginalFilename();

            File f = new File(absPath, filename);

            if(f.exists()){ // 동일 파일명이 존재하면 
                long tm = System.currentTimeMillis();

                filename = String.format("%d_%s", tm, filename);

                f = new File(absPath, filename);
            }

            photo.transferTo(f);
        }

        // request Binding (Dispacher Servlet이 한다.)
        model.addAttribute("title", title);
        model.addAttribute("filename", filename);

        return "result1";

    }

    // upload2.do?title=제목&photo=a.jpg&photo=b.jpg
    @PostMapping("/upload2.do")
    public String upload2(String title, 
        @RequestParam(name="photo") MultipartFile[] photo_array,
        Model model) throws IllegalStateException, IOException {

            // 저장할 위치 정보 구하기(절대 경로)
            String absPath = application.getRealPath("/images/");

            String filename1 = "no_file";
            String filename2 = "no_file";

            for(int i=0; i<photo_array.length; i++){
                MultipartFile photo = photo_array[i];

                if(!photo.isEmpty()){ // 업로드 파일이 존재하면 
            
                    String filename = photo.getOriginalFilename();
        
                    File f = new File(absPath, filename);
        
                    if(f.exists()){ // 동일 파일명이 존재하면 
                        long tm = System.currentTimeMillis();
        
                        filename = String.format("%d_%s", tm, filename);
        
                        f = new File(absPath, filename);
                    }
        
                    photo.transferTo(f);

                    if(i==0){
                        filename1 = filename;
                    } else if(i==1) {
                        filename2 = filename;
                    }
                }
            } // end for 

            model.addAttribute("title", title);
            model.addAttribute("filename1", filename1);
            model.addAttribute("filename2", filename2);


        return "result2";
    }




    // upload3.do?title=제목&photo=a.jpg&photo=b.jpg
    @PostMapping("/upload3.do")
    public String upload3(String title, 
        @RequestParam(name="photo") List<MultipartFile> photo_list,
        Model model) throws IllegalStateException, IOException {

            // 저장할 위치 정보 구하기(절대 경로)
            String absPath = application.getRealPath("/images/");

            List<String> filename_list = new ArrayList<String>();

            for(MultipartFile photo : photo_list) {

                if(!photo.isEmpty()){ // 업로드 파일이 존재하면 
            
                    String filename = photo.getOriginalFilename();
        
                    File f = new File(absPath, filename);
        
                    if(f.exists()){ // 동일 파일명이 존재하면 
                        long tm = System.currentTimeMillis();
        
                        filename = String.format("%d_%s", tm, filename);
        
                        f = new File(absPath, filename);
                    }
        
                    photo.transferTo(f);

                    filename_list.add(filename);
                }
            } // end for 

            model.addAttribute("title", title);
            model.addAttribute("filename_list", filename_list);
            
        return "result3";
    }
}
