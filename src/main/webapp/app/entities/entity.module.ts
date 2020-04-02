import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'person',
        loadChildren: () => import('./person/person.module').then(m => m.JhipsterTestPersonModule)
      },
      {
        path: 'glasses',
        loadChildren: () => import('./glasses/glasses.module').then(m => m.JhipsterTestGlassesModule)
      },
      {
        path: 'biker',
        loadChildren: () => import('./biker/biker.module').then(m => m.JhipsterTestBikerModule)
      },
      {
        path: 'bike',
        loadChildren: () => import('./bike/bike.module').then(m => m.JhipsterTestBikeModule)
      },
      {
        path: 'driver',
        loadChildren: () => import('./driver/driver.module').then(m => m.JhipsterTestDriverModule)
      },
      {
        path: 'car',
        loadChildren: () => import('./car/car.module').then(m => m.JhipsterTestCarModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhipsterTestEntityModule {}
