export interface Job {
	id: string;
	file1name: string;
	file2name: string;
	status: string;
	creationDatetime: string;
	startedDatetime: string | null;
	finishedDatetime: string | null;
}

export interface JobTxId {
	jid: string;
	txid: string;
	filename: string;
	lineno: number;
}

export interface JobReportSummary {
	id: string;
	file1name: string;
	file1LineCount: number;
	file1MatchCount: number;
	file1UnmatchCount: number;
	file2name: string;
	file2LineCount: number;
	file2MatchCount: number;
	file2UnmatchCount: number;
	allMatch: boolean;
	fmCount: number;
	mnmmCount: number;
	ftmmhtCount: number;
	ftmmltCount: number;
	srmmlcCount: number;
	fmmCount: number;
}

export interface JobTxBrokenIntegrityReport {
	jobTxId: JobTxId;
	pfName: string;
	txDate: string;
	txAmt: string;
	txNrtive: string;
	txDscpt: string;
	txType: string;
	walletRef: string;
	counterpartLineno: number;
}

export interface JobTxMismatchReport {
	jobTxId: JobTxId;
	isFirstTxid: boolean | null;
	
	pfName: string;
	isPfNameMatched: boolean | null;

	txDate: string;
	isTxDateMatched: boolean | null;

	txAmt: string;
	isTxAmtMatched: boolean | null;

	txNrtive: string;
	isTxNrtiveMatched: boolean | null;

	txDscpt: string;
	isTxDscptMatched: boolean | null;
	
	txType: string;
	isTxTypeMatched: boolean | null;

	walletRef: string;
	isWalletRefMatched: boolean | null;

	txinfoMatched: boolean;
	txeinfoMatched: boolean;
	txsinfoMatched: boolean;
}

export interface ReconciliationReportRetrievalResult {
	brokenIntegrityReports: Array<JobTxBrokenIntegrityReport> | null;
	misMatchSummaryReport: JobReportSummary | null;
	misMatchDetailReports: Array<JobTxMismatchReport> | null;
}