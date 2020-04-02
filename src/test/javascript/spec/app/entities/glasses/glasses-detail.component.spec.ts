import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTestTestModule } from '../../../test.module';
import { GlassesDetailComponent } from 'app/entities/glasses/glasses-detail.component';
import { Glasses } from 'app/shared/model/glasses.model';

describe('Component Tests', () => {
  describe('Glasses Management Detail Component', () => {
    let comp: GlassesDetailComponent;
    let fixture: ComponentFixture<GlassesDetailComponent>;
    const route = ({ data: of({ glasses: new Glasses(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterTestTestModule],
        declarations: [GlassesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GlassesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GlassesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load glasses on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.glasses).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
