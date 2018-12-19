package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.BillDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO.RefundDAO;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Bill;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Booking;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.Refund;

@Controller
@RequestMapping("/bills")
public class BillController {
	
	/**
	 * Connection to the DAO  of bill
	 */
	@Autowired
	private BillDAO billDAO;
	
	/**
	 * Connection to the DAO of refund
	 */
	@Autowired
	private RefundDAO refundDAO;
	
	
	/**
	 * /bills/create ->  create a new bill and save it in the database.
	 * @return a string describing if the bill is succesfully created or not.
	 */
	@PostMapping("/create")
	@ResponseBody
	public String createBill(@RequestBody @Valid Bill bill) {
		try {
			billDAO.save(bill);
		} catch (Exception ex) {
			return "Error creating the bill: " + ex.toString();
		}
		return "Bill succesfully created with id: " + bill.getId();
	}
	
	/**
	 * /bills/update -> updated the bills with passed
	 * @return A Bill updated
	 */
	@PutMapping("/update")
	@ResponseBody
	public Bill updateBill(@RequestBody @Valid Bill bill) {
		try {
			
			Bill oldBill = billDAO.findBillByID(bill.getId());
			if (oldBill==null) {
				throw new Exception("The Bill with id: " + bill.getId()+ " doesn´t exist in our database" );
			}
			oldBill.setTotalPrice(bill.getTotalPrice());
			oldBill.setCreationDate(bill.getCreationDate());
			billDAO.save(oldBill);
			return oldBill;
		} catch (Exception ex) {
			ex.toString();
			return null;
		}
	}
	
	/**
	 * /bills/getbyid/{id} -> return the bill having the passed id.
	 * @param id The id to search in the database.
	 * @return Bill with the id 
	 */
	@GetMapping("/getbyid/{id}")
	@ResponseBody
	public Bill getBillID(@PathVariable("id") long id) {
		try {
			Bill bill = billDAO.findBillByID(id);
			if (bill==null) {
				throw new Exception("The Bill with id: " + id+ " doesn´t exist in our database" );
			}
			return bill;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * /bills/allbills -> return all the bills..
	 * @return a empty list.
	 */
	@ResponseBody
	@GetMapping("/allbills")
	public List<Bill> getBills() {
		try {
			List<Bill> bills = billDAO.getAllBill();
			return bills;
		} catch (Exception ex) {
			return null;
		}
	}

	
	/**
	 * /bills/delete/{id} -> delete the bill having the passed id.
	 * @param id The id for the bills to delete
	 * @return A string describing if the bill is succesfully deleted or not.
	 */
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public String deleteBill(@PathVariable("id") long id) {
		try {
			if (billDAO.findBillByID(id)==null) {
				throw new Exception("The Bill with id: " + id+ " doesn´t exist in our database" );
			}
			billDAO.delete(new Bill(id));
		} catch (Exception ex) {
			return "Error deleting the bill:" + ex.toString();
		}
		return "bill succesfully deleted with id : " +id;
	}
	
	//REFUND
	/**
	 * 
	 * @param id
	 * @param refund
	 * @return
	 */
	@PostMapping("/create/{id}/refunds")
	@ResponseBody
	public String createBillRefund(@PathVariable("id") long id, @RequestBody @Valid Refund refund) 
	{
		try {
			Bill bill = billDAO.findBillByID(id);
			if (bill==null) {
				throw new Exception("The bill  with id:  " + id + " doesn't exist");
			}
			if (bill.getRefunds().size()!=0) {
				throw new Exception("The Bill with id: "+ id +" has already a refund");
			}
			refund.setBill(bill);
			refundDAO.save(refund);
		}catch(Exception ex) {
			return "Error creating refund  related with the bill  with id: " + id;
		}
		return "Refund  with id "+refund.getId() + " succesfully created linked to bill  with id: " + id;
	}


	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}/refunds")
	@ResponseBody
	public List<Refund> findBillRefund(@PathVariable("id") long id){
		try {
			Bill bill = billDAO.findBillByID(id);
			if (bill==null) {
				throw new Exception("The bill  with id:  " + id + " doesn't exist");
			}
			return bill.getRefunds();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@PutMapping("/update/{id}/refunds/{idR}")
	@ResponseBody
	public Refund updateBillRefund(@PathVariable("id") long id, @RequestBody @Valid Refund refund) {
		try {
			Bill bill = billDAO.findBillByID(id);
			if (bill==null) {
				throw new Exception("The bill  with id:  " + id + " doesn't exist");
			}
			Refund oldrefund = refundDAO.findRefundByID(refund.getId());
			if (oldrefund==null) {
				throw new Exception("The refund  with id:  " + refund.getId() + " doesn't exist");
			}
			oldrefund.setCreationDate(refund.getCreationDate());
			oldrefund.setDescription(refund.getDescription());
			oldrefund.setBill(bill);
			System.out.println(bill.getTotalPrice()+"");
			refundDAO.save(oldrefund);
			System.out.println("guardo");
			return oldrefund;
		} catch (Exception e) {
			return null;
		}
	}
	
	@DeleteMapping("/delete/{id}/refund/{idR}")
	@ResponseBody
	public String deleteBillRefund(@PathVariable("id") long id, @PathVariable("idR") long idR) {
		try {
			Bill bill = billDAO.findBillByID(id);
			if (bill==null) {
				throw new Exception("The bill  with id:  " + id + " doesn't exist");
			}
			Refund refund = refundDAO.findRefundByID(id);
			if (refund==null) {
				throw new Exception("The refund  with id:  " + idR + " doesn't exist");
			}
			refund.setBill(bill);
			refundDAO.delete(refund);
		} catch (Exception e) {
			return "Error deleting the refund :" + e.toString();
		}
		return "refund  with id "+ idR +"succesfully deleted!";
	}
	
	
	//Booking
	/**
	 * Find the booking  of a bill with idP
	 * @param idP of the bill
	 * @return the booking  associated to the bill
	 */
	@GetMapping("/{idP}/bookings")
	@ResponseBody
	public Booking findBillBooking(@PathVariable("idP") long idP){
		try {
			Bill bill = billDAO.findBillByID(idP);
			if (bill==null) {
				throw new Exception("The bill  with id:  " + idP + " doesn't exist");
			}
			return bill.getBooking();
		} catch (Exception e) {
			return null;
		}
		
	}

	
}
