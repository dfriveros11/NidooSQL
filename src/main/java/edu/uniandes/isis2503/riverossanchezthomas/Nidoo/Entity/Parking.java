package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Parking implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="parking_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	 
	/**
	 * Name of the Parking
	 */
	@NotNull 
	private String name;
	
	/**
	 * Addres of the parking
	 */
	@NotNull 
	@Size(max=50)
	private String address;
	
	/**
	 * Parking provider in charge 
	 */
	@JsonBackReference(value="parkingParkingSpot")
	@ManyToOne(fetch=FetchType.EAGER)
	private ParkingProvider parkingProvider;
	
	/**
	 * List of guards
	 */
	@JsonManagedReference
	@OneToMany(fetch=FetchType.LAZY,mappedBy="parking",cascade = CascadeType.ALL)
	private List<Guard> guards;
	
	/**
	 * List of parking spots
	 */
	@JsonManagedReference(value="parkingParkingSpots")
	@OneToMany(fetch=FetchType.LAZY,mappedBy="parking",cascade = CascadeType.ALL)
	private List<ParkingSpot> parkingspots;
	
	public Parking() {}

	public Parking(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @param name
	 * @param address
	 * @param parkingProvider
	 * @param parkingspots
	 * @param guards
	 */
	public Parking(  @NotNull String name,   @NotNull  String address, @NotNull ParkingProvider parkingProvider, List<ParkingSpot> parkingspots, List<Guard> guards) 
	{
		super();
		this.name = name;
		this.address = address;
		this.parkingProvider = parkingProvider;
		this.parkingspots = parkingspots;
		this.guards=guards;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ParkingProvider getParkingProvider() {
		return parkingProvider;
	}

	public void setParkingProvider(ParkingProvider parkingProvider) {
		this.parkingProvider = parkingProvider;
	}

	/**
	 * @return the parkingspots
	 */
	public List<ParkingSpot> getParkingspots() {
		return parkingspots;
	}

	/**
	 * @param parkingspots the parkingspots to set
	 */
	public void setParkingspots(List<ParkingSpot> parkingspots) {
		this.parkingspots = parkingspots;
	}

	/**
	 * @return the guards
	 */
	public List<Guard> getGuards() {
		return guards;
	}

	/**
	 * @param guards the guards to set
	 */
	public void setGuards(List<Guard> guards) {
		this.guards = guards;
	}

	
	
}
