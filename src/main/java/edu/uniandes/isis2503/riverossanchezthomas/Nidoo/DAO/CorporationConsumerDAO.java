package edu.uniandes.isis2503.riverossanchezthomas.Nidoo.DAO;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import edu.uniandes.isis2503.riverossanchezthomas.Nidoo.Entity.CorporationConsumer;

@Transactional
public interface CorporationConsumerDAO extends UserBaseDAO<CorporationConsumer>
{
	@Query("select u from #{#entityName} u where u.id = ?1")
	public CorporationConsumer findCorporationConsumerByID(long id);
	
	@Query("select u from #{#entityName} u")
	public List<CorporationConsumer> getAllCorporationConsumer();

}
