import {Component, inject, signal} from '@angular/core';
import {NgbPagination, NgbTooltip} from '@ng-bootstrap/ng-bootstrap';
import {FsDatePipe} from '../pipe/fs-date-pipe';
import {DatePipe} from '@angular/common';
import {FieldTree, form, FormField} from '@angular/forms/signals';
import {ReactiveFormsModule} from '@angular/forms';
import {FinanceFilter} from '../service/finance-filter';
import {FinanceStore} from '../service/finance.store';
import {PersonStore} from '../service/person.store';
import {PersonNamePipe} from '../pipe/person-name-pipe';

@Component({
  selector: 'fs-finance',
  imports: [
    NgbTooltip,
    FsDatePipe,
    DatePipe,
    ReactiveFormsModule,
    FormField,
    PersonNamePipe,
    NgbPagination
  ],
  templateUrl: './finance.html',
  styleUrl: './finance.css',
})
export class Finance {
  protected readonly financeStore = inject(FinanceStore);
  protected readonly personStore = inject(PersonStore);

  financeFilterForm: FieldTree<FinanceFilter>;
  protected readonly defaultFormValue: FinanceFilter = {
    personId: '',
    description: '',
    amount: 0
  }

  constructor() {
    this.financeFilterForm = form(signal<FinanceFilter>({
      personId: '',
      description: '',
      amount: 0
    }))
  }

  public filter(): void {
    this.financeStore.filter(this.financeFilterForm().value());
  }

  public clear(): void {
    this.financeFilterForm().reset(this.defaultFormValue);
    this.financeStore.clearFilter();
  }

  public remove(id: string): void {
    this.financeStore.delete(id);
  }
}
