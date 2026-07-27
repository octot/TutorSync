import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { API_ENDPOINTS } from '../constants/api.constants';
import { MessageRequest } from '../../shared/models/message-request.model';
import { MessageResponse } from '../../shared/models/message-response.model';
import { SendMessageRequest } from '../../shared/models/send-message-request.model';
import { SendMessageResponse } from '../../shared/models/send-message-response.model';

@Injectable({
    providedIn: 'root'
})
export class MessageService {

    //Dependency injection
    constructor(
        private http: HttpClient
    ) { }
    generateMessages(request: MessageRequest) {
        return this.http.post<MessageResponse>(
            `${environment.apiBaseUrl}${API_ENDPOINTS.GENERATE_MESSAGE}`,
            request
        );

    }
    sendMessages(request: SendMessageRequest) {
        return this.http.post<SendMessageResponse>(
            `${environment.apiBaseUrl}${API_ENDPOINTS.SEND}`,
            request
        );

    }


}