package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class ParkingConsumer extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 277595069729619742L;

	private int creditCard;
	
	@JsonBackReference
	@ManyToOne(fetch=FetchType.LAZY)
	private CorporationConsumer corporativeConsumer;
	
	@JsonManagedReference
	@OneToMany(fetch=FetchType.LAZY, mappedBy="parkingConsumer", cascade=CascadeType.ALL)
	private List<Vehicle> vehicles;
	


	public ParkingConsumer() {	}

	public ParkingConsumer(long id) {
		super(id);
	}

	public ParkingConsumer(@NotNull String name, @NotNull String password, @NotNull String nickName,
			@NotNull String email, int creditCard, List<Vehicle> vehicles, CorporationConsumer corporativeConsumer) {
		super(name, password, nickName, email);
		this.creditCard = creditCard;
		this.vehicles = vehicles;
		this.corporativeConsumer = corporativeConsumer;
	}

	public int getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(int creditCard) {
		this.creditCard = creditCard;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}
	
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	/**
	 * @return the corporativeConsumer
	 */
	public CorporationConsumer getCorporativeConsumer() {
		return corporativeConsumer;
	}

	/**
	 * @param corporativeConsumer the corporativeConsumer to set
	 */
	public void setCorporativeConsumer(CorporationConsumer corporativeConsumer) {
		this.corporativeConsumer = corporativeConsumer;
	}
	
	

}
