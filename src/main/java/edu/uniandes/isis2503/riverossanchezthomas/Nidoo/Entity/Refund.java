package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Refund implements Serializable 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id of the refund
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/**
	 * Date of the creation of the refund
	 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	
    
    @JsonBackReference(value="billRefund")
	@ManyToOne(fetch=FetchType.EAGER)
	private Bill bill;
    
	/**
	 * Description of the refund
	 */
	private String description;

	
	public Refund() {}
	
	/**
	 * 
	 * @param id
	 */
	public Refund(long id) {
		super();
		this.id = id;
	}
	
	/**
	 * Constructor of the refund
	 * @param description
	 */
	public Refund(String description, Date creationDate, Bill bill)
	{
		super();
		this.description = description;
		this.creationDate=creationDate;
		this.bill=bill;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	 * @return the bill
	 */
	public Bill getBill() {
		return bill;
	}

	/**
	 * @param bill the bill to set
	 */
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
	
}
