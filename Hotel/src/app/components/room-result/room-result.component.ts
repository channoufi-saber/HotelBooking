import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../../service/api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-room-result',
  imports: [CommonModule],
  templateUrl: './room-result.component.html',
  styleUrl: './room-result.component.css'
})
export class RoomResultComponent {
    @Input() roomSearchResults: any[] = []; // Input property for room results
  isAdmin: boolean;

  constructor(private router: Router, private apiService: ApiService) {
    this.isAdmin = this.apiService.isAdmin();
  }

  navigateToEditRoom(roomId: string) {
    this.router.navigate([`/admin/edit-room/${roomId}`]);
  }

  navigateToRoomDetails(roomId: string) {
    this.router.navigate([`/room-details/${roomId}`]);
  }
}
