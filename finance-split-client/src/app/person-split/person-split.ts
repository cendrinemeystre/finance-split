import {Component, inject, signal} from '@angular/core';
import {PersonStore} from '../service/person.store';
import {FieldTree, form, FormField, min, required} from '@angular/forms/signals';
import {SplitEntry} from '../service/split-entry';
import {FinanceStore} from '../service/finance.store';
import {FinanceDataDto} from '../core/core-api';
import {FormsModule} from '@angular/forms';

const DEFAULT_SPLIT_ENTRY_VALUE: SplitEntry = {
  amount: 0,
  personId: '',
  description: ''
}

@Component({
  selector: 'fs-person-split',
  imports: [
    FormField,
    FormsModule
  ],
  templateUrl: './person-split.html',
  styleUrl: './person-split.css',
})
export class PersonSplit {
  protected readonly personStore = inject(PersonStore);
  private readonly financeStore = inject(FinanceStore);
  splitEntryForm: FieldTree<SplitEntry>;
  suggestions: string[] = [
    'Ibby',
    'ichoufe',
    'Galaxus/Digitec',
    'Bstelle',
    'Mobility'
  ]

  constructor() {
    const splitEntry = signal<SplitEntry>({
      amount: 0,
      personId: '',
      description: ''
    })
    this.splitEntryForm = form(splitEntry, entry => {
      required(entry.personId);
      required(entry.amount);
      required(entry.description);
      min(entry.amount, 0, {message: 'It must be a positive number'});
    });
  }

  add(): void {
    const entry: FinanceDataDto = {
      personId: this.splitEntryForm.personId().value(),
      amount: this.splitEntryForm.amount().value(),
      description: this.splitEntryForm.description().value(),
    };
    this.financeStore.create(entry);
  }

  clear(): void  {
    this.splitEntryForm().reset(DEFAULT_SPLIT_ENTRY_VALUE);
  }
}
