import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import {
  TuitionActivity,
  TuitionActivityType
} from '../../models/tuition-activity.model';
import { DatePipe } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { PAGINATION } from '../../../../../core/constants/PAGINATION ';
import { TuitionActivityService } from '../../services/tuition-activity.service';
import { ActivityChangeList } from './../../../../features/activity-history/components/activity-change-list/activity-change-list'
import { finalize } from 'rxjs/operators';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-activity-history-page',
  imports: [
    DatePipe,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    RouterLink,
    ActivityChangeList],
  templateUrl: './activity-history-page.html',
  styleUrl: './activity-history-page.css',
})
export class ActivityHistoryPage implements OnInit {
  readonly activityTypes: TuitionActivityType[] = [
    'CREATED',
    'UPDATED',
    'MESSAGE_SENT',
    'MESSAGE_SEND_FAILED'
  ];

  activities: TuitionActivity[] = [];
  currentPage = PAGINATION.DEFAULT_PAGE;
  readonly pageSize = PAGINATION.DEFAULT_PAGE_SIZE;
  isLastPage = false;
  isLoading = false;
  errorMessage = '';
  selectedActivityType?: TuitionActivityType;
  constructor(
    private tuitionActivityService: TuitionActivityService,
    //TODO temporary changes need fix for scalablity
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadActivities();
  }

  loadActivities(): void {
    if (this.isLoading) {
      return;
    }
    this.isLoading = true;
    this.errorMessage = '';
    this.tuitionActivityService
      .getRecentActivities(
        this.currentPage,
        this.pageSize,
        this.selectedActivityType
      )
      .pipe( //act as finally block in ts
        finalize(() => {
          this.isLoading = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (response) => {
          this.activities = [
            ...this.activities,
            ...response.content
          ];
          this.isLastPage = response.last;
        },
        error: (error) => {
          this.errorMessage =
            'Unable to load activities. Please try again.';
          console.error(
            'Failed to load tuition activities',
            error
          );
        }
      });
  }
  loadMore(): void {
    if (this.isLastPage) {
      return;
    }

    this.currentPage++;
    this.loadActivities();
  }
  retry(): void {
    this.loadActivities();
  }
  onActivityTypeChange(
    activityType?: TuitionActivityType
  ): void {

    this.selectedActivityType = activityType;

    this.currentPage = PAGINATION.DEFAULT_PAGE;
    this.activities = [];
    this.isLastPage = false;
    this.errorMessage = '';

    this.loadActivities();
  }
}


//Mock data required to display  
