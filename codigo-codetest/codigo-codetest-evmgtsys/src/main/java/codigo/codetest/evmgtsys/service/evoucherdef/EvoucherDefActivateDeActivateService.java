package codigo.codetest.evmgtsys.service.evoucherdef;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codigo.codetest.evmgtsys.dao.IEvoucherDefDao;
import codigo.codetest.evmgtsys.domain.entity.EvoucherDef;

@Service("evoucherDefActivateDeActivateService")
public class EvoucherDefActivateDeActivateService {

	private final IEvoucherDefDao evoucherDefDao;
	
	@Autowired
	public EvoucherDefActivateDeActivateService(IEvoucherDefDao evoucherDefDao) {
		this.evoucherDefDao = evoucherDefDao;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void serve(EvoucherDefActivateDeActivateServiceParam parm) throws Exception {
		this.prcd1ChangeActiveness(parm);
	}
	
	private void prcd1ChangeActiveness(EvoucherDefActivateDeActivateServiceParam param) {
		Optional<EvoucherDef> optEvd = this.evoucherDefDao.findById(param.getId());
		if (optEvd.isEmpty()) return;
		
		EvoucherDef evd = optEvd.get();
		evd.setIsactive(param.getIsactive());
		
		this.evoucherDefDao.save(evd);
	}
}
