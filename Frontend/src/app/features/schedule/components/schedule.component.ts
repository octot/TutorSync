import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-schedule',
    templateUrl: './schedule.component.html',
    imports: [CommonModule, FormsModule],
})
export class ScheduleComponent {

    demoDate: string = '';
    sendReminder = true;
    tutor = true;
    parent = true;

    constructor(private http: HttpClient) { }

    createSchedule() {

        const recipients: string[] = [];

        if (this.tutor) recipients.push("TUTOR");
        if (this.parent) recipients.push("PARENT");

        const payload = {
            tuitionId: "11111111-1111-1111-1111-111111111111", // replace later
            demoDate: this.demoDate,
            recipients: recipients
        };

        this.http.post('http://localhost:8080/api/schedules', payload)
            .subscribe({
                next: () => alert('Schedule created'),
                error: err => console.error(err)
            });
    }
}