package codigo.codetest.estore.proxy.evmgtsys.service.evoucherdef;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import codigo.codetest.estore.proxy.evmgtsys.entity.EvoucherDef;


@Service("evoucherDefRetrievalByIdService")
public class EvoucherDefRetrievalByIdService {
	
	@Value("${codigo.codetest.evmgtsys.server.url}")
	private String baseUrl;
	
	@Value("${codigo.codetest.evmgtsys.server.evoucherdef-by-id}")
	private String evoucherdefByIdUri;

	public EvoucherDefRetrievalByIdServiceResult serve(EvoucherDefRetrievalByIdServiceParam param) {
		// prcd1 retrieve EvoucherDef from evmgtsys service.
		EvoucherDef evd = this.prcd1RetrieveEvoucherDefFromEvmgtsys(param);
		
		// prcd2 prepare EvoucherDefRetrievalByIdServiceResult, then return it.
		return this.prcd2PrepareResult(evd);
	}
	
	private EvoucherDef prcd1RetrieveEvoucherDefFromEvmgtsys(
			EvoucherDefRetrievalByIdServiceParam param) {
		EvoucherDefRetrievalByIdJsonMsg jsonMsg = WebClient.builder()
			.baseUrl(baseUrl)
			.build()
			.get()
			.uri(evoucherdefByIdUri + "/" + param.getEvoucherDefId())
			.retrieve()
			.bodyToMono(EvoucherDefRetrievalByIdJsonMsg.class)
			.block();
		
		EvoucherDefRetrievalByIdRestControllerResult result = jsonMsg.getData();
		EvoucherDef evoucherDef = result.getEvoucherDef();
		
		return evoucherDef;
	}
	
	private EvoucherDefRetrievalByIdServiceResult prcd2PrepareResult(
			EvoucherDef evd) {
		EvoucherDefRetrievalByIdServiceResult result = new EvoucherDefRetrievalByIdServiceResult();
		result.setEvoucherDef(evd);
		
		return result;
	}
}
