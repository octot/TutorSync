//Frontend model in activity 

export interface TuitionFieldChange {
    field: string;
    oldValue: string;
    newValue: string;
}

export interface TuitionActivity {
    id: string;
    tuitionId: string;
    activityType: TuitionActivityType;
    performedAt: Date;
    remarks: string;


    fieldChanges: TuitionFieldChange[];
}



export type TuitionActivityType =
    | 'CREATED'
    | 'UPDATED'
    | 'MESSAGE_SENT'
    | 'MESSAGE_SEND_FAILED';