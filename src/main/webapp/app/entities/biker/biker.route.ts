import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBiker, Biker } from 'app/shared/model/biker.model';
import { BikerService } from './biker.service';
import { BikerComponent } from './biker.component';
import { BikerDetailComponent } from './biker-detail.component';
import { BikerUpdateComponent } from './biker-update.component';

@Injectable({ providedIn: 'root' })
export class BikerResolve implements Resolve<IBiker> {
  constructor(private service: BikerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBiker> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((biker: HttpResponse<Biker>) => {
          if (biker.body) {
            return of(biker.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Biker());
  }
}

export const bikerRoute: Routes = [
  {
    path: '',
    component: BikerComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Bikers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BikerDetailComponent,
    resolve: {
      biker: BikerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Bikers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BikerUpdateComponent,
    resolve: {
      biker: BikerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Bikers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BikerUpdateComponent,
    resolve: {
      biker: BikerResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Bikers'
    },
    canActivate: [UserRouteAccessService]
  }
];
