package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class ParkingSpot implements Serializable 
{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int number;
	
	
	@JsonBackReference(value="parkingSpotsParkingProvider")
	@ManyToOne(fetch = FetchType.EAGER)
	private ParkingProvider parkingProvider;
	
	@JsonBackReference(value="parkingParkingSpots")
	@ManyToOne(fetch = FetchType.EAGER)
	private Parking parking;
	
	@JsonManagedReference(value="parkingSpotsBooking")
	@OneToMany(fetch=FetchType.LAZY, mappedBy="parkingSpot")
	private List<Booking> bookings;
	
	
	public ParkingSpot() {}
	
	public ParkingSpot(long id) {
		this.id = id;
	}

	public ParkingSpot(int number, @NotNull  ParkingProvider parkingProvider ,  @NotNull  Parking parking, List<Booking> bookings) {
		super();
		this.number = number;
		this.parkingProvider = parkingProvider;
		this.parking=parking;
		this.bookings=bookings;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public ParkingProvider getParkingProvider() {
		return parkingProvider;
	}

	public void setParkingProvider(ParkingProvider parkingProvider) {
		this.parkingProvider = parkingProvider;
	}

	/**
	 * @return the parking
	 */
	public Parking getParking() {
		return parking;
	}

	/**
	 * @param parking the parking to set
	 */
	public void setParking(Parking parking) {
		this.parking = parking;
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

	
	
}
