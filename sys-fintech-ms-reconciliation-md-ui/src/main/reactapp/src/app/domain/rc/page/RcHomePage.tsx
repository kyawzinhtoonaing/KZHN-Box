import { TxComparison } from './rchomepage-components/TxComparison';
import { TxComparisonResult } from './rchomepage-components/TxComparisonResult';
import { UnmatchedReport } from './rchomepage-components/UnmatchedReport';
import { uploadFile1ChangeEventHandler, uploadFile2ChangeEventHandler, 
    compareButtonClickEventHandler, unmatchedReportButtonClickEventHandler, unmatchedReportCloseEventHandler
} from './RcHomePageEventHelper';
import { useAppSelector } from '../../../../app/hooks';
import { selectCsvFile1, selectCsvFile2, selectCsvComparisonStatus, selectMisMatchSummaryReport, 
    selectMisMatchDetailReports, selectShowMisMatchDetailReports 
} from '../../../../features/rc/rcSlice';

export function RcHomePage() {
    const csvFile1 = useAppSelector(selectCsvFile1);
    const csvFile2 = useAppSelector(selectCsvFile2);
    const isComparisonInProgress = useAppSelector(selectCsvComparisonStatus);
    const misMatchSummaryReport = useAppSelector(selectMisMatchSummaryReport);
    const misMatchDetailReports = useAppSelector(selectMisMatchDetailReports);
    const showMisMatchDetailReports = useAppSelector(selectShowMisMatchDetailReports);

    return (
        <>
            <h4 className="text-start">Reconciliation Helper</h4>
            <hr />

            <TxComparison
                csvFile1={csvFile1}
                csvFile2={csvFile2}
                isComparisonInProgress={isComparisonInProgress === 1}
                uploadFile1ChangeEventHandler={uploadFile1ChangeEventHandler}
                uploadFile2ChangeEventHandler={uploadFile2ChangeEventHandler}
                compareButtonClickEventHandler={compareButtonClickEventHandler} />

            <hr />

            {misMatchSummaryReport &&
                <><TxComparisonResult
                    file1name={misMatchSummaryReport.file1name ? misMatchSummaryReport.file1name : ''}
                    file1LineCount={misMatchSummaryReport.file1LineCount ? misMatchSummaryReport.file1LineCount : 0}
                    file1MatchCount={misMatchSummaryReport.file1MatchCount ? misMatchSummaryReport.file1MatchCount : 0}
                    file1UnmatchCount={misMatchSummaryReport.file1UnmatchCount ? misMatchSummaryReport.file1UnmatchCount : 0}
                    file2name={misMatchSummaryReport.file2name ? misMatchSummaryReport.file2name : ''}
                    file2LineCount={misMatchSummaryReport.file2LineCount ? misMatchSummaryReport.file2LineCount : 0}
                    file2MatchCount={misMatchSummaryReport.file2MatchCount ? misMatchSummaryReport.file2MatchCount : 0}
                    file2UnmatchCount={misMatchSummaryReport.file2UnmatchCount ? misMatchSummaryReport.file2UnmatchCount : 0}
                    unmatchedReportButtonClickEventHandler={unmatchedReportButtonClickEventHandler} />

                <hr /></>
            }

            {(misMatchDetailReports !== null 
            && misMatchDetailReports !== undefined 
            && misMatchDetailReports.length > 0
            && showMisMatchDetailReports) &&
                <><UnmatchedReport 
                    misMatchDetailReports={misMatchDetailReports} 
                    unmatchedReportCloseEventHandler={unmatchedReportCloseEventHandler} />
                    
                <hr /></>
            }
        </>
    );
}