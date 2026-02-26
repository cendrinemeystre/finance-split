import {Component, signal} from '@angular/core';
import {SplitEntry} from './split-entry';
import {FieldTree, form, FormField, min, required} from '@angular/forms/signals';
import {ReactiveFormsModule} from '@angular/forms';
import {MatSlideToggle} from '@angular/material/slide-toggle';
import {NgClass} from '@angular/common';
import {ServiceController} from '../service/service-controller';

@Component({
  selector: 'fs-home',
  imports: [
    FormField,
    ReactiveFormsModule,
    MatSlideToggle,
    NgClass
  ],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  splitEntryForm: FieldTree<SplitEntry>;

  constructor(protected serviceController: ServiceController) {
    const splitEntry = signal<SplitEntry>({
      amount: 0,
      person: false,
      description: ''
    })
    this.splitEntryForm = form(splitEntry, entry => {
      required(entry.amount);
      min(entry.amount, 0, {message: 'It must be a positive number'})
    });
    this.serviceController.findAll();
  }

  public add(): void {
    const entry: SplitEntry = {
      person: this.splitEntryForm.person().value(),
      amount: this.splitEntryForm.amount().value(),
      description: this.splitEntryForm.description().value(),
    };
    this.serviceController.add(entry);
  }

  public remove(id: string): void {
    this.serviceController.remove(id);
  }
}
