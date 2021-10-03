import axios, { AxiosResponse } from 'axios';
import { Job, ReconciliationReportRetrievalResult, JobTxMismatchReport } from '../common/models/domainModel';

//const HOST_URL = 'http://localhost:8080';

export async function asyncStartComparisonJob(csvFile1: any, csvFile2: any): Promise<ReconciliationReportRetrievalResult | null> {
    let reportContainer: ReconciliationReportRetrievalResult | null = null;
    try {
        const formConfig = {
            headers: {
                'content-type': 'multipart/form-data',
                'Accept': 'application/json'
            }
        };

        // Reconciliation Job Creation
        const reconciliationJobCreationUrl: string = `rc/servicdesk/job`;    
        const rjcFormData = new FormData();
        rjcFormData.append('csvFile1', csvFile1);
        rjcFormData.append('csvFile2', csvFile2); 
        let reconciliationJobCreationResult: AxiosResponse;
        let reconciliationJob: Job;
        reconciliationJobCreationResult = await axios.post(reconciliationJobCreationUrl, rjcFormData, formConfig);
        reconciliationJob = reconciliationJobCreationResult.data;
        
        // Reconciliation Job Execution
        const jobExecutionUrl: string = `rc/batchapp/startjob`;
        const jeFormData = new FormData();
        jeFormData.append('jobId', reconciliationJob.id ? reconciliationJob.id : '');
        await axios.post(jobExecutionUrl, jeFormData, formConfig);

        // Reconciliation Report Retrieval
        const reportRetrievalUrl: string = `rc/servicdesk/reports/${reconciliationJob.id}`;
        const rrConfig = {
            headers: {
                'Accept': 'application/json'
            }
        };
        let reportRetrievalResult: AxiosResponse;
        
        reportRetrievalResult = await axios.get(reportRetrievalUrl, rrConfig);
        reportContainer = reportRetrievalResult.data;

        let misMatchDetailReports = reportContainer?.misMatchDetailReports ? reportContainer.misMatchDetailReports : null;
        prepareMisMatchDetailReportsForHighlighting(reconciliationJob.file1name, reconciliationJob.file2name, misMatchDetailReports);
    } catch(error) {
        console.log(error);
    }
    return reportContainer;
}

function prepareMisMatchDetailReportsForHighlighting(file1name: string, file2name: string,
    misMatchDetailReports: Array<JobTxMismatchReport> | null): void {
    if (misMatchDetailReports === null) return;

    let txid: string = '';
    let firstFilename: string = '';
    let file1MisMatchDetailReports: Array<JobTxMismatchReport> = [];
    let file2MisMatchDetailReports: Array<JobTxMismatchReport> = [];

    misMatchDetailReports.forEach((misMatchDetailReport) => {
        if (txid !== misMatchDetailReport.jobTxId.txid) {
            let itCount: number = 0;
            let len1: number = file1MisMatchDetailReports.length;
            let len2: number = file2MisMatchDetailReports.length;
            if (len1 === len2) {
                itCount = len1;
            } else {
                itCount = len1 > len2 ? len1 : len2;
            }
            for (let i = 0; i < itCount; i++) {
                let file1MisMatchDetailReport = i < len1 ? file1MisMatchDetailReports[i] : null;
                let file2MisMatchDetailReport = i < len2 ? file2MisMatchDetailReports[i] : null;

                // If one side of two files lacks of transaction information.
                if (file1MisMatchDetailReport === null || file2MisMatchDetailReport === null) {
                    if (file1MisMatchDetailReport !== null) {
                        putMismatchHightlightInfo(file1MisMatchDetailReport,
                            true, false, false, false, false, false, false, false);
                    } else if (file2MisMatchDetailReport !== null) {
                        putMismatchHightlightInfo(file2MisMatchDetailReport,
                            true, false, false, false, false, false, false, false);
                    }
                    continue;
                }

                // If both side of two files have transaction information.
                // Check if data field on both sides are matched.
                let isPfNameMatched = file1MisMatchDetailReport.pfName === file2MisMatchDetailReport.pfName;
                let isTxDateMatched = file1MisMatchDetailReport.txDate === file2MisMatchDetailReport.txDate;
                let isTxAmtMatched = file1MisMatchDetailReport.txAmt === file2MisMatchDetailReport.txAmt;
                let isTxNrtiveMatched = file1MisMatchDetailReport.txNrtive === file2MisMatchDetailReport.txNrtive;
                let isTxDscptMatched = file1MisMatchDetailReport.txDscpt === file2MisMatchDetailReport.txDscpt;
                let isTxTypeMatched = file1MisMatchDetailReport.txType === file2MisMatchDetailReport.txType;
                let isWalletRefMatched = file1MisMatchDetailReport.walletRef === file2MisMatchDetailReport.walletRef;
                let isFile1ComeFirst = i === 0 && firstFilename === file1MisMatchDetailReport.jobTxId.filename;
                let isFile2ComeFirst = i === 0 && firstFilename === file2MisMatchDetailReport.jobTxId.filename;
                putMismatchHightlightInfo(file1MisMatchDetailReport,
                    isFile1ComeFirst, isPfNameMatched, isTxDateMatched, isTxAmtMatched, isTxNrtiveMatched, isTxDscptMatched, isTxTypeMatched, isWalletRefMatched);
                putMismatchHightlightInfo(file2MisMatchDetailReport,
                    isFile2ComeFirst, isPfNameMatched, isTxDateMatched, isTxAmtMatched, isTxNrtiveMatched, isTxDscptMatched, isTxTypeMatched, isWalletRefMatched);
            }

            // reset txid and its related data for next transaction ID.
            txid = misMatchDetailReport.jobTxId.txid;
            firstFilename = misMatchDetailReport.jobTxId.filename;
            file1MisMatchDetailReports = firstFilename === file1name ? [misMatchDetailReport] : [];
            file2MisMatchDetailReports = firstFilename === file2name ? [misMatchDetailReport] : [];
        } else {
            let filename = misMatchDetailReport.jobTxId.filename;
            if (filename === file1name) {
                file1MisMatchDetailReports.push(misMatchDetailReport);
            } else if (filename === file2name) {
                file2MisMatchDetailReports.push(misMatchDetailReport);
            }
        }
    });
}

function putMismatchHightlightInfo(mmReport: JobTxMismatchReport,
    isFirstTxid: boolean,
    isPfNameMatched: boolean,
    isTxDateMatched: boolean,
    isTxAmtMatched: boolean,
    isTxNrtiveMatched: boolean,
    isTxDscptMatched: boolean,
    isTxTypeMatched: boolean,
    isWalletRefMatched: boolean): void {
    
    mmReport.isFirstTxid = isFirstTxid;
    mmReport.isPfNameMatched = isPfNameMatched;
    mmReport.isTxDateMatched = isTxDateMatched;
    mmReport.isTxAmtMatched = isTxAmtMatched;
    mmReport.isTxNrtiveMatched = isTxNrtiveMatched;
    mmReport.isTxDscptMatched = isTxDscptMatched;
    mmReport.isTxTypeMatched = isTxTypeMatched;
    mmReport.isWalletRefMatched = isWalletRefMatched;
}