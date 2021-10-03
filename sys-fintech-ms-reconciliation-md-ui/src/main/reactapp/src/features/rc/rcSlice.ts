import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState } from '../../app/store';
import { JobTxBrokenIntegrityReport, JobReportSummary, JobTxMismatchReport } from '../../app/domain/rc/common/models/domainModel';

export interface ReconciliationHelperState {
    /* RcHomePage's states */
    csvFile1: any | null;
    csvFile2: any | null;
    // 0 for idle status
    // 1 for in-progress status
    // 2 for finished status
    csvComparisonStatus : 0 | 1 | 2;
    brokenIntegrityReports: Array<JobTxBrokenIntegrityReport> | null;
	misMatchSummaryReport: JobReportSummary | null;
	misMatchDetailReports: Array<JobTxMismatchReport> | null;
    showMisMatchDetailReports: boolean;
}

const initialState : ReconciliationHelperState = {
    csvFile1: null,
    csvFile2: null,
    csvComparisonStatus: 0,
    brokenIntegrityReports: null,
    misMatchSummaryReport: null,
    misMatchDetailReports: null,
    showMisMatchDetailReports: false
};

export const rcSlice = createSlice({
    name: 'rc',
    initialState,
    reducers: {
        setCsvFile1: (state, action: PayloadAction<any>) => {
            state.csvFile1 = action.payload;
        },
        setCsvFile2: (state, action: PayloadAction<any>) => {
            state.csvFile2 = action.payload;
        },
        setCsvComparisonStatus: (state, action: PayloadAction<0 | 1 | 2>) => {
            state.csvComparisonStatus = action.payload;
        },
        setBrokenIntegrityReports: (state, action: PayloadAction<Array<JobTxBrokenIntegrityReport> | null>) => {
            state.brokenIntegrityReports = action.payload;
        },
        setMisMatchSummaryReport: (state, action: PayloadAction<JobReportSummary | null>) => {
            state.misMatchSummaryReport = action.payload;
        },
        setMisMatchDetailReports: (state, action: PayloadAction<Array<JobTxMismatchReport> | null>) => {
            state.misMatchDetailReports = action.payload;
        },
        setShowMisMatchDetailReports: (state, action: PayloadAction<boolean>) => {
            state.showMisMatchDetailReports = action.payload;
        }
    }
});

export const { 
    setCsvFile1, setCsvFile2, setCsvComparisonStatus, setBrokenIntegrityReports,
    setMisMatchSummaryReport, setMisMatchDetailReports, setShowMisMatchDetailReports
} = rcSlice.actions;

// The function below is called a selector and allows us to select a value from
// the state. Selectors can also be defined inline where they're used instead of
// in the slice file. For example: `useSelector((state: RootState) => state.counter.value)`
export const selectCsvFile1 = (state: RootState) => state.rc.csvFile1;
export const selectCsvFile2 = (state: RootState) => state.rc.csvFile2;
export const selectCsvComparisonStatus = (state: RootState) => state.rc.csvComparisonStatus;
export const selectBrokenIntegrityReports = (state: RootState) => state.rc.brokenIntegrityReports;
export const selectMisMatchSummaryReport = (state: RootState) => state.rc.misMatchSummaryReport;
export const selectMisMatchDetailReports = (state: RootState) => state.rc.misMatchDetailReports;
export const selectShowMisMatchDetailReports = (state: RootState) => state.rc.showMisMatchDetailReports;

export default rcSlice.reducer;