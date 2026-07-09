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
import { FormsModule } from '@angular/forms';
import { TIME_ZONES } from '../../../../../shared/constants/timezones';
@Component({
  selector: 'app-message-form',
  imports: [ReactiveFormsModule, MatIconModule, MatCardModule, MatFormFieldModule
    , MatInputModule, MatButtonModule, MatProgressSpinnerModule, MatSelectModule, FormsModule
  ],
  templateUrl: './message-form.html',
  styleUrl: './message-form.css',
})

// {"tutionId":"____"}
export class MessageForm {
  generatedMessages: GeneratedMessage[] = [];
  timeZones = TIME_ZONES;
  /**
   * V2 persistence state
   * - null => create mode
   * - has value => loaded existing tuition record / edit mode
   */
  loadedTuitionRecordId: string | null = null;
  isEditMode = false;


  /**
   * Separate lookup input for "Load Existing Tuition" section
   */
  lookupTuitionId = '';


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
  /**
    * Call this when an existing tuition record is fetched successfully
    * from backend and patched into the form.
    */
  setLoadedTuitionState(recordId: string) {
    this.loadedTuitionRecordId = recordId;
    this.isEditMode = true;
  }
  /**
   * Call this when:
   * - starting a brand new tuition
   * - clearing the form
   * - resetting after final send flow
   */
  resetLoadedTuitionState() {
    this.loadedTuitionRecordId = null;
    this.isEditMode = false;
  }

  loadTuition() {
    const tuitionId = this.lookupTuitionId.trim();

    if (!tuitionId) {
      return;
    }
    this.messageService.getTuitionByTuitionId(tuitionId)
      .subscribe(record => {
        this.form.patchValue({
          tuitionId: record.tuitionId,
          tutorName: record.tutorName,
          tutorNumber: record.tutorNumber,
          parentNumber: record.parentNumber,
          paymentFromParent: record.paymentFromParent,
          paymentToTutor: record.paymentToTutor,
          additionalMessageToTutor: record.additionalMessageToTutor,
          additionalMessageToParent: record.additionalMessageToParent,
          schedule: {
            startTime: record.startTime,
            endTime: record.endTime,
            timeZone: record.timeZone
          }
        });
        this.setLoadedTuitionState(record.id);
      });
  }
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
            messages: this.generatedMessages,
            tuitionPayload: this.form.getRawValue(),
            loadedTuitionRecordId: this.loadedTuitionRecordId,
            isEditMode: this.isEditMode
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
            // reset persistence state after full flow
            this.resetLoadedTuitionState();
            this.lookupTuitionId = '';
          }
        });
      });
  }
}
