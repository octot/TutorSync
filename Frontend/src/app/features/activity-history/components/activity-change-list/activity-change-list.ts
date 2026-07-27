import { Component, Input } from '@angular/core';
import { TuitionFieldChange } from '../../models/tuition-activity.model';
import { MatIconModule } from '@angular/material/icon';
@Component({
  selector: 'app-activity-change-list',
  imports: [MatIconModule],
  templateUrl: './activity-change-list.html',
  styleUrl: './activity-change-list.css',
})
export class ActivityChangeList {
  @Input()
  fieldChanges: TuitionFieldChange[] = [];
}
