import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ApiService } from '../service/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  imports: [CommonModule],
templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {

  user:any=null;
  bookings:any[]=[];
  error:any=null;

  constructor(private apiService:ApiService,private router:Router){}
  ngOnInit(): void {
    this.fetchUserProfile()
  }

  fetchUserProfile(){
    this.apiService.myProfile().subscribe({
      next:(response:any)=>{
        this.user=response.user
        this.apiService.myBookings().subscribe({
          next:(bookingResponse:any)=>{
            this.bookings=bookingResponse.bookings
          },
          error:(err)=>{
            this.showError(
              err?.error?.message || err?.error || 'Error getting my bookings: '+err
            )
          }
        })
      },
      error:(err)=>{
            this.showError(
              err?.error?.message || err?.error || 'Error getting my bookings: '+err
            )
          },
        })
      }

      showError(msg:string){
        this.error=msg
        setTimeout(() => {
          this.error=null
        }, 4000);
      }

      handleLogout(){
        this.apiService.logout()
        this.router.navigate(['/home'])
      }

      handleEditProfile(){
        this.router.navigate(['/edit-profile'])
      }
  }

