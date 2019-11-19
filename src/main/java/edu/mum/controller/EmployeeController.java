package edu.mum.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.mum.domain.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


import org.apache.commons.io.FilenameUtils;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.stereotype.Controller

public class EmployeeController {
	
	private static final Log logger = LogFactory.getLog(EmployeeController.class);

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value={"/","employee_input"})
	public String inputEmployee(@ModelAttribute("employee") Employee employee) {
		return "EmployeeForm";
	}

	@RequestMapping(value="/employee_save")
	public String saveEmployee(@Valid @ModelAttribute("employee")  Employee employee, BindingResult bindingResult,
			Model model) {
 		
		 String[] suppressedFields = bindingResult.getSuppressedFields();
		 if (suppressedFields.length > 0) {
 			 bindingResult.reject("foo","***YOU TRIED USING A SUPPRESSED FIELD***");
		 }


			if (bindingResult.hasErrors()) {
				return "EmployeeForm";
			}

		MultipartFile photo = employee.getPhoto();
		String rootDirectory = servletContext.getRealPath("/");
		if (photo != null && !photo.isEmpty()) {
			try {
				// ALL DATA IS ON CLASSES FOLDER
				String subDirectory = "\\resources\\images\\";
				File directory = new File(rootDirectory + subDirectory);
				if (!directory.isDirectory()) {
					directory.mkdirs();
				}
				String fileExtension = FilenameUtils.getExtension(photo.getOriginalFilename());
				String fileRealPath = directory.getAbsolutePath() + "\\" + employee.getId() + "." + fileExtension;
				photo.transferTo(new File(fileRealPath));
				employee.setPhotoUrl(subDirectory + employee.getId() + "." + fileExtension);
			} catch (Exception e) {
				throw new RuntimeException("Photo saving failed", e);
			}
		}
		
	    model.addAttribute("employee", employee);
	    
	   
		return "EmployeeDetails";
	}


	@ExceptionHandler
	public ModelAndView handlerError(HttpServletRequest request, RuntimeException exception) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exceptionStatement", exception.getMessage());
		modelAndView.setViewName("errorHandler");
		return modelAndView;
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
//        binder.setDisallowedFields(new String[]{"firstName"});
      }
}
