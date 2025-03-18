import { Component, OnDestroy, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import SockJS from 'sockjs-client';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  private stompClient: Stomp.Client;
  public privateChats: Map<string, any[]> = new Map<string, any[]>();
  public publicChats: any[] = [];
  public tab: string = 'CHATROOM';
  public userData = {
    username: '',
    receivername: '',
    connected: false,
    message: ''
  };

  ngOnInit(): void {
    this.connect();
  }

  connect(): void {
    ///http://localhost:8095/ws
    const socket = new SockJS('http://chat.localhost:8086/ws');
    this.stompClient = Stomp.over(socket);

    this.stompClient.connect({}, this.onConnected.bind(this)); //, this.onError.bind(this));
  }

  onConnected(): void {
    this.userData.connected = true;
    this.stompClient.subscribe('/chatroom/public', this.onMessageReceived.bind(this));
    this.stompClient.subscribe(`/user/${localStorage.getItem('userName')}/private`, this.onPrivateMessage.bind(this));

    this.userJoin();
  }

  userJoin(): void {
    const currentDate = new Date();
    const cValue = formatDate(currentDate, 'yyyy-MM-dd', 'en-US');
    const chatMessage = {
      senderName: localStorage.getItem('userName'),
      date: cValue,
      status: 'JOIN'
    };
    this.stompClient.send('/app/message', {}, JSON.stringify(chatMessage));
  }

  onMessageReceived(payload: any): void {
    const payloadData = JSON.parse(payload.body);

    switch (payloadData.status) {
      case 'JOIN':
        if (!this.privateChats.has(payloadData.senderName)) {
          this.privateChats.set(payloadData.senderName, []);
        }
        break;
      case 'MESSAGE':
        this.publicChats.push(payloadData);
        break;
    }
  }

  onPrivateMessage(payload: any): void {
    const payloadData = JSON.parse(payload.body);

    if (this.privateChats.has(payloadData.senderName)) {
      const chatArray = this.privateChats.get(payloadData.senderName);
      if (chatArray) {
        chatArray.push(payloadData);
      } else {
        console.error("Chat array is undefined for senderName: ", payloadData.senderName);
      }
    } else {
      this.privateChats.set(payloadData.senderName, [payloadData]);
    }
  }

  onError(err: any): void {
    console.log(err);
  }

  handleMessage(event: any): void {
    this.userData.message = event.target.value;
  }

  sendMessage(): void {
    const currentDate = new Date();
    const cValue = formatDate(currentDate, 'yyyy-MM-dd', 'en-US');
    if (this.stompClient) {
      const chatMessage = {
        senderName: localStorage.getItem('userName'),
        receiverName: "CHATROOM",
        message: this.userData.message,
        date: cValue,
        status: 'MESSAGE'
      };
      this.stompClient.send('/app/message', {}, JSON.stringify(chatMessage));
      this.userData.message = '';
    }
  }

  sendPrivateMessage(): void {
    if (this.stompClient) {
      const chatMessage = {
        senderName: localStorage.getItem('userName'),
        receiverName: this.tab,
        message: this.userData.message,
        status: 'MESSAGE'
      };

      if (this.userData.username !== this.tab) {
        const privateChat = this.privateChats.get(this.tab) || [];
        privateChat.push(chatMessage);
        this.privateChats.set(this.tab, privateChat);
      }

      this.stompClient.send('/app/private-message', {}, JSON.stringify(chatMessage));
      this.userData.message = '';
    }
  }

  handleUsername(event: any): void {
    this.userData.username = event.target.value;
  }

  // registerUser(): void {
  //   this.connect();
  // }

  setTab(tab: string) {
    this.tab = tab;
  }

  setReceiver(receiver: string) {
    this.userData.receivername = receiver;
  }
}
