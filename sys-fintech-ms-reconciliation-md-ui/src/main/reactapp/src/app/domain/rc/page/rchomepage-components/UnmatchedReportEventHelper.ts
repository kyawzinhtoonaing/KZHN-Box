import { ComponentEvent } from '../../../../util/ComponentEventUtil';

// Other Type Declarations
export interface UnmatchedReportCloseEvent {
}


// Envent Registry Declarations
const eventRegistry: Array<string> = [
    "UnmatchedReport_ UnmatchedReportClose_Event"
];


// Event Handler Function Type Declarations
export type UnmatchedReportCloseEventHandler = (event: ComponentEvent<UnmatchedReportCloseEvent>) => void;


// Event Trigger Functions
export function triggerUnmatchedReportCloseEvent(eventHandler?: UnmatchedReportCloseEventHandler) {
    let event: ComponentEvent<UnmatchedReportCloseEvent> = {
        eventId: eventRegistry[0],
        payload: { }
    };
    eventHandler?.call(null, event);
}