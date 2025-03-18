import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Message } from '../message.model';


@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private privateChatMessages: Map<string, Message[]> = new Map<string, Message[]>();
  private privateChatMessagesSubject: BehaviorSubject<Map<string, Message[]>> = new BehaviorSubject<Map<string, Message[]>>(new Map());

  constructor() {}

  public getPrivateChatMessages(): Observable<Map<string, Message[]>> {
    return this.privateChatMessagesSubject.asObservable();
  }

  private updatePrivateChatMessages(messages: Map<string, Message[]>) {
    this.privateChatMessages = messages;
    this.privateChatMessagesSubject.next(messages);
  }

  public handleOutgoingPrivateMessage(message: Message, receiverName: string) {
    const messages = this.privateChatMessages.get(receiverName) || [];
    messages.push(message);
    this.privateChatMessages.set(receiverName, messages);
    this.updatePrivateChatMessages(new Map(this.privateChatMessages));
  }

  public handleIncomingPrivateMessage(message: Message) {
    const senderName = message.senderName;
    const messages = this.privateChatMessages.get(senderName) || [];
    messages.push(message);
    this.privateChatMessages.set(senderName, messages);

    this.updatePrivateChatMessages(new Map(this.privateChatMessages));
  }
}
