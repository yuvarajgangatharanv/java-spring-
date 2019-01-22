package com.rabobank.statementprocessor.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.statementprocessor.objects.StatementRecord;
import com.rabobank.statementprocessor.service.StatementProcessorService;

/**
 * Controller for processing the uploaded statment
 * 
 * @author Yuvaraj
 *
 */
@RestController
@CrossOrigin
public class StatementProcessorController {

	@Autowired
	StatementProcessorService processorService;
	
	/**
	 * 
	 * Process the file uploaded and returns the output
	 * 
	 * @param inputFile
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/process", method = RequestMethod.PUT)
	public byte[] processStatement(@RequestParam("inputFile") MultipartFile inputFile, HttpServletResponse response){
		List<StatementRecord> processedRecords= new ArrayList<>();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
		processedRecords = processorService.processExcel(inputFile);
		processorService.processRecords(processedRecords);
		processorService.generateResult(processedRecords, outputStream);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}
}
