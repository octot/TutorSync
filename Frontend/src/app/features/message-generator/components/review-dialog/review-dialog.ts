import { Component, Inject } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { GeneratedMessage } from '../../../../../shared/models/generated-message.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogRef } from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

import { TuitionSubmissionService } from '../../../../../core/services/tuition-submission.service';
@Component({
  selector: 'app-review-dialog',
  standalone: true,
  imports: [MatDialogModule, MatButtonModule, MatSnackBarModule, MatCardModule, MatIconModule],
  templateUrl: './review-dialog.html',
  styleUrl: './review-dialog.css',
})

//Short form of constructor injection
export class ReviewDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: {
      messages: GeneratedMessage[],
      tuitionPayload: any,
      loadedTuitionRecordId?: string | null;
      isEditMode: boolean
    },

    //Does not require inject because angular can identity through its class type
    private tuitionSubmissionService: TuitionSubmissionService,
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<ReviewDialogComponent>
  ) { }
  isSending = false;
  /** 
    Id from the search bar db lookup for update 
    if id not searched in searchbar 
     1) db look up 
     2) creation of new tution record 
     */
  sendToAdmin() {

    const tuitionId =
      this.data.tuitionPayload?.tuitionId?.trim();

    if (!tuitionId) {
      this.snackBar.open(
        'Tuition ID is required before submitting.',
        'Close',
        {
          duration: 3000,
          horizontalPosition: 'right',
          verticalPosition: 'top'
        }
      );
      return;
    }

    this.isSending = true;
    const request = {
      tuitionId: tuitionId,
      tuitionRequest: this.data.tuitionPayload,
      messageRequest: {
        messages: this.data.messages
      }
    };

    this.tuitionSubmissionService.submit(request)
      .subscribe({
        next: (response) => {
          this.isSending = false;

          this.snackBar.open(
            response.message,
            'Close',
            {
              duration: 4000,
              horizontalPosition: 'right',
              verticalPosition: 'top'
            }
          );

          this.dialogRef.close(true);
        },

        error: (error) => {

          console.error(error);

          this.isSending = false;

          const message =
            error.error?.message ??
            'Unable to submit tuition. Please try again.';

          this.snackBar.open(
            message,
            'Close',
            {
              duration: 4000,
              horizontalPosition: 'right',
              verticalPosition: 'top'
            }
          );
        }
      });
  }

  // Since navigator.clipboard.writeText returns promise have to handle that
  copyMessage(message: string) {
    navigator.clipboard
      .writeText(message)
      .then(() => {
        this.snackBar.open(
          'Message copied successfully.',
          'Close',
          {
            duration: 2000
          }
        );
      })
      .catch(() => {
        this.snackBar.open(
          'Unable to copy the message.',
          'Close',
          {
            duration: 3000
          }
        );
      });
  }
}
