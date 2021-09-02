package codigo.codetest.estore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import codigo.codetest.estore.domain.entity.Evoucher;

@Repository("iEvoucherDao")
public interface IEvoucherDao extends JpaRepository<Evoucher, String> {

}
