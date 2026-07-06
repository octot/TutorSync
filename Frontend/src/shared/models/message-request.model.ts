import { Schedule } from './schedule.model';

export interface MessageRequest {

    tuitionId: string;

    tutorName: string;
    tutorNumber: string;

    // parentName: string;
    parentNumber: string;

    schedule: Schedule;

    paymentFromParent: number;
    paymentToTutor: number;

    additionalMessageToTutor: string;
    additionalMessageToParent: string;

}