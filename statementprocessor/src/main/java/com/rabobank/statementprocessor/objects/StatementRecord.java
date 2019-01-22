package com.rabobank.statementprocessor.objects;

import java.math.BigDecimal;

/**
 * Holds statement record values
 * 
 * @author Yuvaraj
 *
 */
public class StatementRecord {

	private Long referenceNumber;
	private String accountNumber;
	private String description;
	private BigDecimal startBalance;
	private BigDecimal endBalance;
	private BigDecimal mutation;
	private boolean hasError;
	
	/**
	 * @return the referenceNumber
	 */
	public Long getReferenceNumber() {
		return referenceNumber;
	}
	/**
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(Long referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the startBalance
	 */
	public BigDecimal getStartBalance() {
		return startBalance;
	}
	/**
	 * @param startBalance the startBalance to set
	 */
	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}
	/**
	 * @return the endBalance
	 */
	public BigDecimal getEndBalance() {
		return endBalance;
	}
	/**
	 * @param endBalance the endBalance to set
	 */
	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}
	/**
	 * @return the mutation
	 */
	public BigDecimal getMutation() {
		return mutation;
	}
	/**
	 * @param mutation the mutation to set
	 */
	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}
	public boolean isHasError() {
		return hasError;
	}
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	
	
	
}
