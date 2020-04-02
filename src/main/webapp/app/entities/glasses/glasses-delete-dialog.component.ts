import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGlasses } from 'app/shared/model/glasses.model';
import { GlassesService } from './glasses.service';

@Component({
  templateUrl: './glasses-delete-dialog.component.html'
})
export class GlassesDeleteDialogComponent {
  glasses?: IGlasses;

  constructor(protected glassesService: GlassesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.glassesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('glassesListModification');
      this.activeModal.close();
    });
  }
}
