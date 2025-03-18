import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { Message } from '../message.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  ///http://localhost:8095/ws
  webSocketEndPointChat: string = 'http://chat.localhost:8086/ws';
  stompClient: Stomp.Client;

  constructor() { }

  public connect() {
    const socket = new SockJS(this.webSocketEndPointChat);
    this.stompClient = Stomp.over(socket);
    return this.stompClient;
  }

  subscribeToPublicMessages(): Observable<Message> {
    return new Observable(observer => {
      this.stompClient.subscribe('/chatroom/public', (message) => {
        if (message && message.body) {
          const parsedMessage: Message = JSON.parse(message.body);
          observer.next(parsedMessage);
        }
      });
    });
  }

  sendPublicMessage(message: Message): void {
    this.stompClient.send('/app/message', {}, JSON.stringify(message));
  }

  public sendPrivateMessage(message: Message, receiverName: string) {
    const destination = `/app/private-message`;
    this.stompClient.send(destination, {}, JSON.stringify({ ...message, receiverName }));
  }

  public sendBroadcastMessage(message: Message) {
    const destination = '/app/message';
    this.stompClient.send(destination, {}, JSON.stringify(message));
  }
}
