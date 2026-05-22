import {Component, inject, signal} from '@angular/core';
import {PersonStore} from '../service/person.store';
import {FieldTree, form, FormField, required} from '@angular/forms/signals';
import {ReactiveFormsModule} from '@angular/forms';
import {FinanceStore} from '../service/finance.store';

interface Person {
  name: string,
  total: number
}

@Component({
  selector: 'fs-person-manage',
  imports: [
    FormField,
    ReactiveFormsModule
  ],
  templateUrl: './person-manage.component.html',
  styleUrl: './person-manage.component.css',
})
export class PersonManage {
  public readonly personStore = inject(PersonStore);
  private readonly financeStore = inject(FinanceStore);
  private readonly defaultFormEntry: Person = {
    name: '',
    total: 0
  }
  personForm: FieldTree<Person>;

  constructor() {
    const personEntry = signal<Person>(this.defaultFormEntry)
    this.personForm = form(personEntry, entry => {
      required(entry.name);
    })
  }

  add(): void {
    this.personStore.create(this.personForm().value());
    this.personForm().reset(this.defaultFormEntry)
  }

  remove(id: string): void {
    this.personStore.delete(id);
    this.financeStore.findAll();
  }
}
