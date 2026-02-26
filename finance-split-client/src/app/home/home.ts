import {Component, signal} from '@angular/core';
import {SplitEntry} from './split-entry';
import {FieldTree, form, FormField, min, required} from '@angular/forms/signals';
import {ReactiveFormsModule} from '@angular/forms';
import {MatSlideToggle} from '@angular/material/slide-toggle';
import {NgClass} from '@angular/common';

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
  splitEntry: SplitEntry[] = [];

  constructor() {
    const splitEntry = signal<SplitEntry>({
      amount: 0,
      cendrine: false,
      description: ''
    })
    this.splitEntryForm = form(splitEntry, entry => {
      required(entry.amount);
      min(entry.amount, 0, {message: 'It must be a positive number'})
    });

  }
}
