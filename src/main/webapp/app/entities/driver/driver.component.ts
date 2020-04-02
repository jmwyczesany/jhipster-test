import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDriver } from 'app/shared/model/driver.model';
import { DriverService } from './driver.service';
import { DriverDeleteDialogComponent } from './driver-delete-dialog.component';

@Component({
  selector: 'jhi-driver',
  templateUrl: './driver.component.html'
})
export class DriverComponent implements OnInit, OnDestroy {
  drivers?: IDriver[];
  eventSubscriber?: Subscription;

  constructor(protected driverService: DriverService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.driverService.query().subscribe((res: HttpResponse<IDriver[]>) => (this.drivers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDrivers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDriver): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDrivers(): void {
    this.eventSubscriber = this.eventManager.subscribe('driverListModification', () => this.loadAll());
  }

  delete(driver: IDriver): void {
    const modalRef = this.modalService.open(DriverDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.driver = driver;
  }
}
