import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGlasses } from 'app/shared/model/glasses.model';
import { GlassesService } from './glasses.service';
import { GlassesDeleteDialogComponent } from './glasses-delete-dialog.component';

@Component({
  selector: 'jhi-glasses',
  templateUrl: './glasses.component.html'
})
export class GlassesComponent implements OnInit, OnDestroy {
  glasses?: IGlasses[];
  eventSubscriber?: Subscription;

  constructor(protected glassesService: GlassesService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.glassesService.query().subscribe((res: HttpResponse<IGlasses[]>) => (this.glasses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGlasses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGlasses): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGlasses(): void {
    this.eventSubscriber = this.eventManager.subscribe('glassesListModification', () => this.loadAll());
  }

  delete(glasses: IGlasses): void {
    const modalRef = this.modalService.open(GlassesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.glasses = glasses;
  }
}
