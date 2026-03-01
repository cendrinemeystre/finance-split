import {Component, signal} from '@angular/core';
import {SplitController} from '../service/split-controller.service';
import {NgbTooltip} from '@ng-bootstrap/ng-bootstrap';
import {FsDatePipe} from '../pipe/fs-date-pipe';
import {DatePipe} from '@angular/common';
import {FieldTree, form, FormField} from '@angular/forms/signals';
import {ReactiveFormsModule} from '@angular/forms';
import {FinanceFilter} from '../service/finance-filter';
import {Person} from '../service/person';

@Component({
  selector: 'fs-finance',
  imports: [
    NgbTooltip,
    FsDatePipe,
    DatePipe,
    ReactiveFormsModule,
    FormField,

  ],
  templateUrl: './finance.html',
  styleUrl: './finance.css',
})
export class Finance {
  financeFilterForm: FieldTree<FinanceFilter>;
  protected readonly Person: Person[] = [Person.BOTH, Person.CENDRINE, Person.PATRICK];
  protected readonly defaulFormValue: FinanceFilter = {
    person: Person.BOTH,
    description: ''
  }

  constructor(protected serviceController: SplitController) {
    this.serviceController.findAll();
    this.financeFilterForm = form(signal<FinanceFilter>({
      person: Person.BOTH,
      description: ''
    }))
  }

  public filter(): void {
    this.serviceController.filter({
      person: this.financeFilterForm.person().value(),
      description: this.financeFilterForm.description().value()
    })
  }

  public clear(): void {
    this.financeFilterForm().reset(this.defaulFormValue)
  }

  public remove(id: string): void {
    this.serviceController.remove(id);
  }
}
