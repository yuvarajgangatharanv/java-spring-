package com.rabobank.statementprocessor.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.statementprocessor.objects.StatementRecord;

@Service
public class StatementProcessorService {

	String[] HEADERS = {"Reference Number", "Description"};
	/**
	 * Converts the CSV File to Java list
	 * @param inputFile
	 * @return
	 * @throws IOException
	 */
	public List<StatementRecord> processExcel(MultipartFile inputFile) throws IOException{
		List<StatementRecord> processedRecords = new ArrayList<>();

		InputStream inputStream = inputFile.getInputStream();
		try(CSVParser parser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT
				.withFirstRecordAsHeader()
				.withIgnoreHeaderCase()
				.withSkipHeaderRecord()
				.withTrim())){
			parser.getRecords().forEach(record -> {
				StatementRecord processedRecord = new StatementRecord();
				processedRecord.setReferenceNumber(Long.valueOf(record.get(0)));
				processedRecord.setAccountNumber(record.get(1));
				processedRecord.setDescription(record.get(2));
				processedRecord.setStartBalance(new BigDecimal(record.get(3)));
				processedRecord.setMutation(new BigDecimal(record.get(4)));
				processedRecord.setEndBalance(new BigDecimal(record.get(5)));
				processedRecords.add(processedRecord);
			});
		}

		return processedRecords;
	}

	/**
	 * Validate the records
	 * 
	 * @param inputRecords
	 */
	public void processRecords(List<StatementRecord> inputRecords){

		Set<Long> temp = new HashSet<>();
		Set<Long> duplicates = inputRecords.stream().map(record -> record.getReferenceNumber()).filter(record -> !temp.add(record)).collect(Collectors.toSet());
		inputRecords.parallelStream().forEach(record -> {
			if(record.getStartBalance().add(record.getMutation()).compareTo(record.getEndBalance()) != 0 || duplicates.contains(record.getReferenceNumber())){
				record.setHasError(true);
			}
		});
	}

	/**
	 * Creates the output csv
	 * @param inputRecords
	 * @param outputStream
	 * @throws IOException
	 */
	public void generateResult(List<StatementRecord> inputRecords, OutputStream outputStream) throws IOException{

		try(CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(outputStream), CSVFormat.DEFAULT
				.withHeader(HEADERS))) {
			inputRecords.stream().filter(StatementRecord::isHasError).forEach(record -> {
				try {
					printer.printRecord(record.getReferenceNumber(), record.getDescription());
				} catch (IOException e) {
					e.printStackTrace();
					//Go to next record
				}
			});
		}
	}
}
