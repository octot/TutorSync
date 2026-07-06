import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { GeneratedMessage } from '../../../../../shared/models/generated-message.model';
import { MessageService } from '../../../../../core/services/message.service';
import { MatDialog } from '@angular/material/dialog';
import { ReviewDialogComponent } from '../review-dialog/review-dialog';
// Angular Material
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';

import { TIME_ZONES } from '../../../../../shared/constants/timezones';
@Component({
  selector: 'app-message-form',
  imports: [ReactiveFormsModule, MatIconModule, MatCardModule, MatFormFieldModule
    , MatInputModule, MatButtonModule, MatProgressSpinnerModule, MatSelectModule
  ],
  templateUrl: './message-form.html',
  styleUrl: './message-form.css',
})

// {"tutionId":"____"}
export class MessageForm {
  generatedMessages: GeneratedMessage[] = [];
  timeZones = TIME_ZONES;
  private readonly dialog = inject(MatDialog);

  constructor(
    private messageService: MessageService
  ) { }

  form = new FormGroup({
    tuitionId: new FormControl(''),

    tutorName: new FormControl(''),
    tutorNumber: new FormControl(''),

    parentNumber: new FormControl(''),
    // parentName: new FormControl(''),

    schedule: new FormGroup({
      startTime: new FormControl(''),
      endTime: new FormControl(''),
      //Hardcoded timezone to indian later can be changed according to different zones
      timeZone: new FormControl('Asia/Kolkata')
    }),

    paymentFromParent: new FormControl(''),
    paymentToTutor: new FormControl(''),

    additionalMessageToTutor: new FormControl(''),
    additionalMessageToParent: new FormControl('')
  });

  generateMessages() {
    this.messageService.generateMessages(
      this.form.value as any //any structure is accepted in "any"
    )
      .subscribe(response => {
        console.log(response);
        this.generatedMessages = response.messages;

        const dialogRef = this.dialog.open(ReviewDialogComponent, {
          width: '800px',
          maxWidth: '95vw',
          disableClose: true,
          data: {
            messages: this.generatedMessages
          }
        });
        dialogRef.afterClosed().subscribe(result => {

          if (result) {

            this.form.reset();

            this.form.patchValue({
              schedule: {
                timeZone: 'Asia/Kolkata'
              }
            });

            this.generatedMessages = [];
          }



        });
      });
  }


}
