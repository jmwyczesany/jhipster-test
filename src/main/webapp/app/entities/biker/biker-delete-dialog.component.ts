import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBiker } from 'app/shared/model/biker.model';
import { BikerService } from './biker.service';

@Component({
  templateUrl: './biker-delete-dialog.component.html'
})
export class BikerDeleteDialogComponent {
  biker?: IBiker;

  constructor(protected bikerService: BikerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bikerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bikerListModification');
      this.activeModal.close();
    });
  }
}
