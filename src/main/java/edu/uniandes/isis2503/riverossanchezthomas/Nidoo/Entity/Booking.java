package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Booking implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id of the booking
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/**
	 * Beginning date of the boking
	 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date beginDate;
	
    /**
     * End dato of the booking
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endDate;
	
	/**
	 * The parking spot  of the booking
	 */
	@JsonBackReference(value="parkingSpotsBooking")
	@ManyToOne(fetch=FetchType.EAGER)
	private ParkingSpot parkingSpot;
	
	/**
	 * The guard  responsible of the booking
	 */
	@JsonBackReference(value="guardBooking")
	@ManyToOne(fetch=FetchType.EAGER)
	private Guard guard;
	
	/**
	 * The parking provider responsible of the booking
	 */
	@JsonBackReference(value="ParkningProviderbookings")
	@ManyToOne(fetch=FetchType.EAGER)
	private ParkingProvider parkingProvider;
	
	/**
	 * The bill of the booking
	 */
	@JsonManagedReference(value="bookingBill")
	@OneToMany(fetch=FetchType.LAZY, mappedBy="booking")
	private List<Bill> bills;

	@JsonIgnoreProperties("bookings")
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Vehicle> vehicles;
	
	
	public Booking() {}
	
	public Booking(long id) {
		this.id = id;
	}

	public Booking(Date endDate, ParkingProvider parkingProvider, Date beginDate, ParkingSpot parkingSpot,Guard guard,List<Bill>bill,List<Vehicle> vehicles) {
		super();
		this.endDate = endDate;
		this.beginDate=beginDate;
		this.parkingProvider=parkingProvider;
		this.parkingSpot=parkingSpot;
		this.guard=guard;
		this.bills=bill;
		this.vehicles=vehicles;
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
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the parkingProvider
	 */
	public ParkingProvider getParkingProvider() {
		return parkingProvider;
	}

	/**
	 * @param parkingProvider the parkingProvider to set
	 */
	public void setParkingProvider(ParkingProvider parkingProvider) {
		this.parkingProvider = parkingProvider;
	}

	/**
	 * @return the parkingSpot
	 */
	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}

	/**
	 * @param parkingSpot the parkingSpot to set
	 */
	public void setParkingSpot(ParkingSpot parkingSpot) {
		this.parkingSpot = parkingSpot;
	}

	/**
	 * @return the guard
	 */
	public Guard getGuard() {
		return guard;
	}

	/**
	 * @param guard the guard to set
	 */
	public void setGuard(Guard guard) {
		this.guard = guard;
	}

	/**
	 * @return the bill
	 */
	public List<Bill> getBills() {
		return bills;
	}

	/**
	 * @param bill the bill to set
	 */
	public void setBills(List<Bill> bill) {
		this.bills = bill;
	}

	/**
	 * @return the vehicles
	 */
	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	/**
	 * @param vehicles the vehicles to set
	 */
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	/**
	 * @param vehicle
	 */
	public void addOneVehicle(Vehicle vehicle) {
		vehicles.add(vehicle);
	}
}
