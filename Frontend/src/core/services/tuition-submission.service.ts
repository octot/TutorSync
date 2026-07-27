import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { API_ENDPOINTS } from '../constants/api.constants';

@Injectable({
    providedIn: 'root'
})
export class TuitionSubmissionService {

    constructor(
        private http: HttpClient
    ) { }

    submit(request: any) {
        return this.http.post<any>(
            `${environment.apiBaseUrl}${API_ENDPOINTS.SUBMIT_TUITION}`,
            request
        );
    }
}