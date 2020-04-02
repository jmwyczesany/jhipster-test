import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGlasses, Glasses } from 'app/shared/model/glasses.model';
import { GlassesService } from './glasses.service';
import { GlassesComponent } from './glasses.component';
import { GlassesDetailComponent } from './glasses-detail.component';
import { GlassesUpdateComponent } from './glasses-update.component';

@Injectable({ providedIn: 'root' })
export class GlassesResolve implements Resolve<IGlasses> {
  constructor(private service: GlassesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGlasses> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((glasses: HttpResponse<Glasses>) => {
          if (glasses.body) {
            return of(glasses.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Glasses());
  }
}

export const glassesRoute: Routes = [
  {
    path: '',
    component: GlassesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Glasses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GlassesDetailComponent,
    resolve: {
      glasses: GlassesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Glasses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GlassesUpdateComponent,
    resolve: {
      glasses: GlassesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Glasses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GlassesUpdateComponent,
    resolve: {
      glasses: GlassesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Glasses'
    },
    canActivate: [UserRouteAccessService]
  }
];
