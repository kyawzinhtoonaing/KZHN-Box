import * as ownEventHelper from './TxComparisonEventHelper';

// Prop Type Declaration
interface Prop {
    csvFile1: any | null;
    csvFile2: any | null;
    isComparisonInProgress: boolean;
    uploadFile1ChangeEventHandler?: ownEventHelper.UploadFileChangeEventHandler;
    uploadFile2ChangeEventHandler?: ownEventHelper.UploadFileChangeEventHandler;
    compareButtonClickEventHandler?: ownEventHelper.CompareButtonClickEventHandler;
}

export function TxComparison(prop: Prop) {
    return (
        <div className="card">
            <div className="card-header">
                Specify files to compare
            </div>
            <div className="card-body">
                <div className="mb-3">
                    <label htmlFor="uploadFile1" className="form-label">Select file 1</label>
                    <input type="file" className="form-control" id="uploadFile1"
                        onChange={(event) => {ownEventHelper.triggerUploadFile1ChangeEvent(event.target.files, prop.uploadFile1ChangeEventHandler)}} />
                </div>
                <div className="mb-3">
                    <label htmlFor="uploadFile2" className="form-label">Select file 2</label>
                    <input type="file" className="form-control" id="uploadFile2" 
                        onChange={(event) => {ownEventHelper.triggerUploadFile2ChangeEvent(event.target.files, prop.uploadFile2ChangeEventHandler)}} />
                </div>
                <div className="col-auto">
                    <button type="button" className="btn btn-dark"
                        onClick={() => {ownEventHelper.triggerCompareButtonClickEvent(prop.csvFile1, prop.csvFile2, prop.compareButtonClickEventHandler)}}>
                        {prop.isComparisonInProgress &&
                            <span className="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                        }
                        Compare
                    </button>
                </div>
            </div>
        </div>
    );
}