import { store } from '../../../store';
import { UploadFileChangeEventHandler, CompareButtonClickEventHandler, 
    UnmatchedReportButtonClickEventHandler
} from './rchomepage-components/TxComparisonEventHelper';
import { UnmatchedReportCloseEventHandler } from './rchomepage-components/UnmatchedReportEventHelper';
import { 
    setCsvFile1, setCsvFile2, setCsvComparisonStatus, setBrokenIntegrityReports,
    setMisMatchSummaryReport, setMisMatchDetailReports, setShowMisMatchDetailReports
} from '../../../../features/rc/rcSlice';
import { asyncStartComparisonJob } from '../service/RcService';
import { ReconciliationReportRetrievalResult } from '../common/models/domainModel';

// Component Events' Handler Functions
export const uploadFile1ChangeEventHandler: UploadFileChangeEventHandler = (event) => {
    store.dispatch(setCsvFile1(event.payload.csvFile));

    if (store.getState().rc.csvComparisonStatus !== 0) {
        resetHomePageState();
    }
    
}

export const uploadFile2ChangeEventHandler: UploadFileChangeEventHandler = (event) => {
    store.dispatch(setCsvFile2(event.payload.csvFile));

    if (store.getState().rc.csvComparisonStatus !== 0) {
        resetHomePageState();
    }
}

export const compareButtonClickEventHandler: CompareButtonClickEventHandler = async (event) => {
    store.dispatch(setCsvComparisonStatus(1));
    store.dispatch(setBrokenIntegrityReports(null));
    store.dispatch(setMisMatchSummaryReport(null));
    store.dispatch(setMisMatchDetailReports(null));
    store.dispatch(setShowMisMatchDetailReports(false));

    let reportContainer: ReconciliationReportRetrievalResult | null;
    reportContainer = await asyncStartComparisonJob(event.payload.csvFile1, event.payload.csvFile2);
    store.dispatch(setCsvComparisonStatus(2));
    if (reportContainer !== null) {    
        store.dispatch(setBrokenIntegrityReports(reportContainer.brokenIntegrityReports));
        store.dispatch(setMisMatchSummaryReport(reportContainer.misMatchSummaryReport));
        store.dispatch(setMisMatchDetailReports(reportContainer.misMatchDetailReports));
    }
}

export const unmatchedReportButtonClickEventHandler: UnmatchedReportButtonClickEventHandler = (event) => {
    store.dispatch(setShowMisMatchDetailReports(true));
}

export const unmatchedReportCloseEventHandler: UnmatchedReportCloseEventHandler = (event) => {
    store.dispatch(setShowMisMatchDetailReports(false));
}

function resetHomePageState() {
    store.dispatch(setCsvComparisonStatus(0));
    store.dispatch(setBrokenIntegrityReports(null));
    store.dispatch(setMisMatchSummaryReport(null));
    store.dispatch(setMisMatchDetailReports(null));
    store.dispatch(setShowMisMatchDetailReports(false))
}