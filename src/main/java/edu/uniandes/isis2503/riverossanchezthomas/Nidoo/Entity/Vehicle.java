package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
public class Vehicle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8142842788363489374L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true)
	@Size(max=50)
	private String lincensePlate;
	
	private String carModel;
	
	private String carBrand;
	
	@JsonBackReference
	@ManyToOne(fetch=FetchType.LAZY)
	private ParkingConsumer parkingConsumer;
	
	
	@JsonIgnoreProperties("vehicles")
	@ManyToMany(fetch=FetchType.LAZY)
	private List<Booking> bookings;
	
	
	public Vehicle() {}
	
	public Vehicle(long id) {
		this.id = id;
	}

	public Vehicle(String lincensePlate, String carModel, String carBrand, ParkingConsumer parkingConsumer, List<Booking>bookings) {
		super();
		this.lincensePlate = lincensePlate;
		this.carModel = carModel;
		this.carBrand = carBrand;
		this.parkingConsumer = parkingConsumer;
		this.bookings=bookings;
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
	 * @return the lincensePlate
	 */
	public String getLincensePlate() {
		return lincensePlate;
	}

	/**
	 * @param lincensePlate the lincensePlate to set
	 */
	public void setLincensePlate(String lincensePlate) {
		this.lincensePlate = lincensePlate;
	}

	/**
	 * @return the carModel
	 */
	public String getCarModel() {
		return carModel;
	}

	/**
	 * @param carModel the carModel to set
	 */
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	/**
	 * @return the carBrand
	 */
	public String getCarBrand() {
		return carBrand;
	}

	/**
	 * @param carBrand the carBrand to set
	 */
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	/**
	 * @return the parkingConsumer
	 */
	public ParkingConsumer getParkingConsumer() {
		return parkingConsumer;
	}

	/**
	 * @param parkingConsumer the parkingConsumer to set
	 */
	public void setParkingConsumer(ParkingConsumer parkingConsumer) {
		this.parkingConsumer = parkingConsumer;
	}

	/**
	 * @return the bookings
	 */
	public List<Booking> getBookings() {
		return bookings;
	}

	/**
	 * @param bookings the bookings to set
	 */
	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	/**
	 * @param booking
	 */
	public void addOneBooking(Booking booking) {
		bookings.add(booking);
	}
	
}
