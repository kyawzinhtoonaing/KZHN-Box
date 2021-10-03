import { ComponentEvent } from '../../../../util/ComponentEventUtil';

// Other Type Declarations
export interface UploadFileChangeEvent {
    csvFile: any;
}

export interface CompareButtonClickEvent {
    csvFile1: any;
    csvFile2: any;
}

export interface UnmatchedReportButtonClickEvent {

}

// Envent Registry Declarations
const eventRegistry: Array<string> = [
    "TxComparison_ UploadFile1Change_Event",
    "TxComparison_ UploadFile2Change_Event",
    "TxComparison_ CompareButtonClick_Event",
    "TxComparison_ UnmatchedReportButtonClick_Event"
];

// Event Handler Function Type Declarations
export type UploadFileChangeEventHandler = (event: ComponentEvent<UploadFileChangeEvent>) => void;
export type CompareButtonClickEventHandler = (event: ComponentEvent<CompareButtonClickEvent>) => void;
export type UnmatchedReportButtonClickEventHandler = (event: ComponentEvent<UnmatchedReportButtonClickEvent>) => void;

// Event Trigger Functions
export function triggerUploadFile1ChangeEvent(files: any | null, eventHandler?: UploadFileChangeEventHandler) {
    if (files == null) return;
    const csvFile: any = files[0];

    let event: ComponentEvent<UploadFileChangeEvent> = {
        eventId: eventRegistry[0],
        payload: { csvFile: csvFile }
    };
    eventHandler?.call(null, event);
}

export function triggerUploadFile2ChangeEvent(files: any | null, eventHandler?: UploadFileChangeEventHandler) {
    if (files == null) return;
    const csvFile: any = files[0];

    let event: ComponentEvent<UploadFileChangeEvent> = {
        eventId: eventRegistry[1],
        payload: { csvFile: csvFile }
    };
    eventHandler?.call(null, event);
}

export function triggerCompareButtonClickEvent(csvFile1: any, csvFile2: any, eventHandler?: CompareButtonClickEventHandler) {
    let event: ComponentEvent<CompareButtonClickEvent> = {
        eventId: eventRegistry[2],
        payload: { 
            csvFile1: csvFile1,
            csvFile2: csvFile2
        }
    };
    eventHandler?.call(null, event);
}

export function triggerUnmatchedReportButtonClickEvent(eventHandler?: UnmatchedReportButtonClickEventHandler) {
    let event: ComponentEvent<UnmatchedReportButtonClickEvent> = {
        eventId: eventRegistry[3],
        payload: {}
    };
    eventHandler?.call(null, event);
}