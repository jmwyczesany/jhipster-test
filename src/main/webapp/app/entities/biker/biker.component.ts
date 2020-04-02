import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBiker } from 'app/shared/model/biker.model';
import { BikerService } from './biker.service';
import { BikerDeleteDialogComponent } from './biker-delete-dialog.component';

@Component({
  selector: 'jhi-biker',
  templateUrl: './biker.component.html'
})
export class BikerComponent implements OnInit, OnDestroy {
  bikers?: IBiker[];
  eventSubscriber?: Subscription;

  constructor(protected bikerService: BikerService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.bikerService.query().subscribe((res: HttpResponse<IBiker[]>) => (this.bikers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBikers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBiker): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBikers(): void {
    this.eventSubscriber = this.eventManager.subscribe('bikerListModification', () => this.loadAll());
  }

  delete(biker: IBiker): void {
    const modalRef = this.modalService.open(BikerDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.biker = biker;
  }
}
