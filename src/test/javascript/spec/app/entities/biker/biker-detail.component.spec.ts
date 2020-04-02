import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestTestModule } from '../../../test.module';
import { BikerDetailComponent } from 'app/entities/biker/biker-detail.component';
import { Biker } from 'app/shared/model/biker.model';

describe('Component Tests', () => {
  describe('Biker Management Detail Component', () => {
    let comp: BikerDetailComponent;
    let fixture: ComponentFixture<BikerDetailComponent>;
    const route = ({ data: of({ biker: new Biker(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestTestModule],
        declarations: [BikerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BikerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BikerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load biker on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.biker).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
