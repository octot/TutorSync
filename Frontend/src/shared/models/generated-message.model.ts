import { RecipientType } from './recipient-type.enum';

export interface GeneratedMessage {

  recipientType: RecipientType;

  recipientName: string;

  recipientNumber: string;

  message: string;

}