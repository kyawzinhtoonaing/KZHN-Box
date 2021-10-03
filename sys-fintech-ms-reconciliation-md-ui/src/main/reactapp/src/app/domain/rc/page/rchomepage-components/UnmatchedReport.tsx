import { JobTxMismatchReport } from '../../common/models/domainModel';
import * as ownEventHelper from './UnmatchedReportEventHelper';

// Prop Type Declaration
interface Prop {
    misMatchDetailReports: Array<JobTxMismatchReport>;
    unmatchedReportCloseEventHandler?: ownEventHelper.UnmatchedReportCloseEventHandler;
}

export function UnmatchedReport(prop: Prop) {
    return (
        <div className="card">
            <div className="card-header">
                Unmatched report
                <button type="button" className="btn-close position-absolute end-0 me-2" aria-label="Close"
                    onClick={()=>{ownEventHelper.triggerUnmatchedReportCloseEvent(prop.unmatchedReportCloseEventHandler)}}></button>
            </div>
            <div className="card-body">
                <div className="table-responsive">
                    <table className="table table-bordered text-nowrap">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Transaction ID</th>
                                <th>File Name</th>
                                <th>Line No.</th>
                                <th>Profile Name</th>
                                <th>Transaction Date</th>
                                <th>Transaction Amount</th>
                                <th>Transaction Narrative</th>
                                <th>Transaction Description</th>
                                <th>Transaction Type</th>
                                <th>Wallet Reference</th>
                            </tr>
                        </thead>                
                        <tbody>
                            {
                                prop.misMatchDetailReports.map((mmReport, index) => {
                                    return (
                                        <tr key={`${mmReport.jobTxId.txid}${mmReport.jobTxId.filename}${mmReport.jobTxId.lineno}`}>
                                            <td>
                                                {index + 1}
                                            </td>
                                            <td>
                                                {mmReport.isFirstTxid 
                                                    ? mmReport.jobTxId.txid 
                                                    : ''
                                                }
                                            </td>
                                            <td>
                                                {mmReport.jobTxId.filename}
                                            </td>
                                            <td>
                                                {mmReport.jobTxId.lineno}
                                            </td>

                                            <td>
                                                {mmReport.isPfNameMatched 
                                                    ? mmReport.pfName
                                                    : <span className="bg-warning">{mmReport.pfName}</span>
                                                }
                                            </td>
                                            <td>
                                                {mmReport.isTxDateMatched
                                                    ? mmReport.txDate
                                                    : <span className="bg-warning">{mmReport.txDate}</span>
                                                }
                                            </td>
                                            <td>
                                                {mmReport.isTxAmtMatched
                                                    ? mmReport.txAmt
                                                    : <span className="bg-warning">{mmReport.txAmt}</span>
                                                }
                                            </td>
                                            <td>
                                                {mmReport.isTxNrtiveMatched
                                                    ? mmReport.txNrtive
                                                    : <span className="bg-warning">{mmReport.txNrtive}</span>
                                                }
                                            </td>
                                            <td>
                                                {mmReport.isTxDscptMatched
                                                    ? mmReport.txDscpt
                                                    : <span className="bg-warning">{mmReport.txDscpt}</span>
                                                }
                                            </td>
                                            <td>
                                                {mmReport.isTxTypeMatched
                                                    ? mmReport.txType
                                                    : <span className="bg-warning">{mmReport.txType}</span>
                                                }
                                            </td>
                                            <td>
                                                {mmReport.isWalletRefMatched
                                                    ? mmReport.walletRef
                                                    : <span className="bg-warning">{mmReport.walletRef}</span>
                                                }
                                            </td>
                                        </tr>
                                    );
                                })
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}