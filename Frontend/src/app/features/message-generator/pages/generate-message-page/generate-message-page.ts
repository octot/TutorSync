import { Component } from '@angular/core';
import { MessageForm } from '../../components/message-form/message-form';

@Component({
  selector: 'app-generate-message-page',
  imports: [MessageForm],
  templateUrl: './generate-message-page.html',
  styleUrl: './generate-message-page.css',
})
export class GenerateMessagePage {}
