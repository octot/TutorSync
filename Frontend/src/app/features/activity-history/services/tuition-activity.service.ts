import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';
import { API_ENDPOINTS } from '../../../../core/constants/api.constants';
import { Observable } from 'rxjs';
;
import { TuitionActivity, TuitionActivityType } from '../models/tuition-activity.model';
import { PageResponse } from '../models/page-response.model';
@Injectable({
    providedIn: 'root'
})
export class TuitionActivityService {

    constructor(
        private http: HttpClient
    ) { }

    getRecentActivities(
        page: number,
        size: number,
        activityType?: TuitionActivityType  //Optional
    ): Observable<PageResponse<TuitionActivity>> {

        let params = new HttpParams()
            .set('page', page)
            .set('size', size);

        if (activityType) {
            params = params.set(
                'activityType',
                activityType
            );
        }

        return this.http.get<PageResponse<TuitionActivity>>(
            `${environment.apiBaseUrl}${API_ENDPOINTS.TUITION_ACTIVITIES}`,
            { params }
        );
    }
    getActivitiesByTuitionId(
        tuitionId: string,
        page: number,
        size: number
    ): Observable<PageResponse<TuitionActivity>> {

        const params = new HttpParams()
            .set('page', page)
            .set('size', size);

        return this.http.get<PageResponse<TuitionActivity>>(
            `${environment.apiBaseUrl}${API_ENDPOINTS.TUITION_ACTIVITIES}/${tuitionId}`,
            { params }
        );
    }
}