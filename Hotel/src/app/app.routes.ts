import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { RoomResultComponent } from './components/room-result/room-result.component';
import { ProfileComponent } from './profile/profile.component';
import { EditprofileComponent } from './editprofile/editprofile.component';
import { GuardService } from './service/guard.service';

export const routes: Routes = [
  {path:'login',component:LoginComponent},
  {path:'register',component:RegisterComponent},
  {path:'home',component:RoomResultComponent},
  {path:'profile',component:ProfileComponent, canActivate:[GuardService]},
  {path:'edit-profile',component:EditprofileComponent,canActivate:[GuardService]},
];
