import { Component } from '@angular/core';
import { User } from '../user';
import { Router } from '@angular/router';
import { LoginUserService } from '../login-user.service';
import { LoginResponse } from '../login-response';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  user: User = new User();

  constructor(private loginuserservice: LoginUserService, private router:Router){

  }

  ngOnInit(): void{

  }

  onSubmit(): void {
  }

  userLogin(){
    console.log(this.user);
    this.loginuserservice.loginUser(this.user).subscribe((data:LoginResponse) => {
    console.log(data);
    const roles = data.user.roles;
    console.log(roles);
    console.log("Te impusc");
    const searchString1 = "ADMIN";
    // @ts-ignore
      const exists = roles.includes(searchString1);
      const searchString2 = "CLIENT";
      // @ts-ignore
      const exists2 = roles.includes(searchString2);
    if(exists){  ///roles === 'ADMIN'
      this.router.navigate(['/admin-page']);
      alert("Login as ADMIN Successfully");
    }
    else if(exists2 ){ ///roles ==='CLIENT'
      this.router.navigate(['/client-page']);
      alert("Login as CLIENT Successfully");
    }
    else{
      alert("Unknown role. Please contact an admin.");
    }
    },error=>{
      alert("Sorry, please enter correct email and password");
      this.router.navigate(['/login']);
    });

  }

}
