import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestTestModule } from '../../../test.module';
import { BikeDetailComponent } from 'app/entities/bike/bike-detail.component';
import { Bike } from 'app/shared/model/bike.model';

describe('Component Tests', () => {
  describe('Bike Management Detail Component', () => {
    let comp: BikeDetailComponent;
    let fixture: ComponentFixture<BikeDetailComponent>;
    const route = ({ data: of({ bike: new Bike(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestTestModule],
        declarations: [BikeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BikeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BikeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bike on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bike).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
