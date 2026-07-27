import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { TuitionActivity } from '../../models/tuition-activity.model';
import { TuitionActivityService } from '../../services/tuition-activity.service';
import { PAGINATION } from '../../../../../core/constants/PAGINATION ';
import { finalize } from 'rxjs';
import { MatIconModule } from '@angular/material/icon';
import { DatePipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { ActivityChangeList } from './../../../../features/activity-history/components/activity-change-list/activity-change-list'
@Component({
  selector: 'app-tuition-history-page',
  imports: [RouterLink, MatIconModule, DatePipe, MatButtonModule, ActivityChangeList],
  templateUrl: './tuition-history-page.html',
  styleUrl: './tuition-history-page.css',
})
export class TuitionHistoryPage implements OnInit {

  tuitionId = '';

  activities: TuitionActivity[] = [];

  currentPage = PAGINATION.DEFAULT_PAGE;
  readonly pageSize = PAGINATION.DEFAULT_PAGE_SIZE;

  isLastPage = false;
  isLoading = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private tuitionActivityService: TuitionActivityService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {

    this.tuitionId =
      //reads value from /activity-history/:tuitionId
      this.route.snapshot.paramMap.get('tuitionId') ?? '';

    if (this.tuitionId) {
      this.loadActivities();
    }
  }
  loadActivities(): void {
    if (this.isLoading) {
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.tuitionActivityService
      .getActivitiesByTuitionId(
        this.tuitionId,
        this.currentPage,
        this.pageSize
      )
      .pipe(
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
            'Unable to load tuition history. Please try again.';

          console.error(
            'Failed to load tuition history',
            error
          );
        }
      });
  }
  loadMore(): void {
    if (this.isLastPage || this.isLoading) {
      return;
    }

    this.currentPage++;
    this.loadActivities();
  }

}
