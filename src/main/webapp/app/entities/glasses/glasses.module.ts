import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterTestSharedModule } from 'app/shared/shared.module';
import { GlassesComponent } from './glasses.component';
import { GlassesDetailComponent } from './glasses-detail.component';
import { GlassesUpdateComponent } from './glasses-update.component';
import { GlassesDeleteDialogComponent } from './glasses-delete-dialog.component';
import { glassesRoute } from './glasses.route';

@NgModule({
  imports: [JhipsterTestSharedModule, RouterModule.forChild(glassesRoute)],
  declarations: [GlassesComponent, GlassesDetailComponent, GlassesUpdateComponent, GlassesDeleteDialogComponent],
  entryComponents: [GlassesDeleteDialogComponent]
})
export class JhipsterTestGlassesModule {}
