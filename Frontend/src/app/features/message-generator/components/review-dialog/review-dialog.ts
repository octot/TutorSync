import { Component, Inject } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MessageService } from '../../../../../core/services/message.service';
import { GeneratedMessage } from '../../../../../shared/models/generated-message.model';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SendMessageRequest } from '../../../../../shared/models/send-message-request.model';
import { MatDialogRef } from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
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
    private messageService: MessageService,
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
    this.isSending = true;

    if (this.data.loadedTuitionRecordId) {
      this.updateTuitionAndSend(
        this.data.loadedTuitionRecordId,
        'Tuition record updated and messages sent.',
        'Unable to update tuition record. Please try again.',
        'Tuition record was updated, but messages could not be sent.'
      );
      return;
    }

    const tuitionId = this.data.tuitionPayload?.tuitionId?.trim();

    if (!tuitionId) {
      this.isSending = false;
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

    this.handleCreateOrUpdateByTuitionId(tuitionId);
  }

  handleLoadedRecordUpdate() {
    this.updateTuitionAndSend(
      this.data.loadedTuitionRecordId!,
      'Tuition record updated and messages sent.',
      'Unable to update tuition record. Please try again.',
      'Tuition record was updated, but messages could not be sent.'
    );
  }
  private handleCreateOrUpdateByTuitionId(tuitionId: string) {
    this.messageService.getTuitionByTuitionId(tuitionId).subscribe({
      next: (existingRecord) => {
        this.updateTuitionAndSend(
          existingRecord.id,
          'Existing tuition record updated and messages sent. You can use the Tuition ID search above to load existing records faster.',
          'Unable to update the existing tuition record. Please try again.',
          'Tuition record was updated, but messages could not be sent.'
        );
      },

      error: () => {
        this.createTuitionAndSend();
      }
    });
  }
  private createTuitionAndSend() {
    this.messageService.createTuition(this.data.tuitionPayload).subscribe({
      next: () => {
        this.sendMessagesOnly(
          'New tuition record created and messages sent.',
          'Tuition record was created, but messages could not be sent.'
        );
      },

      error: (error) => {
        console.error(error);
        this.isSending = false;

        const message =
          error.error?.message ??
          'Unable to create tuition record. Please try again.';

        this.snackBar.open(message, 'Close', {
          duration: 4000,
          horizontalPosition: 'right',
          verticalPosition: 'top'
        });
      }
    });
  }
  private updateTuitionAndSend(
    recordId: string,
    successMessage: string,
    updateErrorMessage: string,
    sendErrorMessage: string
  ) {
    this.messageService
      .updateTuition(recordId, this.data.tuitionPayload)
      .subscribe({
        next: () => {
          this.sendMessagesOnly(successMessage, sendErrorMessage);
        },

        error: (error) => {
          console.error(error);
          this.isSending = false;

          const message =
            error.error?.message ??
            updateErrorMessage;

          this.snackBar.open(message, 'Close', {
            duration: 4000,
            horizontalPosition: 'right',
            verticalPosition: 'top'
          });
        }
      });
  }
  private sendMessagesOnly(successMessage: string, sendErrorMessage: string) {
    const request: SendMessageRequest = {
      messages: this.data.messages
    };

    this.messageService.sendMessages(request).subscribe({
      next: () => {
        this.isSending = false;

        this.snackBar.open(
          successMessage,
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
          sendErrorMessage;

        this.snackBar.open(message, 'Close', {
          duration: 4000,
          horizontalPosition: 'right',
          verticalPosition: 'top'
        });
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
