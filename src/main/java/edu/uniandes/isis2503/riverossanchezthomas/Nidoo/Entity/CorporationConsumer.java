package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity;

import java.util.List;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author TDJ
 */
@Entity
public class CorporationConsumer extends User {
	/**
	 * Serial number 
	 */
	private static final long serialVersionUID = -6426445484352143044L;

	/**
	 * Name of the representant of the Corporation 
	 */
	private String representant;

	/**
	 * Consumers of a  company 
	 */
	@JsonManagedReference
	@OneToMany(fetch=FetchType.LAZY, mappedBy="corporativeConsumer")
	private List<ParkingConsumer> parkingConsumers;
	
	/**
	 * 
	 */
	public CorporationConsumer() {}

	/**
	 * Constructor for the PK that it´s the id
	 * @param id
	 */
	public CorporationConsumer(long id) {
		super(id);
	}

	/**
	 * Constructor of the  class Corporation Consumer
	 * @param name 
	 * @param password
	 * @param nickName
	 * @param email
	 */
	public CorporationConsumer(@NotNull String name, @NotNull String password, @NotNull String nickName,
			@NotNull String email, String representant, List<ParkingConsumer> parkingConsumer) {
		super(name, password, nickName, email);
		this.representant=representant;
		this.parkingConsumers = parkingConsumer;
	}

	/**
	 * @return the representant
	 */
	public String getRepresentant() {
		return representant;
	}

	/**
	 * @param representant the representant to set
	 */
	public void setRepresentant(String representant) {
		this.representant = representant;
	}

	/**
	 * @return the consumers
	 */
	public List<ParkingConsumer> getParkingConsumers() {
		return parkingConsumers;
	}

	/**
	 * @param consumers the consumers to set
	 */
	public void setParkingConsumers(List<ParkingConsumer> parkingConsumers) {
		this.parkingConsumers = parkingConsumers;
	}
}
