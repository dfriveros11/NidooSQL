package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Bill implements Serializable 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id of the Bill
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/**
	 * Creation date of the bill
	 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate;
	
	/**
	 * Total price of the Bill
	 */
	private Double totalPrice;
	
	/**
	 * The refund of the booking
	 */
	@JsonManagedReference(value="billRefund")
	@OneToMany(fetch=FetchType.LAZY, mappedBy="bill")
	private List<Refund> refunds;
	

	/**
	 * the booking of the bill
	 */
    @JsonBackReference(value="bookingBill")
	@ManyToOne(fetch=FetchType.EAGER)
	private Booking booking;

	public Bill() {}
	
	/**
	 * 
	 * @param id
	 */
	public Bill(long id) {
		super();
		this.id = id;
	}

	
	public Bill(Double totalPrice, Date creationDate, List<Refund> refunds, Booking booking) {
		super();
		this.totalPrice = totalPrice;
		this.creationDate=creationDate;
		this.booking=booking;
		this.refunds=refunds;
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
	 * @return the totalPrice
	 */
	public Double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the refunds
	 */
	public List<Refund> getRefunds() {
		return refunds;
	}

	/**
	 * @param refunds the refunds to set
	 */
	public void setRefunds(List<Refund> refunds) {
		this.refunds = refunds;
	}

	/**
	 * @return the booking
	 */
	public Booking getBooking() {
		return booking;
	}

	/**
	 * @param booking the booking to set
	 */
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
}
