import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(): boolean {

    const userRole = localStorage.getItem("userRole");
    const searchString2 = "CLIENT";
    // @ts-ignore
    const exists2 = userRole.includes(searchString2);
    if (exists2 ) { ///userRole === "[CLIENT]"
      return true;
    } else {
      alert('Permission denied. You do not have access to client page.');
      this.router.navigate(['/admin-page']);
      return false;
    }
  }

}
