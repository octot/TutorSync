import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { API_ENDPOINTS } from '../constants/api.constants'

@Injectable({
  providedIn: 'root'
})
export class TuitionService {

  constructor(
    private http: HttpClient
  ) { }

    //Should remove any and use tutionresponse dto type 
    getTuitionByTuitionId(tuitionId: string) {
        return this.http.get<any>(
            `${environment.apiBaseUrl}${API_ENDPOINTS.GET_TUITION_BY_TUITION_ID}/${encodeURIComponent(tuitionId)}`
        );
    }
    createTuition(request: any) {
        // CREATE_TUITION
        return this.http.post<any>(
            `${environment.apiBaseUrl}${API_ENDPOINTS.CREATE_TUITION}`,
            request
        );

    }

    updateTuition(id: string, request: any) {
        return this.http.put<any>(
            `${environment.apiBaseUrl}${API_ENDPOINTS.UPDATE_TUITION}/${id}`,
            request
        );
    }
}