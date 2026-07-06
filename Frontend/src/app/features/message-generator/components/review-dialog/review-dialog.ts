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

export class ReviewDialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: { messages: GeneratedMessage[] }, //Short form of constructor injection

    //Does not require inject because angular can identity through its class type
    private messageService: MessageService,
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<ReviewDialogComponent>
  ) { }
  isSending = false;
  sendToAdmin() {

    this.isSending = true;
    const request: SendMessageRequest = {
      messages: this.data.messages
    };

    this.messageService.sendMessages(request)
      .subscribe({
        next: (response) => {
          console.log(response);
          this.isSending = false;
          this.snackBar.open(
            response.message,
            'Close',
            {
              duration: 3000,
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
            'Unable to send messages. Please try again.';

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
