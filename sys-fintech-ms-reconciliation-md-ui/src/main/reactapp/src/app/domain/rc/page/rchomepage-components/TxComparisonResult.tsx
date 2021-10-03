import * as ownEventHelper from './TxComparisonEventHelper';

// Prop Type Declaration
interface Prop {
    file1name: string;
    file1LineCount: number;
    file1MatchCount: number;
    file1UnmatchCount: number;
    file2name: string;
    file2LineCount: number;
    file2MatchCount: number;
    file2UnmatchCount: number;
    unmatchedReportButtonClickEventHandler?: ownEventHelper.UnmatchedReportButtonClickEventHandler;
}

export function TxComparisonResult(prop: Prop) {
    return (
        <div className="card">
            <div className="card-header">
                Comparison results
            </div>
            <div className="card-body">
                <div className="row">
                    <div className="col-sm-6">
                        {/*<!-- CSV File1 Summary -->*/}
                        <div className="card">
                            <div className="card-body">
                                <h3 className="card-title">{prop.file1name}</h3>
                                <div className="container">
                                    <div className="row">
                                        <div className="col">
                                            Total Records:
                                        </div>
                                        <div className="col">
                                            {prop.file1LineCount}
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col">
                                            Matching Records:
                                        </div>
                                        <div className="col">
                                            {prop.file1MatchCount}
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col">
                                            Unmatched Records:
                                        </div>
                                        <div className="col">
                                            {prop.file1UnmatchCount}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="col-sm-6">
                        {/*<!-- CSV File2 Summary -->*/}
                        <div className="card">
                            <div className="card-body">
                                <h3 className="card-title">{prop.file2name}</h3>
                                <div className="container">
                                    <div className="row">
                                        <div className="col">
                                            Total Records:
                                        </div>
                                        <div className="col">
                                            {prop.file2LineCount}
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col">
                                            Matching Records:
                                        </div>
                                        <div className="col">
                                            {prop.file2MatchCount}
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col">
                                            Unmatched Records:
                                        </div>
                                        <div className="col">
                                            {prop.file2UnmatchCount}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col-auto mt-3">
                    <button type="button" className="btn btn-dark"
                        onClick={()=>{ownEventHelper.triggerUnmatchedReportButtonClickEvent(prop.unmatchedReportButtonClickEventHandler)}}>
                        Unmatched Report
                    </button>
                </div>
            </div>
        </div>
    );
}