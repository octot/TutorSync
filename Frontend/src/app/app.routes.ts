import { Routes } from '@angular/router';
import { GenerateMessagePage } from './features/message-generator/pages/generate-message-page/generate-message-page';
import { ActivityHistoryPage } from './features/activity-history/pages/activity-history-page/activity-history-page';
import { TuitionHistoryPage } from './features/activity-history/pages/tuition-history-page/tuition-history-page';
export const routes: Routes = [
    {
        path: '',
        component: GenerateMessagePage
    },
    {
        path: 'activity-history',
        component: ActivityHistoryPage
    },
    {
        path: 'activity-history/:tuitionId',
        component: TuitionHistoryPage
    }
];
